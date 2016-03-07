/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csav2.pkg0.classifier_eliminationmodel;

import csav2.pkg0.ReaderWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Random;
import java.util.StringTokenizer;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
//import weka.classifiers.trees.RandomForest;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.core.converters.ArffSaver;

public class Weka {
    	
        // creates model for autosentiment
        public void createTrainingFeatureFile1(String input) throws Exception {
            String file = "Classifier\\featurefile_elimination_trial1.arff"; 
            ArffLoader loader = new ArffLoader();
           
            //ATTRIBUTES
            Attribute attr[]= new Attribute[50];
            
            //numeric
            attr[0] = new Attribute("Positivematch");
            attr[1] = new Attribute("Negativematch");
            attr[2] = new Attribute("FW");
            attr[3] = new Attribute("JJ");
            attr[4] = new Attribute("RB");
            attr[5] = new Attribute("RB_JJ");
            attr[6] = new Attribute("amod");
            attr[7] = new Attribute("acomp");
            attr[8] = new Attribute("advmod");
            attr[9] = new Attribute("BLPositive");
            attr[10] = new Attribute("BLNegative");
            attr[11] = new Attribute("VSPositive");
            attr[12] = new Attribute("VSNegative");            
                        
            //class
            FastVector classValue = new FastVector(3);
            classValue.addElement("p");
            classValue.addElement("n");
            classValue.addElement("o");
            attr[13] = new Attribute("answer", classValue);
            
            FastVector attrs = new FastVector();
            attrs.addElement(attr[0]);
            attrs.addElement(attr[1]);
            attrs.addElement(attr[2]);
            attrs.addElement(attr[3]);
            attrs.addElement(attr[4]);
            attrs.addElement(attr[5]);
            attrs.addElement(attr[6]);
            attrs.addElement(attr[7]);
            attrs.addElement(attr[8]);
            attrs.addElement(attr[9]);
            attrs.addElement(attr[10]);
            attrs.addElement(attr[11]);
            attrs.addElement(attr[12]);
            attrs.addElement(attr[13]);
                        
            // Add Instances
            Instances dataset = new Instances("my_dataset", attrs, 0);
		
	    if(new File(file).isFile()) {
                loader.setFile(new File(file));
                dataset = loader.getDataSet();	
            }
            
            System.out.println("-----------------------------------------");
            System.out.println(input);
            System.out.println("-----------------------------------------");
            
            StringTokenizer tokenizer= new StringTokenizer(input);
            
            while(tokenizer.hasMoreTokens()){
                Instance example = new Instance(14);
                for(int j=0; j<14; j++) {
                    String st= tokenizer.nextToken();
                    System.out.println(j+" "+st);
                    if(j==13)
                        example.setValue(attr[j], st);
                    else
                        example.setValue(attr[j], Integer.parseInt(st));
                }
                dataset.add(example);
            }
            
            //Save dataset
            ArffSaver saver = new ArffSaver();
            saver.setInstances(dataset);
            saver.setFile(new File(file));
            saver.writeBatch();
            
            //Read dataset
            loader.setFile(new File(file));
            dataset = loader.getDataSet();	
            
            //Build classifier
            dataset.setClassIndex(13);
            Classifier classifier = new J48();
            classifier.buildClassifier(dataset);
            
            //Save classifier
            String file1="Classifier\\classifier_wo_autosentiment.model";
            OutputStream os = new FileOutputStream(file1);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(os);
            objectOutputStream.writeObject(classifier);		
	      
            
            // Comment out if not needed
            //Read classifier back
            InputStream is = new FileInputStream(file1);
            ObjectInputStream objectInputStream = new ObjectInputStream(is);
            classifier = (Classifier) objectInputStream.readObject();
            objectInputStream.close();
		
            //Evaluate resample if needed
            //dataset = dataset.resample(new Random(42));
            //split to 70:30 learn and test set
            double percent = 70.0;
            int trainSize = (int) Math.round(dataset.numInstances() * percent / 100);
            int testSize = dataset.numInstances() - trainSize;
            Instances train = new Instances(dataset, 0, trainSize);
            Instances test = new Instances(dataset, trainSize, testSize);
            train.setClassIndex(13);
            test.setClassIndex(13);
            
            //Evaluate
            Evaluation eval = new Evaluation(dataset); //trainset
            eval.crossValidateModel(classifier, dataset, 10, new Random(1));
            System.out.println("EVALUATION:\n"+eval.toSummaryString());
            System.out.println("WEIGHTED MEASURE:\n"+eval.weightedFMeasure());
            System.out.println("WEIGHTED PRECISION:\n"+eval.weightedPrecision());
            System.out.println("WEIGHTED RECALL:\n"+eval.weightedRecall());	
        } 
        
