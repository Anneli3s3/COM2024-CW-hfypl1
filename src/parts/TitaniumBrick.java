package parts;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Random;

public class TitaniumBrick extends Brick {

    private static final String NAME = "Titanium Brick";
    private static final Color DEF_INNER = new Color(135, 134, 129);
    private static final Color DEF_BORDER = Color.WHITE;
    private static final int TITANIUM_STRENGTH = 1;
    private static final double TITANIUM_PROBABILITY = 0.3;

    private Random rnd;
    private Shape brickFace;

    /**
     * TitaniumBrick creates the titanium brick
     * @param point the location of brick placement
     * @param size the dimension of the brick
     */
    public TitaniumBrick(Point point, Dimension size){
        super(NAME,point,size,DEF_BORDER,DEF_INNER,TITANIUM_STRENGTH);
        rnd = new Random();
        brickFace = super.brickFace;
    }

    /**
     * makeBrickFace makes the brick's face
     * @param pos the location of the brick placement
     * @param size the dimension of the brick
     * @return shape of the brick
     */
    @Override
    protected Shape makeBrickFace(Point pos, Dimension size) {
        return new Rectangle(pos,size);
    }

    /**
     * getBrick return the brick face
     * @return brickFace
     */
    @Override
    public Shape getBrick() {
        return brickFace;
    }

    /**
     * setImpact checks if its broken and updates the brick
     * @param point location of impact on the brick
     * @param dir direction of impact
     * @return setImpact status
     */
    public  boolean setImpact(Point2D point , int dir){
        if(super.isBroken())
            return false;
        impact();
        return  super.isBroken();
    }

    /**
     * impact randomize a value and compare with the Titanium's break probability. If the impact is less than the steel breaking probability it will not break the brick.
     */
    public void impact(){
        if(rnd.nextDouble() < TITANIUM_PROBABILITY){
            super.impact();
        }
    }

}