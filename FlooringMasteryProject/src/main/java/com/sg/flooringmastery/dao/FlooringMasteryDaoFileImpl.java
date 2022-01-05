/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.Tax;
import com.sg.flooringmastery.dto.Order;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * @author Mehak Singla
 */
public class FlooringMasteryDaoFileImpl implements FlooringMasteryDao {
    
    Map<String, Product> products = new HashMap<>();
    Map<String, Tax> taxes = new HashMap<>();
    Map<Integer, Order> orders = new HashMap<>();
    
    private String DELIMITER = ",";
    private String PRODUCTFILE = "src/Data/products.txt";
    private String TAXFILE = "src/Data/taxes.txt";
    private File ORDERFOLDER = new File("src/Orders");
    
    public FlooringMasteryDaoFileImpl() {
    }
    
    public void setFiles(String products, String taxes, String orders){
        PRODUCTFILE = products;
        TAXFILE = taxes;
        ORDERFOLDER = new File(orders);
    }
    
    @Override
    public List<Product> getProductList() {
        return new ArrayList<Product>(products.values());
    }

    @Override
    public List<Tax> getTaxList() {
        return new ArrayList<Tax>(taxes.values());
    }
    
    @Override
    public void removeOrder(int orderNumber) {
        orders.remove(orderNumber);
    }

    @Override
    public void addOrder(int orderNumber, Order order){
        orders.put(orderNumber, order);
    }

    @Override
    public Order getOrder(int orderNumber) {
        return orders.get(orderNumber);
    }
    
    @Override
    public List<Order> getOrderList() {
        return new ArrayList<Order>(orders.values());
    }

    @Override
    public Order unmarshallOrder(String line) {
        String[] orderTokens = line.split(DELIMITER);
        Order orderFromFile = new Order();
        orderFromFile.setOrderNumber(Integer.parseInt(orderTokens[0]));
        orderFromFile.setCustomerName(orderTokens[1]);
        orderFromFile.setState(orderTokens[2]);
        orderFromFile.setTaxRate(new BigDecimal(orderTokens[3]));
        orderFromFile.setProductType(orderTokens[4]);
        orderFromFile.setArea(new BigDecimal(orderTokens[5]));
        orderFromFile.setCostPerSqFt(new BigDecimal(orderTokens[6]));
        orderFromFile.setLaborCostPerSqFt(new BigDecimal(orderTokens[7]));
        orderFromFile.setMaterialCost(new BigDecimal(orderTokens[8]));
        orderFromFile.setLaborCost(new BigDecimal(orderTokens[9]));
        orderFromFile.setTaxCost(new BigDecimal(orderTokens[10]));
        orderFromFile.setTotal(new BigDecimal(orderTokens[11]));
        orderFromFile.setOrderDate(LocalDate.parse((orderTokens[12]), DateTimeFormatter.ofPattern("MMddyyyy")));
        return orderFromFile;
        
    }

    public void clearOrders(){
        orders.clear();
    }
    
    @Override
    public Product unmarshallProduct(String line) {
        String[] productTokens = line.split(DELIMITER);
        Product productFromFile = new Product();
        productFromFile.setProductType(productTokens[0]);
        productFromFile.setCostPerSqFt(new BigDecimal(productTokens[1]));
        productFromFile.setLaborCostPerSqFt(new BigDecimal(productTokens[2]));
        return productFromFile;
    }

    @Override
    public Tax unmarshallTax(String line) {
        String[] taxTokens = line.split(DELIMITER);
        Tax taxFromFile = new Tax();
        taxFromFile.setStateAbbrev(taxTokens[0]);
        taxFromFile.setStateName(taxTokens[1]);
        taxFromFile.setTaxRate(new BigDecimal(taxTokens[2]));
        return taxFromFile;
    }

    @Override
    public void readFiles() throws OrderPersistenceException {
        
        String line;
        Scanner scanner;
        Product currProduct;
        try {
            scanner = new Scanner(
                        new BufferedReader(
                            new FileReader(PRODUCTFILE)));
        } catch (FileNotFoundException e) {
            throw new OrderPersistenceException(
                    "-_- Could not open product file", e);
        }
        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            currProduct = unmarshallProduct(line);
            products.put(currProduct.getProductType(), currProduct);
        }
        
        Tax currTax;
        try {
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(TAXFILE)));
        } catch (FileNotFoundException e) {
            throw new OrderPersistenceException(
                        "-_- Could not open tax file" , e);
        }
        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            currTax = unmarshallTax(line);
            taxes.put(currTax.getStateAbbrev(), currTax);
        }
        Order currOrder;
        for (File currFile : ORDERFOLDER.listFiles()) {
            try {
                scanner = new Scanner(currFile);
            } catch (FileNotFoundException e) {
                throw new OrderPersistenceException("Could not find file: " + currFile.getName());
            }
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                currOrder = unmarshallOrder(line);
                orders.put(currOrder.getOrderNumber(), currOrder);
            }
        }
    }
    
    @Override
    public String marshall(Order order) {
        String orderString = order.getOrderNumber() + DELIMITER;
        orderString += order.getCustomerName()+ DELIMITER;
        orderString += order.getState()+ DELIMITER;
        orderString += order.getTaxRate()+ DELIMITER;
        orderString += order.getProductType()+ DELIMITER;
        orderString += order.getArea()+ DELIMITER;
        orderString += order.getCostPerSqFt() + DELIMITER;
        orderString += order.getLaborCostPerSqFt() + DELIMITER;
        orderString += order.getMaterialCost() + DELIMITER;
        orderString += order.getLaborCost() + DELIMITER;
        orderString += order.getTaxCost() + DELIMITER;
        orderString += order.getTotal() + DELIMITER;
        orderString += order.getOrderDate().format(DateTimeFormatter.ofPattern("MMddyyyy"));

        return orderString;
    }

    @Override
    public void writeFile() throws OrderPersistenceException {
        PrintWriter out;
        List<Order> ordersToWrite = this.getOrderList();
        
        if(ORDERFOLDER.length() == 0)
            clearOrderDirectory();
        
        for(Order currentOrder : ordersToWrite) {
            File currFile = new File(ORDERFOLDER + "/" + currentOrder.toFileName());
            try {
                out = new PrintWriter(new FileWriter(currFile));
            } catch (IOException e) {
                throw new OrderPersistenceException("Could not save order file : " + currentOrder.toFileName());
            }
            String orderAsText;
            orderAsText = marshall(currentOrder);
            out.println(orderAsText);
            out.flush();
            out.close();
        }
    }
    
    public void clearOrderDirectory(){
        for(File f: ORDERFOLDER.listFiles()){
            f.delete();
        }
    }
}