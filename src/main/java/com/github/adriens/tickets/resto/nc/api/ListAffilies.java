/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.adriens.tickets.resto.nc.api;

import java.util.ArrayList;
import java.util.Iterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author salad74
 */
public class ListAffilies {
    
    final static Logger logger = LoggerFactory.getLogger(ListAffilies.class);
    
    public static void main(String[] args) {
        try{
            ArrayList<Affilie> affilies = TicketsRestaurantsServiceWrapper.getAffilies();
            Iterator<Affilie> affIter = affilies.iterator();
            Affilie aff = new Affilie();
            
            logger.info("Affilies :\n");
            while(affIter.hasNext()){
                aff =  affIter.next();
                logger.info(aff.toString());
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        
    }
}
