/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.adriens.tickets.resto.nc.api;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author salad74
 */
public class Main {
	final static Logger logger = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) {
		Properties prop = new Properties();
		InputStream input = null;
		String login = null;
		String password = null;

		try {

			input = new FileInputStream(".password");

			// load a properties file
			prop.load(input);

			// get the property value and print it out
			login = prop.getProperty("identifiantTicketResto");
			password = prop.getProperty("mdpTicketResto");

		} catch (IOException ex) {
			// si on ne trouve pas le fichier alors on en crée un que
			// l'utilisateur pourra remplir
			try {
				FileOutputStream out = new FileOutputStream(".password");
				String t = "identifiantTicketResto=\nmdpTicketResto=";
				out.write(t.getBytes());
				out.close();
			} catch (IOException e1) {
				logger.error(e1.getMessage());
				System.exit(1);
			}
			logger.error(ex.getMessage() + " Merci de renseigner vos identifiants/password dans le fichier .password");
			System.exit(1);
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		try {

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

			System.exit(0);

		} catch (IOException e) {
			// si on ne trouve pas le fichier alors on en crée un que
			// l'utilisateur pourra remplir
			try {
				FileOutputStream out = new FileOutputStream(".password");
				String t = "identifiantTicketResto=\nmdpTicketResto=";
				out.write(t.getBytes());
				out.close();
			} catch (IOException e1) {
				logger.error(e1.getMessage());
				System.exit(1);
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage() + " Merci de renseigner vos identifiants/password dans le fichier .password");
			System.exit(1);
		}
	}
}