        //creates model for autosentiment and n-grams
        public void createTrainingFeatureFile2(String input) throws Exception {
            String file = "Classifier\\featurefile_elimination_trial2.arff"; 
            ArffLoader loader = new ArffLoader();
           
            //ATTRIBUTES
            Attribute attr[]= new Attribute[50];
            
            //numeric
            attr[0] = new Attribute("Autosentiment");
            attr[1] = new Attribute("FW");
            attr[2] = new Attribute("JJ");
            attr[3] = new Attribute("RB");
            attr[4] = new Attribute("RB_JJ");
            attr[5] = new Attribute("amod");
            attr[6] = new Attribute("acomp");
            attr[7] = new Attribute("advmod");
            attr[8] = new Attribute("BLPositive");
            attr[9] = new Attribute("BLNegative");
            attr[10] = new Attribute("VSPositive");
            attr[11] = new Attribute("VSNegative");
            
            //class
            FastVector classValue = new FastVector(3);
            classValue.addElement("p");
            classValue.addElement("n");
            classValue.addElement("o");
            attr[12] = new Attribute("answer", classValue);
            
            FastVector attrs = new FastVector();
            attrs.addElement(attr[0]);
            attrs.addElement(attr[1]);
            attrs.addElement(attr[2]);
            attrs.addElement(attr[3]);
            attrs.addElement(attr[4]);
            attrs.addElement(attr[5]);
            attrs.addElement(attr[6]);
            attrs.addElement(attr[7]);
            attrs.addElement(attr[8]);
            attrs.addElement(attr[9]);
            attrs.addElement(attr[10]);
            attrs.addElement(attr[11]);
            attrs.addElement(attr[12]);
                        
            // Add Instances
            Instances dataset = new Instances("my_dataset", attrs, 0);
		
	    if(new File(file).isFile()) {
                loader.setFile(new File(file));
                dataset = loader.getDataSet();	
            }
            
            System.out.println("-----------------------------------------");
            System.out.println(input);
            System.out.println("-----------------------------------------");
            
            StringTokenizer tokenizer= new StringTokenizer(input);
            
            while(tokenizer.hasMoreTokens()){
                Instance example = new Instance(13);
                for(int j=0; j<13; j++) {
                    String st= tokenizer.nextToken();
                    System.out.println(j+" "+st);
                    if(j==0)
                        example.setValue(attr[j], Float.parseFloat(st));
                    else if(j==12)
                        example.setValue(attr[j], st);
                    else
                        example.setValue(attr[j], Integer.parseInt(st));
                }
                dataset.add(example);
            }
            
            //Save dataset
            ArffSaver saver = new ArffSaver();
            saver.setInstances(dataset);
            saver.setFile(new File(file));
            saver.writeBatch();
            
            //Read dataset
            loader.setFile(new File(file));
            dataset = loader.getDataSet();	
            
            //Build classifier
            dataset.setClassIndex(12);
            Classifier classifier = new J48();
            classifier.buildClassifier(dataset);
            
            //Save classifier
            String file1="Classifier\\classifier_wo_wordlist.model";
            OutputStream os = new FileOutputStream(file1);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(os);
            objectOutputStream.writeObject(classifier);		
	      
            
            // Comment out if not needed
            //Read classifier back
            InputStream is = new FileInputStream(file1);
            ObjectInputStream objectInputStream = new ObjectInputStream(is);
            classifier = (Classifier) objectInputStream.readObject();
            objectInputStream.close();
		
            //Evaluate resample if needed
            //dataset = dataset.resample(new Random(42));
            //split to 70:30 learn and test set
            double percent = 70.0;
            int trainSize = (int) Math.round(dataset.numInstances() * percent / 100);
            int testSize = dataset.numInstances() - trainSize;
            Instances train = new Instances(dataset, 0, trainSize);
            Instances test = new Instances(dataset, trainSize, testSize);
            train.setClassIndex(4);
            test.setClassIndex(4);
            
            //Evaluate
            Evaluation eval = new Evaluation(dataset); //trainset
            eval.crossValidateModel(classifier, dataset, 10, new Random(1));
            System.out.println("EVALUATION:\n"+eval.toSummaryString());
            System.out.println("WEIGHTED MEASURE:\n"+eval.weightedFMeasure());
            System.out.println("WEIGHTED PRECISION:\n"+eval.weightedPrecision());
            System.out.println("WEIGHTED RECALL:\n"+eval.weightedRecall());            
        } 
    
        //creates model for autosentiment and n-grams and pos
        public void createTrainingFeatureFile3(String input) throws Exception {
            String file = "Classifier\\feature_file_trial3.arff"; 
            ArffLoader loader = new ArffLoader();
           
            //ATTRIBUTES
            Attribute attr[]= new Attribute[50];
            
            //numeric
            attr[0] = new Attribute("Autosentiment");
            attr[1] = new Attribute("Positivematch");
            attr[2] = new Attribute("Negativematch");
            attr[3] = new Attribute("amod");
            attr[4] = new Attribute("acomp");
            attr[5] = new Attribute("advmod");
            attr[6] = new Attribute("BLPositive");
            attr[7] = new Attribute("BLNegative");
            attr[8] = new Attribute("VSPositive");
            attr[9] = new Attribute("VSNegative");
            
            //class
            FastVector classValue = new FastVector(3);
            classValue.addElement("p");
            classValue.addElement("n");
            classValue.addElement("o");
            attr[10] = new Attribute("answer", classValue);
            
            FastVector attrs = new FastVector();
            attrs.addElement(attr[0]);
            attrs.addElement(attr[1]);
            attrs.addElement(attr[2]);
            attrs.addElement(attr[3]);
            attrs.addElement(attr[4]);
            attrs.addElement(attr[5]);
            attrs.addElement(attr[6]);
            attrs.addElement(attr[7]);
            attrs.addElement(attr[8]);
            attrs.addElement(attr[9]);
            attrs.addElement(attr[10]);
            
            // Add Instances
            Instances dataset = new Instances("my_dataset", attrs, 0);
		
	    if(new File(file).isFile()) {
                loader.setFile(new File(file));
                dataset = loader.getDataSet();	
            }
            
            System.out.println("-----------------------------------------");
            System.out.println(input);
            System.out.println("-----------------------------------------");
            
            StringTokenizer tokenizer= new StringTokenizer(input);
            
            while(tokenizer.hasMoreTokens()){
                Instance example = new Instance(11);
                for(int j=0; j<11; j++) {
                    String st= tokenizer.nextToken();
                    System.out.println(j+" "+st);
                    if(j==0)
                        example.setValue(attr[j], Float.parseFloat(st));
                    else if(j==10)
                        example.setValue(attr[j], st);
                    else
                        example.setValue(attr[j], Integer.parseInt(st));
                }
                dataset.add(example);
            }
            
            //Save dataset
            ArffSaver saver = new ArffSaver();
            saver.setInstances(dataset);
            saver.setFile(new File(file));
            saver.writeBatch();
            
            //Read dataset
            loader.setFile(new File(file));
            dataset = loader.getDataSet();	
            
            //Build classifier
            dataset.setClassIndex(10);
            Classifier classifier = new J48();
            classifier.buildClassifier(dataset);
            
            //Save classifier
            String file1="Classifier\\classifier_wo_pos.model";
            OutputStream os = new FileOutputStream(file1);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(os);
            objectOutputStream.writeObject(classifier);		
	      
            
            // Comment out if not needed
            //Read classifier back
            InputStream is = new FileInputStream(file1);
            ObjectInputStream objectInputStream = new ObjectInputStream(is);
            classifier = (Classifier) objectInputStream.readObject();
            objectInputStream.close();
		
            //Evaluate resample if needed
            //dataset = dataset.resample(new Random(42));
            //split to 70:30 learn and test set
            double percent = 70.0;
            int trainSize = (int) Math.round(dataset.numInstances() * percent / 100);
            int testSize = dataset.numInstances() - trainSize;
            Instances train = new Instances(dataset, 0, trainSize);
            Instances test = new Instances(dataset, trainSize, testSize);
            train.setClassIndex(10);
            test.setClassIndex(10);
            
            //Evaluate
            Evaluation eval = new Evaluation(dataset); //trainset
            eval.crossValidateModel(classifier, dataset, 10, new Random(1));
            System.out.println("EVALUATION:\n"+eval.toSummaryString());
            System.out.println("WEIGHTED MEASURE:\n"+eval.weightedFMeasure());
            System.out.println("WEIGHTED PRECISION:\n"+eval.weightedPrecision());
            System.out.println("WEIGHTED RECALL:\n"+eval.weightedRecall());
        } 
    
