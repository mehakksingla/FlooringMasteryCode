/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sg.flooringmastery.controller;

import com.sg.flooringmastery.dao.OrderPersistenceException;
import com.sg.flooringmastery.service.FlooringMasteryServiceLayer;
import com.sg.flooringmastery.service.InvalidDataException;
import com.sg.flooringmastery.ui.View;
import java.time.LocalDate;
import java.util.List;
import com.sg.flooringmastery.dto.Order;

/**
 * @author Mehak Singla
 */
public class Controller {
    
    private FlooringMasteryServiceLayer service;
    private View view;
    
    public Controller(FlooringMasteryServiceLayer service, View view){
        this.service=service;
        this.view=view;
        
    }
    
    public void run() throws OrderPersistenceException{
            boolean keepGoing = true;
            
            service.readFiles();
            while(keepGoing){
                int answer = view.printMenuAndGetSelection();
                try{
                    switch(answer){
                        case 1:
                            displayOrders();
                            break;
                        case 2:
                            addOrder();
                            break;
                        case 3:
                            editOrder();
                            break;
                        case 4:
                            removeOrder();
                            break;
                        case 5:
                            keepGoing = false;
                            break;
                        }
                    }catch(Exception e){
                    view.displayException(e);
                }
                service.writeFile(); 
            } exitMessage();
    }
        
    
    public void displayOrders() throws InvalidDataException{
        LocalDate orderDate = view.displayGetDate();
        List<Order> orders;
        try{
            orders = service.getOrdersByDate(orderDate);
        }catch(InvalidDataException e){
            throw e;
        }
        view.displayOrdersList(orders);
    }
    
    public void addOrder() throws OrderPersistenceException{
        Order toAdd = view.displayAddOrder(service.getAllOrders(),service.getAllTaxes(),service.getAllProducts());
        boolean toContinue = view.displayConfirmOrder(toAdd);
        if(toContinue){
            service.insertOrder(toAdd);
            view.displayAddedSuccessfully();
        }
    }
    
    public void editOrder() throws InvalidDataException,OrderPersistenceException {
        Order toEdit;
        LocalDate orderDate = view.displayGetDate();
        int orderNumber = view.displayGetOrderNumber();
        try{
            toEdit = service.getOrder(orderNumber,orderDate);
        }catch(InvalidDataException e){
            throw e;
        }
        Order editedOrder = view.displayEditMenu(toEdit,service.getAllProducts(),service.getAllTaxes());
        if(view.displayConfirmOrder(editedOrder)){
            service.editOrder(editedOrder);
            view.displayEditedSuccesfully();
        }
    }
    
    public void removeOrder() throws InvalidDataException,OrderPersistenceException{
        int answer;
        Order toRemove;
        LocalDate orderDate = view.displayGetDate();
        int orderNumber = view.displayGetOrderNumber();
       
        toRemove = service.getOrder(orderNumber,orderDate);
        
        answer = view.displayRemoveOrder(toRemove);
        if(answer == 1){
            service.removeOrder(toRemove);
            view.displayRemovedSuccessfully();
        }
    } 
   
    private void exitMessage(){
        view.displayExitBanner();
    }
}
