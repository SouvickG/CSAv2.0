/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package csav2.pkg0;

/**
 *
 * @author Souvick
 */
public class FeatureTest {
    
    public void testfeatures(Statistics st){
        String op="",op1="";
        int id;
        op1+="Num\tSource\tTarget\tAutosentiment\tUnimatch\tBimatch\tTrimatch\tPositivematch\tUnimatchn\tBimatchn\tTrimatchn\t\tNegativematch\tFW\tJJ\tRB\tRB_JJ\tamod\tacomp\tadvmod\tBLPOS\tBLNEG\tVSPOS\tVSNEG\tDSENT\tID\n";
        System.out.println("Num\tSource\tTarget\tAutosentiment\tUnimatchp\tBimatchp\tTrimatchp\tPositivematch\tUnimatchn\tBimatchn\tTrimatchn\t\tNegativematch\tFW\tJJ\tRB\tRB_JJ\tamod\tacomp\tadvmod\tBLPOS\tBLNEG\tVSPOS\tVSNEG\tDSENT\tID\n");
        for(int i=0; i<st.globalcounter; i++){
            op1+=st.id[i]+"\t";
            
            //Coulumn 1: Source
            System.out.print(st.source[i]+"\t");
            op1+=st.source[i]+"\t";
            
            //Column 2: Target
            System.out.print(st.target[i]+"\t");
            op1+=st.target[i]+"\t";
            
            //Column 3: Autosentiment
            System.out.print(st.autosentiment[i]+"\t");
            op+=st.autosentiment[i]+"\t";
            op1+=st.autosentiment[i]+"\t";
            
            System.out.print(st.unimatchp[i]+"\t");
            //op+=st.unimatchp[i]+"\t";
            op1+=st.unimatchp[i]+"\t";
            
            System.out.print(st.bimatchp[i]+"\t");
            //op+=st.bimatchp[i]+"\t";
            op1+=st.bimatchp[i]+"\t";
            
            System.out.print(st.trimatchp[i]+"\t");
            //op+=st.trimatchp[i]+"\t";
            op1+=st.trimatchp[i]+"\t";
            
            System.out.print(st.fourgmatchp[i]+"\t");
            //op+=st.fourgmatchp[i]+"\t";
            op1+=st.fourgmatchp[i]+"\t";
            
            //Column 4: Positive Polarity Match
            System.out.print(st.positivewords[i]+"\t");
            op+=st.positivewords[i]+"\t";
            op1+=st.positivewords[i]+"\t";
            
            System.out.print(st.unimatchn[i]+"\t");
            //op+=st.unimatchn[i]+"\t";
            op1+=st.unimatchn[i]+"\t";
            
            System.out.print(st.bimatchn[i]+"\t");
            //op+=st.bimatchn[i]+"\t";
            op1+=st.bimatchn[i]+"\t";
            
            System.out.print(st.trimatchn[i]+"\t");
            //op+=st.trimatchn[i]+"\t";
            op1+=st.trimatchn[i]+"\t";
            
            System.out.print(st.fourgmatchn[i]+"\t");
            //op+=st.fourgmatchn[i]+"\t";
            op1+=st.fourgmatchn[i]+"\t";
            
            //Column 5: Negative Polarity Match
            System.out.print(st.negativewords[i]+"\t");
            op+=st.negativewords[i]+"\t";
            op1+=st.negativewords[i]+"\t";
            
            //Column 6: FW
            System.out.print(st.isFWPresent[i]+"\t");
            op+=st.isFWPresent[i]+"\t";
            op1+=st.isFWPresent[i]+"\t";
            
            //Column 7: JJ
            System.out.print(st.isJJPresent[i]+"\t");
            op+=st.isJJPresent[i]+"\t";
            op1+=st.isJJPresent[i]+"\t";
            
            //Column 8: RB
            System.out.print(st.isRBPresent[i]+"\t");
            op+=st.isRBPresent[i]+"\t";
            op1+=st.isRBPresent[i]+"\t";
            
            //Column 9: RBJJ
            System.out.print(st.isRBJJPresent[i]+"\t");
            op+=st.isRBJJPresent[i]+"\t";
            op1+=st.isRBJJPresent[i]+"\t";
            
            //Column 10: AMOD
            System.out.print(st.isAmodPresent[i]+"\t");            
            op+=st.isAmodPresent[i]+"\t";
            op1+=st.isAmodPresent[i]+"\t";
            
            //Column 11: ACOMP
            System.out.print(st.isAcompPresent[i]+"\t");           
            op+=st.isAcompPresent[i]+"\t";
            op1+=st.isAcompPresent[i]+"\t";
            
            //Column 12: ADVMOD
            System.out.print(st.isAdvmodPresent[i]+"\t");            
            op+=st.isAdvmodPresent[i]+"\t";
            op1+=st.isAdvmodPresent[i]+"\t";
            
            //Column 13: BLPOS
            System.out.print(st.blpos[i]+"\t");            
            op+=st.blpos[i]+"\t";
            op1+=st.blpos[i]+"\t";
            
            //Column 14: BLNEG
            System.out.print(st.blneg[i]+"\t");            
            op+=st.blneg[i]+"\t";
            op1+=st.blneg[i]+"\t";
            
            //Column 15: VSPOS
            System.out.print(st.vspos[i]+"\t");            
            op+=st.vspos[i]+"\t";
            op1+=st.vspos[i]+"\t";
            
            //Column 16: VSNEG
            System.out.print(st.vsneg[i]+"\t");            
            op+=st.vsneg[i]+"\t";
            op1+=st.vsneg[i]+"\t";
            
            id = st.id[i];
            
            //Column 17: DEFAULT SENTI
            op+= "o"+"\t";
            op1+= "o"+"\t";
            
            //Column 18: ID
            op+= id+"\n";            
            op1+= id+"\n";  
        }
        ReaderWriter rw = new ReaderWriter();
        rw.writeToFile(op1,"FeatureFile_Test", "txt");
        /*
        Weka1 weka = new Weka1();
        try {
            weka.classifyTestSet(op, "F:\\CSS","F:\\CSS\\");
        } catch (Exception ex) {
            Logger.getLogger(FeaturePrint.class.getName()).log(Level.SEVERE, null, ex);
        }
        */
    }
    
}
