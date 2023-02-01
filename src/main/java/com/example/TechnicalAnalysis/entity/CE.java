package com.example.TechnicalAnalysis.entity;


import javax.persistence.*;
import java.text.DecimalFormat;
import java.util.Date;

@Entity
@NamedQuery(name = "CE.findByDate",
		query = "select e from CE e where e.pastAndCurrentRecord = 'Current'" +
				"and to_char(e.date, 'YYYY-MM-DD HH24:MI') = ?1 and e.underlying = ?2")
public class CE {

	private static final DecimalFormat df = new DecimalFormat("0.0000");
	@Id
	Long ceId;

	Long id;

	Date date;
	double strikePrice;
	
	String expiryDate;
	
	String underlying;
	
	String identifier;
	
	double openInterest;
	
	double changeinOpenInterest;
	
	double pchangeinOpenInterest;
	
	double totalTradedVolume;
	
	double impliedVolatility;
	
	double lastPrice;
	
	double change;
	
	double pChange;
	
	double totalBuyQuantity;
	
	double totalSellQuantity;
	
	double bidQty;
	
	double bidprice;
	
	double askQty;
	
	double askPrice;
	
	double underlyingValue;
	
	double totOI;
	
	double totVol;

	String pastAndCurrentRecord;


	public Long getCeId() {
		return ceId;
	}

	public void setCeId(Long ceId) {
		this.ceId = ceId;
	}

	public String getPastAndCurrentRecord() {
		return pastAndCurrentRecord;
	}

	public void setPastAndCurrentRecord(String pastAndCurrentRecord) {
		this.pastAndCurrentRecord = pastAndCurrentRecord;
	}

	public double getTotOI() {
		return totOI;
	}

	public void setTotOI(double totOI) {
		this.totOI = totOI;
	}

	public double getTotVol() {
		return totVol;
	}

	public void setTotVol(double totVol) {
		this.totVol = totVol;
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

	public String getUnderlying() {
		return underlying;
	}

	public void setUnderlying(String underlying) {
		this.underlying = underlying;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public double getOpenInterest() {
		return openInterest;
	}

	public void setOpenInterest(double openInterest) {
		this.openInterest = openInterest;
	}

	public double getChangeinOpenInterest() {
		return changeinOpenInterest;
	}

	public void setChangeinOpenInterest(double changeinOpenInterest) {
		this.changeinOpenInterest = changeinOpenInterest;
	}

	public double getPchangeinOpenInterest() {
		return pchangeinOpenInterest;
	}

	public void setPchangeinOpenInterest(double pchangeinOpenInterest) {
		this.pchangeinOpenInterest = pchangeinOpenInterest;
	}

	public double getTotalTradedVolume() {
		return totalTradedVolume;
	}

	public void setTotalTradedVolume(double totalTradedVolume) {
		this.totalTradedVolume = totalTradedVolume;
	}

	public double getImpliedVolatility() {
		return impliedVolatility;
	}

	public void setImpliedVolatility(double impliedVolatility) {
		this.impliedVolatility = impliedVolatility;
	}

	public double getLastPrice() {
		return lastPrice;
	}

	public void setLastPrice(double lastPrice) {
		this.lastPrice = lastPrice;
	}

	public double getChange() {
		return change;
	}

	public void setChange(double change) {
		change = Double.parseDouble(df.format(change));
		this.change = change;
	}

	public double getpChange() {
		return pChange;
	}

	public void setpChange(double pChange) {
		this.pChange = pChange;
	}

	public double getTotalBuyQuantity() {
		return totalBuyQuantity;
	}

	public void setTotalBuyQuantity(double totalBuyQuantity) {
		this.totalBuyQuantity = totalBuyQuantity;
	}

	public double getTotalSellQuantity() {
		return totalSellQuantity;
	}

	public void setTotalSellQuantity(double totalSellQuantity) {
		this.totalSellQuantity = totalSellQuantity;
	}

	public double getBidQty() {
		return bidQty;
	}

	public void setBidQty(double bidQty) {
		this.bidQty = bidQty;
	}

	public double getBidprice() {
		return bidprice;
	}

	public void setBidprice(double bidprice) {
		this.bidprice = bidprice;
	}

	public double getAskQty() {
		return askQty;
	}

	public void setAskQty(double askQty) {
		this.askQty = askQty;
	}

	public double getAskPrice() {
		return askPrice;
	}

	public void setAskPrice(double askPrice) {
		this.askPrice = askPrice;
	}

	public double getUnderlyingValue() {
		return underlyingValue;
	}

	public void setUnderlyingValue(double underlyingValue) {
		this.underlyingValue = underlyingValue;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "CE{" +
				"strikePrice=" + strikePrice +
				", expiryDate='" + expiryDate + '\'' +
				", underlying='" + underlying + '\'' +
				", identifier='" + identifier + '\'' +
				", openInterest=" + openInterest +
				", changeinOpenInterest=" + changeinOpenInterest +
				", pchangeinOpenInterest=" + pchangeinOpenInterest +
				", totalTradedVolume=" + totalTradedVolume +
				", impliedVolatility=" + impliedVolatility +
				", lastPrice=" + lastPrice +
				", change=" + change +
				", pChange=" + pChange +
				", totalBuyQuantity=" + totalBuyQuantity +
				", totalSellQuantity=" + totalSellQuantity +
				", bidQty=" + bidQty +
				", bidprice=" + bidprice +
				", askQty=" + askQty +
				", askPrice=" + askPrice +
				", underlyingValue=" + underlyingValue +
				", totOI=" + totOI +
				", totVol=" + totVol +
				'}';
	}
}
