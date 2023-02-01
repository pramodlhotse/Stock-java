package com.example.TechnicalAnalysis.entity;

import com.fasterxml.jackson.annotation.JsonProperty;


public class DataPast {



	double strikePrice;
	
	String expiryDate;

	@JsonProperty("PE")
	PEPast pe;

	@JsonProperty("CE")
	CEPast ce;

	public PEPast getPe() {
		return pe;
	}

	public void setPe(PEPast pe) {
		this.pe = pe;
	}

	public CEPast getCe() {
		return ce;
	}

	public void setCe(CEPast ce) {
		this.ce = ce;
	}

	public double getStrikePrice() {
		return strikePrice;
	}
	public void setStrikePrice(double strikePrice) {
		this.strikePrice = strikePrice;
	}
	public String getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}


	@Override
	public String toString() {
		return "Data{" +
				"strikePrice=" + strikePrice +
				", expiryDate='" + expiryDate + '\'' +
				", pe=" + pe +
				", ce=" + ce +
				'}';
	}
}


