/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collageia;
import java.io.*;
import java.awt.*;
import java.awt.image.*;
import java.util.ArrayList;
import java.util.Scanner;
import javax.imageio.ImageIO;

/**
 *
 * @author Nishu
 */
public class CollageDrawer {
    private String filename;
    private final int TILE_WIDTH;
    private final int TILE_HEIGHT;
    private BufferedImage image;
    private PicLibrary picLibrary;
    private DrawingPanel panel;
    private Graphics g;
    private String saveToFile;
    private Scanner scan = new Scanner(System.in);
    private static int id = 0;
    
    public CollageDrawer(String filename, PicLibrary picLibrary, int tileWidth, int tileHeight, String saveToFiles) throws IOException {
        this.filename = filename;
        this.picLibrary = picLibrary;
        System.out.println("File path of picture to be collaged: " + filename);
        image = ImageIO.read(new File(filename));
        panel = new DrawingPanel(image.getWidth(), image.getHeight());
        g = panel.getGraphics();
        g.drawImage(image, 0, 0, null);
        TILE_WIDTH = tileWidth;
        TILE_HEIGHT = tileHeight;
        this.saveToFile = saveToFile;
        id++;
    }
    
    /**
     * Creates a collage of the photo from the file path passed through the constructor, using the Pictures from the PicLibrary passed through the constructor
     * @throws IOException
     */
    public void collage() throws IOException {
        int tileSize = TILE_WIDTH*TILE_HEIGHT;
        int numTiles = ((image.getWidth()*image.getHeight())/tileSize);
        int[] tileAvgColors = new int[numTiles];
        
        int x = 0;
        int y = 0;
        System.out.println("\nProcessing image...");
        for (int i = 0; i < numTiles; i++) {
            int red = 0;
            int green = 0;
            int blue = 0;
            
            for (int j = y; j < (y+TILE_HEIGHT); j++) {
                for (int k = x; k < (x+TILE_WIDTH); k++) {
                    Color pixel = new Color(image.getRGB(k, j));
                    
                    red += pixel.getRed();
                    green += pixel.getGreen();
                    blue += pixel.getBlue();
                }
            }
            
            x += TILE_WIDTH;
            if (x >= image.getWidth()) {
                x = 0;
                y += TILE_HEIGHT;
            }
            
            red /= tileSize;
            green /= tileSize;
            blue /= tileSize;
            
            tileAvgColors[i] = ((red+green+blue)/3);
        }
        
        ArrayList<Picture> pictures = picLibrary.getList();
        x = 0;
        y = 0;
        
        System.out.println("\nCreating collage...");
        
        BufferedImage[] tiles = new BufferedImage[numTiles];
        int count = 0;
        for (int avgColor : tileAvgColors) {
            Picture pic = pictures.get(closestIndex(avgColor, pictures));
            tiles[count] = pic.getImage();
            count++;
        }
        
        panel.sleep(5000);
        panel.clear();
        for (BufferedImage tile : tiles) {
            g.drawImage(tile, x, y, TILE_WIDTH, TILE_HEIGHT, null);
            x += TILE_WIDTH;
            if (x >= image.getWidth()) {
                x = 0;
                y += TILE_HEIGHT;
            }
        }
        System.out.println("\nCollage created");
        
        /*
        System.out.print("\nSave image? Type y for yes or n for no\n> ");
        String response = scan.nextLine().toLowerCase();
        while (!response.equals("y") && !response.equals("n")) {
            System.out.print("Type y for yes or n for no\n> ");
            response = scan.nextLine().toLowerCase();
        }
        if (response.equals("y")) {
            TODO: Figure out how to save DrawingPanel content to a file
            
            File saveFolder = new File("C:\\Pictures\\Collages/");
            File temp = File.createTempFile("Collage "+id, ".jpg", saveFolder);
            panel.save(temp);
        }
        System.out.println("Thank you for using the Collager!");
        System.exit(0);
        */
    }
    
    private int closestIndex(int num, ArrayList<Picture> pics) {
        int closest = 0;
        int smallestDistance = Math.abs(pics.get(0).getAvgColor() - num);
        
        for (int i = 1; i < pics.size(); i++) {
            if ((Math.abs(pics.get(i).getAvgColor()-num)) < smallestDistance) {
                smallestDistance = (Math.abs(pics.get(i).getAvgColor() - num));
                closest = i;
            }
        }
        
        return closest;
    }
}
