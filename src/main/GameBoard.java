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
import parts.Brick;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.FontRenderContext;


public class GameBoard extends JComponent implements KeyListener,MouseListener,MouseMotionListener {

    private static final String CONTINUE = "Continue";
    private static final String RESTART = "Restart";
    private static final String EXIT = "Exit";
    private static final String PAUSE = "Pause Menu";
    private static final int TEXT_SIZE = 30;
    private static final Color MENU_COLOR = new Color(0, 208, 255);

    private static final int DEF_WIDTH = 1000;
    private static final int DEF_HEIGHT = 750;

    private static final Color BG_COLOR = Color.WHITE;

    /* The name of the variable is changed from gameTimer to gameCounter
    as it counts the bricks and balls left rather than calculating the time for the game to run */
    private Timer gameCounter;

    private Wall wall;

    private String message;

    private boolean showPauseMenu;

    private Font menuFont;

    private Rectangle continueButtonRect;
    private Rectangle exitButtonRect;
    private Rectangle restartButtonRect;
    private Rectangle gameFace;
    private int strLen;

    protected static Brick[] bricks;

    protected int ballXSpeed;
    protected int ballYSpeed;

    private DebugConsole debugConsole;

    /* In game */
    public GameBoard(JFrame owner, Dimension gameDimension){
        super();

        strLen = 0;
        showPauseMenu = false;

        ballXSpeed = Ball.getSpeedX();
        ballYSpeed = Ball.getSpeedY();

        HighScore hs = new HighScore();

        gameFace = new Rectangle(new Point(0, 0), gameDimension);
        this.setPreferredSize(gameDimension);

        menuFont = new Font("Monospaced",Font.PLAIN,TEXT_SIZE);

        this.initialize();
        message = "";
        wall = new Wall(new Rectangle(0,0,DEF_WIDTH,DEF_HEIGHT),30,3,6/2,new Point(500,700));

        debugConsole = new DebugConsole(owner,wall,this);

        wall.nextLevel();

        gameCounter = new Timer(10,e ->{
            wall.move();
            wall.findImpacts();
            message = String.format("Bricks: %d Balls: %d Score %d",Wall.getBrickCount(),wall.getBallCount(),wall.score);
            if(wall.isBallLost()){
                if(wall.ballEnd()){
                    wall.wallReset();
                    hs.addScore("Local_Player", wall.score);
                    message = "Game over";
                }
                wall.ballReset();
                gameCounter.stop();
            }
            else if(wall.isDone()){
                if(Wall.hasLevel()){
                    message = "Go to Next Level";
                    gameCounter.stop();
                    wall.ballReset();
                    wall.wallReset();
                    wall.nextLevel();
                }
                else{
                    message = "ALL WALLS DESTROYED";
                    gameCounter.stop();
                }
            }

            repaint();
        });

    }

    public GameBoard() {

    }


    /* initialise the values for the game to run */
    private void initialize(){
        this.setPreferredSize(new Dimension(DEF_WIDTH,DEF_HEIGHT));
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.addKeyListener(this);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }

    /* Display on the screen */
    public void paint(Graphics g){

        Graphics2D g2d = (Graphics2D) g;

        clear(g2d);

        g2d.setColor(Color.BLUE);
        g2d.drawString(message,250,225);

        drawBall(wall.ball,g2d);

        for(Brick b : wall.bricks) {
            if (!b.isBroken()){
                drawBrick(b, g2d);
            }
        }
        drawPlayer(wall.player,g2d);

        if(showPauseMenu) {
            drawMenu(g2d);
        }

        Toolkit.getDefaultToolkit().sync();
    }

    /* Clear the background of the screen */
    private void clear(Graphics2D g2d){
        Color tmp = g2d.getColor();
        g2d.setColor(BG_COLOR);
        g2d.fillRect(0,0,getWidth(),getHeight());
        g2d.setColor(tmp);
    }

    /* Draws the brick */
    private void drawBrick(Brick brick,Graphics2D g2d){
        Color tmp = g2d.getColor();

        g2d.setColor(brick.getInnerColor());
        g2d.fill(brick.getBrick());

        g2d.setColor(brick.getBorderColor());
        g2d.draw(brick.getBrick());


        g2d.setColor(tmp);
    }

    /* Draws the ball */
    private void drawBall(Ball ball, Graphics2D g2d){
        Color tmp = g2d.getColor();

        Shape s = ball.getBallFace();

        g2d.setColor(ball.getInnerColor());
        g2d.fill(s);

        g2d.setColor(ball.getBorderColor());
        g2d.draw(s);

        g2d.setColor(tmp);
    }

