/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sg.flooringmastery.ui;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 * @author Mehak Singla
 */
public class UserIOConsoleImpl implements UserIO{
    
    Scanner sc = new Scanner(System.in);

    @Override
    public void print(String msg) {
        System.out.print(msg);
    }

    @Override
    public void println(String message) {
        System.out.println(message);
    }

    @Override
    public int readInt(String prompt) {
        System.out.println(prompt);
        Integer i = sc.nextInt();
        return i;
    }

    @Override
    public int readInt(String prompt, int min, int max) {
           Integer i;
       do{
           System.out.println(prompt);
           i = Integer.parseInt(sc.nextLine());
           if(i>max || i<min){
               System.out.println("Your values are not within range.");
           }
       }while(i>max || i<min);
       return i;
    }
    
    @Override
    public String readString(String prompt) {
        System.out.println(prompt);
        String str = sc.nextLine();
        return str;
    }

    @Override
    public String readString(String prompt, int min, int max) {
        String str;
        do{
            System.out.println(prompt);
            str = sc.nextLine();
            if(str.length()>max || str.length()<min){
                System.out.println("Your values are not within range.");
            }
        }while(str.length()>max || str.length()<min);
        return str;
    }

    @Override
    public String formatCurrency(BigDecimal amount) {
        return NumberFormat.getCurrencyInstance().format(amount);
    }

    @Override
    public LocalDate readDate(String prompt) {
        System.out.println(prompt);
        LocalDate date = LocalDate.parse(prompt, DateTimeFormatter.ofPattern("MM-DD-YYYY"));
        return date;
    }

    @Override
    public LocalDate readDate(String prompt, LocalDate min, LocalDate max) {
        LocalDate d;
        do{
            System.out.println(prompt);
            d = LocalDate.parse(prompt, DateTimeFormatter.ofPattern("MM-DD-YYYY"));
            if(d.isAfter(min)&& d.isBefore(max)){
                System.out.println("Please choose a date within bounds.");
            }
        }while(d.isAfter(min) && d.isBefore(max));        
        return d;
    }

  
    @Override
    public BigDecimal readBigDecimal(String prompt, int scale, BigDecimal min) {
         boolean valid = false;
        BigDecimal result = null;
        String value = null;
        do {
            result = new BigDecimal(value).setScale(scale, RoundingMode.CEILING);
            if (result.compareTo(min) >= 0) {
                valid = true;
            } else {
                String minString = String.valueOf(min);
                System.out.printf("The value must be greater than %s.\n", minString);
            }
        } while (!valid);

        return result;
    }

}
