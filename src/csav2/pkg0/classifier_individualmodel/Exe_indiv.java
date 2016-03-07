/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package csav2.pkg0.classifier_individualmodel;

import java.io.FileNotFoundException;

/**
 *
 * @author Souvick
 */
public class Exe_indiv {
    public static void main(String[] args) throws FileNotFoundException{
        FeatureTraining training = new FeatureTraining();        
        FeatureTest test = new FeatureTest();
        training.trainFeatures();
        test.testfeatures();
    }
}
