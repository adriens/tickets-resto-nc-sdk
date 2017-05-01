/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.adriens.tickets.resto.nc.api;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

/**
 *
 * @author salad74
 */
public class Affilie {

    private String enseigne;
    private String categorie;
    private String cuisine;
    private String telephone;
    private String adresse;
    private String commune;
    private String quartier;

    private PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();

    public Affilie() {

    }

    public Affilie(String enseigne, String categorie, String cuisine, String telephone, String adresse, String commune, String quartier) {
        setEnseigne(enseigne);
        setCategorie(categorie);
        setCuisine(cuisine);
        setTelephone(telephone);
        setAdresse(adresse);
        setCommune(commune);
        setQuartier(quartier);

    }

    public static final String getFormattedTelephoneNumber(String phoneNumber) {
        if (phoneNumber == null) {
            return null;
        }
        if(phoneNumber.trim().isEmpty()){
            return "";
        }
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        try {
            Phonenumber.PhoneNumber ncNumberProto = phoneUtil.parse(phoneNumber ,"NC");
            //System.out.println("Parsed tel : " + ncNumberProto);
            //System.out.println(phoneUtil.format(ncNumberProto, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL));
            return phoneUtil.format(ncNumberProto, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL);
        } catch (NumberParseException e) {
            System.err.println("PhoneNumber NumberParseException was thrown: " + e.toString());
            return null;
        }
    
    }
    
    @Override
    public String toString() {
        return getEnseigne();
    }

    /**
     * @return the enseigne
     */
    public String getEnseigne() {
        return enseigne;
    }

    /**
     * @param enseigne the enseigne to set
     */
    public void setEnseigne(String enseigne) {
        this.enseigne = enseigne;
    }

    /**
     * @return the categorie
     */
    public String getCategorie() {
        return categorie;
    }

    /**
     * @param categorie the categorie to set
     */
    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    /**
     * @return the cuisine
     */
    public String getCuisine() {
        return cuisine;
    }

    /**
     * @param cuisine the cuisine to set
     */
    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    /**
     * @return the telephone
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * @param telephone the telephone to set
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     * @return the adresse
     */
    public String getAdresse() {
        return adresse;
    }

    /**
     * @param adresse the adresse to set
     */
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    /**
     * @return the commune
     */
    public String getCommune() {
        return commune;
    }

    /**
     * @param commune the commune to set
     */
    public void setCommune(String commune) {
        this.commune = commune;
    }

    /**
     * @return the quartier
     */
    public String getQuartier() {
        return quartier;
    }

    /**
     * @param quartier the quartier to set
     */
    public void setQuartier(String quartier) {
        this.quartier = quartier;
    }

}
