/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sg.flooringmastery.ui;

import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.Tax;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;



/**
 * @author Mehak Singla
 */
public class View {
    
    private UserIO io;
    
    public View(UserIO io){
        this.io = io;
    }
    
    public int printMenuAndGetSelection(){
        io.println("********************************");
        io.println("<<<< Flooring Mastery Main Menu >>>>");
        io.println("1. Display Orders");
        io.println("2. Add Orders");
        io.println("3. Edit Orders");
        io.println("4. Remove Orders");
        io.println("5. Exit");
        io.println("********************************");
        return io.readInt("Please select from the above options:", 1,5);
    }
    
    public void displayOrdersList(List<Order> orders){
        io.println("LISTING ALL ORDERS FROM " + orders.get(0).getOrderDate());
        for(Order currOrder : orders){
            displayOrder(currOrder);
        }
    }
    
    
    public void displayOrder(Order ordr){
        io.println("Date: " + ordr.getOrderDate());
        io.println("Order Number: " + ordr.getOrderNumber());
        io.println("Customer : " + ordr.getCustomerName());
        io.println("State: " + ordr.getState());
        io.println("Tax Rate: " + ordr.getTaxRate() + "%");
        io.println("Product Type: " + ordr.getProductType());
        io.println("Area: " + ordr.getArea() + " sqft");
        io.println("Cost Per Square Foot: " + ordr.getCostPerSqFt());
        io.println("Labor Cost Per Square Foot: " + ordr.getLaborCostPerSqFt());
        io.println("Material Cost: " + ordr.getMaterialCost());
        io.println("Labor Cost: " + ordr.getLaborCost());
        io.println("Tax: " + ordr.getTaxCost());
        io.println("======= Total: " +ordr.getTotal() + " ========");
        
    }
    
    public int displayGetOrderNumber(){
        int orderNumber = io.readInt("Please enter the order's order number.");
        return orderNumber;
    }
        
    public LocalDate displayGetDate(){ 
        String dateAsString = io.readString("Please enter a date in MMDDYYYY format");
        while(!(dateAsString.matches("^[0-9]{8}$"))){  
            dateAsString = io.readString("Invalid format: Please format date as MMDDYYYY");
        }
        LocalDate date = LocalDate.parse(dateAsString,DateTimeFormatter.ofPattern("MMddyyyy"));
        return date;
    }
   
    
    public int displayRemoveOrder(Order toRemove){
        io.println("REMOVE MENU");
        displayOrder(toRemove);
        return io.readInt("Are you sure you would like to delete this order? \n1.Yes \n2.No");
    }
        
    public void displayRemovedSuccessfully(){
        io.println("ORDER SUCCESSFULLY REMOVED");
        displayContinue();
    }

    public void displayAllProducts(List<Product> products){
        int i = 1;
        for(Product p : products){
            String toPrint = String.format("\n%d: %-10s \nCost per square foot: %-5s \nLabor cost per square foot: %s\n"
                    ,i,p.getProductType(),p.getCostPerSqFt(),p.getLaborCostPerSqFt());
            io.print(toPrint);
            i++;
        }
    }
    
    
    public Order displayAddOrder(List<Order> orders,List<Tax> taxes, List<Product> products){
        io.println("ADDING ORDER");
        Order newOrder;
        Tax orderTax = taxes.get(0);
        Product orderProduct;
        int max;
        
        
        LocalDate orderDate = displayGetDate();
        while(LocalDate.now().isAfter(orderDate)){
            io.print("Order date must be in the future.");
            orderDate = displayGetDate();
        }
        
        String customerName = io.readString("Please enter the customer's name. ");
        while(customerName.equals("") || !(customerName.matches("^[A-Za-z0-9,.\\s]*$"))){
            customerName = io.readString("Please enter the customer's name. (Names can only contain alphanumeric values, commas, and periods.");
        }
        String orderState = io.readString("Please enter the order's state.");
        
        List<String> stateNames = taxes.stream().map(t -> t.getStateAbbrev()).collect(Collectors.toList());
        
        while(!stateNames.contains(orderState)){
            orderState = io.readString("We cannot sell in " + orderState + ", please enter a valid state, or type 0 to return to the main menu.");
            if(orderState.equals("0"))
                return null;
        }
        
        displayAllProducts(products);
        int answerToProductType = io.readInt("\nWhich product would you like to select?",1,products.size());
        orderProduct = products.get(answerToProductType-1);
        String orderAreaString = io.readString("Please enter the area of the flooring.");
        
        
        BigDecimal orderArea = new BigDecimal(orderAreaString);
        while(orderArea.compareTo(new BigDecimal("100"))== -1){
            orderAreaString = io.readString("Please enter the area of the flooring. (Area must be a minimum of 100sq ft.)");
            orderArea = new BigDecimal(orderAreaString);
        }
        
        if(orders.isEmpty())
            max = 0;
        else{
            List<Integer> orderNumbers = orders.stream().map(p->p.getOrderNumber()).collect(Collectors.toList());
            max = orders.get(0).getOrderNumber();
            for(Integer currNum : orderNumbers){
                if(currNum > max)
                    max = currNum;
            }
        }
        for(Tax tax : taxes){
            if(tax.getStateAbbrev().equals(orderState)){
                orderTax = tax;
                break;
            }
        }
        
        newOrder = new Order(orderProduct,orderTax);
        newOrder.setOrderNumber(max+1);
        newOrder.setOrderDate(orderDate);
        newOrder.setCustomerName(customerName);
        newOrder.setArea(orderArea);
        newOrder.calculateCosts();
        
        return newOrder;
    }
    
