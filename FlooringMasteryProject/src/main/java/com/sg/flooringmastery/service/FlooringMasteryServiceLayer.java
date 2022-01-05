/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.OrderPersistenceException;
import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.Tax;
import com.sg.flooringmastery.dto.Order;
import java.time.LocalDate;
import java.util.List;
/**
 * @author Mehak Singla
 */
public interface FlooringMasteryServiceLayer {

    public void insertOrder(Order toInsert) throws OrderPersistenceException;
    public void editOrder(Order order) throws InvalidDataException,OrderPersistenceException;
    public void removeOrder(Order order) throws OrderPersistenceException;
    public Order getOrder(int orderNumber, LocalDate date) throws InvalidDataException;
    public List<Order> getOrdersByDate(LocalDate date) throws InvalidDataException;
    public void readFiles() throws OrderPersistenceException;
    public void writeFile() throws OrderPersistenceException;
    public List<Product> getAllProducts();
    public List<Tax> getAllTaxes();
    public List<Order> getAllOrders();
    
}
