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

import parts.Ball;

import java.awt.*;


public class Player {


    public static final Color BORDER_COLOR = Color.BLACK.darker().darker();
    public static final Color INNER_COLOR = Color.lightGray;

    private static final int DEF_MOVE_AMOUNT = 5;

    private Rectangle playerFace;
    private Point ballPoint;
    private int moveAmount;
    private int min;
    private int max;

    /**
     * Player is the paddle of the game
     * @param ballPoint
     * @param width
     * @param height
     * @param container
     */
    public Player(Point ballPoint,int width,int height,Rectangle container) {
        this.ballPoint = ballPoint;
        moveAmount = 0;
        playerFace = makeRectangle(width, height);
        min = container.x + (width / 2);
        max = min + container.width - width;

    }

    /**
     * makeRectangle will created the box which is the paddle
     * @param width width of the paddle
     * @param height height of paddle
     * @return the dimensions of the rectangle
     */
    private Rectangle makeRectangle(int width,int height){
        Point p = new Point((int)(ballPoint.getX() - (width / 2)),(int)ballPoint.getY());
        return  new Rectangle(p,new Dimension(width,height));
    }

    /**
     * impact will check if it impacted the paddle
     * @param b ball object
     * @return the ball contacting the paddle
     */
    public boolean impact(Ball b){
        return playerFace.contains(b.getPosition()) && playerFace.contains(b.down) ;
    }

    /**
     * move will set the paddle back where the ball restarts
     */
    public void move(){
        double x = ballPoint.getX() + moveAmount;
        if(x < min || x > max)
            return;
        ballPoint.setLocation(x,ballPoint.getY());
        playerFace.setLocation(ballPoint.x - (int)playerFace.getWidth()/2,ballPoint.y);
    }

    /**
     * moveLeft moves the paddle left
     */
    public void moveLeft(){
        moveAmount = -DEF_MOVE_AMOUNT;
    }

    /**
     * movRight moves the paddle right
     */
    public void movRight(){
        moveAmount = DEF_MOVE_AMOUNT;
    }

    /**
     * stop doesn't move the paddle and stays at the position it stopped
     */
    public void stop(){
        moveAmount = 0;
    }

    /**
     * getPlayerFace displays the paddle
     * @return the paddle object
     */
    public Shape getPlayerFace(){
        return  playerFace;
    }

    /**
     * moveTo moves the paddle back to the ball's position
     * @param p
     */
    public void moveTo(Point p){
        ballPoint.setLocation(p);
        playerFace.setLocation(ballPoint.x - (int)playerFace.getWidth()/2,ballPoint.y);
    }
}