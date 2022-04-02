package dam_a45125.thematiccalculator;

public class Calculator {


    public double sum(double num1, double num2){
        return num1 + num2;
    }

    public double sub(double num1, double num2){
        return num1 - num2;
    }

    public double mult(double num1, double num2){
        return num1 * num2;
    }

    public double div(double num1, double num2){
        return num1/num2;
    }

    public double cosseno(double num1){return Math.cos(num1);}

    public double seno(double num1){return Math.sin(num1);}

    public double tangente(double num1){ return Math.tan(num1);}

    public double module(double num1, double num2){return num1 % num2;}

}
