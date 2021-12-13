package main;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class LoadImg {
    private BufferedImage img;
    public static String mainMenuBackground = "/Background.png";
    public static String startButtonBefore = "/startButton1.png";
    public static String startButtonAfter = "/startButton2.png";
    public static String infoButtonBefore = "/controlsButton1.png";
    public static String infoButtonAfter = "/controlsButton2.png";
    public static String scoreButtonBefore = "/scoreButton1.png";
    public static String scoreButtonAfter = "/scoreButton2.png";
    public static String exitButtonBefore = "/exitButton1.png";
    public static String exitButtonAfter = "/exitButton2.png";
    public static String infoBackground = "/controlBackground.png";
    public static String highScoreBackground = "/scoreBackground.png";

    /**
     * ImageLoader loads the image into img
     *
     * @param path The name of the file
     */
    public LoadImg(String path) {
        try {
            img = ImageIO.read(LoadImg.class.getResourceAsStream(path));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * getImage will retrieve when called
     *
     * @return image of that's called
     */
    public BufferedImage getImage() {
        return img;
    }

}
