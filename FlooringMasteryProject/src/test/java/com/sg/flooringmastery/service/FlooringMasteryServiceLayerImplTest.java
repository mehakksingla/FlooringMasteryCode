/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.FlooringMasteryAuditDaoImpl;
import com.sg.flooringmastery.dao.FlooringMasteryDaoFileImpl;
import com.sg.flooringmastery.dao.OrderPersistenceException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.sg.flooringmastery.dto.Order;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Mehak Singla
 */
public class FlooringMasteryServiceLayerImplTest {
    
    FlooringMasteryDaoFileImpl dao = new FlooringMasteryDaoFileImpl();
    FlooringMasteryServiceLayer service = new FlooringMasteryServiceLayerImpl(dao,new FlooringMasteryAuditDaoImpl());
//    FlooringMasteryServiceLayer service;
    
    public FlooringMasteryServiceLayerImplTest() {
//        ApplicationContext appContext = new ClassPathXmlApplicationContext("applicationContext.xml");
//        service = appContext.getBean("service", FlooringMasteryServiceLayer.class);
        dao.setFiles("src/testData/testProducts.txt", "src/testData/testTaxes.txt", "src/testOrders");
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
//        service.clearOrders();
    }

      @Test
    public void testInsertOrder() {
        List<Order> isEmpty = service.getAllOrders();
        LocalDate testDate = LocalDate.now();
        assertTrue(isEmpty.isEmpty(), "List should be empty, but has " + isEmpty.size() + " elements.");
        Order isEquals = new Order();
        Order testOrder = new Order();
        testOrder.setOrderNumber(1);
        testOrder.setOrderDate(testDate);
        
        
        try{
            service.insertOrder(testOrder);
            isEmpty = service.getAllOrders();
            assertFalse(isEmpty.isEmpty(), "Order was not added to list");
        
            isEquals = service.getOrder(1,testDate);
        }catch(OrderPersistenceException e){
            
        }catch(InvalidDataException e){
            fail("Order not added to collection");
        }

        assertTrue(testOrder.equals(isEquals), "Retrieved item does not match added item");
    }
    
    @Test
    public void testGetOrdersByDate(){
        Order september = new Order();
        LocalDate testDate = LocalDate.parse("09102023",DateTimeFormatter.ofPattern("MMddyyyy"));
        september.setOrderNumber(1);
        september.setOrderDate(testDate);
        List<Order> byDate = new ArrayList<>();
        List<Order> allOrders = new ArrayList<>();
        
        try{
            service.insertOrder(september);
            allOrders = service.getAllOrders();
            assertTrue(allOrders.size() == 1,"Incorrect number of orders in collection. Expected 1, but got " + allOrders.size());
        
            byDate = service.getOrdersByDate(testDate);
            
        }catch(InvalidDataException e){
            fail("No Orders found for date");
        }catch(OrderPersistenceException e){
            fail("File could not be found");
        }
        
        assertTrue(september.equals(byDate.get(0)),"Retrieved order by date does not match initial order");
    }
    
    @Test
    public void testEditOrder(){
        LocalDate testDate = LocalDate.now();
        Order testOrder = new Order();
        List<Order> allOrders = service.getAllOrders();
        Order getOrder = new Order();
        assertEquals(allOrders.size(),0,"Orders List not empty at start");
        
        testOrder.setOrderNumber(1);
        testOrder.setOrderDate(testDate);
        testOrder.setCustomerName("John");
        testOrder.setState("TX");
        
        try{
            service.insertOrder(testOrder);
        }catch(OrderPersistenceException e){     
        }
        
        allOrders = service.getAllOrders();
        assertEquals(allOrders.size(),1,"Order was not added to list");
        
        Order editedOrder = new Order();
        editedOrder.setOrderNumber(1);
        editedOrder.setOrderDate(testDate);
        editedOrder.setCustomerName("Bob");
        editedOrder.setState("WA");
        
        try{
            service.editOrder(editedOrder);
            getOrder = service.getOrder(1,testDate);
        }catch(InvalidDataException e){
            fail("Original order and edited order do not match");
        }catch(OrderPersistenceException e){     
        }
        assertNotEquals(testOrder,getOrder,"Original order not edited");
        assertEquals(editedOrder,getOrder,"Edited information is not correct");
    }
    
    @Test
    public void testRemoveOrder(){
        LocalDate testDate = LocalDate.now();
        Order testOrder = new Order();
        testOrder.setOrderNumber(1);
        testOrder.setOrderDate(testDate);
        testOrder.setCustomerName("John");
        testOrder.setState("TX");
        List<Order> allOrders = service.getAllOrders();
        
        assertTrue(allOrders.isEmpty(),"List not empty at start");
           
        try{
            service.insertOrder(testOrder);
            allOrders = service.getAllOrders();  
            assertFalse(allOrders.isEmpty(),"Order not added correctly to list");
            
            service.removeOrder(testOrder);
            allOrders = service.getAllOrders(); 
            assertTrue(allOrders.isEmpty(),"Order was not removed correctly");
        }catch(OrderPersistenceException e){
        }
    }
    
}
