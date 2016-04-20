package com.amberyork.wakeme;



public class MovementBin {

    //I am working with epoch because it allows me to round into bins easily
    private long binStartEpoch,binStopEpoch; //for recording actual first and last data point time

    private String binMinuteString;
    private int binN;//how many samples in bin
    private double binAvX,binAvY,binAvZ;//average movements
    private double binStdX,binStdY,binStdZ;//standard dev movements


    // constructors
    public MovementBin(){
    }

    /**
     *  @description constructor NEW MovementBin (new MovementBin input - no id/"created at" needed because this is done on insert)
     *  also doesn't use triggered_at because that hasn't happened yet
     *  @example      MovementBin newMovementBin = new MovementBin(set_time,boolLights, boolHeat, boolSound);

     */

    //constructor loaded up
    public MovementBin(long binStartEpoch,long binStopEpoch, String binMinuteString,int binN,
                       double binAvX,double binAvY,double binAvZ, double binStdX,double binStdY,double binStdZ) {

        this.binStartEpoch = binStartEpoch;
        this.binStopEpoch = binStopEpoch;
        this.binMinuteString = binMinuteString;
        this.binN = binN;
        this.binAvX = binAvX;
        this.binAvY = binAvY;
        this.binAvZ = binAvZ;
        this.binStdX = binStdX;
        this.binStdY = binStdY;
        this.binStdZ = binStdZ;

    }


    public long getBinStartEpoch() {
        return binStartEpoch;
    }

    public void setBinStartEpoch(long binStartEpoch) {
        this.binStartEpoch = binStartEpoch;
    }

    public long getBinStopEpoch() {
        return binStopEpoch;
    }

    public void setBinStopEpoch(long binStopEpoch) {
        this.binStopEpoch = binStopEpoch;
    }

    public String getBinMinuteString() {
        return binMinuteString;
    }

    public void setBinMinuteString(String binMinuteString) {
        this.binMinuteString = binMinuteString;
    }

    public int getBinN() {
        return binN;
    }

    public void setBinN(int binN) {
        this.binN = binN;
    }

    public double getBinAvX() {
        return binAvX;
    }

    public void setBinAvX(double binAvX) {
        this.binAvX = binAvX;
    }

    public double getBinAvY() {
        return binAvY;
    }

    public void setBinAvY(double binAvY) {
        this.binAvY = binAvY;
    }

    public double getBinAvZ() {
        return binAvZ;
    }

    public void setBinAvZ(double binAvZ) {
        this.binAvZ = binAvZ;
    }

    public double getBinStdX() {
        return binStdX;
    }

    public void setBinStdX(double binStdX) {
        this.binStdX = binStdX;
    }

    public double getBinStdY() {
        return binStdY;
    }

    public void setBinStdY(double binStdY) {
        this.binStdY = binStdY;
    }

    public double getBinStdZ() {
        return binStdZ;
    }

    public void setBinStdZ(double binStdZ) {
        this.binStdZ = binStdZ;
    }



}