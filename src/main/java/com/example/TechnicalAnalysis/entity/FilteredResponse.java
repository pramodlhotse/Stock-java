package com.example.TechnicalAnalysis.entity;





import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;


public class FilteredResponse {


	List<DataPast> pastRecords1;
	List<Data> pastRecords;

	List<Data> records;
	
	double currentStrike;
	
	double underlyingPrice;
	
	double pastUnderlyingPrice;
	
	double putTotalOI;
	
	double callTotalOI;
	
	double putTotalVolume;
	
	double callTotalVolume;

	public List<DataPast> getPastRecords1() {
		return pastRecords1;
	}

	public void setPastRecords1(List<DataPast> pastRecords1) {
		this.pastRecords1 = pastRecords1;
	}

	public double getPutTotalVolume() {
		return putTotalVolume;
	}
	public void setPutTotalVolume(double putTotalVolume) {
		this.putTotalVolume = putTotalVolume;
	}
	public double getCallTotalVolume() {
		return callTotalVolume;
	}
	public void setCallTotalVolume(double callTotalVolume) {
		this.callTotalVolume = callTotalVolume;
	}
	public double getPutTotalOI() {
		return putTotalOI;
	}
	public void setPutTotalOI(double putTotalOI) {
		this.putTotalOI = putTotalOI;
	}
	public double getCallTotalOI() {
		return callTotalOI;
	}
	public void setCallTotalOI(double callTotalOI) {
		this.callTotalOI = callTotalOI;
	}

	HashMap<Double, Double> putLevels = new HashMap();
	HashMap<Double, Double> callLevels = new HashMap();
	public List<Data> getPastRecords() {
		return pastRecords;
	}
	public void setPastRecords(List<Data> pastRecords) {
		this.pastRecords = pastRecords;
	}
	public List<Data> getRecords() {
		return records;
	}
	public void setRecords(List<Data> records) {
		this.records = records;
	}
	public double getCurrentStrike() {
		return currentStrike;
	}
	public void setCurrentStrike(double currentStrike) {
		this.currentStrike = currentStrike;
	}
	public double getUnderlyingPrice() {
		return underlyingPrice;
	}
	public void setUnderlyingPrice(double underlyingPrice) {
		this.underlyingPrice = underlyingPrice;
	}
	public double getPastUnderlyingPrice() {
		return pastUnderlyingPrice;
	}
	public void setPastUnderlyingPrice(double pastUnderlyingPrice) {
		this.pastUnderlyingPrice = pastUnderlyingPrice;
	}
	public HashMap<Double, Double> getPutLevels() {
		return putLevels;
	}
	public void setPutLevels(HashMap<Double, Double> putLevels) {
		this.putLevels = putLevels;
	}
	public HashMap<Double, Double> getCallLevels() {
		return callLevels;
	}
	public void setCallLevels(HashMap<Double, Double> callLevels) {
		this.callLevels = callLevels;
	}




	@Override
	public String toString() {
		return "FilteredResponse{" +
				"pastRecords=" + pastRecords +
				", records=" + records +
				", currentStrike=" + currentStrike +
				", underlyingPrice=" + underlyingPrice +
				", pastUnderlyingPrice=" + pastUnderlyingPrice +
				", putTotalOI=" + putTotalOI +
				", callTotalOI=" + callTotalOI +
				", putTotalVolume=" + putTotalVolume +
				", callTotalVolume=" + callTotalVolume +
				", putLevels=" + putLevels +
				", callLevels=" + callLevels +
				'}';
	}
}
