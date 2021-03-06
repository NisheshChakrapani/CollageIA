/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collageia;
import java.awt.Color;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author Nishu
 */
public class PicLibrary {
    private ArrayList<Picture> pictures = new ArrayList();
    private String folderDirectory;
    private File directory;
    private String[] fileNames;

    public PicLibrary(String folderDirectory) throws IOException {
        this.folderDirectory = folderDirectory;
        directory = new File(this.folderDirectory);
        System.out.println("Number of images found: " + this.numImages());
        if (this.numNotImage()!=0) {
            System.out.println("Number of non-image files found: " + this.numNotImage()+"\n");
        }
        this.setLibrary();
    }
    
    private int numImages() {
        fileNames = directory.list(new FilenameFilter() {
            public boolean accept (File directory, String fileName) {
                if ((fileName.endsWith(".png") || fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".gif"))) {
                    return true;
                } else {
                    return false;
                }
            }
        });
        
        return fileNames.length;
    }
    
    private int numNotImage() {
        String[] allFiles = directory.list();
        int num = allFiles.length-fileNames.length;
        for (String s : allFiles) {
            if (s.endsWith(".db")) {
                num--;
            }
        }
        return (num);
    }
    
    private void setLibrary() throws IOException {
        System.out.println("Loading library...\n");
        for (int i = 0; i < fileNames.length; i++) {
            Picture pic = new Picture(folderDirectory, fileNames[i]);
            pictures.add(pic);
        }
        System.out.println("Complete.\n");
    }
    
    /**
     * Prints out every Picture object in the PicLibrary
     */
    public void printPictures() {
        for (Object o : pictures) {
            System.out.println(o);
        }
    }
    
    /**
     * Gets the ArrayList of Picture objects that the PicLibrary uses to store all of the Pictures
     * @return Every picture in the PicLibrary as an ArrayList
     */
    public ArrayList getList() {
        return pictures;
    }
}
