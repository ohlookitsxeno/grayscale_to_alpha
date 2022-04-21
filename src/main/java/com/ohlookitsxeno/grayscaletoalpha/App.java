package com.ohlookitsxeno.grayscaletoalpha;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;

public class App 
{
    static String filePath = "";
    static Boolean fileExists = false;
    static String [] fileOut = {null,null};
    static BufferedImage image = null;
    static BufferedImage finalImage = null;
    static Boolean deleteBlack = false;
    public static void main( String[] args )
    {
        arguments(args);

        if(fileExists){
            loadImage();
            if(fileOut[0] == null)
                System.out.println("Error: No valid output file, exiting.");
        }else
            System.out.println("Error: No valid input file, exiting.");

        
        if(image != null && fileOut[0] != null){
            finalImage = greyscaleToAlpha(image);
            saveImage(finalImage);
        }
    }

    //reads arguments
    public static void arguments(String[] args){
        for(int anum = 0; anum < args.length; anum++){

            String arg = args[anum];

            switch(arg){
                case "-file":
                case "-f":
                    if(anum < args.length - 1) //checks if next argument exists
                        loadFile(args[++anum]);
                    else
                        System.out.println("Error: Missing file argument. Usage: " + arg + " <file>");
                    break;
                case "-output":
                case "-out":
                case "-o":
                    if(anum < args.length - 1){ //checks if next argument exists
                        if(setOutput(args[++anum]))
                            System.out.println("Output Successfully set to " + fileOut[0] + "." + fileOut[1]);
                        else
                            System.out.println("Error: Invalid output format.");
                    }else
                        System.out.println("Error: Missing output argument. Usage: " + arg + " <name.ext>");
                    break;
                case "-black":
                case "-bl":
                case "-b":
                    deleteBlack = true;
                    break;
                case "-white":
                case "-wh":
                case "-w":
                    deleteBlack = false;
                    break;
                default:
                System.out.println("Note: Unrecognized argument '" + arg + "', Ignoring.");
            }
        }
    } 

    public static Boolean setOutput(String name){
        String [] formats = {"png","gif","bmp"};
        int index = name.lastIndexOf('.');
        if(index == -1)
            return false;
        for(String f : formats){
            if(name.substring(index + 1).toLowerCase().equals(f)){
                fileOut[0] = name.substring(0, index);
                fileOut[1] = f;
                return true;
            }
        }
        return false;
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
            File out = new File(fileOut[0] + "." + fileOut[1]);
            ImageIO.write(save, fileOut[1], out);
            System.out.println("Success!");
        } catch (IOException e){
            System.out.println("Error: Writing to image failed.");
        }
    }

    public static BufferedImage greyscaleToAlpha(BufferedImage img){
        BufferedImage out = new BufferedImage(img.getWidth(),img.getHeight(),BufferedImage.TYPE_INT_ARGB);
        for(int x = 0; x < img.getWidth(); x++){
            for(int y = 0; y < img.getHeight(); y++){
                int color = img.getRGB(x,y);
                int r = (color >> 16) & 255;
                int g = (color >> 8) & 255;
                int b = color & 255;
                int gray = (r+g+b)/3;
                int fincolor;
                if(deleteBlack)
                    fincolor = (gray<<24) | (0xff << 16) | (0xff << 8) | 0xff;
                else
                    fincolor = (255-gray<<24);
                out.setRGB(x,y,fincolor);
            }
        }
        return out;
    }
}
