package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TextReader{
    private final Map<Character, Integer> symbols = new HashMap<>();
    private final String text;
    Scanner scanner;
    public TextReader(File file) throws FileNotFoundException {
        scanner = new Scanner(file);StringBuilder textBuilder = new StringBuilder();
        while (scanner.hasNextLine()){
            textBuilder.append(scanner.nextLine());
        }
        this.text = textBuilder.toString().toLowerCase().replaceAll("[^a-z]", "");
        for(int i = 0; i < 26; i++){
            symbols.put((char)('a'+i), 0);
        }
        for(int i = this.text.length() - 1; i >= 0; i--){
            put(this.text.charAt(i));
        }
    }

    public TextReader(String text){
        this.text = text.toLowerCase().replaceAll("[^a-z]", "");
        for(int i = 0; i < 26; i++){
            symbols.put((char)('a'+i), 0);
        }
        for(int i = this.text.length() - 1; i >= 0; i--){
            put(this.text.charAt(i));
        }
    }

    public DataVector getSymbolsRatioVector(){
        double[] data_vector = new double[26];
        for(int i = 0; i < data_vector.length; i++) {
            data_vector[i] = (double) symbols.get((char) ('a' + i)) / text.length();
        }
        return new DataVector(data_vector);
    }

    private void put(char c){
        symbols.put(c, symbols.get(c) + 1);
    }
}
