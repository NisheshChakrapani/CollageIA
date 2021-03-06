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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 *
 * @author Nishu
 */
public class CollageCreator extends javax.swing.JFrame {

    /**
     * Creates new form CollageCreator
     */
    private static String filename;
    private static final int TILE_WIDTH = 4;
    private static final int TILE_HEIGHT = 2;
    private static BufferedImage image;
    private static PicLibrary picLib;
    private static JFrame frame = new JFrame();
    public CollageCreator(String filepath, PicLibrary picLib) throws IOException {
        initComponents();
        this.filename = filepath;
        System.out.println("File path of picture to be collaged: " + filename);
        image = ImageIO.read(new File(filename));
        frame.getContentPane().add(new JLabel(new ImageIcon(image)));
        frame.setVisible(true);
        frame.pack();
        this.picLib = picLib;
    }
    
    /**
     *
     * @param g, a Graphics object obtained from the JPanel using CollageCreator.getGraphics()
     * @throws IOException
     */
    /*public static void collage(Graphics g) throws IOException {
        int tileSize = TILE_WIDTH*TILE_HEIGHT;
        int numTiles = (image.getWidth()*image.getHeight())/tileSize;
        int[] tileAvgColors = new int[numTiles];
        
        int x = 0;
        int y = 0;
        for (int i = 0; i < numTiles; i++) {
            int red = 0;
            int green = 0;
            int blue = 0;
            
            for (int j = y; j < (y+TILE_HEIGHT); j++) {     
                for (int k = x; k < (x+TILE_WIDTH); k++) {
                    Color pixel = new Color(image.getRGB(k, j));
                    red+=pixel.getRed();
                    green+=pixel.getGreen();
                    blue+=pixel.getBlue();
                }
            }
            x+=TILE_WIDTH;
            if (x >= image.getWidth()) {
                x = 0;
                y+=TILE_HEIGHT;
            }
            red/=tileSize;
            green/=tileSize;
            blue/=tileSize;
            
            tileAvgColors[i] = ((red+green+blue)/3);
        }
        
        ArrayList<Picture> pictures = picLib.getList();
        x = 0;
        y = 0;
        JFrame newFrame = new JFrame();
        newFrame.setBounds(0, 0, image.getWidth(), image.getHeight());
        for (int avgColor : tileAvgColors) {
            Picture pic = pictures.get(closestIndex(avgColor, pictures));
            JLabel label = new JLabel(new ImageIcon(pic.getImage()));
            label.setBounds(x, y, TILE_WIDTH, TILE_HEIGHT);
            newFrame.getContentPane().add(label);
            x+=TILE_WIDTH;
            if (x>=image.getWidth()) {
                x = 0;
                y+=TILE_HEIGHT;
            }
        }
        System.out.println("\nProcessing image...");
        newFrame.setVisible(true);
        System.out.println("Creating collage...");
        frame.setVisible(false);
    }*/

    /*private static int closestIndex(int num, ArrayList<Picture> pics) {
        int closest = 0;
        int smallestDistance = Math.abs(pics.get(0).getAvgColor()-num);
        for (int i = 1; i < pics.size(); i++) {
            if ((Math.abs(pics.get(i).getAvgColor()-num)) < smallestDistance) {
                smallestDistance = (Math.abs(pics.get(i).getAvgColor()-num));
                closest = i;
            }
        }
        
        return closest;
    }*/
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CollageCreator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CollageCreator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CollageCreator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CollageCreator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new CollageCreator(filename, picLib).setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(CollageCreator.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