        //creates model for autosentiment and n-grams and pos and dep
        public void createTrainingFeatureFile4(String input) throws Exception {
            String file = "Classifier\\featurefile_elimination_trial4.arff"; 
            ArffLoader loader = new ArffLoader();
           
            //ATTRIBUTES
            Attribute attr[]= new Attribute[50];
            
            //numeric
            attr[0] = new Attribute("Autosentiment");
            attr[1] = new Attribute("Positivematch");
            attr[2] = new Attribute("Negativematch");
            attr[3] = new Attribute("FW");
            attr[4] = new Attribute("JJ");
            attr[5] = new Attribute("RB");
            attr[6] = new Attribute("RB_JJ");
            attr[7] = new Attribute("BLPositive");
            attr[8] = new Attribute("BLNegative");
            attr[9] = new Attribute("VSPositive");
            attr[10] = new Attribute("VSNegative");
            
            //class
            FastVector classValue = new FastVector(3);
            classValue.addElement("p");
            classValue.addElement("n");
            classValue.addElement("o");
            attr[11] = new Attribute("answer", classValue);
            
            FastVector attrs = new FastVector();
            attrs.addElement(attr[0]);
            attrs.addElement(attr[1]);
            attrs.addElement(attr[2]);
            attrs.addElement(attr[3]);
            attrs.addElement(attr[4]);
            attrs.addElement(attr[5]);
            attrs.addElement(attr[6]);
            attrs.addElement(attr[7]);
            attrs.addElement(attr[8]);
            attrs.addElement(attr[9]);
            attrs.addElement(attr[10]);
            attrs.addElement(attr[11]);
                        
            // Add Instances
            Instances dataset = new Instances("my_dataset", attrs, 0);
		
	    if(new File(file).isFile()) {
                loader.setFile(new File(file));
                dataset = loader.getDataSet();	
            }
            
            System.out.println("-----------------------------------------");
            System.out.println(input);
            System.out.println("-----------------------------------------");
            
            StringTokenizer tokenizer= new StringTokenizer(input);
            
            while(tokenizer.hasMoreTokens()){
                Instance example = new Instance(12);
                for(int j=0; j<12; j++) {
                    String st= tokenizer.nextToken();
                    System.out.println(j+" "+st);
                    if(j==0)
                        example.setValue(attr[j], Float.parseFloat(st));
                    else if(j==11)
                        example.setValue(attr[j], st);
                    else
                        example.setValue(attr[j], Integer.parseInt(st));
                }
                dataset.add(example);
            }
            
            //Save dataset
            ArffSaver saver = new ArffSaver();
            saver.setInstances(dataset);
            saver.setFile(new File(file));
            saver.writeBatch();
            
            //Read dataset
            loader.setFile(new File(file));
            dataset = loader.getDataSet();	
            
            //Build classifier
            dataset.setClassIndex(11);
            Classifier classifier = new J48();
            classifier.buildClassifier(dataset);
            
            //Save classifier
            String file1="Classifier\\classifier_wo_dependency.model";
            OutputStream os = new FileOutputStream(file1);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(os);
            objectOutputStream.writeObject(classifier);		
	      
            
            // Comment out if not needed
            //Read classifier back
            InputStream is = new FileInputStream(file1);
            ObjectInputStream objectInputStream = new ObjectInputStream(is);
            classifier = (Classifier) objectInputStream.readObject();
            objectInputStream.close();
		
            //Evaluate resample if needed
            //dataset = dataset.resample(new Random(42));
            //split to 70:30 learn and test set
            double percent = 70.0;
            int trainSize = (int) Math.round(dataset.numInstances() * percent / 100);
            int testSize = dataset.numInstances() - trainSize;
            Instances train = new Instances(dataset, 0, trainSize);
            Instances test = new Instances(dataset, trainSize, testSize);
            train.setClassIndex(11);
            test.setClassIndex(11);
            
            //Evaluate
            Evaluation eval = new Evaluation(dataset); //trainset
            eval.crossValidateModel(classifier, dataset, 10, new Random(1));
            System.out.println("EVALUATION:\n"+eval.toSummaryString());
            System.out.println("WEIGHTED MEASURE:\n"+eval.weightedFMeasure());
            System.out.println("WEIGHTED PRECISION:\n"+eval.weightedPrecision());
            System.out.println("WEIGHTED RECALL:\n"+eval.weightedRecall());           
        } 
    
        //creates model for autosentiment and n-grams and pos and dep and wl1
        public void createTrainingFeatureFile5(String input) throws Exception {
            String file = "Classifier\\featurefile_elimination_trial5.arff"; 
            ArffLoader loader = new ArffLoader();
           
            //ATTRIBUTES
            Attribute attr[]= new Attribute[50];
            
            //numeric
            attr[0] = new Attribute("Autosentiment");
            attr[1] = new Attribute("Positivematch");
            attr[2] = new Attribute("Negativematch");
            attr[3] = new Attribute("FW");
            attr[4] = new Attribute("JJ");
            attr[5] = new Attribute("RB");
            attr[6] = new Attribute("RB_JJ");
            attr[7] = new Attribute("amod");
            attr[8] = new Attribute("acomp");
            attr[9] = new Attribute("advmod");
            attr[10] = new Attribute("VSPositive");
            attr[11] = new Attribute("VSNegative");
            
            //class
            FastVector classValue = new FastVector(3);
            classValue.addElement("p");
            classValue.addElement("n");
            classValue.addElement("o");
            attr[12] = new Attribute("answer", classValue);
            
            FastVector attrs = new FastVector();
            attrs.addElement(attr[0]);
            attrs.addElement(attr[1]);
            attrs.addElement(attr[2]);
            attrs.addElement(attr[3]);
            attrs.addElement(attr[4]);
            attrs.addElement(attr[5]);
            attrs.addElement(attr[6]);
            attrs.addElement(attr[7]);
            attrs.addElement(attr[8]);
            attrs.addElement(attr[9]);
            attrs.addElement(attr[10]);
            attrs.addElement(attr[11]);
            attrs.addElement(attr[12]);
                        
            // Add Instances
            Instances dataset = new Instances("my_dataset", attrs, 0);
		
	    if(new File(file).isFile()) {
                loader.setFile(new File(file));
                dataset = loader.getDataSet();	
            }
            
            System.out.println("-----------------------------------------");
            System.out.println(input);
            System.out.println("-----------------------------------------");
            
            StringTokenizer tokenizer= new StringTokenizer(input);
            
            while(tokenizer.hasMoreTokens()){
                Instance example = new Instance(13);
                for(int j=0; j<13; j++) {
                    String st= tokenizer.nextToken();
                    System.out.println(j+" "+st);
                    if(j==0)
                        example.setValue(attr[j], Float.parseFloat(st));
                    else if(j==12)
                        example.setValue(attr[j], st);
                    else
                        example.setValue(attr[j], Integer.parseInt(st));
                }
                dataset.add(example);
            }
            
            //Save dataset
            ArffSaver saver = new ArffSaver();
            saver.setInstances(dataset);
            saver.setFile(new File(file));
            saver.writeBatch();
            
            //Read dataset
            loader.setFile(new File(file));
            dataset = loader.getDataSet();	
            
            //Build classifier
            dataset.setClassIndex(12);
            Classifier classifier = new J48();
            classifier.buildClassifier(dataset);
            
            //Save classifier
            String file1="Classifier\\classifier_wo_blist.model";
            OutputStream os = new FileOutputStream(file1);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(os);
            objectOutputStream.writeObject(classifier);		
	      
            
            // Comment out if not needed
            //Read classifier back
            InputStream is = new FileInputStream(file1);
            ObjectInputStream objectInputStream = new ObjectInputStream(is);
            classifier = (Classifier) objectInputStream.readObject();
            objectInputStream.close();
		
            //Evaluate resample if needed
            //dataset = dataset.resample(new Random(42));
            //split to 70:30 learn and test set
            double percent = 70.0;
            int trainSize = (int) Math.round(dataset.numInstances() * percent / 100);
            int testSize = dataset.numInstances() - trainSize;
            Instances train = new Instances(dataset, 0, trainSize);
            Instances test = new Instances(dataset, trainSize, testSize);
            train.setClassIndex(12);
            test.setClassIndex(12);
            
            //Evaluate
            Evaluation eval = new Evaluation(dataset); //trainset
            eval.crossValidateModel(classifier, dataset, 10, new Random(1));
            System.out.println("EVALUATION:\n"+eval.toSummaryString());
            System.out.println("WEIGHTED MEASURE:\n"+eval.weightedFMeasure());
            System.out.println("WEIGHTED PRECISION:\n"+eval.weightedPrecision());
            System.out.println("WEIGHTED RECALL:\n"+eval.weightedRecall());           
        } 
        
