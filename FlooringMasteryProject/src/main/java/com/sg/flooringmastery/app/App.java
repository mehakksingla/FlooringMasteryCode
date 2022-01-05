/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sg.flooringmastery.app;

import com.sg.flooringmastery.controller.Controller;
import com.sg.flooringmastery.dao.OrderPersistenceException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Mehak Singla
 */
public class App {
    public static void main(String[] args)  throws OrderPersistenceException {
        
        ApplicationContext appContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        Controller controller = appContext.getBean("controller", Controller.class);
        controller.run();
    }
}