    /* Draws the player (paddle) */
    private void drawPlayer(Player p, Graphics2D g2d){
        Color tmp = g2d.getColor();

        Shape s = p.getPlayerFace();
        g2d.setColor(Player.INNER_COLOR);
        g2d.fill(s);

        g2d.setColor(Player.BORDER_COLOR);
        g2d.draw(s);

        g2d.setColor(tmp);
    }

    /* Draws the menu */
    private void drawMenu(Graphics2D g2d){
        obscureGameBoard(g2d);
        drawPauseMenu(g2d);
    }

    /* loads the background for the pause menu */
    private void obscureGameBoard(Graphics2D g2d){

        Composite tmp = g2d.getComposite();
        Color tmpColor = g2d.getColor();

        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.55f);
        g2d.setComposite(ac);

        g2d.setColor(Color.BLACK);
        g2d.fillRect(0,0,DEF_WIDTH,DEF_HEIGHT);

        g2d.setComposite(tmp);
        g2d.setColor(tmpColor);
    }

    /* Draws the pause menu */
    private void drawPauseMenu(Graphics2D g2d){
        Font tmpFont = g2d.getFont();
        Color tmpColor = g2d.getColor();


        g2d.setFont(menuFont);
        g2d.setColor(MENU_COLOR);

        if(strLen == 0){
            FontRenderContext frc = g2d.getFontRenderContext();
            strLen = menuFont.getStringBounds(PAUSE,frc).getBounds().width;
        }

        int x = (this.getWidth() - strLen) / 2;
        int y = this.getHeight() / 10;

        g2d.drawString(PAUSE,x,y);

        x = this.getWidth() / 8;
        y = this.getHeight() / 4;


        if(continueButtonRect == null){
            FontRenderContext frc = g2d.getFontRenderContext();
            continueButtonRect = menuFont.getStringBounds(CONTINUE,frc).getBounds();
            continueButtonRect.setLocation(x,y-continueButtonRect.height);
        }

        g2d.drawString(CONTINUE,x,y);

        y *= 2;

        if(restartButtonRect == null){
            restartButtonRect = (Rectangle) continueButtonRect.clone();
            restartButtonRect.setLocation(x,y-restartButtonRect.height);
        }

        g2d.drawString(RESTART,x,y);

        y *= 3.0/2;

        if(exitButtonRect == null){
            exitButtonRect = (Rectangle) continueButtonRect.clone();
            exitButtonRect.setLocation(x,y-exitButtonRect.height);
        }

        g2d.drawString(EXIT,x,y);



        g2d.setFont(tmpFont);
        g2d.setColor(tmpColor);
    }


    @Override
    public void keyTyped(KeyEvent keyEvent) {
    }


    @Override
    public void keyPressed(KeyEvent keyEvent) {
        switch(keyEvent.getKeyCode()){
            case KeyEvent.VK_A:
                wall.player.moveLeft();
                break;
            case KeyEvent.VK_D:
                wall.player.movRight();
                break;
            case KeyEvent.VK_ESCAPE:
                showPauseMenu = !showPauseMenu;
                repaint();
                gameCounter.stop();
                break;
            case KeyEvent.VK_SPACE:
                if(!showPauseMenu)
                    if(gameCounter.isRunning())
                        gameCounter.stop();
                    else
                        gameCounter.start();
                break;
            case KeyEvent.VK_F1:
                    debugConsole.setVisible(true);
            default:
                wall.player.stop();
        }
    }


    @Override
    public void keyReleased(KeyEvent keyEvent) {
        wall.player.stop();
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        HighScore hs = new HighScore();
        if(!showPauseMenu)
            return;
        if(continueButtonRect.contains(p)){
            showPauseMenu = false;
            repaint();
        }
        else if(restartButtonRect.contains(p)){
            message = "Restarting Game...";
            wall.ballReset();
            wall.wallReset();
            showPauseMenu = false;
            hs.addScore("Local_Player", wall.score);
            repaint();
        }
        else if(exitButtonRect.contains(p)){
            System.exit(0);

            hs.addScore("Local_Player", wall.score);
        }

    }

    /**
     * mousedPressed overrides mouseEvent
     * @param mouseEvent mouseEvent object
     */
    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }


    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }


    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }


    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }


    @Override
    public void mouseDragged(MouseEvent mouseEvent) {

    }


    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if(exitButtonRect != null && showPauseMenu) {
            if (exitButtonRect.contains(p) || continueButtonRect.contains(p) || restartButtonRect.contains(p))
                this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            else
                this.setCursor(Cursor.getDefaultCursor());
        }
        else{
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    /*
     * When the cursor clicks on something outside the game screen, the game stops
     * A message showing "Focus Lost" will be prompt
     * Clicking on the Space bar can continue the game
     */
    public void onLostFocus(){
        gameCounter.stop();
        message = "Focus Lost";
        repaint();
    }

}