package main;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.IOException;


public class HomeMenu extends JLayeredPane {

    private JLabel menuBackground = new JLabel();
    private Image mainMenuBackground = new LoadImg(LoadImg.mainMenuBackground).getImage();
    private ImageIcon menuIcon = new ImageIcon(mainMenuBackground);

    private JLabel infoBackground = new JLabel();
    private Image infoPage = new LoadImg(LoadImg.infoBackground).getImage();
    private ImageIcon infoIcon = new ImageIcon(infoPage);

    private JLabel hsBackground = new JLabel();
    private Image highScoreBackground = new LoadImg(LoadImg.highScoreBackground).getImage();
    private ImageIcon hsIcon = new ImageIcon(highScoreBackground);

    private JLabel exitInfo = new JLabel();
    private JLabel exitHS = new JLabel();

    private JLabel startButton = new JLabel();
    private Image startButtonBefore = new LoadImg(LoadImg.startButtonBefore).getImage();
    private ImageIcon startBeforeIcon = new ImageIcon(startButtonBefore);
    private Image startButtonAfter = new LoadImg(LoadImg.startButtonAfter).getImage();
    private ImageIcon startAfterIcon = new ImageIcon(startButtonAfter);

    private JLabel infoButton = new JLabel();
    private Image infoButtonBefore = new LoadImg(LoadImg.infoButtonBefore).getImage();
    private ImageIcon infoBeforeIcon = new ImageIcon(infoButtonBefore);
    private Image infoButtonAfter = new LoadImg(LoadImg.infoButtonAfter).getImage();
    private ImageIcon infoAfterIcon  = new ImageIcon(infoButtonAfter);

    private JLabel highScoreButton = new JLabel();
    private Image highScoreButtonBefore = new LoadImg(LoadImg.scoreButtonBefore).getImage();
    private ImageIcon hsBeforeIcon = new ImageIcon(highScoreButtonBefore);
    private Image highScoreButtonAfter = new LoadImg(LoadImg.scoreButtonAfter).getImage();
    private ImageIcon hsAfterIcon = new ImageIcon(highScoreButtonAfter);

    private JLabel exitButton = new JLabel();
    private Image exitButtonBefore = new LoadImg(LoadImg.exitButtonBefore).getImage();
    private ImageIcon exitBeforeIcon = new ImageIcon(exitButtonBefore);
    private Image exitButtonAfter = new LoadImg(LoadImg.exitButtonAfter).getImage();
    private ImageIcon exitAfterIcon = new ImageIcon(exitButtonAfter);

    JTextArea highScoreText = new JTextArea();

