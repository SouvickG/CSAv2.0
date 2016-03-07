package csav2;

import csav2.pkg0.FileSelection;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Souvick
 */
public class FeatureBuildTest_additive {
 
    public void buildTestFeatures() throws FileNotFoundException{
        String op="",op1="",op2="",op3="",op4="",op5="",op6="";
        int id;
        
        FileSelection fs = new FileSelection();
        String path = fs.fileSelect("Select test file for feature analysis");
        File file = new File(path);
        
        Scanner scnr = new Scanner(file);
        String line=scnr.nextLine();
        int lineNum=0;
        
        while(scnr.hasNextLine()){
            
            line=scnr.nextLine();
            lineNum++;
            
            if(line!=""){
                String arr[] = line.split("\t");
                if(arr.length!=25){
                    System.out.println("Error:"+lineNum+"\t"+arr[0]+"\t"+arr[23]);
                }
                //System.out.println(line);
                op1+=arr[3]+"\t"+arr[25]+"\t"+arr[26]+"\n";
                op2+=arr[3]+"\t"+arr[8]+"\t"+arr[13]+"\t"+arr[25]+"\t"+arr[26]+"\n";
                op3+=arr[3]+"\t"+arr[8]+"\t"+arr[13]+"\t"+arr[14]+"\t"+arr[15]+"\t"+arr[16]+"\t"+arr[17]+"\t"+arr[25]+"\t"+arr[26]+"\n";
                op4+=arr[3]+"\t"+arr[8]+"\t"+arr[13]+"\t"+arr[14]+"\t"+arr[15]+"\t"+arr[16]+"\t"+arr[17]+"\t"+arr[18]+"\t"+arr[19]+"\t"+arr[20]+"\t"+arr[25]+"\t"+arr[26]+"\n";
                op5+=arr[3]+"\t"+arr[8]+"\t"+arr[13]+"\t"+arr[14]+"\t"+arr[15]+"\t"+arr[16]+"\t"+arr[17]+"\t"+arr[18]+"\t"+arr[19]+"\t"+arr[20]+"\t"+arr[21]+"\t"+arr[22]+"\t"+arr[25]+"\t"+arr[26]+"\n";
                op6+=arr[3]+"\t"+arr[8]+"\t"+arr[13]+"\t"+arr[14]+"\t"+arr[15]+"\t"+arr[16]+"\t"+arr[17]+"\t"+arr[18]+"\t"+arr[19]+"\t"+arr[20]+"\t"+arr[21]+"\t"+arr[22]+"\t"+arr[23]+"\t"+arr[24]+"\t"+arr[25]+"\t"+arr[26]+"\n";
            }
        }
        
        Weka_additive weka = new Weka_additive();
        
        try {
            weka.classifyTestSet1(op1);
        } catch (Exception ex) {
            Logger.getLogger(FeatureBuildTest_additive.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            weka.classifyTestSet2(op2);
        } catch (Exception ex) {
            Logger.getLogger(FeatureBuildTest_additive.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            weka.classifyTestSet3(op3);
        } catch (Exception ex) {
            Logger.getLogger(FeatureBuildTest_additive.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            weka.classifyTestSet4(op4);
        } catch (Exception ex) {
            Logger.getLogger(FeatureBuildTest_additive.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            weka.classifyTestSet5(op5);
        } catch (Exception ex) {
            Logger.getLogger(FeatureBuildTest_additive.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            weka.classifyTestSet6(op6);
        } catch (Exception ex) {
            Logger.getLogger(FeatureBuildTest_additive.class.getName()).log(Level.SEVERE, null, ex);
        }
    }  
}
