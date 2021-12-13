package parts;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;


public class CementBrick extends Brick {


    private static final String NAME = "Cement Brick";
    private static final Color DEF_INNER = new Color(147, 147, 147);
    private static final Color DEF_BORDER = new Color(217, 199, 175);
    private static final int CEMENT_STRENGTH = 2;

    private Crack crack;
    private Shape brickFace;

    /**
     * CementBrick will create the cement brick when called
     * @param point location of brick placement
     * @param size dimension of brick
     */
    public CementBrick(Point point, Dimension size){
        super(NAME,point,size,DEF_BORDER,DEF_INNER,CEMENT_STRENGTH);
        crack = new Crack(DEF_CRACK_DEPTH,DEF_STEPS);
        brickFace = super.brickFace;
    }

    /**
     * makeBrickFace will created the rectangular shape of the brick
     * @param pos position of the brick
     * @param size size of the brick
     * @return the shape of the brick
     */
    @Override
    protected Shape makeBrickFace(Point pos, Dimension size) {
        return new Rectangle(pos,size);
    }

    /**
     * setImpact will check if its broken and update the brick
     * @param point the position of the brick
     * @param dir direction of impact
     * @return boolean status
     */
    @Override
    public boolean setImpact(Point2D point, int dir) {
        if(super.isBroken())
            return false;
        super.impact();
        if(!super.isBroken()){
            crack.makeCrack(point,dir);
            updateBrick();
            return false;
        }
        return true;
    }

    /**
     * getBrick gets the brickFace
     * @return brickFace
     */
    @Override
    public Shape getBrick() {
        return brickFace;
    }

    /**
     * updateBrick updates the brick if its not broken
     */
    private void updateBrick(){
        if(!super.isBroken()){
            GeneralPath gp = crack.draw();
            gp.append(super.brickFace,false);
            brickFace = gp;
        }
    }

    /**
     * repair will repair the brick and reset the brick's look
     */
    public void repair(){
        super.repair();
        crack.reset();
        brickFace = super.brickFace;
    }
}