/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sg.flooringmastery.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * @author Mehak Singla
 */
public class Order {
    
    private int orderNumber;
    private String customerName;
    private String state;
    private String productType;
    private BigDecimal taxRate;
    private BigDecimal area;
    private BigDecimal costPerSqFt;
    private BigDecimal laborCostPerSqFt;
    private BigDecimal materialCost;
    private BigDecimal laborCost;
    private BigDecimal taxCost;
    private BigDecimal total;
    private LocalDate orderDate;
    
    /**
     * area
     * materialCost = area*costPerSqFt
     * laborCost = area * laborCostPerSqFt
     * taxCost = (materialCost+laborCost) * (taxRate/100)
     * total = materialCost + laborCost + taxCost
     * 
     */
    
    public Order(){
        
    }

    public Order(Product product, Tax tax) {
        this.productType = product.getProductType();
        this.costPerSqFt = product.getCostPerSqFt();
        this.laborCostPerSqFt = product.getLaborCostPerSqFt();
        this.state = tax.getStateAbbrev();
        this.taxRate = tax.getTaxRate();
    }
    
    public void calculateCosts(){
        this.materialCost = this.area.multiply(this.costPerSqFt).setScale(2, RoundingMode.HALF_UP);
        this.laborCost = this.area.multiply(this.laborCostPerSqFt).setScale(2, RoundingMode.HALF_UP);
        this.taxCost = (this.materialCost.add(this.laborCost)).multiply(this.taxRate.divide
                                (new BigDecimal("100"))).setScale(2, RoundingMode.HALF_UP);
        this.total = (this.materialCost.add(this.laborCost).add(this.taxCost)).setScale(2, RoundingMode.HALF_UP);
    }

    
    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public BigDecimal getArea() {
        return area;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }

    public BigDecimal getCostPerSqFt() {
        return costPerSqFt;
    }

    public void setCostPerSqFt(BigDecimal costPerSqFt) {
        this.costPerSqFt = costPerSqFt;
    }

    public BigDecimal getLaborCostPerSqFt() {
        return laborCostPerSqFt;
    }

    public void setLaborCostPerSqFt(BigDecimal laborCostPerSqFt) {
        this.laborCostPerSqFt = laborCostPerSqFt;
    }

    public BigDecimal getMaterialCost() {
        return materialCost;
    }

    public void setMaterialCost(BigDecimal materialCost) {
        this.materialCost = materialCost;
    }

    public BigDecimal getLaborCost() {
        return laborCost;
    }

    public void setLaborCost(BigDecimal laborCost) {
        this.laborCost = laborCost;
    }

    public BigDecimal getTaxCost() {
        return taxCost;
    }

    public void setTaxCost(BigDecimal taxCost) {
        this.taxCost = taxCost;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    @Override
    public String toString() {
        return "Order{" + "orderNumber=" + orderNumber + ", customerName=" + customerName + 
                ", state=" + state + ", productType=" + productType + ", taxRate=" + taxRate + 
                ", area=" + area + ", costPerSqFt=" + costPerSqFt + ", laborCostPerSqFt=" + laborCostPerSqFt + 
                ", materialCost=" + materialCost + ", laborCost=" + laborCost + ", taxCost=" + taxCost + 
                ", total=" + total + ", orderDate=" + orderDate + '}';
    }

    public String toFileName(){
        return "orders_" + this.orderDate.format(DateTimeFormatter.ofPattern("MMddyyyy"))+".txt";
    }
    
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + this.orderNumber;
        hash = 23 * hash + Objects.hashCode(this.customerName);
        hash = 23 * hash + Objects.hashCode(this.state);
        hash = 23 * hash + Objects.hashCode(this.productType);
        hash = 23 * hash + Objects.hashCode(this.taxRate);
        hash = 23 * hash + Objects.hashCode(this.area);
        hash = 23 * hash + Objects.hashCode(this.costPerSqFt);
        hash = 23 * hash + Objects.hashCode(this.laborCostPerSqFt);
        hash = 23 * hash + Objects.hashCode(this.materialCost);
        hash = 23 * hash + Objects.hashCode(this.laborCost);
        hash = 23 * hash + Objects.hashCode(this.taxCost);
        hash = 23 * hash + Objects.hashCode(this.total);
        hash = 23 * hash + Objects.hashCode(this.orderDate);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Order other = (Order) obj;
        if (this.orderNumber != other.orderNumber) {
            return false;
        }
        if (!Objects.equals(this.customerName, other.customerName)) {
            return false;
        }
        if (!Objects.equals(this.state, other.state)) {
            return false;
        }
        if (!Objects.equals(this.productType, other.productType)) {
            return false;
        }
        if (!Objects.equals(this.taxRate, other.taxRate)) {
            return false;
        }
        if (!Objects.equals(this.area, other.area)) {
            return false;
        }
        if (!Objects.equals(this.costPerSqFt, other.costPerSqFt)) {
            return false;
        }
        if (!Objects.equals(this.laborCostPerSqFt, other.laborCostPerSqFt)) {
            return false;
        }
        if (!Objects.equals(this.materialCost, other.materialCost)) {
            return false;
        }
        if (!Objects.equals(this.laborCost, other.laborCost)) {
            return false;
        }
        if (!Objects.equals(this.taxCost, other.taxCost)) {
            return false;
        }
        if (!Objects.equals(this.total, other.total)) {
            return false;
        }
        if (!Objects.equals(this.orderDate, other.orderDate)) {
            return false;
        }
        return true;
    }
    
}
