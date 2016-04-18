package com.calcserver.intz.calcserver;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Intz on 10.04.2016.
 */
public class Operations implements IEntity {
    private long id;
    private long operantId;
    private double num1;
    private double num2;
    private double res;
    private long timestamp;
    private String operant;

    public Operations() {

    }

    public Operations(int operantId, float num1, float num2, float res, int timestamp) {
        setOperantId(operantId);
        setNum1(num1);
        setNum2(num2);
        setRes(res);
        setTimestamp(timestamp);
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public long getOperantId() {
        return operantId;
    }

    public void setOperantId(long operantId) {
        this.operantId = operantId;
    }

    public double getNum1() {
        return num1;
    }

    public void setNum1(double num1) {
        this.num1 = num1;
    }

    public double getNum2() {
        return num2;
    }

    public void setNum2(double num2) {
        this.num2 = num2;
    }

    public double getRes() {
        return res;
    }

    public void setRes(double res) {
        this.res = res;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getOperant() { return operant; }

    public void setOperant(String op) { this.operant = op; }

    private String timeStampToDate(long timestamp) {
        Date date = new Date(timestamp);
        SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss dd-MM-yyyy");
        return format.format(date);
    }

    @Override
    public String toString() {
        //return getNum1()+""+getOperant()+""+getNum2()+"="+getRes()+"   Time: "+timeStampToDate(getTimestamp());
        return "Time: "+timeStampToDate(getTimestamp())+"\n  "+getNum1()+""+getOperant()+""+getNum2()+"="+getRes();
    }
}
