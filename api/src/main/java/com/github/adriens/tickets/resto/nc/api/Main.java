/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.adriens.tickets.resto.nc.api;

import com.gargoylesoftware.htmlunit.ProxyConfig;
import java.util.ArrayList;
import java.util.Iterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author salad74
 */
public class Main {
    final static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            String login = args[0];
            String password = args[1];
            
            // Avec proxy :
            //ProxyConfig proxyConfig =  new ProxyConfig("proxyweb", 3128);
            
            // Sans proxy
            ProxyConfig proxyConfig =  null;
            
            TicketsRestaurantsServiceWrapper wrap = new TicketsRestaurantsServiceWrapper(proxyConfig,login, password);
            // now deal with with account, credit, transactions ;-p
            logger.info("################################################");
            logger.info("Solde (XPF) : " + wrap.getAccountBalance());
            logger.info("Employeur : " + wrap.getAccountEmployeer());
            logger.info("Beneficiaire : " + wrap.getAccountName());
            logger.info("################################################");
            
            
            // Listing transactions
            logger.info("################################################");
            logger.info("Transactions :\n");
            Iterator<Transaction> iter = wrap.getTransactions().iterator();
            
            while (iter.hasNext()) {
                logger.info(iter.next().toString());
                
            }
            logger.info("################################################");
            
            
            logger.info("################################################");
            logger.info("Fetching affilies :");
            ArrayList<Affilie> affilies = TicketsRestaurantsServiceWrapper.getAffilies();
            Iterator<Affilie> affIter = affilies.iterator();
            Affilie lAffilie = new Affilie();
            while(affIter.hasNext()){
                lAffilie = affIter.next();
                logger.info(lAffilie.toString());
            }
            
            System.exit(0);
            
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            System.exit(1);
        }
    }
}