        public void createTrainingFeatureFile6(String input) throws Exception {
            String file = "Classifier\\featurefile_elimination_trial6.arff"; 
            ArffLoader loader = new ArffLoader();
           
            //ATTRIBUTES
            Attribute attr[]= new Attribute[50];
            
            //numeric
            attr[0] = new Attribute("Autosentiment");
            attr[1] = new Attribute("Positivematch");
            attr[2] = new Attribute("Negativematch");
            attr[3] = new Attribute("FW");
            attr[4] = new Attribute("JJ");
            attr[5] = new Attribute("RB");
            attr[6] = new Attribute("RB_JJ");
            attr[7] = new Attribute("amod");
            attr[8] = new Attribute("acomp");
            attr[9] = new Attribute("advmod");
            attr[10] = new Attribute("BLPositive");
            attr[11] = new Attribute("BLNegative");
            
            //class
            FastVector classValue = new FastVector(3);
            classValue.addElement("p");
            classValue.addElement("n");
            classValue.addElement("o");
            attr[12] = new Attribute("answer", classValue);
            
            FastVector attrs = new FastVector();
            attrs.addElement(attr[0]);
            attrs.addElement(attr[1]);
            attrs.addElement(attr[2]);
            attrs.addElement(attr[3]);
            attrs.addElement(attr[4]);
            attrs.addElement(attr[5]);
            attrs.addElement(attr[6]);
            attrs.addElement(attr[7]);
            attrs.addElement(attr[8]);
            attrs.addElement(attr[9]);
            attrs.addElement(attr[10]);
            attrs.addElement(attr[11]);
            attrs.addElement(attr[12]);
                        
            // Add Instances
            Instances dataset = new Instances("my_dataset", attrs, 0);
		
	    if(new File(file).isFile()) {
                loader.setFile(new File(file));
                dataset = loader.getDataSet();	
            }
            
            System.out.println("-----------------------------------------");
            System.out.println(input);
            System.out.println("-----------------------------------------");
            
            StringTokenizer tokenizer= new StringTokenizer(input);
            
            while(tokenizer.hasMoreTokens()){
                Instance example = new Instance(13);
                for(int j=0; j<13; j++) {
                    String st= tokenizer.nextToken();
                    System.out.println(j+" "+st);
                    if(j==0)
                        example.setValue(attr[j], Float.parseFloat(st));
                    else if(j==12)
                        example.setValue(attr[j], st);
                    else
                        example.setValue(attr[j], Integer.parseInt(st));
                }
                dataset.add(example);
            }
            
            //Save dataset
            ArffSaver saver = new ArffSaver();
            saver.setInstances(dataset);
            saver.setFile(new File(file));
            saver.writeBatch();
            
            //Read dataset
            loader.setFile(new File(file));
            dataset = loader.getDataSet();	
            
            //Build classifier
            dataset.setClassIndex(12);
            Classifier classifier = new J48();
            classifier.buildClassifier(dataset);
            
            //Save classifier
            String file1="Classifier\\classifier_wo_vsent.model";
            OutputStream os = new FileOutputStream(file1);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(os);
            objectOutputStream.writeObject(classifier);		
	      
            
            // Comment out if not needed
            //Read classifier back
            InputStream is = new FileInputStream(file1);
            ObjectInputStream objectInputStream = new ObjectInputStream(is);
            classifier = (Classifier) objectInputStream.readObject();
            objectInputStream.close();
		
            //Evaluate resample if needed
            //dataset = dataset.resample(new Random(42));
            //split to 70:30 learn and test set
            double percent = 70.0;
            int trainSize = (int) Math.round(dataset.numInstances() * percent / 100);
            int testSize = dataset.numInstances() - trainSize;
            Instances train = new Instances(dataset, 0, trainSize);
            Instances test = new Instances(dataset, trainSize, testSize);
            train.setClassIndex(12);
            test.setClassIndex(12);
            
            //Evaluate
            Evaluation eval = new Evaluation(dataset); //trainset
            eval.crossValidateModel(classifier, dataset, 10, new Random(1));
            System.out.println("EVALUATION:\n"+eval.toSummaryString());
            System.out.println("WEIGHTED MEASURE:\n"+eval.weightedFMeasure());
            System.out.println("WEIGHTED PRECISION:\n"+eval.weightedPrecision());
            System.out.println("WEIGHTED RECALL:\n"+eval.weightedRecall());           
        } 
        
