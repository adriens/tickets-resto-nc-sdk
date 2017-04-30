/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.adriens.tickets.resto.nc.api;

/**
 *
 * @author salad74
 */
public class RealMain {

    public static void main(String[] args) {
        try {
            String login = "xxxxxxx";
            String password = "xxxxxxx";
            TicketsRestaurantsServiceWrapper wrap = new TicketsRestaurantsServiceWrapper(login, password);
            // now deal with with account, credit, transactions ;-p
            
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