    /* Creates Home Menu and display buttons */
    public HomeMenu(GameFrame owner,Dimension area){

        this.setFocusable(true);
        this.requestFocusInWindow();
        this.setLayout(null);
        this.setPreferredSize(area);

        //sets the size and location of menu background
        menuBackground.setBounds(0,0,1000,750);
        menuBackground.setIcon(menuIcon);

        //sets button size and location
        startButton.setBounds(397,210,206,65);
        startButton.setIcon(startBeforeIcon);
        startButton.addMouseListener(new MouseInputListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Point p = e.getPoint();

                if(startButton.contains(p)){
                    owner.enableGameBoard(); //loads the GameBoard
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                Point p = e.getPoint();

                if(startButton.contains(p)){
                    startButton.setIcon(startAfterIcon);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                Point p = e.getPoint();
                startButton.setIcon(startBeforeIcon);
                if(startButton.contains(p)){
                    owner.enableGameBoard();
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }

            @Override
            public void mouseDragged(MouseEvent e) {
                Point p = e.getPoint();

                if(startButton.contains(p)){
                    startButton.setIcon(startAfterIcon);
                }else{
                    startButton.setIcon(startBeforeIcon);
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
        });

        //set the info button size and location
        infoButton.setBounds(397,280,206,65);
        infoButton.setIcon(infoBeforeIcon);
        infoButton.addMouseListener(new MouseInputListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Point p = e.getPoint();

                if(infoButton.contains(p)){
                    enableInfo();//enableInfo to display the enableInfo
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                Point p = e.getPoint();

                if(infoButton.contains(p)){
                        infoButton.setIcon(infoAfterIcon);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                Point p = e.getPoint();

                infoButton.setIcon(infoBeforeIcon);
                if(infoButton.contains(p)){
                    enableInfo();
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }

            @Override
            public void mouseDragged(MouseEvent e) {
                Point p = e.getPoint();

                if(infoButton.contains(p)){
                    infoButton.setIcon(infoAfterIcon);
                }else{
                    infoButton.setIcon(infoBeforeIcon);
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
        });

        //sets the exit button size and location
        exitButton.setBounds(397,350,206,65);
        exitButton.setIcon(exitBeforeIcon);
        exitButton.addMouseListener(new MouseInputListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                Point p = mouseEvent.getPoint();

                if(exitButton.contains(p)){
                    System.exit(0); //stops the game
                }
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                Point p = mouseEvent.getPoint();

                if(exitButton.contains(p)){
                    exitButton.setIcon(exitAfterIcon);
                }
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                Point p = mouseEvent.getPoint();

                exitButton.setIcon(exitBeforeIcon);
                if(exitButton.contains(p)){
                    System.exit(0);
                }
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {


            }


            @Override
            public void mouseDragged(MouseEvent mouseEvent) {
                Point p = mouseEvent.getPoint();

                if(exitButton.contains(p)){
                    exitButton.setIcon(exitAfterIcon);
                }else{
                    exitButton.setIcon(exitBeforeIcon);
                }
            }

            @Override
            public void mouseMoved(MouseEvent mouseEvent) {

            }
        });

        //sets HighScore button size and location
        highScoreButton.setBounds(397,420,206,65);
        highScoreButton.setIcon(hsBeforeIcon);
        highScoreButton.addMouseListener(new MouseInputListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                Point p = mouseEvent.getPoint();

                if(highScoreButton.contains(p)){
                    try {
                        enableHighScore();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                Point p = mouseEvent.getPoint();

                if(highScoreButton.contains(p)){
                    highScoreButton.setIcon(hsAfterIcon);
                }
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                Point p = mouseEvent.getPoint();

                highScoreButton.setIcon(hsBeforeIcon);
                if(highScoreButton.contains(p)){
                    try {
                        enableHighScore();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {


            }


            @Override
            public void mouseDragged(MouseEvent mouseEvent) {
                Point p = mouseEvent.getPoint();

                if(highScoreButton.contains(p)){
                    highScoreButton.setIcon(hsAfterIcon);
                }else{
                    highScoreButton.setIcon(hsBeforeIcon);
                }
            }

            @Override
            public void mouseMoved(MouseEvent mouseEvent) {

            }
        });

        enableMainMenu(); //enable the main menu

    }


    public void enableMainMenu(){
        this.add(menuBackground, DEFAULT_LAYER);
        this.add(startButton, PALETTE_LAYER);
        this.add(exitButton, PALETTE_LAYER);
        this.add(highScoreButton, PALETTE_LAYER);
        this.add(infoButton, PALETTE_LAYER);
    }


    public void enableInfo(){
        this.remove(menuBackground);
        this.remove(startButton);
        this.remove(infoButton);
        this.remove(exitButton);
        this.remove(highScoreButton);

        infoBackground.setBounds(0,0,1000,750);
        infoBackground.setIcon(infoIcon);

        //set the size and location of info exit button
        exitInfo.setBounds(794,690,206,65);
        exitInfo.setIcon(exitBeforeIcon);
        exitInfo.addMouseListener(new MouseInputListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                Point p = mouseEvent.getPoint();

                if(exitInfo.contains(p)){
                    remove(infoBackground);
                    remove(exitInfo);
                    enableMainMenu();

                }
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                Point p = mouseEvent.getPoint();

                if(exitInfo.contains(p)){
                    exitInfo.setIcon(exitAfterIcon);
                }
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                Point p = mouseEvent.getPoint();

                exitInfo.setIcon(exitBeforeIcon);
                if(exitInfo.contains(p)){
                    remove(infoBackground);
                    remove(exitInfo);
                    enableMainMenu();


                }
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {


            }


            @Override
            public void mouseDragged(MouseEvent mouseEvent) {
                Point p = mouseEvent.getPoint();

                if(exitInfo.contains(p)){
                    exitInfo.setIcon(exitAfterIcon);
                }else{
                    exitInfo.setIcon(exitBeforeIcon);
                }
            }

            @Override
            public void mouseMoved(MouseEvent mouseEvent) {

            }
        });

        this.add(infoBackground,POPUP_LAYER);
        this.add(exitInfo, DRAG_LAYER);

    }

    /* display high score */
    public void enableHighScore() throws IOException {
        this.remove(menuBackground);
        this.remove(startButton);
        this.remove(infoButton);
        this.remove(exitButton);
        this.remove(highScoreButton);

        hsBackground.setBounds(0,0,1000,750);
        hsBackground.setIcon(hsIcon);
        this.add(hsBackground, POPUP_LAYER);

        exitHS.setBounds(794,690,206,65);
        exitHS.setIcon(exitBeforeIcon);
        exitHS.addMouseListener(new MouseInputListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                Point p = mouseEvent.getPoint();

                if(exitHS.contains(p)){
                    remove(hsBackground);
                    enableMainMenu();
                    remove(exitHS);
                    remove(highScoreText);

                }
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                Point p = mouseEvent.getPoint();

                if(exitHS.contains(p)){
                    exitHS.setIcon(exitAfterIcon);
                }
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                Point p = mouseEvent.getPoint();

                exitHS.setIcon(exitBeforeIcon);
                if(exitHS.contains(p)){
                    remove(hsBackground);
                    enableMainMenu();
                    remove(exitHS);
                    remove(highScoreText);

                }
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {


            }


            @Override
            public void mouseDragged(MouseEvent mouseEvent) {
                Point p = mouseEvent.getPoint();

                if(exitHS.contains(p)){
                    exitHS.setIcon(exitAfterIcon);
                }else{
                    exitHS.setIcon(exitBeforeIcon);
                }
            }

            @Override
            public void mouseMoved(MouseEvent mouseEvent) {

            }
        });


        this.add(exitHS, DRAG_LAYER);

        //displays HighScore
        highScoreText.setSize(300, 200);
        highScoreText.setLocation(300,200);

        HighScore hs = new HighScore();
        highScoreText.setText(hs.getHighscoreString());
        this.add(highScoreText, DRAG_LAYER);

    }

}