        public void classifyTestSet1(String input) throws Exception{
            String ids="";
            ReaderWriter rw = new ReaderWriter();
        
            //ATTRIBUTES
            Attribute attr[]= new Attribute[50];
            
            //numeric
            attr[0] = new Attribute("Positivematch");
            attr[1] = new Attribute("Negativematch");
            attr[2] = new Attribute("FW");
            attr[3] = new Attribute("JJ");
            attr[4] = new Attribute("RB");
            attr[5] = new Attribute("RB_JJ");
            attr[6] = new Attribute("amod");
            attr[7] = new Attribute("acomp");
            attr[8] = new Attribute("advmod");
            attr[9] = new Attribute("BLPositive");
            attr[10] = new Attribute("BLNegative");
            attr[11] = new Attribute("VSPositive");
            attr[12] = new Attribute("VSNegative");   
                        
            //class
            FastVector classValue = new FastVector(3);
            classValue.addElement("p");
            classValue.addElement("n");
            classValue.addElement("o");
            attr[13] = new Attribute("answer", classValue);
            
            FastVector attrs = new FastVector();
            attrs.addElement(attr[0]);
            attrs.addElement(attr[1]);
            attrs.addElement(attr[2]);
            attrs.addElement(attr[3]);
            attrs.addElement(attr[4]);
            attrs.addElement(attr[5]);
            attrs.addElement(attr[6]);
            attrs.addElement(attr[7]);
            attrs.addElement(attr[8]);
            attrs.addElement(attr[9]);
            attrs.addElement(attr[10]);
            attrs.addElement(attr[11]);
            attrs.addElement(attr[12]);
            attrs.addElement(attr[13]);
            
            // Add Instances
            Instances dataset = new Instances("my_dataset", attrs, 0);
            
            
            StringTokenizer tokenizer= new StringTokenizer(input);
            
            while(tokenizer.hasMoreTokens()){
                Instance example = new Instance(14);
                for(int j=0; j<14; j++) {
                    String st= tokenizer.nextToken();
                    System.out.println(j+" "+st);
                    if(j==13)
                        example.setValue(attr[j], st);
                    else
                        example.setValue(attr[j], Integer.parseInt(st));
                }
                ids+=tokenizer.nextToken()+"\t";
                dataset.add(example);
            }
            
            //Save dataset
            String file = "Classifier\\featurefile_elimination_test1.arff";
            ArffSaver saver = new ArffSaver();
            saver.setInstances(dataset);
            saver.setFile(new File(file));
            saver.writeBatch();
		
	    //Read dataset
            ArffLoader loader = new ArffLoader();
            loader.setFile(new File(file));
            dataset = loader.getDataSet();
            
            //Build classifier
            dataset.setClassIndex(13);
            
            //Read classifier back
            String file1="Classiifer\\classifier_wo_autosentiment.model";
            InputStream is = new FileInputStream(file1);
            Classifier classifier;
            ObjectInputStream objectInputStream = new ObjectInputStream(is); 
            classifier = (Classifier) objectInputStream.readObject();
            
            //Evaluate
            Instances test = new Instances(dataset, 0, dataset.numInstances());
            test.setClassIndex(13);
            
            //Do eval
            Evaluation eval = new Evaluation(test); //trainset
            eval.evaluateModel(classifier, test); //testset
            System.out.println(eval.toSummaryString());
            System.out.println("WEIGHTED F-MEASURE:"+eval.weightedFMeasure());
            System.out.println("WEIGHTED PRECISION:"+eval.weightedPrecision());
            System.out.println("WEIGHTED RECALL:"+eval.weightedRecall());
            
            //output predictions
            String optest="",val="";
            StringTokenizer op=new StringTokenizer(ids);
            int count=0;
            while(op.hasMoreTokens()){
                double[] prediction=classifier.distributionForInstance(test.instance(count));
                count+=1;
                //optest+=op.nextToken()+" "+Double.toString((double) Math.round((prediction[0]) * 1000) / 1000)+"\n";                
                if (prediction[0]>prediction[1]){
                    if(prediction[0]>prediction[2]){
                        val = "p: " + Double.toString((double) Math.round((prediction[0]) * 1000) / 1000);
                    }
                    else{
                        val = "o: " +Double.toString((double) Math.round((prediction[2]) * 1000) / 1000);
                    }                    
                }
                else{
                    if(prediction[1]>prediction[2]){
                        val = "n: " + Double.toString((double) Math.round((prediction[1]) * 1000) / 1000);
                    }
                    else{
                        val = "o: " +Double.toString((double) Math.round((prediction[2]) * 1000) / 1000);
                    }
                }
                optest += op.nextToken() + "\t" + val + "\n";
            }
            rw.writeToFile(optest, "Answers_Elimination_Test1", "txt");                
        }

        public void classifyTestSet2(String input) throws Exception{
            String ids="";
            ReaderWriter rw = new ReaderWriter();
        
            //ATTRIBUTES
            Attribute attr[]= new Attribute[50];
            
            //numeric
            attr[0] = new Attribute("Autosentiment");
            attr[1] = new Attribute("FW");
            attr[2] = new Attribute("JJ");
            attr[3] = new Attribute("RB");
            attr[4] = new Attribute("RB_JJ");
            attr[5] = new Attribute("amod");
            attr[6] = new Attribute("acomp");
            attr[7] = new Attribute("advmod");
            attr[8] = new Attribute("BLPositive");
            attr[9] = new Attribute("BLNegative");
            attr[10] = new Attribute("VSPositive");
            attr[11] = new Attribute("VSNegative");
            
            //class
            FastVector classValue = new FastVector(3);
            classValue.addElement("p");
            classValue.addElement("n");
            classValue.addElement("o");
            attr[12] = new Attribute("answer", classValue);
            
            FastVector attrs = new FastVector();
            attrs.addElement(attr[0]);
            attrs.addElement(attr[1]);
            attrs.addElement(attr[2]);
            attrs.addElement(attr[3]);
            attrs.addElement(attr[4]);
            attrs.addElement(attr[5]);
            attrs.addElement(attr[6]);
            attrs.addElement(attr[7]);
            attrs.addElement(attr[8]);
            attrs.addElement(attr[9]);
            attrs.addElement(attr[10]);
            attrs.addElement(attr[11]);
            attrs.addElement(attr[12]);
            
            // Add Instances
            Instances dataset = new Instances("my_dataset", attrs, 0);
	
            
            StringTokenizer tokenizer= new StringTokenizer(input);
            
            while(tokenizer.hasMoreTokens()){
                Instance example = new Instance(13);
                for(int j=0; j<13; j++) {
                    String st= tokenizer.nextToken();
                    System.out.println(j+" "+st);
                    if(j==0)
                        example.setValue(attr[j], Float.parseFloat(st));
                    else if(j==12)
                        example.setValue(attr[j], st);
                    else
                        example.setValue(attr[j], Integer.parseInt(st));
                }
                ids+=tokenizer.nextToken()+"\t";
                dataset.add(example);
            }
            
            //Save dataset
            String file = "Classifier\\featurefile_elimination_test2.arff";
            ArffSaver saver = new ArffSaver();
            saver.setInstances(dataset);
            saver.setFile(new File(file));
            saver.writeBatch();
		
	    //Read dataset
            ArffLoader loader = new ArffLoader();
            loader.setFile(new File(file));
            dataset = loader.getDataSet();
            
            //Build classifier
            dataset.setClassIndex(12);
            
            //Read classifier back
            String file1="Classifier\\classifier_wo_wordlist.model";
            InputStream is = new FileInputStream(file1);
            Classifier classifier;
            ObjectInputStream objectInputStream = new ObjectInputStream(is); 
            classifier = (Classifier) objectInputStream.readObject();
            
            //Evaluate
            Instances test = new Instances(dataset, 0, dataset.numInstances());
            test.setClassIndex(12);
            
            //Do eval
            Evaluation eval = new Evaluation(test); //trainset
            eval.evaluateModel(classifier, test); //testset
            System.out.println(eval.toSummaryString());
            System.out.println("WEIGHTED F-MEASURE:"+eval.weightedFMeasure());
            System.out.println("WEIGHTED PRECISION:"+eval.weightedPrecision());
            System.out.println("WEIGHTED RECALL:"+eval.weightedRecall());
            
            //output predictions
            String optest="",val="";
            StringTokenizer op=new StringTokenizer(ids);
            int count=0;
            while(op.hasMoreTokens()){
                double[] prediction=classifier.distributionForInstance(test.instance(count));
                count+=1;
                //optest+=op.nextToken()+" "+Double.toString((double) Math.round((prediction[0]) * 1000) / 1000)+"\n";                
                if (prediction[0]>prediction[1]){
                    if(prediction[0]>prediction[2]){
                        val = "p: " + Double.toString((double) Math.round((prediction[0]) * 1000) / 1000);
                    }
                    else{
                        val = "o: " +Double.toString((double) Math.round((prediction[2]) * 1000) / 1000);
                    }                    
                }
                else{
                    if(prediction[1]>prediction[2]){
                        val = "n: " + Double.toString((double) Math.round((prediction[1]) * 1000) / 1000);
                    }
                    else{
                        val = "o: " +Double.toString((double) Math.round((prediction[2]) * 1000) / 1000);
                    }
                }
                optest += op.nextToken() + "\t" + val + "\n";
            }
            rw.writeToFile(optest, "Answers_elimination_Test2", "txt");                
        }

