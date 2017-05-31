/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.adriens.tickets.resto.nc.cli;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author salad74
 */
public class Main {
    
    final static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        try{
                ExcelWrapper wrap = new ExcelWrapper();
            
            wrap.createOrUpdateSolde("solde.xlsx", args[0], args[1]);
            wrap.generateAffiliesSheet();
            }
            catch(Exception ex){
                logger.error(ex.getMessage());
            }
        
        /*try {
            logger.info("LOGIN : " + args[0]);
            logger.info("PASSWORD : " + args[1]);
            
            TicketsRestaurantsServiceWrapper ticketsService = new TicketsRestaurantsServiceWrapper(args[0], args[1]);
            int soldeBalance = ticketsService.getAccountBalance();
            logger.info("SOLDE détecté : " + soldeBalance);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }*/

    }
}
