/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sg.flooringmastery.dto;

import java.math.BigDecimal;

/**
 * @author Mehak Singla
 */
public class Tax {
    
    private String stateAbbrev, stateName;
    private BigDecimal taxRate;

    public Tax() {
    }

    public Tax(String stateAbbrev, String stateName, BigDecimal taxRate) {
        this.stateAbbrev = stateAbbrev;
        this.stateName = stateName;
        this.taxRate = taxRate;
    }

    public String getStateAbbrev() {
        return stateAbbrev;
    }

    public void setStateAbbrev(String stateAbbrev) {
        this.stateAbbrev = stateAbbrev;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }
    
}
