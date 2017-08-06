/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.adriens.tickets.resto.nc.api;

import java.util.Date;
import junit.framework.TestCase;

/**
 *
 * @author salad74
 */
public class TransactionTest extends TestCase{
    public TransactionTest(){
        
    }
    
    public void testIsRecharge(){
        Transaction t = new Transaction();
        
        t = new Transaction(new Date(), "Recharge", 0, 0);
        assertTrue(t.isRecharge());
        
        t = new Transaction(new Date(), "Transaction CHAMPION N'GEA", 0, 0);
        assertFalse(t.isRecharge());
    }
    public void testIsTransaction(){
        Transaction t = new Transaction();
        
        t = new Transaction(new Date(), "Recharge", 0, 0);
        assertFalse(t.isTransaction());
        
        t = new Transaction(new Date(), "Transaction CHAMPION N'GEA", 0, 0);
        assertTrue(t.isTransaction());
    
    }
    
}
