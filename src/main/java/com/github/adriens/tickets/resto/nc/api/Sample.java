/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.adriens.tickets.resto.nc.api;

import java.util.Iterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author salad74
 */
public class Sample {

    final static Logger logger = LoggerFactory.getLogger(Sample.class);

    public static void main(String[] args) {
        try {
            String login = "xxxxxxxx";
            String password = "xxxxxx";

            TicketsRestaurantsServiceWrapper wrap = new TicketsRestaurantsServiceWrapper(login, password, ServiceType.BOTH);
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

        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(1);
        }

    }
}
