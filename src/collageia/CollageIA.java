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
        cd.collage();
    }   
}
