package com.calcserver.intz.calcserver;

/**
 * Created by Intz on 10.04.2016.
 */
public class OperationTypes implements IEntity {
    private long id;
    private String operant;
    private int lifetimeCounter;

    public OperationTypes() {
    }

    public OperationTypes(String operant, int lifetimeCounter) {
        setOperant(operant);
        setLifetimeCounter(lifetimeCounter);
    }

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public String getOperant() { return operant; }

    public void setOperant(String operant) { this.operant = operant.trim(); }

    public int getLifetimeCounter() { return lifetimeCounter; }

    public void setLifetimeCounter(int lifetimeCounter) { this.lifetimeCounter = lifetimeCounter; }

    @Override
    public String toString() {
        return operant + " has been used " + lifetimeCounter + " times.";
    }
}
