/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.FlooringMasteryAuditDao;
import com.sg.flooringmastery.dao.FlooringMasteryDao;
import com.sg.flooringmastery.dao.OrderPersistenceException;
import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.Tax;
import com.sg.flooringmastery.dto.Order;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author Mehak Singla
 */
public class FlooringMasteryServiceLayerImpl implements FlooringMasteryServiceLayer{

    private FlooringMasteryDao dao;
    private FlooringMasteryAuditDao auditDao;
    
    public FlooringMasteryServiceLayerImpl(FlooringMasteryDao dao,FlooringMasteryAuditDao auditDao){
        this.dao = dao;
        this.auditDao = auditDao;
    }
    /**
     * Adds an order to the in-memory map
     */
    
    @Override
    public void insertOrder(Order toInsert) throws OrderPersistenceException{
        dao.addOrder(toInsert.getOrderNumber(), toInsert);
        try{
            auditDao.writeAuditEntry("ORDER SUCCESSFULLY ADDED.");
        } catch(OrderPersistenceException e){
            throw e;
        }
    }
    
    /**
     * 
     */
    @Override
    public Order getOrder(int orderNumber, LocalDate date)throws InvalidDataException{
        List<Order> possibleOrder = getOrdersByDate(date);
        List<Order> fromList = possibleOrder.stream().filter(p -> p.getOrderNumber() == orderNumber).collect(Collectors.toList());
        if(fromList.isEmpty())
            throw new InvalidDataException("ERROR: No order with that date/number combination");
        Order toReturn = fromList.get(0);
        return toReturn;
            
        
    }
    /**
     * Removes an entry from the map and replaces it with a new one
     */
    @Override
    public void editOrder(Order order) throws InvalidDataException,OrderPersistenceException{
        Order oldOrder = getOrder(order.getOrderNumber(),order.getOrderDate());
        if(oldOrder == null)
            throw new InvalidDataException("ERROR: COULD NOT FIND ORDER FOR THAT DATE WHILE EDITING");
        removeOrder(oldOrder);
        try{
            insertOrder(order);
            auditDao.writeAuditEntry("ORDER SUCCESSFULLY EDITED (REMOVED ORIGINAL AND ADDED NEW DATA");
        } catch(OrderPersistenceException e){
            throw e;
        }
    }
    /**
     * removes an order from the map
     */
    @Override
    public void removeOrder(Order order) throws OrderPersistenceException{
        try{
            dao.removeOrder(order.getOrderNumber());
            auditDao.writeAuditEntry("ORDER SUCCESSFULLY REMOVED");
        } catch(OrderPersistenceException e){
            throw e;
        }
    }
    
    @Override
    public List<Order> getOrdersByDate(LocalDate date) throws InvalidDataException{
        List<Order> orders = dao.getOrderList().stream().filter(p -> p.getOrderDate().equals(date)).collect(Collectors.toList());
        if(orders.isEmpty()){
            throw new InvalidDataException("ERROR: COULD NOT FIND ORDERS FOR THAT DATE");
        }
        return orders;
    }
    
    @Override
    public void readFiles() throws OrderPersistenceException{
        try{
        dao.readFiles();
        auditDao.writeAuditEntry("DATA LOADED FROM THE TEXT FILE");
        } catch (OrderPersistenceException e){
            throw e;
        }
    }
    
    @Override
    public void writeFile() throws OrderPersistenceException{
        try{
        dao.writeFile();
        auditDao.writeAuditEntry("DATA WRITTEN TO THE TEXT FILe");
        } catch (OrderPersistenceException e){
            throw e;
        }
    }
    

    @Override
    public List<Product> getAllProducts() {
       return dao.getProductList();
    }

    @Override
    public List<Tax> getAllTaxes() {
        return dao.getTaxList();
    }

    @Override
    public List<Order> getAllOrders() {
        return dao.getOrderList();
    }

}
