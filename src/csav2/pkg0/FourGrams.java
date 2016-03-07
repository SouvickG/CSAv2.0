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
import java.util.HashMap;
import java.util.Scanner;

public class FourGrams {

    // Returns if the string is a punctuation 
    public boolean isPunctuation(String s){
        if(s.equals(",") || s.equals(";") || s.equals(".")|| s.equals("~"))
            return true;
        else return s.equals("/");
    }
    
    // Returns Fourgrams and their frequency
    public HashMap<String,Integer> getFourgramsWF(String text){
        //System.out.println("Inside getFourgrams");
        HashMap<String,Integer> hm = new HashMap<>();
        String w1="",w2="",w3="",w4="";
        Scanner scan = new Scanner(text);
        if (scan.hasNext()) {
            w1 = scan.next();
        }
        if (scan.hasNext()) {
            w2 = scan.next();
        }
        if (scan.hasNext()) {
            w3 = scan.next();
        }
        while(scan.hasNext()){
            w4 = scan.next();
            if(w1.length()<1 || isPunctuation(w1)){
                w1 = w2;
                w2 = w3;
                w3 = w4;
                continue;
            }                
            if(w2.length()<1 || isPunctuation(w2)){
                w2 = w3;
                w3 = w4;
                continue;
            }
            if(w3.length()<1 || isPunctuation(w3)){
                w3 = w4;
                continue;
            }
            if(w4.length()<1 || isPunctuation(w4)){
                continue;
            }
            
            String k = w1+"_"+w2+"_"+w3+"_"+w4;
            if(hm.containsKey(k)){
                int i = hm.get(k);
                hm.put(k, i+1);
            }
            else{
                hm.put(k,1);
            }
            w1 = w2;
            w2 = w3;
            w3 = w4;
        }
        //System.out.println(hm.toString());
        SortHashMapv2 shm= new SortHashMapv2();
        hm = shm.sortHashMapByValuesD(hm);
        return hm;
    }
    
    // Returns Trigram and their frequency
    public String getFourgrams(String text){
        //System.out.println("Inside getTrigrams");
        HashMap<String,Integer> hm = new HashMap<>();
        String w1="",w2="",w3="",w4="";
        Scanner scan = new Scanner(text);
        if (scan.hasNext()) {
            w1 = scan.next();
        }
        if (scan.hasNext()) {
            w2 = scan.next();
        }
        if (scan.hasNext()) {
            w3 = scan.next();
        }
        while(scan.hasNext()){
            w4 = scan.next();
            if(w1.length()<1 || isPunctuation(w1)){
                w1 = w2;
                w2 = w3;
                w3 = w4;
                continue;
            }                
            if(w2.length()<1 || isPunctuation(w2)){
                w2 = w3;
                w3 = w4;
                continue;
            }
            if(w3.length()<1 || isPunctuation(w3)){
                w3 = w4;
                continue;
            }
            if(w4.length()<1 || isPunctuation(w4)){
                continue;
            }
            
            String k = w1+"_"+w2+"_"+w3+"_"+w4;
            if(hm.containsKey(k)){
                int i = hm.get(k);
                hm.put(k, i+1);
            }
            else{
                hm.put(k,1);
            }
            w1 = w2;
            w2 = w3;
            w3 = w4;
        }
        //System.out.println(hm.toString());
        SortHashMapv2 shm= new SortHashMapv2();
        return shm.hashToString(hm);
    }
    
        
    // Returns Fourgram and their frequency in string form
    public String getFourgramsWF_toString(String text){
        return getFourgramsWF(text).toString();
    }
    
    // NEW METHOD
    // Calculates a similarity index of the two hashmaps
    public int compareHashmaps(HashMap<String,Integer> hm1, HashMap<String,Integer> hm2){
        int match = 0;
        //int count = hm1.size() + hm2.size();
        for (final String key : hm1.keySet()) {
            if (hm2.containsKey(key)) {
                match+=1;                
            }
        }
        return match;
    }
    
    // Returns the fourgram similarity measure for the 2 imput texts
    public int getFourgramSimilarity(String input1, String input2){
        return compareHashmaps(getFourgramsWF(input1),getFourgramsWF(input2));
    }
}

