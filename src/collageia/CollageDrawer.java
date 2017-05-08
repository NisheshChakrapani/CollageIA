/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collageia;
import java.io.*;
import java.awt.*;
import java.awt.image.*;
import java.nio.file.Files;
import java.nio.file.Paths;
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
    private int id = 1;
    
    public CollageDrawer(String filename, PicLibrary picLibrary, int tileWidth, int tileHeight, String saveToFile) throws IOException {
        this.filename = filename;
        this.picLibrary = picLibrary;
        image = ImageIO.read(new File(filename));
        panel = new DrawingPanel(image.getWidth(), image.getHeight());
        panel.setVisible(true);
        g = panel.getGraphics();
        g.drawImage(image, 0, 0, null);
        TILE_WIDTH = tileWidth;
        TILE_HEIGHT = tileHeight;
        this.saveToFile = saveToFile;
        while (image.getWidth()%TILE_WIDTH != 0) {
            image = image.getSubimage(0, 0, image.getWidth()-1, image.getHeight());
        }
        while (image.getHeight()%TILE_HEIGHT != 0) {
            image = image.getSubimage(0, 0, image.getWidth(), image.getHeight()-1);
        }
    } 
    
    /**
     * Creates a collage of the photo from the file path passed through the constructor, using the Pictures from the PicLibrary passed through the constructor
     * @throws IOException
     */
    public void collage() throws IOException, InterruptedException {
        int tileSize = TILE_WIDTH*TILE_HEIGHT;
        int numTiles = ((image.getWidth()*image.getHeight())/tileSize);
        Color[] tileAvgColors = new Color[numTiles];
        
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
            
            tileAvgColors[i] = new Color(red, green, blue);
        }
        
        ArrayList<Picture> pictures = picLibrary.getList();
        x = 0;
        y = 0;
        
        System.out.println("\nCreating collage...");
        
        BufferedImage[] tiles = new BufferedImage[numTiles];
        int count = 0;
        for (Color avgColor : tileAvgColors) {
            Picture pic = (closestPicture(avgColor, pictures));
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
          
        System.out.print("\nSave image? Type y for yes or n for no\n> ");
        String response = scan.nextLine().toLowerCase();
        while (!response.equals("y") && !response.equals("n")) {
            System.out.print("Type y for yes or n for no\n> ");
            response = scan.nextLine().toLowerCase();
        }
        String name2 = "";
        if (response.equals("y")) {
            String name = "Collage " + id + ".png";
            System.out.print("Give a name to the file? Type the filename you want or type 'D' for default filename\n> ");
            boolean done = false;
            while (!done) {
                id++;   
                name = scan.nextLine();
                while (containsIllegalChars(name) || filenameTooLong(name) || fileAlreadyExists(name)) {
                    System.out.print("Illegal filename (either too long, contains an illegal character, or already exists within save destination folder). Enter filename:\n> ");
                    name = scan.nextLine();
                }
                if (name.toUpperCase().equals("D")) {
                    name2 = "Collage" + id + "SideBySide.png";
                    name = "Collage " + id + ".png";
                    done = true;
                } else {
                    name2 = name + "SideBySide.png";
                    name = name + ".png";
                    done = true;
                }
            }
            System.out.println("Creating file \"" + name + "\" in folder " + saveToFile);
            Files.createDirectory(Paths.get(saveToFile+name.substring(0, name.length()-4)));
            panel.save(new File(saveToFile+name.substring(0, name.length()-4)+"\\"+name));
            System.out.println("Successful.");
            System.out.println("Saving side-by-side comparsion file...");
            DrawingPanel temp = new DrawingPanel(panel.getWidth()*2, panel.getHeight());
            Graphics g2 = temp.getGraphics();
            g2.drawImage(image, 0, 0, null);
            BufferedImage collage = ImageIO.read(new File(saveToFile+name.substring(0, name.length()-4)+"\\"+name));
            g2.drawImage(collage, image.getWidth(), 0, null);
            temp.save(new File(saveToFile+name.substring(0, name.length()-4)+"\\"+name2));
            System.out.println("Successful.");
            System.out.println("Save complete.");

            panel.setVisible(false);
            temp.setVisible(false);
            Desktop d = Desktop.getDesktop();
            d.open(new File(saveToFile+name.substring(0, name.length()-4)+"\\"+name2));
        }
        System.out.print("Want to make another collage? Type y for yes or n for no\n> ");
        String answer = scan.next();
        while (!answer.equalsIgnoreCase("n") && !answer.equalsIgnoreCase("y")) {
            System.out.print("Type y for yes or n for no\n> ");
            answer = scan.next();
        }
        if (answer.equalsIgnoreCase("n")) {
            System.out.println("Thanks for using the collager!");
            System.exit(0);
        } else {
            panel.setVisible(false);
            FilepathRetriever fr = new FilepathRetriever();
            String toBeCollaged = fr.getCollageImagePath();
            int tileWidth = fr.getTileWidth();
            int tileHeight = fr.getTileHeight();
            CollageDrawer cd = new CollageDrawer(toBeCollaged, picLibrary, tileWidth, tileHeight, saveToFile);
            cd.collage();
        }
    }
    
    private Picture closestPicture(Color color, ArrayList<Picture> pics) {
        KDTree tree = new KDTree(pics);
        tree.createTree();
        TreeNode best = tree.nearestNeighbor(color);
        Picture bestPic = pics.get(0);
        for (Picture pic : pics) {
            if (pic.getAvgColor().equals(best.getPicture().getAvgColor()))
                bestPic = pic;
        }

        return bestPic;
    }
    
    private boolean containsIllegalChars(String s) {
        //ILLEGAL CHARS: /, \, ?, %, *, :, |, ", <, >
        if (s.contains("/") || s.contains("\\") || s.contains("?") || s.contains("%") || s.contains("*") || s.contains(":") || s.contains("|")
                || s.contains("\"") || s.contains("<") || s.contains(">")) {
            return true;
        } else {
            return false;
        }
    }
    
    private boolean filenameTooLong(String s) {
        if (s.length() >= 100) {
            return true;
        } else {
            return false;
        }
    }
    
    private boolean fileAlreadyExists(String s) {
        File newFile = new File(saveToFile+s+".png");
        if (newFile.exists() && !newFile.isDirectory()) {
            return true;
        } else {
            return false;
        }
    }
}
