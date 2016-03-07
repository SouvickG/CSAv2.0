/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package csav2.pkg0;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author Souvick
 */
public class PolarPhraseIdentification {
       
    public HashMap<String,Integer> getPositivePolarityWordsHM(){
        Scanner scanner;
        HashMap<String,Integer> hm = new HashMap();
        File file = new File("Resources\\positives.txt");
        try {
            scanner = new Scanner(file);
            while(scanner.hasNextLine()){
                String pword = scanner.nextLine();
                if(hm.containsKey(pword)){
                }
                else{
                    hm.put(pword,1);
                }
            }                
        } catch (FileNotFoundException e) {
            System.out.println("Scanner error:"+e);
        }  
        return hm;
    }
    
    public HashMap<String,Integer> getNegativePolarityWordsHM(){
        Scanner scanner;
        HashMap<String,Integer> hm = new HashMap();
        File file = new File("Resources\\negatives.txt");
        try {
            scanner = new Scanner(file);
            while(scanner.hasNextLine()){
                String nword = scanner.nextLine();
                if(hm.containsKey(nword)){
                }
                else{
                    hm.put(nword,1);
                }
            }                
        } catch (FileNotFoundException e) {
            System.out.println("Scanner error:"+e);
        }  
        return hm;
    }
    
    public void createPosNegHM(){
        Statistics s = new Statistics();
        PolarPhraseIdentification p = new PolarPhraseIdentification();
        s.hmnegatives = p.getNegativePolarityWordsHM();
        s.hmpositives = p.getPositivePolarityWordsHM();
    }
    
    // Calculates a similarity index of the two hashmaps
    public String compareHashmaps(HashMap<String,Integer> hm1, HashMap<String,Integer> hm2){
        int match = 0;
        String op ="";
        for (final String key : hm1.keySet()) {
            if (hm2.containsKey(key)) {
                match+=1;               
                op+=(key.toString()+"\t"+match+"\n");
            }
        }
        return op;
    }
    
    public static void main(String args[]){
        Statistics s = new Statistics();
        SentenceExtractionv2 se2 = new SentenceExtractionv2();
        PolarPhraseIdentification p = new PolarPhraseIdentification();
        ParserTagging pt = new ParserTagging();
        GetWordAndPOSv2 gwp = new GetWordAndPOSv2();
        Unigrams u = new Unigrams();
        Bigrams b = new Bigrams();
        Trigrams t = new Trigrams();
        FourGrams f = new FourGrams();
        
        se2.extractWindow(s);
        s.hmnegatives = p.getNegativePolarityWordsHM();
        s.hmpositives = p.getPositivePolarityWordsHM();
        
        String op1p="",op2p="",op3p="",op4p="",op1n="",op2n="",op3n="",op4n="";
        for(int i=0; i<s.globalcounter; i++){
            System.out.println(i);
            s.taggedoutput[i] = pt.postag(s.sentence[i]);
            s.words[i] = gwp.getWords(s.taggedoutput[i]);
            s.bigrams[i] = b.getBigramsWF(s.words[i]);
            s.trigrams[i] = t.getTrigramsWF(s.words[i]);
            s.fourgrams[i] = f.getFourgramsWF(s.words[i]);
            
            // Obtaining match with positive polar phrases
            op1p+= p.compareHashmaps(u.getUnigramsWF(s.words[i]),s.hmpositives);
            op2p+= p.compareHashmaps(b.getBigramsWF(s.words[i]),s.hmpositives);
            op3p+= p.compareHashmaps(t.getTrigramsWF(s.words[i]),s.hmpositives);
            op4p+= p.compareHashmaps(f.getFourgramsWF(s.words[i]),s.hmpositives);
            
            // Obtaining match with negative polar phrases
            op1n+= p.compareHashmaps(u.getUnigramsWF(s.words[i]),s.hmnegatives);
            op2n+= p.compareHashmaps(b.getBigramsWF(s.words[i]),s.hmnegatives);
            op3n+= p.compareHashmaps(t.getTrigramsWF(s.words[i]),s.hmnegatives);
            op4n+= p.compareHashmaps(f.getFourgramsWF(s.words[i]), s.hmnegatives);            
        }
        System.out.println("Unigrams positive:\n"+op1p);
        System.out.println("Bigrams positive:\n"+op2p);
        System.out.println("Trigrams positive:\n"+op3p);
        System.out.println("4grams positive:\n"+op4p);
        System.out.println("Unigrams negative:\n"+op1n);
        System.out.println("Bigrams negative:\n"+op2n);
        System.out.println("Trigrams negative:\n"+op3n);
        System.out.println("4grams negative:\n"+op4n);
        
    }
}