        public void classifyTestSet3(String input) throws Exception{
            String ids="";
            ReaderWriter rw = new ReaderWriter();
        
            //ATTRIBUTES
            Attribute attr[]= new Attribute[50];
            
            //numeric
            attr[0] = new Attribute("Autosentiment");
            attr[1] = new Attribute("Positivematch");
            attr[2] = new Attribute("Negativematch");
            attr[3] = new Attribute("amod");
            attr[4] = new Attribute("acomp");
            attr[5] = new Attribute("advmod");
            attr[6] = new Attribute("BLPositive");
            attr[7] = new Attribute("BLNegative");
            attr[8] = new Attribute("VSPositive");
            attr[9] = new Attribute("VSNegative");
            
            //class
            FastVector classValue = new FastVector(3);
            classValue.addElement("p");
            classValue.addElement("n");
            classValue.addElement("o");
            attr[10] = new Attribute("answer", classValue);
            
            FastVector attrs = new FastVector();
            attrs.addElement(attr[0]);
            attrs.addElement(attr[1]);
            attrs.addElement(attr[2]);
            attrs.addElement(attr[3]);
            attrs.addElement(attr[4]);
            attrs.addElement(attr[5]);
            attrs.addElement(attr[6]);
            attrs.addElement(attr[7]);
            attrs.addElement(attr[8]);
            attrs.addElement(attr[9]);
            attrs.addElement(attr[10]);
            
            // Add Instances
            Instances dataset = new Instances("my_dataset", attrs, 0);
	
                        
            StringTokenizer tokenizer= new StringTokenizer(input);
            
            while(tokenizer.hasMoreTokens()){
                Instance example = new Instance(11);
                for(int j=0; j<11; j++) {
                    String st= tokenizer.nextToken();
                    System.out.println(j+" "+st);
                    if(j==0)
                        example.setValue(attr[j], Float.parseFloat(st));
                    else if(j==10)
                        example.setValue(attr[j], st);
                    else
                        example.setValue(attr[j], Integer.parseInt(st));
                }
                ids+=tokenizer.nextToken()+"\t";
                dataset.add(example);
            }
            
            //Save dataset
            String file = "Classifier\\featurefile_elimination_test3.arff";
            ArffSaver saver = new ArffSaver();
            saver.setInstances(dataset);
            saver.setFile(new File(file));
            saver.writeBatch();
		
	    //Read dataset
            ArffLoader loader = new ArffLoader();
            loader.setFile(new File(file));
            dataset = loader.getDataSet();
            
            //Build classifier
            dataset.setClassIndex(10);
            
            //Read classifier back
            String file1="Classifier\\classifier_wo_pos.model";
            InputStream is = new FileInputStream(file1);
            Classifier classifier;
            ObjectInputStream objectInputStream = new ObjectInputStream(is); 
            classifier = (Classifier) objectInputStream.readObject();
            
            //Evaluate
            Instances test = new Instances(dataset, 0, dataset.numInstances());
            test.setClassIndex(10);
            
            //Do eval
            Evaluation eval = new Evaluation(test); //trainset
            eval.evaluateModel(classifier, test); //testset
            System.out.println(eval.toSummaryString());
            System.out.println("WEIGHTED F-MEASURE:"+eval.weightedFMeasure());
            System.out.println("WEIGHTED PRECISION:"+eval.weightedPrecision());
            System.out.println("WEIGHTED RECALL:"+eval.weightedRecall());
            
            //output predictions
            String optest="",val="";
            StringTokenizer op=new StringTokenizer(ids);
            int count=0;
            while(op.hasMoreTokens()){
                double[] prediction=classifier.distributionForInstance(test.instance(count));
                count+=1;
                //optest+=op.nextToken()+" "+Double.toString((double) Math.round((prediction[0]) * 1000) / 1000)+"\n";                
                if (prediction[0]>prediction[1]){
                    if(prediction[0]>prediction[2]){
                        val = "p: " + Double.toString((double) Math.round((prediction[0]) * 1000) / 1000);
                    }
                    else{
                        val = "o: " +Double.toString((double) Math.round((prediction[2]) * 1000) / 1000);
                    }                    
                }
                else{
                    if(prediction[1]>prediction[2]){
                        val = "n: " + Double.toString((double) Math.round((prediction[1]) * 1000) / 1000);
                    }
                    else{
                        val = "o: " +Double.toString((double) Math.round((prediction[2]) * 1000) / 1000);
                    }
                }
                optest += op.nextToken() + "\t" + val + "\n";
            }
            rw.writeToFile(optest, "Answers_elimination_Test3", "txt");                
        }

