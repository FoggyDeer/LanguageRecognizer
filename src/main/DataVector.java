package main;

public class DataVector implements Cloneable{
    private double[] dataArray;

    public DataVector(double[] inputData){
        dataArray = new double[inputData.length];
        System.arraycopy(inputData, 0, dataArray, 0, inputData.length);
    }
    public double[] getArray() {
        return dataArray;
    }

    public int getSize(){
        return dataArray.length;
    }

    public double multiply(DataVector dataVector) throws VectorSizeException {
        if(dataArray.length != dataVector.getSize()){
            throw new VectorSizeException();
        }

        double result = 0;
        double[] b = dataVector.getArray();
        for(int i = 0; i < dataArray.length; i++){
            result += dataArray[i] * b[i];
        }
        return result;
    }

    public DataVector multiply(double value){
        double[] a = new double[dataArray.length];
        System.arraycopy(dataArray, 0, a, 0, dataArray.length);
        for(int i = 0; i < dataArray.length; i++){
            a[i] *= value;
        }
        return new DataVector(a);
    }

    public DataVector add(DataVector dataVector) throws VectorSizeException {
        if(dataArray.length != dataVector.getSize()){
            throw new VectorSizeException();
        }
        double[] a = new double[dataArray.length];
        System.arraycopy(dataArray, 0, a, 0, dataArray.length);
        double[] b = dataVector.getArray();

        for(int i = 0; i < dataArray.length; i++){
            a[i] += b[i];
        }
        return new DataVector(a);
    }

    public void normalizeValues(){
        double length = getVectorLength();
        for (int i = 0; i < dataArray.length; i++){
            dataArray[i] /= length;
        }
    }
    private double getVectorLength(){
        double length = 0;
        for (double weight : dataArray) {
            length += Math.pow(weight, 2);
        }
        return Math.sqrt(length);
    }

    @Override
    public DataVector clone() {
        try {
            DataVector clone = (DataVector) super.clone();
            double[] data =new double[dataArray.length];
            System.arraycopy(dataArray, 0, data, 0, data.length);
            clone.dataArray = data;
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public static class VectorSizeException extends Exception{
        VectorSizeException(){
            super("Provided wrong vector size!");
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder().append("[ ");
        for(double j : dataArray)
            result.append(j).append(", ");
        result.delete(result.length()-2, result.length()).append(" ]");
        return result.toString();
    }
}
