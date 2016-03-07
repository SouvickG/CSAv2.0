package csav2;
import java.io.FileNotFoundException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Souvick
 */
public class Exe_additive {
    
    public static void main(String[] args) throws FileNotFoundException{
        Trial();
        Test();
    }
    
    public static void Trial() throws FileNotFoundException {
        Exe_additive e = new Exe_additive();
        FeatureBuildTraining_additive fp = new FeatureBuildTraining_additive();
        fp.buildTrainingFeatures();
    }
    
    public static void Test() throws FileNotFoundException{
        Exe_additive e = new Exe_additive();
        FeatureBuildTest_additive ft = new FeatureBuildTest_additive();
        ft.buildTestFeatures();
    }   
    
}
