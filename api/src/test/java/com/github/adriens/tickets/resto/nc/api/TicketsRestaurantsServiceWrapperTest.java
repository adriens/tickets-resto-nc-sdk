/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.adriens.tickets.resto.nc.api;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import static com.github.adriens.tickets.resto.nc.api.TicketsRestaurantsServiceWrapper.LOGIN_FORM_ID;
import static com.github.adriens.tickets.resto.nc.api.TicketsRestaurantsServiceWrapper.URL;
import java.io.IOException;
import junit.framework.TestCase;

/**
 *
 * @author salad74
 */
public class TicketsRestaurantsServiceWrapperTest extends TestCase {

    public TicketsRestaurantsServiceWrapperTest(){
        java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(java.util.logging.Level.OFF);
        java.util.logging.Logger.getLogger("org.apache.http").setLevel(java.util.logging.Level.OFF);

    }
    public void testSoldeConverter() {
        assertEquals(21310, TicketsRestaurantsServiceWrapper.extractSolde("21 310 XPF"));
    }

    public void testHomePage() throws Exception {
        try (final WebClient webClient = new WebClient()) {
            final HtmlPage page = webClient.getPage(TicketsRestaurantsServiceWrapper.URL);
            assertEquals(TicketsRestaurantsServiceWrapper.SITE_TITLE, page.getTitleText());
            
            final HtmlPage pageMentionsLegales = webClient.getPage(TicketsRestaurantsServiceWrapper.URL_MENTIONS_LEGALES);
            assertTrue("Le RCS doit matcher", pageMentionsLegales.asText().contains(TicketsRestaurantsServiceWrapper.RCS));
        }
    }
    public void tesAPIComplianceWithWebsite(){
            try{
                assertTrue(TicketsRestaurantsServiceWrapper.loginFormExistsWithExpectedId());
            }
            catch(IOException ex){
                assertNull(ex);
            }
            
            try{
                WebClient webClient = new WebClient(BrowserVersion.CHROME);
            HtmlPage htmlPage = webClient.getPage(URL);
            HtmlForm form = htmlPage.getHtmlElementById(LOGIN_FORM_ID);
                assertTrue(TicketsRestaurantsServiceWrapper.loginFormExistsWithExpectedUsernameInput(htmlPage));
            }
            catch(IOException ex){
                assertNull(ex);
            }
            
            try{
                WebClient webClient = new WebClient(BrowserVersion.CHROME);
            HtmlPage htmlPage = webClient.getPage(URL);
            HtmlForm form = htmlPage.getHtmlElementById(LOGIN_FORM_ID);
                assertTrue(TicketsRestaurantsServiceWrapper.loginFormExistsWithExpectedPasswordInput(htmlPage));
            }
            catch(IOException ex){
                assertNull(ex);
            }
            
            try{
                WebClient webClient = new WebClient(BrowserVersion.CHROME);
            HtmlPage htmlPage = webClient.getPage(URL);
            HtmlForm form = htmlPage.getHtmlElementById(LOGIN_FORM_ID);
                assertTrue(TicketsRestaurantsServiceWrapper.loginFormExistsWithExpectedSubmitButton(htmlPage));
            }
            catch(IOException ex){
                assertNull(ex);
            }
            
    
    }
}
