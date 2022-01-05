/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.ui;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 *
 * @author Mehak Singla
 */
public interface UserIO {
    
    void print(String msg);
    
    void println(String message);

    int readInt(String prompt);

    int readInt(String prompt, int min, int max);

    String readString(String prompt);
    
    String readString(String prompt, int min, int max);
    
    String formatCurrency(BigDecimal amount);
    
    LocalDate readDate(String prompt);
    
    LocalDate readDate(String prompt, LocalDate min, LocalDate max);
    
    BigDecimal readBigDecimal(String prompt, int scale, BigDecimal min);
    
}
