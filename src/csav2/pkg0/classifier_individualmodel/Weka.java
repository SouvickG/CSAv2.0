/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csav2.pkg0.classifier_individualmodel;

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
            String file = "Classifier\\featurefile_individual_trial1.arff"; 
            ArffLoader loader = new ArffLoader();
           
            //ATTRIBUTES
            Attribute attr[]= new Attribute[50];
            
            //numeric
            attr[0] = new Attribute("Autosentiment");
                        
            //class
            FastVector classValue = new FastVector(3);
            classValue.addElement("p");
            classValue.addElement("n");
            classValue.addElement("o");
            attr[1] = new Attribute("answer", classValue);
            
            FastVector attrs = new FastVector();
            attrs.addElement(attr[0]);
            attrs.addElement(attr[1]);
                        
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
                Instance example = new Instance(2);
                for(int j=0; j<2; j++) {
                    String st= tokenizer.nextToken();
                    System.out.println(j+" "+st);
                    if(j==0)
                        example.setValue(attr[j], Float.parseFloat(st));
                    else if(j==1)
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
            dataset.setClassIndex(1);
            Classifier classifier = new J48();
            classifier.buildClassifier(dataset);
            
            //Save classifier
            String file1="Classifier\\classifier_autosentiment.model";
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
            train.setClassIndex(1);
            test.setClassIndex(1);
            
            //Evaluate
            Evaluation eval = new Evaluation(dataset); //trainset
            eval.crossValidateModel(classifier, dataset, 10, new Random(1));
            System.out.println("EVALUATION:\n"+eval.toSummaryString());
            System.out.println("WEIGHTED MEASURE:\n"+eval.weightedFMeasure());
            System.out.println("WEIGHTED PRECISION:\n"+eval.weightedPrecision());
            System.out.println("WEIGHTED RECALL:\n"+eval.weightedRecall());	
        } 
        
        //creates model for wordlists
        public void createTrainingFeatureFile2(String input) throws Exception {
            String file = "Classifier\\featurefile_individual_trial2.arff"; 
            ArffLoader loader = new ArffLoader();
           
            //ATTRIBUTES
            Attribute attr[]= new Attribute[50];
            
            //numeric
            attr[0] = new Attribute("Positivematch");
            attr[1] = new Attribute("Negativematch");
            
            //class
            FastVector classValue = new FastVector(3);
            classValue.addElement("p");
            classValue.addElement("n");
            classValue.addElement("o");
            attr[2] = new Attribute("answer", classValue);
            
            FastVector attrs = new FastVector();
            attrs.addElement(attr[0]);
            attrs.addElement(attr[1]);
            attrs.addElement(attr[2]);
                        
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
                Instance example = new Instance(3);
                for(int j=0; j<3; j++) {
                    String st= tokenizer.nextToken();
                    System.out.println(j+" "+st);
                    if(j==2)
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
            dataset.setClassIndex(2);
            Classifier classifier = new J48();
            classifier.buildClassifier(dataset);
            
            //Save classifier
            String file1="Classifier\\classifier_wordlist.model";
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
            train.setClassIndex(2);
            test.setClassIndex(2);
            
            //Evaluate
            Evaluation eval = new Evaluation(dataset); //trainset
            eval.crossValidateModel(classifier, dataset, 10, new Random(1));
            System.out.println("EVALUATION:\n"+eval.toSummaryString());
            System.out.println("WEIGHTED MEASURE:\n"+eval.weightedFMeasure());
            System.out.println("WEIGHTED PRECISION:\n"+eval.weightedPrecision());
            System.out.println("WEIGHTED RECALL:\n"+eval.weightedRecall());            
        } 
    
        //creates model for pos
        public void createTrainingFeatureFile3(String input) throws Exception {
            String file = "Classifier\\featurefile_individual_trial3.arff"; 
            ArffLoader loader = new ArffLoader();
           
            //ATTRIBUTES
            Attribute attr[]= new Attribute[50];
            
            //numeric
            attr[0] = new Attribute("FW");
            attr[1] = new Attribute("JJ");
            attr[2] = new Attribute("RB");
            attr[3] = new Attribute("RB_JJ");
            
            //class
            FastVector classValue = new FastVector(3);
            classValue.addElement("p");
            classValue.addElement("n");
            classValue.addElement("o");
            attr[4] = new Attribute("answer", classValue);
            
            FastVector attrs = new FastVector();
            attrs.addElement(attr[0]);
            attrs.addElement(attr[1]);
            attrs.addElement(attr[2]);
            attrs.addElement(attr[3]);
            attrs.addElement(attr[4]);
            
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
                Instance example = new Instance(5);
                for(int j=0; j<5; j++) {
                    String st= tokenizer.nextToken();
                    System.out.println(j+" "+st);
                    if(j==4)
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
            dataset.setClassIndex(4);
            Classifier classifier = new J48();
            classifier.buildClassifier(dataset);
            
            //Save classifier
            String file1="Classifier\\classifier_pos.model";
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
    
        //creates model for dependency
        public void createTrainingFeatureFile4(String input) throws Exception {
            String file = "Classifier\\featurefile_individual_trial4.arff"; 
            ArffLoader loader = new ArffLoader();
           
            //ATTRIBUTES
            Attribute attr[]= new Attribute[50];
            
            //numeric
            attr[0] = new Attribute("amod");
            attr[1] = new Attribute("acomp");
            attr[2] = new Attribute("advmod");
            
            //class
            FastVector classValue = new FastVector(3);
            classValue.addElement("p");
            classValue.addElement("n");
            classValue.addElement("o");
            attr[3] = new Attribute("answer", classValue);
            
            FastVector attrs = new FastVector();
            attrs.addElement(attr[0]);
            attrs.addElement(attr[1]);
            attrs.addElement(attr[2]);
            attrs.addElement(attr[3]);
                        
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
                Instance example = new Instance(4);
                for(int j=0; j<4; j++) {
                    String st= tokenizer.nextToken();
                    System.out.println(j+" "+st);
                    if(j==3)
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
            dataset.setClassIndex(3);
            Classifier classifier = new J48();
            classifier.buildClassifier(dataset);
            
            //Save classifier
            String file1="Classifier\\classifier_dependency.model";
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
            train.setClassIndex(3);
            test.setClassIndex(3);
            
            //Evaluate
            Evaluation eval = new Evaluation(dataset); //trainset
            eval.crossValidateModel(classifier, dataset, 10, new Random(1));
            System.out.println("EVALUATION:\n"+eval.toSummaryString());
            System.out.println("WEIGHTED MEASURE:\n"+eval.weightedFMeasure());
            System.out.println("WEIGHTED PRECISION:\n"+eval.weightedPrecision());
            System.out.println("WEIGHTED RECALL:\n"+eval.weightedRecall());           
        } 
    
        //creates model for blist
        public void createTrainingFeatureFile5(String input) throws Exception {
            String file = "Classifier\\featurefile_individual_trial5.arff"; 
            ArffLoader loader = new ArffLoader();
           
            //ATTRIBUTES
            Attribute attr[]= new Attribute[50];
            
            //numeric
            attr[0] = new Attribute("BLPositive");
            attr[1] = new Attribute("BLNegative");
            
            //class
            FastVector classValue = new FastVector(3);
            classValue.addElement("p");
            classValue.addElement("n");
            classValue.addElement("o");
            attr[2] = new Attribute("answer", classValue);
            
            FastVector attrs = new FastVector();
            attrs.addElement(attr[0]);
            attrs.addElement(attr[1]);
            attrs.addElement(attr[2]);
                        
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
                Instance example = new Instance(3);
                for(int j=0; j<3; j++) {
                    String st= tokenizer.nextToken();
                    System.out.println(j+" "+st);
                    if(j==2)
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
            dataset.setClassIndex(2);
            Classifier classifier = new J48();
            classifier.buildClassifier(dataset);
            
            //Save classifier
            String file1="Classifier\\classifier_blist.model";
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
            train.setClassIndex(2);
            test.setClassIndex(2);
            
            //Evaluate
            Evaluation eval = new Evaluation(dataset); //trainset
            eval.crossValidateModel(classifier, dataset, 10, new Random(1));
            System.out.println("EVALUATION:\n"+eval.toSummaryString());
            System.out.println("WEIGHTED MEASURE:\n"+eval.weightedFMeasure());
            System.out.println("WEIGHTED PRECISION:\n"+eval.weightedPrecision());
            System.out.println("WEIGHTED RECALL:\n"+eval.weightedRecall());           
        } 
        
        public void createTrainingFeatureFile6(String input) throws Exception {
            String file = "Classifier\\featurefile_individual_trial6.arff"; 
            ArffLoader loader = new ArffLoader();
           
            //ATTRIBUTES
            Attribute attr[]= new Attribute[50];
            
            //numeric
            attr[0] = new Attribute("VSPositive");
            attr[1] = new Attribute("VSNegative");
            
            //class
            FastVector classValue = new FastVector(3);
            classValue.addElement("p");
            classValue.addElement("n");
            classValue.addElement("o");
            attr[2] = new Attribute("answer", classValue);
            
            FastVector attrs = new FastVector();
            attrs.addElement(attr[0]);
            attrs.addElement(attr[1]);
            attrs.addElement(attr[2]);
                        
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
                Instance example = new Instance(3);
                for(int j=0; j<3; j++) {
                    String st= tokenizer.nextToken();
                    System.out.println(j+" "+st);
                    if(j==2)
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
            dataset.setClassIndex(2);
            Classifier classifier = new J48();
            classifier.buildClassifier(dataset);
            
            //Save classifier
            String file1="Classifier\\classifier_vsent.model";
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
            train.setClassIndex(2);
            test.setClassIndex(2);
            
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
            attr[0] = new Attribute("Autosentiment"); 
                        
            //class
            FastVector classValue = new FastVector(3);
            classValue.addElement("p");
            classValue.addElement("n");
            classValue.addElement("o");
            attr[1] = new Attribute("answer", classValue);
            
            FastVector attrs = new FastVector();
            attrs.addElement(attr[0]);
            attrs.addElement(attr[1]);
            
            // Add Instances
            Instances dataset = new Instances("my_dataset", attrs, 0);
            
            StringTokenizer tokenizer= new StringTokenizer(input);
            
            while(tokenizer.hasMoreTokens()){
                Instance example = new Instance(2);
                for(int j=0; j<2; j++) {
                    String st= tokenizer.nextToken();
                    System.out.println(j+" "+st);
                    if(j==0)
                        example.setValue(attr[j], Float.parseFloat(st));
                    else if(j==1)
                        example.setValue(attr[j], st);
                    else
                        example.setValue(attr[j], Integer.parseInt(st));
                }
                ids+=tokenizer.nextToken()+"\t";
                dataset.add(example);
            }
            
            //Save dataset
            String file = "Classifier\\featurefile_individual_test1.arff";
            ArffSaver saver = new ArffSaver();
            saver.setInstances(dataset);
            saver.setFile(new File(file));
            saver.writeBatch();
		
	    //Read dataset
            ArffLoader loader = new ArffLoader();
            loader.setFile(new File(file));
            dataset = loader.getDataSet();
            
            //Build classifier
            dataset.setClassIndex(1);
            
            //Read classifier back
            String file1="Classifier\\classifier_autosentiment.model";
            InputStream is = new FileInputStream(file1);
            Classifier classifier;
            ObjectInputStream objectInputStream = new ObjectInputStream(is); 
            classifier = (Classifier) objectInputStream.readObject();
            
            //Evaluate
            Instances test = new Instances(dataset, 0, dataset.numInstances());
            test.setClassIndex(1);
            
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
            rw.writeToFile(optest,"Answers_individual_Test1", "txt");                
        }

        public void classifyTestSet2(String input) throws Exception{
            String ids="";
            ReaderWriter rw = new ReaderWriter();
        
            //ATTRIBUTES
            Attribute attr[]= new Attribute[50];
            
            //numeric
            attr[0] = new Attribute("Positivematch");
            attr[1] = new Attribute("Negativematch");
            
            //class
            FastVector classValue = new FastVector(3);
            classValue.addElement("p");
            classValue.addElement("n");
            classValue.addElement("o");
            attr[2] = new Attribute("answer", classValue);
            
            FastVector attrs = new FastVector();
            attrs.addElement(attr[0]);
            attrs.addElement(attr[1]);
            attrs.addElement(attr[2]);
            
            // Add Instances
            Instances dataset = new Instances("my_dataset", attrs, 0);
	
            StringTokenizer tokenizer= new StringTokenizer(input);
            
            while(tokenizer.hasMoreTokens()){
                Instance example = new Instance(3);
                for(int j=0; j<3; j++) {
                    String st= tokenizer.nextToken();
                    System.out.println(j+" "+st);
                    if(j==2)
                        example.setValue(attr[j], st);
                    else
                        example.setValue(attr[j], Integer.parseInt(st));
                }
                ids+=tokenizer.nextToken()+"\t";
                dataset.add(example);
            }
            
            //Save dataset
            String file = "Classifier\\featurefile_individual_test2.arff";
            ArffSaver saver = new ArffSaver();
            saver.setInstances(dataset);
            saver.setFile(new File(file));
            saver.writeBatch();
		
	    //Read dataset
            ArffLoader loader = new ArffLoader();
            loader.setFile(new File(file));
            dataset = loader.getDataSet();
            
            //Build classifier
            dataset.setClassIndex(2);
            
            //Read classifier back
            String file1="Classifier\\classifier_wordlist.model";
            InputStream is = new FileInputStream(file1);
            Classifier classifier;
            ObjectInputStream objectInputStream = new ObjectInputStream(is); 
            classifier = (Classifier) objectInputStream.readObject();
            
            //Evaluate
            Instances test = new Instances(dataset, 0, dataset.numInstances());
            test.setClassIndex(2);
            
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
            rw.writeToFile(optest, "Answers_individual_Test2", "txt");                
        }

        public void classifyTestSet3(String input) throws Exception{
            String ids="";
            ReaderWriter rw = new ReaderWriter();
        
            //ATTRIBUTES
            Attribute attr[]= new Attribute[50];
            
            //numeric
            attr[0] = new Attribute("FW");
            attr[1] = new Attribute("JJ");
            attr[2] = new Attribute("RB");
            attr[3] = new Attribute("RB_JJ");
            
            //class
            FastVector classValue = new FastVector(3);
            classValue.addElement("p");
            classValue.addElement("n");
            classValue.addElement("o");
            attr[4] = new Attribute("answer", classValue);
            
            FastVector attrs = new FastVector();
            attrs.addElement(attr[0]);
            attrs.addElement(attr[1]);
            attrs.addElement(attr[2]);
            attrs.addElement(attr[3]);
            attrs.addElement(attr[4]);
            
            // Add Instances
            Instances dataset = new Instances("my_dataset", attrs, 0);
	         
            StringTokenizer tokenizer= new StringTokenizer(input);
            
            while(tokenizer.hasMoreTokens()){
                Instance example = new Instance(5);
                for(int j=0; j<5; j++) {
                    String st= tokenizer.nextToken();
                    System.out.println(j+" "+st);
                    if(j==4)
                        example.setValue(attr[j], st);
                    else
                        example.setValue(attr[j], Integer.parseInt(st));
                }
                ids+=tokenizer.nextToken()+"\t";
                dataset.add(example);
            }
            
            //Save dataset
            String file = "Classifier\\featurefile_individual_test3.arff";
            ArffSaver saver = new ArffSaver();
            saver.setInstances(dataset);
            saver.setFile(new File(file));
            saver.writeBatch();
		
	    //Read dataset
            ArffLoader loader = new ArffLoader();
            loader.setFile(new File(file));
            dataset = loader.getDataSet();
            
            //Build classifier
            dataset.setClassIndex(4);
            
            //Read classifier back
            String file1="Classifier\\classifier_pos.model";
            InputStream is = new FileInputStream(file1);
            Classifier classifier;
            ObjectInputStream objectInputStream = new ObjectInputStream(is); 
            classifier = (Classifier) objectInputStream.readObject();
            
            //Evaluate
            Instances test = new Instances(dataset, 0, dataset.numInstances());
            test.setClassIndex(4);
            
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
            rw.writeToFile(optest, "Answers_individual_Test3", "txt");                
        }

        public void classifyTestSet4(String input) throws Exception{
            String ids="";
            ReaderWriter rw = new ReaderWriter();
        
            //ATTRIBUTES
            Attribute attr[]= new Attribute[50];
            
            //numeric
            attr[0] = new Attribute("amod");
            attr[1] = new Attribute("acomp");
            attr[2] = new Attribute("advmod");
            
            //class
            FastVector classValue = new FastVector(3);
            classValue.addElement("p");
            classValue.addElement("n");
            classValue.addElement("o");
            attr[3] = new Attribute("answer", classValue);
            
            FastVector attrs = new FastVector();
            attrs.addElement(attr[0]);
            attrs.addElement(attr[1]);
            attrs.addElement(attr[2]);
            attrs.addElement(attr[3]);
                        
            // Add Instances
            Instances dataset = new Instances("my_dataset", attrs, 0);
	
            StringTokenizer tokenizer= new StringTokenizer(input);
            
            while(tokenizer.hasMoreTokens()){
                Instance example = new Instance(4);
                for(int j=0; j<4; j++) {
                    String st= tokenizer.nextToken();
                    System.out.println(j+" "+st);
                    if(j==3)
                        example.setValue(attr[j], st);
                    else
                        example.setValue(attr[j], Integer.parseInt(st));
                }
                ids+=tokenizer.nextToken()+"\t";
                dataset.add(example);
            }
            
            //Save dataset
            String file = "Classifier\\featurefile_individual_test4.arff";
            ArffSaver saver = new ArffSaver();
            saver.setInstances(dataset);
            saver.setFile(new File(file));
            saver.writeBatch();
		
	    //Read dataset
            ArffLoader loader = new ArffLoader();
            loader.setFile(new File(file));
            dataset = loader.getDataSet();
            
            //Build classifier
            dataset.setClassIndex(3);
            
            //Read classifier back
            String file1="Classifier\\classifier_dependency.model";
            InputStream is = new FileInputStream(file1);
            Classifier classifier;
            ObjectInputStream objectInputStream = new ObjectInputStream(is); 
            classifier = (Classifier) objectInputStream.readObject();
            
            //Evaluate
            Instances test = new Instances(dataset, 0, dataset.numInstances());
            test.setClassIndex(3);
            
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
            rw.writeToFile(optest, "Answers_individual_Test4", "txt");                
        }

        public void classifyTestSet5(String input) throws Exception{
            String ids="";
            ReaderWriter rw = new ReaderWriter();
        
            //ATTRIBUTES
            Attribute attr[]= new Attribute[50];
            
            //numeric
            attr[0] = new Attribute("BLPositive");
            attr[1] = new Attribute("BLNegative");
            
            //class
            FastVector classValue = new FastVector(3);
            classValue.addElement("p");
            classValue.addElement("n");
            classValue.addElement("o");
            attr[2] = new Attribute("answer", classValue);
            
            FastVector attrs = new FastVector();
            attrs.addElement(attr[0]);
            attrs.addElement(attr[1]);
            attrs.addElement(attr[2]);
                        
            // Add Instances
            Instances dataset = new Instances("my_dataset", attrs, 0);
	
            StringTokenizer tokenizer= new StringTokenizer(input);
            
            while(tokenizer.hasMoreTokens()){
                Instance example = new Instance(3);
                for(int j=0; j<3; j++) {
                    String st= tokenizer.nextToken();
                    System.out.println(j+" "+st);
                    if(j==2)
                        example.setValue(attr[j], st);
                    else
                        example.setValue(attr[j], Integer.parseInt(st));
                }
                ids+=tokenizer.nextToken()+"\t";
                dataset.add(example);
            }
            
            //Save dataset
            String file = "Classifier\\featurefile_individual_test5.arff";
            ArffSaver saver = new ArffSaver();
            saver.setInstances(dataset);
            saver.setFile(new File(file));
            saver.writeBatch();
		
	    //Read dataset
            ArffLoader loader = new ArffLoader();
            loader.setFile(new File(file));
            dataset = loader.getDataSet();
            
            //Build classifier
            dataset.setClassIndex(2);
            
            //Read classifier back
            String file1="Classifier\\classifier_blist.model";
            InputStream is = new FileInputStream(file1);
            Classifier classifier;
            ObjectInputStream objectInputStream = new ObjectInputStream(is); 
            classifier = (Classifier) objectInputStream.readObject();
            
            //Evaluate
            Instances test = new Instances(dataset, 0, dataset.numInstances());
            test.setClassIndex(2);
            
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
            rw.writeToFile(optest, "Answers_individual_Test5", "txt");                
        }
        
        public void classifyTestSet6(String input) throws Exception{
            String ids="";
            ReaderWriter rw = new ReaderWriter();
        
            //ATTRIBUTES
            Attribute attr[]= new Attribute[50];
            
            //numeric
            attr[0] = new Attribute("VSPositive");
            attr[1] = new Attribute("VSNegative");
            
            //class
            FastVector classValue = new FastVector(3);
            classValue.addElement("p");
            classValue.addElement("n");
            classValue.addElement("o");
            attr[2] = new Attribute("answer", classValue);
            
            FastVector attrs = new FastVector();
            attrs.addElement(attr[0]);
            attrs.addElement(attr[1]);
            attrs.addElement(attr[2]);
                        
            // Add Instances
            Instances dataset = new Instances("my_dataset", attrs, 0);
	
            StringTokenizer tokenizer= new StringTokenizer(input);
            
            while(tokenizer.hasMoreTokens()){
                Instance example = new Instance(3);
                for(int j=0; j<3; j++) {
                    String st= tokenizer.nextToken();
                    System.out.println(j+" "+st);
                    if(j==2)
                        example.setValue(attr[j], st);
                    else
                        example.setValue(attr[j], Integer.parseInt(st));
                }
                ids+=tokenizer.nextToken()+"\t";
                dataset.add(example);
            }
            
            //Save dataset
            String file = "Classifier\\featurefile_individual_test6.arff";
            ArffSaver saver = new ArffSaver();
            saver.setInstances(dataset);
            saver.setFile(new File(file));
            saver.writeBatch();
		
	    //Read dataset
            ArffLoader loader = new ArffLoader();
            loader.setFile(new File(file));
            dataset = loader.getDataSet();
            
            //Build classifier
            dataset.setClassIndex(2);
            
            //Read classifier back
            String file1="Classifier\\classifier_vsent.model";
            InputStream is = new FileInputStream(file1);
            Classifier classifier;
            ObjectInputStream objectInputStream = new ObjectInputStream(is); 
            classifier = (Classifier) objectInputStream.readObject();
            
            //Evaluate
            Instances test = new Instances(dataset, 0, dataset.numInstances());
            test.setClassIndex(2);
            
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
            rw.writeToFile(optest, "Answers_individual_Test6", "txt");                
        }

    
}
