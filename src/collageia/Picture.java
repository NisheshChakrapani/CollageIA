/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collageia;
import java.io.*;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.ImageIO;

/**
 *
 * @author Nishu
 */
public class Picture {
    private String filename;
    private BufferedImage img;
    private final int WIDTH;
    private final int HEIGHT;
    private Color color;
    
    public Picture (String folderDirectory, String filename) throws IOException {
        this.filename = filename;
        img = ImageIO.read(new File(folderDirectory+filename));
        WIDTH = img.getWidth();
        HEIGHT = img.getHeight();
        this.setAvgColor();
    }
    
    /**
     * Gets the filename of the image associated with the current Picture object
     * @return The filename as a String
     */
    public String getFilename() {
        return filename;
    }
    
    /**
     * Gets the image associated with the current Picture object
     * @return The image as a BufferedImage
     */
    public BufferedImage getImage() {
        return img;
    }
    
    private void setAvgColor() {
        int[] avgs = this.getAvgRGB();
        this.color = new Color(avgs[0], avgs[1], avgs[2]);
    }
    
    /**
     * Gets the average color of the image associated with the Picture object
     * @return The average color as a Color object, made with the average red, green, and blue values per pixel
     */
    public Color getAvgColor() {
        return this.color;
    }
    
    private int[] getAvgRGB() {
        int numPixels = WIDTH * HEIGHT;
        int[] redVals = new int[numPixels];
        int[] greenVals = new int[numPixels];
        int[] blueVals = new int[numPixels];
        int x = 0;
        int y = 0;
        int pixel = 0;
        while (pixel < numPixels) {
            while (x < WIDTH) {
                Color color = new Color(img.getRGB(x, y));
                redVals[pixel] = color.getRed();
                greenVals[pixel] = color.getGreen();
                blueVals[pixel] = color.getBlue();
                x++;
                pixel++;
            }
            x = 0;
            y++;
        }

        int redSum = 0;
        int greenSum = 0;
        int blueSum = 0;
        for (int i = 0; i < numPixels; i++) {
            redSum += redVals[i];
            greenSum += greenVals[i];
            blueSum += blueVals[i];
        }

        int[] avgs = new int[3];
        avgs[0] = redSum / numPixels;
        avgs[1] = greenSum / numPixels;
        avgs[2] = blueSum / numPixels;

        return avgs;
    }
    
    @Override
    public String toString() {
        String rgb = "(" + getAvgColor().getRed() + ", " + getAvgColor().getGreen() + ", " + getAvgColor().getBlue() + ")";
        return this.filename + rgb;
    }

    public int getRed() {
        return color.getRed();
    }
    public int getGreen() {
        return color.getGreen();
    }
    public int getBlue() {
        return color.getBlue();
    }
}
