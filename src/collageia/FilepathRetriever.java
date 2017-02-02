/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collageia;

import java.io.File;
import java.util.Scanner;

/**
 *
 * @author Nishu
 */
public class FilepathRetriever {
    private Scanner scan = new Scanner(System.in);
    private String libraryPath;
    private String collageImagePath;
    private String savePath;
    private int tileWidth;
    private int tileHeight;
    
    public FilepathRetriever() {
        this.retrieveLibraryPath();
        System.out.println();
        this.retrieveCollageImagePath();
        System.out.println();
        this.retrieveSavePath();
        System.out.println();
        this.retrieveTileWidth();
        System.out.println();
        this.retrieveTileHeight();
        System.out.println();
    }
    
    private void retrieveLibraryPath() {
        System.out.print("Enter file folder path for your library of pictures you want used to create the collage, with an ending "
                + "'\\' at the end (i.e. C\\Users\\Me\\Pictures\\CollagePhotos\\)\n> ");
        String path = scan.nextLine();
        File f = new File(path);
        while (!f.isDirectory() || path.charAt(path.length()-1)!='\\' || f.listFiles().length==0 || !f.exists()) {
            System.out.print("Invalid path. Make sure you typed the file folder path correctly, that the folder exists, and that the folder contains images\n> ");
            path = scan.nextLine();
            f = new File(path);
        }
        
        this.libraryPath = path;
    }
    
    private void retrieveCollageImagePath() {
        System.out.print("Enter path for image file for the picture that you want to make a collage of, making sure that the file ends with "
                + "'.jpg', '.png', '.gif', or '.jpeg'\n> ");
        String path = scan.nextLine();
        boolean legal = path.endsWith(".jpg") || path.endsWith(".gif") || path.endsWith(".png") || path.endsWith(".jpeg");
        File f = new File(path);
        while (!legal || !f.exists()) {
            System.out.print("Invalid path. Make sure the referenced image file exists and it ends in '.jpg', '.png', '.gif', or '.jpeg'\n> ");
            path = scan.nextLine();
            legal = path.endsWith(".jpg") || path.endsWith(".gif") || path.endsWith(".png") || path.endsWith(".jpeg");
            f = new File(path);
        }
        
        this.collageImagePath = path;
    }
    
    private void retrieveSavePath() {
        System.out.print("Enter file folder path for where you want your collage to be saved, with an ending "
                + "'\\' at the end (i.e. C\\Users\\Me\\Pictures\\Saved Collages\\)\n> ");
        String path = scan.nextLine();
        File f = new File(path);
        while (!f.isDirectory() || path.charAt(path.length()-1)!='\\' || !f.exists()) {
            System.out.print("Invalid path. Make sure you typed the file folder path correctly and that the folder contains images\n> ");
            path = scan.nextLine();
            f = new File(path);
        }
        
        this.savePath = path;
    }
    
    private void retrieveTileWidth() {
        System.out.print("Enter the width of each tile in the collage, as a number from 2 to 32 pixels\n> ");
        boolean retrieved = false;
        int width = 0;
        while (!retrieved) {
            String input = scan.nextLine();
            try {
                width = Integer.parseInt(input);
                if (width >= 2 && width <= 32) {
                    retrieved = true;
                } else {
                    System.out.print("Enter a number from 2 to 32\n> ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Enter a number from 2 to 32\n> ");
            }
        }
        
        this.tileWidth = width;
    }
    
    private void retrieveTileHeight() {
        System.out.print("Enter the height of each tile in the collage, as a number from 2 to 32 pixels\n> ");
        boolean retrieved = false;
        int height = 0;
        while (!retrieved) {
            String input = scan.nextLine();
            try {
                height = Integer.parseInt(input);
                if (height >= 2 && height <= 32) {
                    retrieved = true;
                } else {
                    System.out.print("Enter a number from 2 to 32\n> ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Enter a number from 2 to 32\n> ");
            }
        }
        
        this.tileHeight = height;
    }
    
    public String getLibraryPath() {
        return libraryPath;
    }
    
    public String getCollageImagePath() {
        return collageImagePath;
    }
    
    public String getSavePath() {
        return savePath;
    }
    
    public int getTileWidth() {
        return tileWidth;
    }
    
    public int getTileHeight() {
        return tileHeight;
    }
}
