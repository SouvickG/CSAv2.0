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
public class FeatureBuildTraining_additive {
    
    public void buildTrainingFeatures() throws FileNotFoundException{
        String op="",op1="",op2="", op3="", op4="", op5="",op6="",op7="",id="";
        
        FileSelection fs = new FileSelection();
        String path = fs.fileSelect("Select training file for feature analysis");
        File file = new File(path);
        
        Scanner scnr = new Scanner(file);
        String line=scnr.nextLine();
        while(scnr.hasNextLine()){
         
            line=scnr.nextLine();
            //System.out.println(line);
            
            if(line!=""){
                String arr[] = line.split("\t");
            
                op1+=arr[3]+"\t"+arr[25]+"\n";
                op2+=arr[3]+"\t"+arr[8]+"\t"+arr[13]+"\t"+arr[25]+"\n";
                op3+=arr[3]+"\t"+arr[8]+"\t"+arr[13]+"\t"+arr[14]+"\t"+arr[15]+"\t"+arr[16]+"\t"+arr[17]+"\t"+arr[25]+"\n";
                op4+=arr[3]+"\t"+arr[8]+"\t"+arr[13]+"\t"+arr[14]+"\t"+arr[15]+"\t"+arr[16]+"\t"+arr[17]+"\t"+arr[18]+"\t"+arr[19]+"\t"+arr[20]+"\t"+arr[25]+"\n";
                op5+=arr[3]+"\t"+arr[8]+"\t"+arr[13]+"\t"+arr[14]+"\t"+arr[15]+"\t"+arr[16]+"\t"+arr[17]+"\t"+arr[18]+"\t"+arr[19]+"\t"+arr[20]+"\t"+arr[21]+"\t"+arr[22]+"\t"+arr[25]+"\n";
                op6+=arr[3]+"\t"+arr[8]+"\t"+arr[13]+"\t"+arr[14]+"\t"+arr[15]+"\t"+arr[16]+"\t"+arr[17]+"\t"+arr[18]+"\t"+arr[19]+"\t"+arr[20]+"\t"+arr[21]+"\t"+arr[22]+"\t"+arr[23]+"\t"+arr[24]+"\t"+arr[25]+"\n";
            }
        }
        
        System.out.println("In featureprint2:\n"+op2);
        Weka_additive weka = new Weka_additive();
        
        try {
            weka.createTrainingFeatureFile1(op1);            
        } catch (Exception ex) {
            Logger.getLogger(FeatureBuildTraining_additive.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            weka.createTrainingFeatureFile2(op2);
        } catch (Exception ex) {
            Logger.getLogger(FeatureBuildTraining_additive.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            weka.createTrainingFeatureFile3(op3);
        } catch (Exception ex) {
            Logger.getLogger(FeatureBuildTraining_additive.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            weka.createTrainingFeatureFile4(op4);
        } catch (Exception ex) {
            Logger.getLogger(FeatureBuildTraining_additive.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            weka.createTrainingFeatureFile5(op5);
        } catch (Exception ex) {
            Logger.getLogger(FeatureBuildTraining_additive.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            weka.createTrainingFeatureFile6(op6);
        } catch (Exception ex) {
            Logger.getLogger(FeatureBuildTraining_additive.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
