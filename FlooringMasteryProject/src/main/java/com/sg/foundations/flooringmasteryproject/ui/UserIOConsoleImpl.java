/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sg.foundations.flooringmasteryproject.ui;

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
    public double readDouble(String prompt) {
        System.out.println(prompt);
        Double d = sc.nextDouble();
        return d;   
    }

    @Override
    public double readDouble(String prompt, double min, double max) {
        Double d;
        do{
            System.out.println(prompt);
            d= Double.parseDouble(sc.nextLine());

            if (d > max || d< min){
                System.out.println("Your entry was not within the specified bounds."); 
            }
        }while(d > max || d< min);
         
        return d;
    }

    @Override
    public float readFloat(String prompt) {
        System.out.println(prompt);
        Float f = sc.nextFloat();
        return f;
    }

    @Override
    public float readFloat(String prompt, float min, float max) {
        Float f;
       do{
           System.out.println(prompt);
           f = Float.parseFloat(sc.nextLine());
           if(f>max || f<min){
               System.out.println("Your values are not within range.");
           }
       }while(f>max || f<min);
       return f;
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
    public long readLong(String prompt) {
        System.out.println(prompt);
        Long l = sc.nextLong();
        return l;
    }

    @Override
    public long readLong(String prompt, long min, long max) {
        Long lg;
        do{
            System.out.println(prompt);
            lg = Long.parseLong(sc.nextLine());
            
            if(lg>max || lg<min){
                System.out.println("Your values are not within range.");
            }
        }while(lg>max || lg<min);
        return lg;
    }

    @Override
    public String readString(String prompt) {
        System.out.println(prompt);
        String str = sc.nextLine();
        return str;
    }

}