        public void classifyTestSet4(String input) throws Exception{
            String ids="";
            ReaderWriter rw = new ReaderWriter();
        
            //ATTRIBUTES
            Attribute attr[]= new Attribute[50];
            
            //numeric
            attr[0] = new Attribute("Autosentiment");
            attr[1] = new Attribute("Positivematch");
            attr[2] = new Attribute("Negativematch");
            attr[3] = new Attribute("FW");
            attr[4] = new Attribute("JJ");
            attr[5] = new Attribute("RB");
            attr[6] = new Attribute("RB_JJ");
            attr[7] = new Attribute("BLPositive");
            attr[8] = new Attribute("BLNegative");
            attr[9] = new Attribute("VSPositive");
            attr[10] = new Attribute("VSNegative");
            
            //class
            FastVector classValue = new FastVector(3);
            classValue.addElement("p");
            classValue.addElement("n");
            classValue.addElement("o");
            attr[11] = new Attribute("answer", classValue);
            
            FastVector attrs = new FastVector();
            attrs.addElement(attr[0]);
            attrs.addElement(attr[1]);
            attrs.addElement(attr[2]);
            attrs.addElement(attr[3]);
            attrs.addElement(attr[4]);
            attrs.addElement(attr[5]);
            attrs.addElement(attr[6]);
            attrs.addElement(attr[7]);
            attrs.addElement(attr[8]);
            attrs.addElement(attr[9]);
            attrs.addElement(attr[10]);
            attrs.addElement(attr[11]);
                        
            // Add Instances
            Instances dataset = new Instances("my_dataset", attrs, 0);
	
            StringTokenizer tokenizer= new StringTokenizer(input);
            
            while(tokenizer.hasMoreTokens()){
                Instance example = new Instance(12);
                for(int j=0; j<12; j++) {
                    String st= tokenizer.nextToken();
                    System.out.println(j+" "+st);
                    if(j==0)
                        example.setValue(attr[j], Float.parseFloat(st));
                    else if(j==11)
                        example.setValue(attr[j], st);
                    else
                        example.setValue(attr[j], Integer.parseInt(st));
                }
                ids+=tokenizer.nextToken()+"\t";
                dataset.add(example);
            }
            
            //Save dataset
            String file = "Classifier\\featurefile_elimination_test4.arff";
            ArffSaver saver = new ArffSaver();
            saver.setInstances(dataset);
            saver.setFile(new File(file));
            saver.writeBatch();
		
	    //Read dataset
            ArffLoader loader = new ArffLoader();
            loader.setFile(new File(file));
            dataset = loader.getDataSet();
            
            //Build classifier
            dataset.setClassIndex(11);
            
            //Read classifier back
            String file1="Classifier\\classifier_wo_dependency.model";
            InputStream is = new FileInputStream(file1);
            Classifier classifier;
            ObjectInputStream objectInputStream = new ObjectInputStream(is); 
            classifier = (Classifier) objectInputStream.readObject();
            
            //Evaluate
            Instances test = new Instances(dataset, 0, dataset.numInstances());
            test.setClassIndex(11);
            
            //Do eval
            Evaluation eval = new Evaluation(test); //trainset
            eval.evaluateModel(classifier, test); //testset
            System.out.println(eval.toSummaryString());
            System.out.println("WEIGHTED F-MEASURE:"+eval.weightedFMeasure());
            System.out.println("WEIGHTED PRECISION:"+eval.weightedPrecision());
            System.out.println("WEIGHTED RECALL:"+eval.weightedRecall());
            
            //output predictions
            String optest="",val="";
            StringTokenizer op=new StringTokenizer(ids);
            int count=0;
            while(op.hasMoreTokens()){
                double[] prediction=classifier.distributionForInstance(test.instance(count));
                count+=1;
                //optest+=op.nextToken()+" "+Double.toString((double) Math.round((prediction[0]) * 1000) / 1000)+"\n";                
                if (prediction[0]>prediction[1]){
                    if(prediction[0]>prediction[2]){
                        val = "p: " + Double.toString((double) Math.round((prediction[0]) * 1000) / 1000);
                    }
                    else{
                        val = "o: " +Double.toString((double) Math.round((prediction[2]) * 1000) / 1000);
                    }                    
                }
                else{
                    if(prediction[1]>prediction[2]){
                        val = "n: " + Double.toString((double) Math.round((prediction[1]) * 1000) / 1000);
                    }
                    else{
                        val = "o: " +Double.toString((double) Math.round((prediction[2]) * 1000) / 1000);
                    }
                }
                optest += op.nextToken() + "\t" + val + "\n";
            }
            rw.writeToFile(optest, "Answers_elimination_Test4", "txt");                
        }

        public void classifyTestSet5(String input) throws Exception{
            String ids="";
            ReaderWriter rw = new ReaderWriter();
        
            //ATTRIBUTES
            Attribute attr[]= new Attribute[50];
            
            //numeric
            attr[0] = new Attribute("Autosentiment");
            attr[1] = new Attribute("Positivematch");
            attr[2] = new Attribute("Negativematch");
            attr[3] = new Attribute("FW");
            attr[4] = new Attribute("JJ");
            attr[5] = new Attribute("RB");
            attr[6] = new Attribute("RB_JJ");
            attr[7] = new Attribute("amod");
            attr[8] = new Attribute("acomp");
            attr[9] = new Attribute("advmod");
            attr[10] = new Attribute("VSPositive");
            attr[11] = new Attribute("VSNegative");
            
            //class
            FastVector classValue = new FastVector(3);
            classValue.addElement("p");
            classValue.addElement("n");
            classValue.addElement("o");
            attr[12] = new Attribute("answer", classValue);
            
            FastVector attrs = new FastVector();
            attrs.addElement(attr[0]);
            attrs.addElement(attr[1]);
            attrs.addElement(attr[2]);
            attrs.addElement(attr[3]);
            attrs.addElement(attr[4]);
            attrs.addElement(attr[5]);
            attrs.addElement(attr[6]);
            attrs.addElement(attr[7]);
            attrs.addElement(attr[8]);
            attrs.addElement(attr[9]);
            attrs.addElement(attr[10]);
            attrs.addElement(attr[11]);
            attrs.addElement(attr[12]);
                        
            // Add Instances
            Instances dataset = new Instances("my_dataset", attrs, 0);
	
            StringTokenizer tokenizer= new StringTokenizer(input);
            
            while(tokenizer.hasMoreTokens()){
                Instance example = new Instance(13);
                for(int j=0; j<13; j++) {
                    String st= tokenizer.nextToken();
                    System.out.println(j+" "+st);
                    if(j==0)
                        example.setValue(attr[j], Float.parseFloat(st));
                    else if(j==12)
                        example.setValue(attr[j], st);
                    else
                        example.setValue(attr[j], Integer.parseInt(st));
                }
                ids+=tokenizer.nextToken()+"\t";
                dataset.add(example);
            }
            
            //Save dataset
            String file = "Classifier\\featurefile_elimination_test5.arff";
            ArffSaver saver = new ArffSaver();
            saver.setInstances(dataset);
            saver.setFile(new File(file));
            saver.writeBatch();
		
	    //Read dataset
            ArffLoader loader = new ArffLoader();
            loader.setFile(new File(file));
            dataset = loader.getDataSet();
            
            //Build classifier
            dataset.setClassIndex(12);
            
            //Read classifier back
            String file1="Classifier\\classifier_wo_blist.model";
            InputStream is = new FileInputStream(file1);
            Classifier classifier;
            ObjectInputStream objectInputStream = new ObjectInputStream(is); 
            classifier = (Classifier) objectInputStream.readObject();
            
            //Evaluate
            Instances test = new Instances(dataset, 0, dataset.numInstances());
            test.setClassIndex(12);
            
            //Do eval
            Evaluation eval = new Evaluation(test); //trainset
            eval.evaluateModel(classifier, test); //testset
            System.out.println(eval.toSummaryString());
            System.out.println("WEIGHTED F-MEASURE:"+eval.weightedFMeasure());
            System.out.println("WEIGHTED PRECISION:"+eval.weightedPrecision());
            System.out.println("WEIGHTED RECALL:"+eval.weightedRecall());
            
            //output predictions
            String optest="",val="";
            StringTokenizer op=new StringTokenizer(ids);
            int count=0;
            while(op.hasMoreTokens()){
                double[] prediction=classifier.distributionForInstance(test.instance(count));
                count+=1;
                //optest+=op.nextToken()+" "+Double.toString((double) Math.round((prediction[0]) * 1000) / 1000)+"\n";                
                if (prediction[0]>prediction[1]){
                    if(prediction[0]>prediction[2]){
                        val = "p: " + Double.toString((double) Math.round((prediction[0]) * 1000) / 1000);
                    }
                    else{
                        val = "o: " +Double.toString((double) Math.round((prediction[2]) * 1000) / 1000);
                    }                    
                }
                else{
                    if(prediction[1]>prediction[2]){
                        val = "n: " + Double.toString((double) Math.round((prediction[1]) * 1000) / 1000);
                    }
                    else{
                        val = "o: " +Double.toString((double) Math.round((prediction[2]) * 1000) / 1000);
                    }
                }
                optest += op.nextToken() + "\t" + val + "\n";
            }
            rw.writeToFile(optest, "Answers_elimination_Test5", "txt");                
        }
        
