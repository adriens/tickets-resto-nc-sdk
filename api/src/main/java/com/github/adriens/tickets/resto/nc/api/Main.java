/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.adriens.tickets.resto.nc.api;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author salad74
 */
public class Main {

    public static void main(String[] args) {
        try {
            String login = args[0];
            String password = args[1];
            
            TicketsRestaurantsServiceWrapper wrap = new TicketsRestaurantsServiceWrapper(login, password);
            // now deal with with account, credit, transactions ;-p
            System.out.println("################################################");
            System.out.println("Solde (XPF) : " + wrap.getAccountBalance());
            System.out.println("Employeur : " + wrap.getAccountEmployeer());
            System.out.println("Beneficiaire : " + wrap.getAccountName());
            
            // Listing transactions
            /*System.out.println("Transactions :\n");
            Iterator<Transaction> iter = wrap.getTransactions().iterator();
            
            while (iter.hasNext()) {
                System.out.println(iter.next().toString());
                
            }
            System.out.println("################################################");
            */
            
            System.out.println("Affilies :");
            ArrayList<Affilie> affilies = TicketsRestaurantsServiceWrapper.getAffilies();
            Iterator<Affilie> affIter = affilies.iterator();
            Affilie lAffilie = new Affilie();
            while(affIter.hasNext()){
                lAffilie = affIter.next();
                System.out.println(lAffilie);
            }
            
            System.exit(0);
            
        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(1);
        }
    }
}
