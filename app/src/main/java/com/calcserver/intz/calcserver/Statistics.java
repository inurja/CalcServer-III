package com.calcserver.intz.calcserver;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Intz on 10.04.2016.
 */
public class Statistics implements IEntity {
    private long id;
    private long daystamp;
    private long operantId;
    private int dayCounter;
    private String operant;

    public Statistics() {

    }

    public Statistics(int daystamp, int operantId, int dayCounter) {
        setDaystamp(daystamp);
        setOperantId(operantId);
        setDayCounter(dayCounter);
    };

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public long getDaystamp() { return daystamp; }

    public void setDaystamp(long daystamp) { this.daystamp = daystamp; }

    public long getOperantId() { return operantId; }

    public void setOperantId(long operantId) { this.operantId = operantId; }

    public int getDayCounter() { return dayCounter; }

    public void setDayCounter(int dayCounter) { this.dayCounter = dayCounter; }
    
    public String getOperant() { return operant; }

    public void setOperant(String op) { this.operant = op; }

    private String dayStampToDate(long l) {
        SimpleDateFormat in = new SimpleDateFormat("ddMMyyyy");
        SimpleDateFormat out = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        try {
            date = in.parse(String.valueOf(l));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return out.format(date);
    }

    @Override
    public String toString() {
        return "Date: "+dayStampToDate(getDaystamp())+"\n  Operant: "+getOperant()+" has been used "+getDayCounter()+" times";
    }
}
