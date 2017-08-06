/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.adriens.tickets.resto.nc.api;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author salad74
 */
public class HtmlUnitSamples {

    public static void main(String[] args) {
        /* System.out.println("Hello");
        try{
            TicketsRestaurantsServiceWrapper test = new TicketsRestaurantsServiceWrapper();
            test.connect("adrien", "coucou");
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
         */
        try {

            // turn off htmlunit warnings
            java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(java.util.logging.Level.OFF);
            java.util.logging.Logger.getLogger("org.apache.http").setLevel(java.util.logging.Level.OFF);

            WebClient webClient = new WebClient(BrowserVersion.CHROME);
            HtmlPage htmlPage = webClient.getPage("http://www.ticketrestaurant.nc");
            HtmlForm form = htmlPage.getHtmlElementById("login-form");

            HtmlTextInput loginField = form.getInputByName("username");
            loginField.setValueAttribute("xxxxxxxx");

            HtmlPasswordInput passwdField = form.getInputByName("password");
            passwdField.setValueAttribute("xxxxxxxx");

            HtmlButton button = form.getButtonByName("Submit");
            HtmlPage page2 = button.click();
            
            if (page2.asText().contains("Cet utilisateur n'existe pas")) {
                System.err.println("Cet utilisateur n'existe pas");
                throw (new Exception("Cet utilisateur n'existe pas"));
            } else {
                System.out.println("Successfully connected !");
                if(page2.asText().contains("Mon compte")){
                    System.out.println("Mon compte trouve.");
                }
                else{
                    System.err.println("Impossible de trouver mon compte");
                    throw (new Exception("Impossible de trouver mon compte"));
                }
            }
            System.out.println(page2.asText());
            // Extract Nom
            // xpath : /html/body/div[1]/div[2]/div[2]/div/strong[1]
            String name = page2.getFirstByXPath("/html/body/div[1]/div[2]/div[2]/div/strong[1]/text()").toString();
            //String name = element.getNodeValue();
            System.out.println("NOM DETECTE : " + name );

            // Extract Employeur
            String employeur = page2.getFirstByXPath("/html/body/div[1]/div[2]/div[2]/div/strong[2]/text()").toString();
            System.out.println("EMPLOYEUR DETECTE : " + employeur );
            
            // Extract solde
            String solde = page2.getFirstByXPath("/html/body/div[1]/div[2]/div[2]/div/strong[3]/text()").toString();
            System.out.println("SOLDE DETECTE : " + solde );
            
            solde = solde.replace("XPF", "");
            solde = StringUtils.chomp(solde);
            solde = StringUtils.deleteWhitespace(solde);
            System.out.println("SOLDE DETECTE : " + solde );
            
            // derniere date et montant de recharge
            
            // derniere depense/debit/lieu/date
            
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
