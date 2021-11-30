/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sg.foundations.flooringmasteryproject.dao;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

/**
 * @author Mehak Singla
 */
public class FlooringMasteryAuditDaoImpl implements FlooringMasteryAuditDao{
    
     public static final String AUDIT_FILE = "audit.txt";

    public void writeAuditEntry (String entry) throws OrderPersistenceException { //it can throw exception
        
        PrintWriter out;
        
        try{
            out = new PrintWriter(new FileWriter(AUDIT_FILE, true));
        }catch(IOException e){
            throw new OrderPersistenceException("Could not persist audit information.", e);
        }
        
        LocalDateTime timestamp = LocalDateTime.now();
        out.println(timestamp.toString() + " : " + entry);
        out.flush();
        
    }


}