        public void classifyTestSet6(String input) throws Exception{
            String ids="";
            ReaderWriter rw = new ReaderWriter();
        
            //ATTRIBUTES
            Attribute attr[]= new Attribute[50];
            
            //numeric
            attr[0] = new Attribute("Autosentiment");
            attr[1] = new Attribute("Positivematch");
            attr[2] = new Attribute("Negativematch");
            attr[3] = new Attribute("FW");
            attr[4] = new Attribute("JJ");
            attr[5] = new Attribute("RB");
            attr[6] = new Attribute("RB_JJ");
            attr[7] = new Attribute("amod");
            attr[8] = new Attribute("acomp");
            attr[9] = new Attribute("advmod");
            attr[10] = new Attribute("BLPositive");
            attr[11] = new Attribute("BLNegative");
            
            //class
            FastVector classValue = new FastVector(3);
            classValue.addElement("p");
            classValue.addElement("n");
            classValue.addElement("o");
            attr[12] = new Attribute("answer", classValue);
            
            FastVector attrs = new FastVector();
            attrs.addElement(attr[0]);
            attrs.addElement(attr[1]);
            attrs.addElement(attr[2]);
            attrs.addElement(attr[3]);
            attrs.addElement(attr[4]);
            attrs.addElement(attr[5]);
            attrs.addElement(attr[6]);
            attrs.addElement(attr[7]);
            attrs.addElement(attr[8]);
            attrs.addElement(attr[9]);
            attrs.addElement(attr[10]);
            attrs.addElement(attr[11]);
            attrs.addElement(attr[12]);
                        
            // Add Instances
            Instances dataset = new Instances("my_dataset", attrs, 0);
	
            StringTokenizer tokenizer= new StringTokenizer(input);
            
            while(tokenizer.hasMoreTokens()){
                Instance example = new Instance(13);
                for(int j=0; j<13; j++) {
                    String st= tokenizer.nextToken();
                    System.out.println(j+" "+st);
                    if(j==0)
                        example.setValue(attr[j], Float.parseFloat(st));
                    else if(j==12)
                        example.setValue(attr[j], st);
                    else
                        example.setValue(attr[j], Integer.parseInt(st));
                }
                ids+=tokenizer.nextToken()+"\t";
                dataset.add(example);
            }
            
            //Save dataset
            String file = "Classifier\\featurefile_elimination_test6.arff";
            ArffSaver saver = new ArffSaver();
            saver.setInstances(dataset);
            saver.setFile(new File(file));
            saver.writeBatch();
		
	    //Read dataset
            ArffLoader loader = new ArffLoader();
            loader.setFile(new File(file));
            dataset = loader.getDataSet();
            
            //Build classifier
            dataset.setClassIndex(12);
            
            //Read classifier back
            String file1="Classifier\\classifier_wo_vsent.model";
            InputStream is = new FileInputStream(file1);
            Classifier classifier;
            ObjectInputStream objectInputStream = new ObjectInputStream(is); 
            classifier = (Classifier) objectInputStream.readObject();
            
            //Evaluate
            Instances test = new Instances(dataset, 0, dataset.numInstances());
            test.setClassIndex(12);
            
            //Do eval
            Evaluation eval = new Evaluation(test); //trainset
            eval.evaluateModel(classifier, test); //testset
            System.out.println(eval.toSummaryString());
            System.out.println("WEIGHTED F-MEASURE:"+eval.weightedFMeasure());
            System.out.println("WEIGHTED PRECISION:"+eval.weightedPrecision());
            System.out.println("WEIGHTED RECALL:"+eval.weightedRecall());
            
            //output predictions
            String optest="",val="";
            StringTokenizer op=new StringTokenizer(ids);
            int count=0;
            while(op.hasMoreTokens()){
                double[] prediction=classifier.distributionForInstance(test.instance(count));
                count+=1;
                //optest+=op.nextToken()+" "+Double.toString((double) Math.round((prediction[0]) * 1000) / 1000)+"\n";                
                if (prediction[0]>prediction[1]){
                    if(prediction[0]>prediction[2]){
                        val = "p: " + Double.toString((double) Math.round((prediction[0]) * 1000) / 1000);
                    }
                    else{
                        val = "o: " +Double.toString((double) Math.round((prediction[2]) * 1000) / 1000);
                    }                    
                }
                else{
                    if(prediction[1]>prediction[2]){
                        val = "n: " + Double.toString((double) Math.round((prediction[1]) * 1000) / 1000);
                    }
                    else{
                        val = "o: " +Double.toString((double) Math.round((prediction[2]) * 1000) / 1000);
                    }
                }
                optest += op.nextToken() + "\t" + val + "\n";
            }
            rw.writeToFile(optest, "Answers_elimination_Test6", "txt");                
        }

    
}
