/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.adriens.tickets.resto.nc.api;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableBody;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author salad74
 */
public class TicketsRestaurantsServiceWrapper {

    /**
     * @return the transactions
     */
    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    /**
     * @param transactions the transactions to set
     */
    public void setTransactions(ArrayList<Transaction> transactions) {
        this.transactions = transactions;
    }

    public static final String URL = "http://www.ticketrestaurant.nc/";
    public static final String URL_MENTIONS_LEGALES = "http://www.ticketrestaurant.nc/mentions-legales";
    public static final String URL_AFFILIES = "http://www.ticketrestaurant.nc/liste-des-affilies";

    public static final String SITE_TITLE = "Ticket Restaurant - La carte ticket restaurant en Nouvelle Calédonie";
    // form ids
    public static final String LOGIN_FORM_ID = "login-form";
    public static final String LOGIN_FORM_FIELD_ID_LOGIN = "username";
    public static final String LOGIN_FORM_FIELD_ID_PASSWORD = "password";
    public static final String LOGIN_FORM_FIELD_ID_SUBMIT_BUTTON = "Submit";

    // static texts
    public static final String TEXT_USER_DOES_NOT_EXISTS = "Cet utilisateur n'existe pas";
    public static final String TEXT_MON_COMPTE = "Mon compte";
    public static final String TEXT_RECHARGE = "Recharge";
    public static final String TEXT_TRANSACTION = "Transaction";
    public static final String RCS = "RCS NOUMEA 2013 B 1 181 346";
    // xpath resources
    public static final String XPATH_NAME = "/html/body/div[1]/div[2]/div[2]/div/strong[1]/text()";
    public static final String XPATH_EMPLOYEUR = "/html/body/div[1]/div[2]/div[2]/div/strong[2]/text()";
    public static final String XPATH_SOLDE = "/html/body/div[1]/div[2]/div[2]/div/strong[3]/text()";

    // Account Page
    private HtmlPage accountPage;
    private String accountName;
    private String accountEmployeer;
    private int accountBalance;

    private ArrayList<Transaction> transactions;

    public TicketsRestaurantsServiceWrapper() {
        // Disable verbose logs
        java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(java.util.logging.Level.OFF);
        java.util.logging.Logger.getLogger("org.apache.http").setLevel(java.util.logging.Level.OFF);

    }

    public TicketsRestaurantsServiceWrapper(String login, String password) throws Exception {
        // Disable verbose logs
        java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(java.util.logging.Level.OFF);
        java.util.logging.Logger.getLogger("org.apache.http").setLevel(java.util.logging.Level.OFF);
        setUpAccount(login, password);
        feedTransactions();

    }

    // static utility functions
    public final static int extractSolde(String soldeString) throws NumberFormatException {
        if (soldeString == null) {
            return 0;
        }
        if (soldeString.trim().length() == 0) {
            return 0;
        }
        if (soldeString.isEmpty()) {
            return 0;
        }
        String solde = soldeString;
        System.out.println("Input SOLDE : " + solde);

        solde = solde.replace("XPF", "");
        solde = StringUtils.chomp(solde);
        solde = StringUtils.deleteWhitespace(solde);
        System.out.println("SOLDE DETECTE : " + solde);
        // convert it to a number
        try {
            return Integer.parseInt(solde);
        } catch (NumberFormatException ex) {
            System.err.println("Unable to cast String of solde <" + solde + ">into it numeric version : " + ex.getMessage());
            throw ex;
        }
    }

