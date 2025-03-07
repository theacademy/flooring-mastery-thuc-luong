package com.sg.flooringmastery.dto;

import java.math.BigDecimal;

public class Tax {
    private String state;
    private String stateAbbreviation;
    private BigDecimal taxRate;

    public Tax(String stateAbbreviation) {
        this.stateAbbreviation = stateAbbreviation;
    }

    public Tax(String stateAbbreviation, String state, BigDecimal taxRate) {
        this.state = state;
        this.stateAbbreviation = stateAbbreviation;
        this.taxRate = taxRate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStateAbbreviation() {
        return stateAbbreviation;
    }

    public void setStateAbbreviation(String stateAbbreviation) {
        this.stateAbbreviation = stateAbbreviation;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }
}
