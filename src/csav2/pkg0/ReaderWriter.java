/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package csav2.pkg0;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author Souvick
 */
public class ReaderWriter {
        
    // Reads the file and returns a string
    public String readFile(String filename, String ext) throws IOException {
        //System.out.println("Inside readFile");
        String filepath = "//Resources//"+filename+"."+ext;
        BufferedReader br = new BufferedReader(new FileReader(filepath));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }
            return sb.toString();
        } finally {
            br.close();
        }
    }

    // Writes output string to file
    public void writeToFile(String str,String filename, String ext){
        String path = "//Resources//"+filename+"."+ext;
        try{
            PrintWriter out = new PrintWriter(path);
            out.println(str);
            out.close();
        }
        catch(FileNotFoundException e){System.out.println(e);}
    }
}
