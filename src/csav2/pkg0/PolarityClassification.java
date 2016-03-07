/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package csav2.pkg0;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author Souvick
 */
public class PolarityClassification {
        
    public HashMap<String,Double> getBLPositiveWordsHM(){
        Scanner scanner;
        HashMap<String,Double> hm = new HashMap();
        File file = new File("Resources//BL_pos.txt");
        try {
            scanner = new Scanner(file);
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                String arr[] = line.split("\t");
                String pword = arr[0];
                Double score = Double.parseDouble(arr[1]);
                if(hm.containsKey(pword)){
                }
                else{
                    hm.put(pword,score);
                }
            }                
        } catch (FileNotFoundException e) {
            System.out.println("Scanner error:"+e);
        }  
        return hm;
    }
    
    public HashMap<String,Double> getBLNegativeWordsHM(){
        Scanner scanner;
        HashMap<String,Double> hm = new HashMap();
        File file = new File("Resources//BL_neg.txt");
        try {
            scanner = new Scanner(file);            
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                String arr[] = line.split("\t");
                String nword = arr[0];
                Double score = Double.parseDouble(arr[1]);
                
                if(hm.containsKey(nword)){
                }
                else{
                    hm.put(nword,score);
                }
            }                
        } catch (FileNotFoundException e) {
            System.out.println("Scanner error:"+e);
        }  
        return hm;
    }
    
    
    public HashMap<String,Double> getVSPositiveWordsHM(){
        Scanner scanner;
        HashMap<String,Double> hm = new HashMap();
        File file = new File("Resources//VS_pos.txt");
        try {
            scanner = new Scanner(file);
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                String arr[] = line.split("\t");
                String pword = arr[0];
                Double score = Double.parseDouble(arr[1]);
                
                if(hm.containsKey(pword)){
                }
                else{
                    hm.put(pword,score);
                }
            }                
        } catch (FileNotFoundException e) {
            System.out.println("Scanner error:"+e);
        }  
        return hm;
    }
    
    public HashMap<String,Double> getVSNegativeWordsHM(){
        Scanner scanner;
        HashMap<String,Double> hm = new HashMap();
        File file = new File("Resources//VS_neg.txt");
        try {
            scanner = new Scanner(file);
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                String arr[] = line.split("\t");
                String nword = arr[0];
                Double score = Double.parseDouble(arr[1]);
                
                if(hm.containsKey(nword)){
                }
                else{
                    hm.put(nword,score);
                }
            }                
        } catch (FileNotFoundException e) {
            System.out.println("Scanner error:"+e);
        }  
        return hm;
    }
    
    public void createPosNegHM(){
        Statistics s = new Statistics();
        PolarityClassification p = new PolarityClassification();
        s.hmblpos = p.getBLPositiveWordsHM();
        s.hmblneg = p.getBLNegativeWordsHM();
        s.hmvspos = p.getVSPositiveWordsHM();
        s.hmvsneg = p.getVSNegativeWordsHM();
    }
    
    public static void main(String args[]) throws IOException{
        PolarityClassification p = new PolarityClassification();
        System.out.println("BL POS:\n"+p.getBLPositiveWordsHM().toString());
        System.out.println("BL NEG:\n"+p.getBLNegativeWordsHM().toString());
        System.out.println("VS POS:\n"+p.getVSPositiveWordsHM().toString());
        System.out.println("VS POS:\n"+p.getVSNegativeWordsHM().toString());
    }
}
