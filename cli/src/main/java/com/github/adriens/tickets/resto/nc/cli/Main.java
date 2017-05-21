/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.adriens.tickets.resto.nc.cli;

import com.github.adriens.tickets.resto.nc.api.TicketsRestaurantsServiceWrapper;

/**
 *
 * @author salad74
 */
public class Main {

    public static void main(String[] args) {

        try{
                ExcelWrapper wrap = new ExcelWrapper();
            
            wrap.createOrUpdateSolde("solde.xlsx", args[0], args[1]);
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        
        /*try {
            System.out.println("LOGIN : " + args[0]);
            System.out.println("PASSWORD : " + args[1]);
            
            TicketsRestaurantsServiceWrapper ticketsService = new TicketsRestaurantsServiceWrapper(args[0], args[1]);
            int soldeBalance = ticketsService.getAccountBalance();
            System.out.println("SOLDE détecté : " + soldeBalance);
        } catch (Exception ex) {
            ex.printStackTrace();
        }*/

    }
}
