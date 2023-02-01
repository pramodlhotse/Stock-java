package com.example.TechnicalAnalysis.model;


import javax.persistence.*;
import java.util.Date;


@Entity
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Date date;
    Double currentStrike;

    Double underlyingPrice;

    String underlying;

    public String getUnderlying() {
        return underlying;
    }

    public void setUnderlying(String underlying) {
        this.underlying = underlying;
    }

    Double pastUnderlyingPrice;

    Double putTotalOI;

    Double callTotalOI;

    @Column(precision=8, scale=2)
    Double putTotalVolume;

    @Column(precision=8, scale=2)
    Double callTotalVolume;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getCurrentStrike() {
        return currentStrike;
    }

    public void setCurrentStrike(Double currentStrike) {
        this.currentStrike = currentStrike;
    }

    public Double getUnderlyingPrice() {
        return underlyingPrice;
    }

    public void setUnderlyingPrice(double underlyingPrice) {
        this.underlyingPrice = underlyingPrice;
    }

    public Double getPastUnderlyingPrice() {
        return pastUnderlyingPrice;
    }

    public void setPastUnderlyingPrice(Double pastUnderlyingPrice) {
        this.pastUnderlyingPrice = pastUnderlyingPrice;
    }

    public Double getPutTotalOI() {
        return putTotalOI;
    }

    public void setPutTotalOI(Double putTotalOI) {
        this.putTotalOI = putTotalOI;
    }

    public Double getCallTotalOI() {
        return callTotalOI;
    }

    public void setCallTotalOI(Double callTotalOI) {
        this.callTotalOI = callTotalOI;
    }

    public Double getPutTotalVolume() {
        return putTotalVolume;
    }

    public void setPutTotalVolume(Double putTotalVolume) {
        this.putTotalVolume = putTotalVolume;
    }

    public Double getCallTotalVolume() {
        return callTotalVolume;
    }

    public void setCallTotalVolume(Double callTotalVolume) {
        this.callTotalVolume = callTotalVolume;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setUnderlyingPrice(Double underlyingPrice) {
        this.underlyingPrice = underlyingPrice;
    }
}
