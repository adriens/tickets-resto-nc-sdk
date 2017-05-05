/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.adriens.tickets.resto.nc.cli;

/**
 *
 * @author salad74
 */
public class Main {
       public static void main(String[] args) {
            try{
                ExcelWrapper wrap = new ExcelWrapper();
            wrap.create("affilies.xlsx");
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
            
       }
}
