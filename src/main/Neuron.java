package main;

import java.util.Random;

public class Neuron implements Cloneable {
    private DataVector weights = null;
    private double alpha;
    private double t;
    private String name;

    public Neuron(double alpha){
        this.alpha = alpha;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void initSize(int size){
        double[] randData = new double[size];
        for(int i = 0; i < randData.length; i++){
            randData[i] = new Random().nextDouble() * 2 - 1;
        }
        weights = new DataVector(randData);
        t = new Random().nextDouble() * 2 - 1;
    }
    public double test(DataVector inputVector) throws DataVector.VectorSizeException {
        return weights.multiply(inputVector);
    }

    public int testBias(DataVector inputVector) throws DataVector.VectorSizeException {
        return test(inputVector) >= t ? 1 : 0;
    }

    public boolean train(DataVector inputVector, int expectedAnswer) throws DataVector.VectorSizeException {
        int result = testBias(inputVector);
        if(result == expectedAnswer)
            return true;
        weights = weights.add(inputVector.multiply((expectedAnswer - result) * alpha));
        t = t + (expectedAnswer - result) * alpha * -1;
        return false;
    }

    public void normalizeWeights(){
        weights.normalizeValues();
    }



    @Override
    public String toString() {
        if(weights == null)
            return null;
        return weights + " t: " + t;
    }

    @Override
    public Neuron clone() {
        try {
            Neuron clone = (Neuron) super.clone();
            clone.alpha = alpha;
            clone.t = t;
            clone.weights = weights.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}