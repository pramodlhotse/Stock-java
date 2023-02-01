package com.example.TechnicalAnalysis.model;

public class PriceResponse {

    private Integer id;
    private Double underlying;
    private Double ltpce;

    private Double ltppe;

    private String expiryDate;

    public Double getUnderlying() {
        return underlying;
    }

    public PriceResponse(Integer id, double underlying, double ltpce, double ltppe, String expiryDate) {
        this.id = id;
        this.underlying = underlying;
        this.ltpce = ltpce;
        this.ltppe = ltppe;
        this.expiryDate = expiryDate;
    }

    public void setUnderlying(Double underlying) {
        this.underlying = underlying;
    }

    public Double getLtpce() {
        return ltpce;
    }

    public void setLtpce(Double ltpce) {
        this.ltpce = ltpce;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getLtppe() {
        return ltppe;
    }

    public void setLtppe(Double ltppe) {
        this.ltppe = ltppe;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }
}
