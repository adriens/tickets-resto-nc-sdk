/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.adriens.tickets.resto.nc.api;

import junit.framework.TestCase;

/**
 *
 * @author salad74
 */
public class AffilieTest extends TestCase{
    public AffilieTest(){
        
    }
    public void  testTelephoneFormating(){
        //Affilie aff = new Affilie();
        //aff = new Affilie("Test", "Cat", "Cuisine", "289747", "adresse", "commune", "quartier" );
        //System.out.println("Fomated tel : " + aff.getFormattedTelephoneNumber());
        assertEquals("+687 28.97.47", Affilie.getFormattedTelephoneNumber("289747"));
        assertEquals(null, Affilie.getFormattedTelephoneNumber(null));
        assertEquals("", Affilie.getFormattedTelephoneNumber(""));
        assertEquals("", Affilie.getFormattedTelephoneNumber(" "));
        
        
    }
}
