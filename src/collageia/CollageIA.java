/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collageia;

import java.awt.*;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author Nishu
 */
public class CollageIA {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        // TODO code application logic here
        FilepathRetriever fr = new FilepathRetriever();
        String path = fr.getLibraryPath();
        String toBeCollaged = fr.getCollageImagePath();
        String saveTo = fr.getSavePath();
        int tileWidth = fr.getTileWidth();
        int tileHeight = fr.getTileHeight();

        PicLibrary pl = new PicLibrary(path);
        
        //Draw using Java swing
        /*CollageCreator cc = new CollageCreator(toBeCollaged, pl);
        Graphics g = cc.getGraphics();
        cc.collage(g);*/
        
        //Draw using Drawing Panel
        CollageDrawer cd = new CollageDrawer(toBeCollaged, pl, tileWidth, tileHeight, saveTo);
        Scanner scan = new Scanner(System.in);
        try {
            boolean done = false;
            while (!done) {
                cd.collage();
                System.out.print("Want to make another collage? Type y for yes or n for no\n> ");
                String answer = scan.next();
                while (!answer.equalsIgnoreCase("n") && !answer.equalsIgnoreCase("y")) {
                    System.out.print("Type y for yes or n for no\n> ");
                    answer = scan.next();
                }
                if (answer.equalsIgnoreCase("n")) {
                    System.out.println("Thanks for using the collager!");
                    done = true;
                } else {
                    FilepathRetriever fr2 = new FilepathRetriever();
                    String newToBeCollaged = fr.getCollageImagePath();
                    int newTileWidth = fr.getTileWidth();
                    int newTileHeight = fr.getTileHeight();
                    cd = new CollageDrawer(newToBeCollaged, pl, newTileWidth, newTileHeight, saveTo);
                }
            }
            System.exit(0);
        } catch (OutOfMemoryError | StackOverflowError e) {
            System.out.println("Image was too large. Collage aborted.");
            System.exit(0);
        }
    }   
}
