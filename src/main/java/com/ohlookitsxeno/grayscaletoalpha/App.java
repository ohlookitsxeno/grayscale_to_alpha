package com.ohlookitsxeno.grayscaletoalpha;

import java.io.File;

import javax.lang.model.util.ElementScanner14;

public class App 
{
    static String filePath = "";
    static Boolean fileExists = false;
    public static void main( String[] args )
    {
        arguments(args);

        if(fileExists)
            System.out.println("File '" + filePath + "' loaded.");
        else
            System.out.println("Error: No valid file, exiting.");
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
}