    public boolean displayConfirmOrder(Order currOrder){
        displayOrder(currOrder);
        if(io.readInt("Does this look correct?\n1.Yes\n2.No",1,2) == 1)
            return true;
        return false;
    }
    
    public void displayAddedSuccessfully(){
        io.println("ORDER ADDED SUCCESFULLY");
        displayContinue();
    }
    
    public Order displayEditMenu(Order toEdit,List<Product> products,List<Tax> taxes){
        io.readString("EDITING MENU");
        Order editedOrder;
        Product newProduct ;
        Tax newTax = taxes.get(0);
        BigDecimal orderArea;
        Product originalProduct = (products.stream().filter(p -> p.getProductType().equals(toEdit.getProductType())).collect(Collectors.toList()).get(0));
        List<String> listOfProductNames = products.stream().map(p->p.getProductType()).collect(Collectors.toList());
        String newName = io.readString("Enter customer name("+ toEdit.getCustomerName()+")");
        while(!(newName.matches("^[A-Za-z0-9,.\\s]*$"))){
            io.print("Names can only contain alphanumeric values, commas, and periods.");
            newName = io.readString("Please enter customer name("+ toEdit.getCustomerName()+")");
        }
        if(newName.equals(""))
            newName = toEdit.getCustomerName();
        
        List<String> stateNames = taxes.stream().map(t -> t.getStateAbbrev()).collect(Collectors.toList());
        String newState = io.readString("Please enter customer state (" + toEdit.getState() +")");
        
        while( !stateNames.contains(newState) && !(newState.equals(""))){
            newState  = io.readString("We cannot sell in " + newState + ", please enter a valid state.");
        }
        
        if(newState.equals(""))
            newState = toEdit.getState();
        
        String newProductType = io.readString("Please enter product type("+ toEdit.getProductType()+")");
        
        while(!(listOfProductNames.contains(newProductType))){
            if(newProductType.equals("")){
                newProduct = originalProduct;
                break;
            }
            io.print("Invalid option, please enter a product type by name.");
            displayAllProducts(products);     
            newProductType = io.readString("Please enter product type("+ toEdit.getProductType()+")");
        }
        
        final String finalProductType = newProductType;
        List<Product> fromFilter = products.stream().filter(p->p.getProductType().equals(finalProductType)).collect(Collectors.toList());
        if(fromFilter.isEmpty()){
            newProduct = originalProduct;
        }
        else{
            newProduct = fromFilter.get(0);
        }
        
        String orderAreaString = io.readString("Please enter the area of the flooring("+ toEdit.getArea()+").");
        
        if(orderAreaString.equals("")){
            orderArea = toEdit.getArea();
        }
        else{
            orderArea = new BigDecimal(orderAreaString);
            while(orderArea.compareTo(new BigDecimal("100"))== -1){
                orderAreaString = io.readString("Please enter the area of the flooring. (Area must be a minimum of 100sq ft.)");
                if(orderAreaString.equals(""))
                    orderArea = toEdit.getArea();
                orderArea = new BigDecimal(orderAreaString);
            }
        }
        
        for(Tax tax : taxes){
            if(tax.getStateAbbrev().equals(newState)){
                newTax = tax;
                break;
            }
        }
        
        editedOrder = new Order(newProduct,newTax);
        editedOrder.setOrderDate(toEdit.getOrderDate());
        editedOrder.setOrderNumber(toEdit.getOrderNumber());
        editedOrder.setCustomerName(newName);
        editedOrder.setArea(orderArea);
        editedOrder.calculateCosts();
        return editedOrder;
    }

    
    public void displayEditedSuccesfully(){
        io.println("ORDER SUCCESSFULLY EDITED");
        displayContinue();
    }
    
    public void displayDisplayOrderBanner(){
        io.println("======Display Orders======");
    }
     
    public void displayException(Exception e){
        io.print(e.getMessage());
    }
        
    public void displayExitBanner() {
        io.println("Good Bye!!!");
    }

    public void displayUnknownCommandBanner() {
        io.print("Unknown Command!!!");
        displayContinue();
    }

    public void displayErrorMessage(String errorMsg) {
        io.print("=== ERROR ===");
        io.print(errorMsg);
        displayContinue();
    }

    public void displayContinue() {
        io.readString("Please hit enter to continue.");
    }
    
}
