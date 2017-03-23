package redittai.com.shawarma.is.life;

import android.util.Log;

/**
 * Created by USER on 13/02/2017.
 */

public class shawarma {


    private String name;
    private int pita;
    private int laffa;
    private int isEgel;
    private int isCeves;
    private int isPargit;
    private int kosher;
    private double rank;
    private int parking;
    private double lat;
    private double lon;
    private int counter;
    private double total_rate;
    private float distance;
    private String FBkey;
    private int not_shawarma;


    public shawarma(String name, int pita, int laffa, int isEgel, int isCeves, int isPargit, int kosher, double rank, int parking, double lat, double lon) {
        this.name = name;
        this.pita = pita;
        this.laffa = laffa;
        this.isEgel = isEgel;
        this.isCeves = isCeves;
        this.isPargit = isPargit;
        this.kosher = kosher;
        this.total_rate = rank;
        this.parking = parking;
        this.lat= lat;
        this.lon= lon;
        this.counter = 1;
        this.rank = rank;
        this.distance = 0;
        this.not_shawarma=0;
    }

    public shawarma() {
    }

    public void setRank(double rank) {
        this.rank = rank;
    }

    public void setTotal_rate(double total_rate) {
        this.total_rate = total_rate;
    }

    public String getFBkey() {
        return FBkey;
    }

    public void setFBkey(String FBkey) {
        this.FBkey = FBkey;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPita() {
        return pita;
    }

    public void setPita(int pita) {
        this.pita = pita;
    }

    public int getLaffa() {
        return laffa;
    }

    public void setLaffa(int laffa) {
        this.laffa = laffa;
    }

    public int getKosher() {
        return kosher;
    }

    public void setKosher(int kosher) {
        this.kosher = kosher;
    }

    public int getParking() {
        return parking;
    }

    public void setParking(int parking) {
        this.parking = parking;
    }

    public double getRank() {
        return rank;
    }



    public int getIsEgel() {
        return isEgel;
    }

    public void setIsEgel(int isEgel) {
        this.isEgel = isEgel;
    }

    public int getIsCeves() {
        return isCeves;
    }

    public void setIsCeves(int isCeves) {
        this.isCeves = isCeves;
    }

    public int getIsPargit() {
        return isPargit;
    }

    public void setIsPargit(int isPargit) {
        this.isPargit = isPargit;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public double getTotal_rate() {
        return total_rate;
    }

    public int getNot_shawarma() {
        return not_shawarma;
    }

    public void setNot_shawarma(int not_shawarma) {
        this.not_shawarma = not_shawarma;
    }

    @Override
    public String toString() {
        return "shawarma{" +
                "name='" + name + '\'' +
                ", pita=" + pita +
                ", laffa=" + laffa +
                ", isEgel=" + isEgel +
                ", isCeves=" + isCeves +
                ", isPargit=" + isPargit +
                ", kosher=" + kosher +
                ", rank=" + rank +
                ", parking=" + parking +
                ", lat=" + lat +
                ", lon=" + lon +
                ", counter=" + counter +
                ", total_rate=" + total_rate +
                ", distance=" + distance +
                '}';
    }

    public void rateStore (float newRate)
    {
        this.total_rate += newRate;
        Log.i("a7","total rate: "+total_rate);
        this.counter++;
        this.rank = total_rate / counter;
    }

}
