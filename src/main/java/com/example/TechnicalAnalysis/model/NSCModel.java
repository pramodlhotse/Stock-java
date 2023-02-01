package com.example.TechnicalAnalysis.model;

public class NSCModel {
    private double impliedVolatility;
    private double totalTradedVolume;

    public NSCModel(double impliedVolatility, double totalTradedVolume) {
        this.impliedVolatility = impliedVolatility;
        this.totalTradedVolume = totalTradedVolume;
    }

    public double getImpliedVolatility() {
        return impliedVolatility;
    }

    public void setImpliedVolatility(double impliedVolatility) {
        this.impliedVolatility = impliedVolatility;
    }

    public double getTotalTradedVolume() {
        return totalTradedVolume;
    }

    public void setTotalTradedVolume(double totalTradedVolume) {
        this.totalTradedVolume = totalTradedVolume;
    }

    @Override
    public String toString() {
        return "NSCModel{" +
                "impliedVolatility=" + impliedVolatility +
                ", totalTradedVolume=" + totalTradedVolume +
                '}';
    }
}
