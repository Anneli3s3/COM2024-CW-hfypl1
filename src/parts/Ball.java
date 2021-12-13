package parts;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.RectangularShape;

abstract public class Ball {

    private Shape ballFace;

    private Point2D center;

    public Point2D up;
    public Point2D down;
    public Point2D left;
    public Point2D right;

    private Color border;
    private Color inner;

    private static int xSpeed;
    public static int ySpeed;

    protected abstract Shape makeBall(Point2D center,int radiusA,int radiusB);

    /**
     * Ball will create the ball when call by passing to ballFace. It will also set the initial speed of 0 and the spawn location
     * @param center
     * @param radiusA
     * @param radiusB
     * @param inner
     * @param border
     */
    public Ball(Point2D center,int radiusA,int radiusB,Color inner,Color border){
        this.center = center;

        up = new Point2D.Double();
        down = new Point2D.Double();
        left = new Point2D.Double();
        right = new Point2D.Double();

        up.setLocation(center.getX(),center.getY()-(radiusB / 2));
        down.setLocation(center.getX(),center.getY()+(radiusB / 2));

        left.setLocation(center.getX()-(radiusA /2),center.getY());
        right.setLocation(center.getX()+(radiusA /2),center.getY());


        ballFace = makeBall(center,radiusA,radiusB);

        this.border = border;
        this.inner  = inner;
        xSpeed = 0;
        ySpeed = 0;
    }

    /**
     * move would move the ball along the frame
     */
    public void move(){
        RectangularShape tmp = (RectangularShape) ballFace;
        center.setLocation((center.getX() + xSpeed),(center.getY() + ySpeed));
        double w = tmp.getWidth();
        double h = tmp.getHeight();

        tmp.setFrame((center.getX() -(w / 2)),(center.getY() - (h / 2)),w,h);
        setPoints(w,h);

        ballFace = tmp;
    }

    /**
     * setSpeed sets the speed of x and y
     * @param x x speed
     * @param y y speed
     */
    public void setSpeed(int x,int y){
        xSpeed = x;
        ySpeed = y;
    }

    /**
     * setXSpeed sets x speed
     * @param s x speed
     */
    public void setXSpeed(int s){
        xSpeed = s;
    }

    /**
     * setYSpeed sets y speed
     * @param s y speed
     */
    public void setYSpeed(int s){
        ySpeed = s;
    }

    /**
     * reverseX sets the reverse speed of the ball to make it go to the left
     */
    public void reverseX(){
        xSpeed *= -1;
    }

    /**
     * reverseY sets the reverse speed of the ball to make it go backwards
     */
    public void reverseY(){
        ySpeed *= -1;
    }

    /**
     * getBorderColor returns the color of the boarder of the ball
     * @return color of the ball
     */
    public Color getBorderColor(){
        return border;
    }

    /**
     * getInnerColor returns the inner color of the ball
     * @return inner ball color
     */
    public Color getInnerColor(){
        return inner;
    }

    /**
     * getPosition will get the position of the ball
     * @return center
     */
    public Point2D getPosition(){
        return center;
    }

    /**
     * getBallFace will get the look of the ball
     * @return ballFace
     */
    public Shape getBallFace(){
        return ballFace;
    }

    /**
     * moveTo moves the ball of the position
     * @param p position of the ball
     */
    public void moveTo(Point p){
        center.setLocation(p);

        RectangularShape tmp = (RectangularShape) ballFace;
        double w = tmp.getWidth();
        double h = tmp.getHeight();

        tmp.setFrame((center.getX() -(w / 2)),(center.getY() - (h / 2)),w,h);
        ballFace = tmp;
    }

    /**
     * setPoints sets the points of the ball to the location of the screen
     * @param width width of the frame
     * @param height height of the frame
     */
    private void setPoints(double width,double height){
        up.setLocation(center.getX(),center.getY()-(height / 2));
        down.setLocation(center.getX(),center.getY()+(height / 2));

        left.setLocation(center.getX()-(width / 2),center.getY());
        right.setLocation(center.getX()+(width / 2),center.getY());
    }

    /**
     * getSpeedX gets x speed
     * @return x speed
     */
    public static int getSpeedX(){
        return xSpeed;
    }

    /**
     * getSpeedY gets y speed
     * @return y speed
     */
    public static int getSpeedY(){
        return ySpeed;
    }

}