    public final static Date convertFromTextDate(String dateInString) throws ParseException {
        // input format expected : DD/MM/YYYY
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date = formatter.parse(dateInString);
            //System.out.println(date);
            //System.out.println(formatter.format(date));
            return date;
        } catch (ParseException ex) {
            System.err.println("Unable to parse date <" + dateInString + "> to date : " + ex.getMessage());
            throw ex;
        }
    }

    protected final static boolean loginFormExistsWithExpectedId() throws IOException {
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        HtmlPage htmlPage = webClient.getPage(TicketsRestaurantsServiceWrapper.URL);
        // check if we can find the form
        HtmlForm form = htmlPage.getHtmlElementById(LOGIN_FORM_ID);
        if (form == null) {
            return false;
        } else {
            return true;
        }
    }

    protected final static boolean loginFormExistsWithExpectedUsernameInput(HtmlPage htmlPage) throws IOException {
        //check if we can fin login input
        HtmlForm form = htmlPage.getHtmlElementById(LOGIN_FORM_ID);
        HtmlTextInput loginField = form.getInputByName(LOGIN_FORM_FIELD_ID_LOGIN);
        if (loginField == null) {
            return false;
        } else {
            return true;
        }
    }

    protected final static boolean loginFormExistsWithExpectedPasswordInput(HtmlPage htmlPage) throws IOException {
        //check if we can fin login input
        HtmlForm form = htmlPage.getHtmlElementById(LOGIN_FORM_ID);
        HtmlPasswordInput passwdField = form.getInputByName(LOGIN_FORM_FIELD_ID_PASSWORD);
        return (passwdField != null) ? true : false;
    }

    protected final static boolean loginFormExistsWithExpectedSubmitButton(HtmlPage htmlPage) throws IOException {
        //check if we can fin login input
        HtmlForm form = htmlPage.getHtmlElementById(LOGIN_FORM_ID);
        HtmlButton button = form.getButtonByName(LOGIN_FORM_FIELD_ID_SUBMIT_BUTTON);
        if (button != null) {
            return true;
        } else {
            return false;
        }
    }

    private void setUpAccount(String login, String password) throws Exception {
        // first perform login to webpage
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        HtmlPage htmlPage = webClient.getPage(URL);
        HtmlForm form = htmlPage.getHtmlElementById(LOGIN_FORM_ID);

        HtmlTextInput loginField = form.getInputByName(LOGIN_FORM_FIELD_ID_LOGIN);
        loginField.setValueAttribute(login);

        HtmlPasswordInput passwdField = form.getInputByName(LOGIN_FORM_FIELD_ID_PASSWORD);
        passwdField.setValueAttribute(password);

        HtmlButton button = form.getButtonByName(LOGIN_FORM_FIELD_ID_SUBMIT_BUTTON);
        this.accountPage = button.click();

        if (accountPage.asText().contains(TEXT_USER_DOES_NOT_EXISTS)) {
            System.err.println(TEXT_USER_DOES_NOT_EXISTS);
            throw (new Exception(TEXT_USER_DOES_NOT_EXISTS));
        } else {
            System.out.println("Successfully connected !");
            if (accountPage.asText().contains(TEXT_MON_COMPTE)) {
                System.out.println("Mon compte trouve.");
            } else {
                System.err.println("Impossible de trouver mon compte");
                throw (new Exception("Impossible de toruver mon compte"));
            }
        }
        // Extract name
        this.setAccountName(accountPage.getFirstByXPath(XPATH_NAME).toString());
        System.out.println("Name found : <" + getAccountName() + ">");

        // Extract Employeer
        this.setAccountEmployeer(accountPage.getFirstByXPath(XPATH_EMPLOYEUR).toString());
        System.out.println("Employeer found : <" + getAccountEmployeer() + ">");

        // Extract balance
        this.setAccountBalance(extractSolde(accountPage.getFirstByXPath(XPATH_SOLDE).toString()));
        System.out.println("Balace found : <" + getAccountBalance() + ">");
    }

    private void feedTransactions() throws Exception {
        // first, click on "TRANSACTIONS
        // find the transaction button
        setTransactions(new ArrayList<>());
        HtmlAnchor transactionsAnchor = accountPage.getAnchorByHref("/transactions");
        HtmlPage transactionsPage = transactionsAnchor.click();
        //System.out.println(transactionsPage.asText());
        if (transactionsPage.asText().contains("Liste de vos transactions")) {
            System.out.println("Transactions found");
        } else {
            System.err.println("Was not able to fetch transactions");
            throw new Exception("Was not able to fetch transactions");
        }
        // now we have to fetch transactions, one by one
        HtmlTable transactionsTable = transactionsPage.getHtmlElementById("DataTables_Table_0");
        String dateAsString;
        String libele;
        String debitAsString;
        String credititAsString;
        Date transactionDate;
        Transaction lTransaction = new Transaction();
        for (final HtmlTableBody body : transactionsTable.getBodies()) {
            final List<HtmlTableRow> rows = body.getRows();
            System.out.println("Rows found : " + rows.size());
            // now fetch each row
            Iterator<HtmlTableRow> rowIter = rows.iterator();
            HtmlTableRow theRow;

            while (rowIter.hasNext()) {
                theRow = rowIter.next();
                dateAsString = theRow.getCell(0).asText();
                libele = theRow.getCell(1).asText();
                debitAsString = theRow.getCell(2).asText();
                credititAsString = theRow.getCell(3).asText();
                //System.out.println(theRow);
                lTransaction = new Transaction(convertFromTextDate(dateAsString), libele, extractSolde(debitAsString), extractSolde(credititAsString));
                getTransactions().add(lTransaction);
                System.out.println("Added new transction : " + lTransaction.toString());
            }
            System.out.println("End of <" + getTransactions().size() + "> transactions fetching");
        }
    }

    @Override
    public String toString() {
        String out = "Nom : " + getAccountName() + "\n" + "Employeer : " + getAccountEmployeer() + "\n" + "Balance : " + getAccountBalance();
        return out;
    }

    /**
     * @return the accountName
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * @param accountName the accountName to set
     */
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    /**
     * @return the accountEmployeer
     */
    public String getAccountEmployeer() {
        return accountEmployeer;
    }

    /**
     * @param accountEmployeer the accountEmployeer to set
     */
    public void setAccountEmployeer(String accountEmployeer) {
        this.accountEmployeer = accountEmployeer;
    }

    /**
     * @return the accountBalance
     */
    public int getAccountBalance() {
        return accountBalance;
    }

    /**
     * @param accountBalance the accountBalance to set
     */
    public void setAccountBalance(int accountBalance) {
        this.accountBalance = accountBalance;
    }

    public static final void getAffilies() throws IOException {
        WebClient webClient = new WebClient();
        HtmlPage affiliesPage = webClient.getPage(TicketsRestaurantsServiceWrapper.URL_AFFILIES);
        
        // Create temp file and fill it with site contents
        File temp = File.createTempFile("affilies", ".html.tmp");
        FileUtils.writeStringToFile(temp, affiliesPage.asXml());
        // disable js on the html page so we can fetch all in a single shot !
        webClient.getOptions().setJavaScriptEnabled(false);
        // load the page
        HtmlPage localAffiliesPage = webClient.getPage("file://" + temp.getAbsolutePath());
        
        // parse the page !
        String tableId = "DataTables_Table_0";
        final HtmlTable table = localAffiliesPage.getHtmlElementById(tableId);
        
        for (final HtmlTableBody body : table.getBodies()) {
            final List<HtmlTableRow> rows = body.getRows();
            System.out.println("Affilies detected : " + rows.size());
            // now fetch each row
            Iterator<HtmlTableRow> rowIter = rows.iterator();
            HtmlTableRow theRow;
            System.out.println("Fetching affilies...");
            String lEnseigne;
            String lCategorie;
            String lCuisine;
            String lTelephone;
            String lAdresse;
            String lCommune;
            String lQuartier;
            int affiliesCount = 0;
            while (rowIter.hasNext()) {
                theRow = rowIter.next();
                affiliesCount++;
                lEnseigne = theRow.getCell(0).asText();
                lCategorie = theRow.getCell(1).asText();
                lCuisine = theRow.getCell(2).asText();
                lTelephone = theRow.getCell(3).asText();
                lAdresse = theRow.getCell(4).asText();
                lCommune = theRow.getCell(5).asText();
                lQuartier = theRow.getCell(6).asText();
                System.out.println("Found affilie <" + affiliesCount + "/" + rows.size() + "> : <" + lEnseigne + ">" + lCommune);
                //System.out.println(theRow.asXml());
                /*
                dateAsString = theRow.getCell(0).asText();
                libele = theRow.getCell(1).asText();
                debitAsString = theRow.getCell(2).asText();
                credititAsString = theRow.getCell(3).asText();
                System.out.println(theRow);
                lTransaction = new Transaction(convertFromTextDate(dateAsString), libele, extractSolde(debitAsString), extractSolde(credititAsString));
                getTransactions().add(lTransaction);
                System.out.println("Added new transction : " + lTransaction.toString());
                */
            }
        }
    }
}
