package parts;


import java.awt.*;



public class ClayBrick extends Brick {

    private static final String NAME = "Clay Brick";
    private static final Color DEF_INNER = new Color(178, 34, 34).darker();
    private static final Color DEF_BORDER = Color.GRAY;
    private static final int CLAY_STRENGTH = 1;

    /**
     * ClayBrick creates the Clay brick when called
     * @param point location of brick placement
     * @param size dimension of the brick
     */
    public ClayBrick(Point point, Dimension size){
        super(NAME,point,size,DEF_BORDER,DEF_INNER,CLAY_STRENGTH);
    }

    /**
     * makeBrickFace will make the Clay brick
     * @param pos location of brick placement
     * @param size dimension of the brick
     * @return
     */
    @Override
    protected Shape makeBrickFace(Point pos, Dimension size) {
        return new Rectangle(pos,size);
    }

    /**
     * getBrick return the brick face from the super class Brick
     * @return the brickFace
     */
    @Override
    public Shape getBrick() {
        return super.brickFace;
    }


}