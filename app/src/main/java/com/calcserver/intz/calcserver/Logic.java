package com.calcserver.intz.calcserver;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

/**
 * Created by intz on 9.03.16.
 */
public class Logic extends BroadcastReceiver {

    public static ArrayList<String> numberList = new ArrayList<String>();
    public static String numberString = "";
    public static String input = "";

    public static double num1;
    public static double num2;
    public static String operator;
    public static String lastOperator;
    public static double answer = 0.0;

    public static String resultString = "";
    public static String errorString = "";

    private UOW uow;

    @Override
    public void onReceive(Context context, Intent intent) {
        uow = new UOW(context);
        if (isOrderedBroadcast()) {

            Bundle extras = intent.getExtras();
            if (extras != null) {
                input = extras.getString("Input");
                checkInput(input);
            }
            setResultCode(Activity.RESULT_OK);
            for (int i = 0; i < numberList.size(); i++) { //get result from ArrayList
                resultString = resultString + numberList.get(i).toString();
            }
            if(!errorString.isEmpty()) {
                setResultData(errorString);
            } else {
                setResultData(resultString);
            }
            errorString = "";
            resultString = ""; //empty resultString so previous Strings won't be added in the newer calculations
        }
    }
    
    public void checkInput(String input) {
        if(input.contains("C")) { //if clear button pressed, clear screen and memory
            clearArrayAndString();
        } else if(isOperator(input) && numberList.size() == 0) { //if operator pressed before any numbers, do nothing.
            return;
        } else if(numberList.size() == 2 && lastIsOperator(numberList) && isOperator(input)) { //if many operators pressed in a row, choose last
            numberList.remove(1); //remove old operand
            numberList.add(1, input); //add operand that was last inputted by user
        } else if(input.contains("=") && numberList.size() < 3) { //if equals pressed before enough numbers inputted, do nothing
            return;
        } else if(input.contains("=") && numberList.size() == 3) { //if equals is pressed and enough numbers, then calculate whats on the screen
            setCalculateParameters(numberList); //set parameters for calculation
            calculate(num1, num2, operator);
            setAnswerAndPrepare();
        } else if(isOperator(input) && numberList.size() > 0) { //if operator pressed with enough numbers
            numberList.add(input);
            numberString = "";

            if(numberList.size() == 4) { //calculate first two numbers if we already have 2 entered numbers
                setCalculateParameters(numberList);
                calculate(num1, num2, operator);
                setAnswerAndPrepare();
            }
        } else if(input.contains(".") && numberList.size() > 0 && numberList.get(numberList.size()-1).contains(".")) { //if dot inputted, but there already is a dot !!!!!!!! contains . in whole string?
            return;
        } else if(input.contains("0") && numberString.equals("0"))  { // if input is 0 but 0 has already been inputted, prevent multiple 0s
            return;
        } else if(!isOperator(input) && numberString.equals("0")) { // if number inputted after 0 was inputted as the first number
            numberString = input; //overwrite zero
            numberList.remove(numberList.size()-1); //delete old zero
            numberList.add(numberString); //add new input
        } else if(input.contains(".") && numberString.isEmpty()) { // if dot pressed without any numbers, make it "0."
            numberString = numberString + 0 + input;
            numberList.add(numberList.size(), numberString);
        } else { //all checks passed, add to ArrayList (presumably(lol inb4 crash) numbers)
            numberString = numberString + input; //concatanate strings

            if (numberList.size() > 0 && !lastIsOperator(numberList)) { //add concatanated number herrr
                numberList.remove(numberList.size()-1); //remove old value, so new concatanated value can be added
            }
            numberList.add(numberString); //add new improved input to ArrayList
        }
    }

    public void calculate(double num1, double num2, String operator) {
        if(operator.contains("+")) {
            answer = num1 + num2;
        } else if(operator.contains("-")) {
            answer = num1 - num2;
        } else if(operator.contains("*")) {
            answer = num1 * num2;
        } else if(operator.contains("/") && num2 != 0) {
            answer = num1 / num2;
        } else {
            errorString = "Error :(";
        }

        if(errorString == "") {
            uow.addToDatabase(num1, num2, operator, answer);
        }
    }

    public void setCalculateParameters(ArrayList<String> arr) {
        if(arr.size() == 4) { //if 4 items in List (last is operator) then get lastOperator
            lastOperator = numberList.get(3).toString();
        }

        num1 = Double.parseDouble(arr.get(0));
        num2 = Double.parseDouble(arr.get(2));
        operator = arr.get(1).toString();
    }

    public void setAnswerAndPrepare() {
        int size = numberList.size(); //get size cuz its gon be gone soon
        numberList.clear(); //clear numberList Array from past values
        numberString = ""; //empty string so new values can be inputted
        numberList.add(Double.toString(round(answer, 2))); //add ROUNDED answer to ArrayList index 0
        if(size == 4) {
            numberList.add(lastOperator); //add operand to ArrayList index 1, so further calculations can be carried out
        }
    }

    public void clearArrayAndString() {
        numberList.clear();
        numberString = "";
    }

    public boolean lastIsOperator(ArrayList<String> arr) {
        if(arr.get(arr.size()-1).contains("+") ||
                arr.get(arr.size()-1).contains("-") ||
                arr.get(arr.size()-1).contains("*") ||
                arr.get(arr.size()-1).contains("/")) {
            return true;
        } return false;
    }

    public boolean isOperator(String str) {
        if(str.contains("+") || str.contains("-") || str.contains("*") || str.contains("/")) {
            return true;
        } return false;
    }

    public static double round(double value, int places) {
        if(places < 0) {
            throw new IllegalArgumentException();
        }

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
