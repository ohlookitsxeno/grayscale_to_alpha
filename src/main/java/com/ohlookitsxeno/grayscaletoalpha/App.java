package com.ohlookitsxeno.grayscaletoalpha;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import java.awt.Image;
import java.awt.image.BufferedImage;

public class App 
{
    static String filePath = "";
    static Boolean fileExists = false;
    static BufferedImage image = null;
    static BufferedImage finalImage = null;
    public static void main( String[] args )
    {
        arguments(args);

        if(fileExists)
            loadImage();
        else
            System.out.println("Error: No valid file, exiting.");

        if(image != null){
            finalImage = image;
            saveImage(finalImage);
        }
    }

    //reads arguments
    public static void arguments(String[] args){
        for(int arg = 0; arg < args.length; arg++){
            switch(args[arg]){
                case "-file":
                    if(arg < args.length - 1) //checks if next argument exists
                        loadFile(args[++arg]);
                    else
                       System.out.println("Error: Missing file argument. Usage: -file <file>");
                    break;
                default:
                    System.out.println("Note: Unrecognized argument '" + args[arg] + "', Ignoring.");
            }
        }
    } 

    //loading specific file
    public static void loadFile(String path){
        if(fileExists){ //if valid file already declared, ignore rest
            System.out.println("Note: Only using first file specified - '" + filePath + "'.");
            return;
        }
        
        fileExists = new File(path).isFile(); //check if file exists
        if(fileExists)
            filePath = path; //set main variable
        else
            System.out.println("Error: File '" + path + "' does not exist.");
    }

    public static void loadImage(){
        try {
            image = ImageIO.read(new File(filePath));
        } catch(IOException e){
            System.out.println("Error: Failed to read file");
        }
        if(image != null)
            System.out.println("Image '" + filePath + "' loaded.");
        else
            System.out.println("Error: Image not a valid type.");
    }

    public static void saveImage(BufferedImage save){
        try {
            File out = new File("save.png");
            ImageIO.write(save, "png", out);
            System.out.println("Success!");
        } catch (IOException e){
            System.out.println("Error: Writing to image failed.");
        }
    }
}
