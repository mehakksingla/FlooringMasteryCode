/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.Tax;
import com.sg.flooringmastery.dto.Order;
import java.util.List;

/**
 *
 * @author Mehak Singla
 */
public interface FlooringMasteryDao {
    
    public List<Order> getOrderList();
    public List<Product> getProductList();
    public List<Tax> getTaxList();
    public void removeOrder(int orderNumber);
    public void addOrder(int orderNumber, Order order);
    public Order getOrder(int orderNumber);
    public Order unmarshallOrder(String line);
    public Product unmarshallProduct(String line);
    public Tax unmarshallTax(String line);
    public String marshall(Order order);
    public void writeFile() throws OrderPersistenceException;
    public void readFiles() throws OrderPersistenceException;
    
}
