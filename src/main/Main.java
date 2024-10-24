package main;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main {
    private static final String trainFolder = "Train-bundle";
    private static final double learningRate = 0.3;
    private static volatile ArrayList<Neuron> neurons = new ArrayList<>();
    private static final ArrayList<String> languages = new ArrayList<>();
    public static void train(){
        int fails;
        ArrayList<List<File>> filesLists = new ArrayList<>();
        for(String lang : languages){
            filesLists.add(List.of(new File(trainFolder+"\\"+lang).listFiles()));
        }
        int epoch = 0;
        DataVector vector;
        do{
            epoch++;
            fails = 0;
            for(int i = 0; i < filesLists.size(); i++) {
                for (File file : filesLists.get(i)) {
                    try {
                        vector = new TextReader(file).getSymbolsRatioVector();
                        for (int j = 0; j < neurons.size(); j++) {
                            if(!neurons.get(j).train(vector, (i == j) ? 1 : 0)) {
                                fails++;
                            }
                        }
                    } catch (IOException ignored) {
                    } catch (DataVector.VectorSizeException e) {
                        System.out.println(Arrays.toString(e.getStackTrace()));
                        System.out.println("___________");
                    }
                }
            }
            //System.out.println("Fails: " + fails);
        }while (fails > 0);

        System.out.println("Epoch: " + epoch);

        for (Neuron neuron : neurons) {
            neuron.normalizeWeights();
        }
    }

    public static String test(String text){
        TextReader textReader = new TextReader(text);
        DataVector vector = textReader.getSymbolsRatioVector();
        vector.normalizeValues();
        Map<Double, String> map = new TreeMap<>();
        try {
            for(int i = 0; i < neurons.size(); i++){
                map.put(neurons.get(i).test(vector), neurons.get(i).getName());
            }
        }catch (DataVector.VectorSizeException e){}
        return map.values().stream().toList().getLast();
    }

    public static void main(String[] args) {
        File file = new File(trainFolder);
        Neuron neuron;
        for(File s : file.listFiles()){
            if (s.isDirectory()) {
                languages.add(s.getName());
                neuron = new Neuron(learningRate);
                neuron.setName(s.getName());
                neuron.initSize(26);
                neurons.add(neuron);
            }
        }
        train();
        SwingUtilities.invokeLater(MainForm::new);
    }
}
