package parts;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.Random;

/**
 * Created by filippo on 04/09/16.
 *
 */
abstract public class Brick  {


    public static final int DEF_CRACK_DEPTH = 1;
    public static final int DEF_STEPS = 35;


    public static final int UP_IMPACT = 100;
    public static final int DOWN_IMPACT = 200;
    public static final int LEFT_IMPACT = 300;
    public static final int RIGHT_IMPACT = 400;



    public class Crack{

        private static final int CRACK_SECTIONS = 3;
        private static final double JUMP_PROBABILITY = 0.7;

        public static final int LEFT = 10;
        public static final int RIGHT = 20;
        public static final int UP = 30;
        public static final int DOWN = 40;
        public static final int VERTICAL = 100;
        public static final int HORIZONTAL = 200;



        private GeneralPath crack;

        private int crackDepth;
        private int steps;

        /**
         * Crack will create and instance of crack and insert the passed parameter into the local variables
         * @param crackDepth
         * @param steps
         */
        public Crack(int crackDepth, int steps){
            crack = new GeneralPath();
            this.crackDepth = crackDepth;
            this.steps = steps;

        }

        /**
         * draw will return the crack
         * @return crack
         */
        public GeneralPath draw(){
            return crack;
        }

        /**
         * reset will reset the crack back to zero
         */
        public void reset(){
            crack.reset();
        }

        /**
         * makeCrack will draw the crack lines on the brick with a crack function
         * @param point position/point of impact
         * @param direction the direction of impact
         */
        protected void makeCrack(Point2D point, int direction){
            Rectangle bounds = Brick.this.brickFace.getBounds();

            Point impact = new Point((int)point.getX(),(int)point.getY());
            Point start = new Point();
            Point end = new Point();

            switch(direction){
                case LEFT:
                    start.setLocation(bounds.x + bounds.width, bounds.y);
                    end.setLocation(bounds.x + bounds.width, bounds.y + bounds.height);
                    Point tmp = makeRandomPoint(start,end,VERTICAL);
                    drawCrack(impact,tmp);

                    break;
                case RIGHT:
                    start.setLocation(bounds.getLocation());
                    end.setLocation(bounds.x, bounds.y + bounds.height);
                    tmp = makeRandomPoint(start,end,VERTICAL);
                    drawCrack(impact,tmp);

                    break;
                case UP:
                    start.setLocation(bounds.x, bounds.y + bounds.height);
                    end.setLocation(bounds.x + bounds.width, bounds.y + bounds.height);
                    tmp = makeRandomPoint(start,end,HORIZONTAL);
                    drawCrack(impact,tmp);
                    break;
                case DOWN:
                    start.setLocation(bounds.getLocation());
                    end.setLocation(bounds.x + bounds.width, bounds.y);
                    tmp = makeRandomPoint(start,end,HORIZONTAL);
                    drawCrack(impact,tmp);
                    break;

            }
        }

        /**
         * drawCrack will draw the cracked line/s
         * @param start point of where the ball touches the brick
         * @param end point of where the crack will end
         */
        protected void drawCrack(Point start, Point end){

            GeneralPath path = new GeneralPath();


            path.moveTo(start.x,start.y);

            double w = (end.x - start.x) / (double)steps;
            double h = (end.y - start.y) / (double)steps;

            int bound = crackDepth;
            int jump  = bound * 5;

            double x,y;

            for(int i = 1; i < steps;i++){

                x = (i * w) + start.x;
                y = (i * h) + start.y + randomInBounds(bound);

                if(inMiddle(i,CRACK_SECTIONS,steps))
                    y += jumps(jump,JUMP_PROBABILITY);

                path.lineTo(x,y);

            }

            path.lineTo(end.x,end.y);
            crack.append(path,true);
        }

        /**
         * randomInBounds will randomize the in bounds
         * @param bound bound
         * @return bound
         */
        private int randomInBounds(int bound){
            int n = (bound * 2) + 1;
            return rnd.nextInt(n) - bound;
        }

        /**
         * inMiddle returns the true if the crack is in the middle
         * @param i
         * @param steps
         * @param divisions
         * @return true if its in the middle
         */
        private boolean inMiddle(int i,int steps,int divisions){
            int low = (steps / divisions);
            int up = low * (divisions - 1);

            return  (i > low) && (i < up);
        }

        /**
         * jumps returns the in bounds
         * @param bound the bound value
         * @param probability the probability of the crack jump
         * @return passes to the randomInBounds method or returns zero
         */
        private int jumps(int bound,double probability){
            if(rnd.nextDouble() > probability)
                return randomInBounds(bound);
            return  0;

        }

        /**
         * makeRandomPoint sets random points at the end of the crack
         * @param from initial crack point
         * @param to target crack end point
         * @param direction direction of crack
         * @return randomly generated end crack point
         */
        private Point makeRandomPoint(Point from,Point to, int direction){

            Point out = new Point();
            int pos;

            switch(direction){
                case HORIZONTAL:
                    pos = rnd.nextInt(to.x - from.x) + from.x;
                    out.setLocation(pos,to.y);
                    break;
                case VERTICAL:
                    pos = rnd.nextInt(to.y - from.y) + from.y;
                    out.setLocation(to.x,pos);
                    break;
            }
            return out;
        }

    }

    private static Random rnd;

    private String name;
    protected Shape brickFace;

    private Color border;
    private Color inner;

    private int fullStrength;
    private int strength;

    private boolean broken;

    protected abstract Shape makeBrickFace(Point pos,Dimension size);

    /**
     * Brick will call to make the brickFace
     * @param name brick name
     * @param pos brick position
     * @param size brick size
     * @param border brick border color
     * @param inner brick color
     * @param strength brick's strength
     */
    public Brick(String name, Point pos,Dimension size,Color border,Color inner,int strength){
        rnd = new Random();
        broken = false;
        this.name = name;
        brickFace = makeBrickFace(pos,size);
        this.border = border;
        this.inner = inner;
        this.fullStrength = this.strength = strength;

    }

    /**
     * setImpact return the true or false on whether the brick is broken or not
     * @param point
     * @param dir
     * @return
     */
    public  boolean setImpact(Point2D point , int dir){
        if(broken)
            return false;
        impact();
        return  broken;
    }

    /**
     * getBrick return the shape of the brick
     * @return
     */
    public abstract Shape getBrick();

    /**
     * getBorderColor will return the brick's border color
     * @return the border color
     */
    public Color getBorderColor(){
        return  border;
    }

    /**
     * getInnerColor return the brick's color
     * @return the inner color of the brick
     */
    public Color getInnerColor(){
        return inner;
    }

    /**
     * findImpact checks for the impact
     * @param b Ball b
     * @return impact value
     */
    public final int findImpact(Ball b){
        if(broken)
            return 0;
        int out  = 0;
        if(brickFace.contains(b.right))
            out = LEFT_IMPACT;
        else if(brickFace.contains(b.left))
            out = RIGHT_IMPACT;
        else if(brickFace.contains(b.up))
            out = DOWN_IMPACT;
        else if(brickFace.contains(b.down))
            out = UP_IMPACT;
        return out;
    }

    /**
     * isBroken returns broken status if called
     * @return broken status
     */
    public final boolean isBroken(){
        return broken;
    }

    /**
     * repair will repair the brick to the original brick's strength and set the it back to a false broken status
     */
    public void repair() {
        broken = false;
        strength = fullStrength;
    }

    /**
     * impact will decrease the strength of the brick when called
     */
    public void impact(){
        strength--;
        broken = (strength == 0);
    }


}




