/*
 *  Brick Destroy - A simple Arcade video game
 *   Copyright (C) 2017  Filippo Ranza
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package main;

import parts.*;
import parts.Ball;
import parts.Brick;
import parts.CementBrick;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Random;


public class Wall extends GameBoard {

    protected static final int CLAY = 1;
    protected static final int STEEL = 2;
    protected static final int CEMENT = 3;
    protected static final int TITANIUM = 4;
    protected static final int DIAMOND = 5;

    private static int brickCount;
    public int score = 0;
    private static int level;

    private static Brick[][] levels;

    private Random rnd;
    private Rectangle area;

    Ball ball;
    Player player;

    private Point startPoint;

    private int ballCount;
    private boolean ballLost;

    /**
     * Wall is the border of the game
     * @param drawArea size of the game console
     * @param brickCount the amount of bricks
     * @param lineCount the amount of lines
     * @param brickDimensionRatio size of the brick
     * @param ballPos the position of the ball
     */
    public Wall(Rectangle drawArea, int brickCount, int lineCount, double brickDimensionRatio, Point ballPos){
        super();

        this.startPoint = new Point(ballPos);

        levels = Levels.makeLevels(drawArea,brickCount,lineCount,brickDimensionRatio);
        level = 0;

        ballCount = 3;
        ballLost = false;

        rnd = new Random();

        makeBall(ballPos);

        int xSpeed ,ySpeed;

        do{
            xSpeed = 4;
        }while(xSpeed == 0);
        do{
            ySpeed = -4;
        }while(ySpeed == 0);

        ball.setSpeed(xSpeed,ySpeed);

        player = new Player((Point) ballPos.clone(),150,10, drawArea);

        area = drawArea;

    }

    /**
     * makeBall will created the ball when called
     * @param ballPos the initial position of the ball
     */
   private void makeBall(Point2D ballPos){
        ball = new RubberBall(ballPos);
    }

    /**
     * move will move the ball and the player will move when called
     */
    public void move(){
        player.move();
        ball.move();
    }

    /**
     * findImpacts finds the if the ball impact brick
     */
    public void findImpacts(){
        if(player.impact(ball)){
            ball.reverseY();
        }
        else if(impactWall()){
            /*for efficiency reverse is done into method impactWall
            * because for every brick program checks for horizontal and vertical impacts
            */
            brickCount--;
            score += 100;
        }
        else if(impactBorder()) {
            ball.reverseX();
        }
        else if(ball.getPosition().getY() < area.getY()){
            ball.reverseY();
        }
        else if(ball.getPosition().getY() > area.getY() + area.getHeight()){
            ballCount--;
            ballLost = true;
        }
    }

    /**
     * impactWall set the ball's direction to certain direction
     * @return false to re-initial the impactWall when it gets called again
     */
    private boolean impactWall(){
        for(Brick b : bricks){
            switch(b.findImpact(ball)) {
                //Vertical Impact
                case Brick.UP_IMPACT:
                    ball.reverseY();
                    return b.setImpact(ball.down, Brick.Crack.UP);
                case Brick.DOWN_IMPACT:
                    ball.reverseY();
                    return b.setImpact(ball.up,Brick.Crack.DOWN);

                //Horizontal Impact
                case Brick.LEFT_IMPACT:
                    ball.reverseX();
                    return b.setImpact(ball.right,Brick.Crack.RIGHT);
                case Brick.RIGHT_IMPACT:
                    ball.reverseX();
                    return b.setImpact(ball.left,Brick.Crack.LEFT);
            }
        }
        return false;
    }

    /**
     * impactBoarder will check if the ball hit the wall
     * @return the true if it hits the wall
     */
    private boolean impactBorder(){
        Point2D p = ball.getPosition();
        return ((p.getX() < area.getX()) ||(p.getX() > (area.getX() + area.getWidth())));
    }

    /**
     * getBrickCount will return the brickCount
     * @return brickCount
     */
    public static int getBrickCount(){
        return brickCount;
    }

    /**
     *getBallCount will return the ballCount
     * @return ballCount
     */
    public int getBallCount(){
        return ballCount;
    }

    /**
     * isBallLost will result ballLost
     * @return ballLost
     */
    public boolean isBallLost(){
        return ballLost;
    }

    /**
     * ballReset resets the ball speed
     */
    public void ballReset(){
        player.moveTo(startPoint);
        ball.moveTo(startPoint);
        int xSpeed, ySpeed;
        do{
            xSpeed = 4;
        }while(xSpeed == 0);
        do{
            ySpeed = -4;
        }while(ySpeed == 0);

        ball.setSpeed(xSpeed,ySpeed);
        ballLost = false;
    }

    /**
     * wallReset will reset the wall
     */
    public void wallReset(){
        for(Brick b : bricks)
            b.repair();
        brickCount = bricks.length;
        ballCount = 3;
    }

    /**
     * ballEnd return true if the ballCount is zero
     * @return true if ballCount is zero
     */
    public boolean ballEnd(){
        return ballCount == 0;
    }

    /**
     * isDone return true if ballCount is zero
     * @return true if ballCount is zero
     */
    public boolean isDone(){
        return brickCount == 0;
    }


    /**
     * nextLevel will move to the next level and reset the bricks
     */
    public static void nextLevel(){
        bricks = levels[level++];
        brickCount = bricks.length;
    }

    /**
     * hasLevel will return true if
     * @return
     */
    public static boolean hasLevel(){
        return level < levels.length;
    }

    /**
     * setBallXSpeed sets the x speed of the ball
     * @param s the speed value
     */
    public void setBallXSpeed(int s){
        ball.setXSpeed(s);
    }

    /**
     * setBallYSpeed sets the x speed of the ball
     * @param s the speed value
     */
    public void setBallYSpeed(int s){
        ball.setYSpeed(s);
    }

    /**
     * resetBallCount resets the ball count to 3
     */
    public void resetBallCount(){
        ballCount = 3;
    }

    /**
     * makeBrick when call will make the brick type
     * @param point brick location
     * @param size size of the brick
     * @param type the type of the brick
     * @return the new instance of the brick type's class
     */
    static Brick makeBrick(Point point, Dimension size, int type){
        Brick out;
        switch(type){
            case CLAY:
                out = new ClayBrick(point,size);
                break;
            case STEEL:
                out = new SteelBrick(point,size);
                break;
            case CEMENT:
                out = new CementBrick(point, size);
                break;
            case TITANIUM:
                out = new TitaniumBrick(point, size);
                break;
            case DIAMOND:
                out = new DiamondBrick(point, size);
                break;
            default:
                throw  new IllegalArgumentException(String.format("Unknown Type:%d\n",type));
        }
        return  out;
    }

}