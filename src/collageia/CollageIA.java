/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collageia;

import java.awt.*;
import java.io.IOException;

/**
 *
 * @author Nishu
 */
public class CollageIA {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        String path = "C:\\Users\\Nishu\\Pictures\\Calvin and Hobbes Backgrounds\\"; //Home
        //String path = "H:\\Pictures\\"; //School

        PicLibrary pl = new PicLibrary(path);
        pl.printPictures();
        
        String toBeCollaged = "C:\\Users\\Nishu\\Pictures\\Programming\\CalvinAndHobbesMassive.jpg"; //Home
        //String toBeCollaged = "H:\\Fireplace.jpg"; //School
        
        //Draw using Java swing
        /*CollageCreator cc = new CollageCreator(toBeCollaged, pl);
        Graphics g = cc.getGraphics();
        cc.collage(g);*/

        String saveTo = "C:\\Users\\Nishu\\Pictures\\Collages\\"; //Home
        //String saveTo = "H:\\Saved Collages\\"; //School
        //Draw using Drawing Panel
        CollageDrawer cd = new CollageDrawer(toBeCollaged, pl, 32, 20, saveTo);
        cd.collage();
    }   
}
