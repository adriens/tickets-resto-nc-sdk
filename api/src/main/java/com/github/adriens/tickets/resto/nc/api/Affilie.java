/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.adriens.tickets.resto.nc.api;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author salad74
 */
public class Affilie implements Comparable<Affilie>{

    private String enseigne;
    private String categorie;
    private String cuisine;
    private String telephone;
    private String adresse;
    private String commune;
    private String quartier;
    private static final HashMap<String,URL> affilieGoogleMapsUrls;
    
    static {
        affilieGoogleMapsUrls = new HashMap<>();
        try{
            affilieGoogleMapsUrls.put("A LA PETITE FRANCE", new URL("https://goo.gl/maps/yhiAguUKvNu"));
            affilieGoogleMapsUrls.put("A LA VIEILLE FRANCE", new URL("https://goo.gl/maps/ohTHpEiZCR42"));
            affilieGoogleMapsUrls.put("APETAHI", new URL("https://goo.gl/maps/SW2oFreeAtC2"));
            affilieGoogleMapsUrls.put("AQUA'VENA", new URL("https://goo.gl/maps/zidFEtkGKw82"));
            affilieGoogleMapsUrls.put("ART 'CAFE", new URL("https://goo.gl/maps/sosNAvzR1k12"));
            affilieGoogleMapsUrls.put("ASIA TAKE AWAY", new URL("https://goo.gl/maps/DUqJyRYec5x"));
            affilieGoogleMapsUrls.put("AU P'TIT GOURMET", new URL("https://goo.gl/maps/n8HtQ22vkym"));
            affilieGoogleMapsUrls.put("BABYLONE CAFE", new URL("https://goo.gl/maps/ctXy3BrarLy"));
            affilieGoogleMapsUrls.put("BARISTA CAFE", new URL("https://goo.gl/maps/JfCV4J3jBbN2"));
            affilieGoogleMapsUrls.put("BEACH ROCK CAFE", new URL("https://goo.gl/maps/4oPSHaxVjVK2"));
            affilieGoogleMapsUrls.put("BILBOQUET PLAGE", new URL("https://goo.gl/maps/XRoWSg15vM92"));
            affilieGoogleMapsUrls.put("BIOMONDE CENTRE VILLE", new URL("https://goo.gl/maps/53hhzFawiXG2"));
            affilieGoogleMapsUrls.put("BIOMONDE MICHEL ANGE", new URL("https://goo.gl/maps/JrtcZhjVvXk"));
            affilieGoogleMapsUrls.put("BIOMONDE ROBINSON", new URL("https://goo.gl/maps/5rWJGwLq59U2"));
            
            affilieGoogleMapsUrls.put("BISTROT D'AUSTERLITZ", new URL("https://goo.gl/maps/KTvR5LqU3BF2"));
            affilieGoogleMapsUrls.put("BLOOM ' S", new URL("https://goo.gl/maps/b7GecCE2izP2"));
            affilieGoogleMapsUrls.put("BOUCHERIE DE BOULARI", new URL("https://goo.gl/maps/eogk7hx3r7B2"));
            affilieGoogleMapsUrls.put("BOUCHERIE LES JUMEAUX", new URL("https://goo.gl/maps/VAPufvp2vkr"));
            affilieGoogleMapsUrls.put("BOULANGERIE ST HONORE", new URL("https://goo.gl/maps/ze8SnqXwLtC2"));
            affilieGoogleMapsUrls.put("BOULOUPARIS PIZZA", new URL("https://goo.gl/maps/CgeRnHB3Fyo"));
            affilieGoogleMapsUrls.put("BRASSERIE TANNHAUSER", new URL("https://goo.gl/maps/zqLX47auJHs"));
            affilieGoogleMapsUrls.put("CACAO SAMPAKA", new URL("https://goo.gl/maps/vD622uoQhy42"));
            affilieGoogleMapsUrls.put("CAFE D'AUSTERLITZ", new URL("https://goo.gl/maps/3zs8VVUzv9D2"));
            affilieGoogleMapsUrls.put("CAFE TERRASSE", new URL("https://goo.gl/maps/ecbA7TRquj52"));
            affilieGoogleMapsUrls.put("CAFETERIA IRIS", new URL("https://goo.gl/maps/bHQHq5gXoW32"));
            affilieGoogleMapsUrls.put("CARPE DIEM", new URL("https://goo.gl/maps/3R2UZ1w3VDT2"));
            affilieGoogleMapsUrls.put("CASINO BELLE VIE", new URL("https://goo.gl/maps/QuwywfaHLHy"));
            affilieGoogleMapsUrls.put("CASINO JOHNSTON", new URL("https://goo.gl/maps/Z9qfdbd5vGK2"));
            affilieGoogleMapsUrls.put("CASINO MONT DORE", new URL("https://goo.gl/maps/FuNSmHDinSR2"));
            affilieGoogleMapsUrls.put("CASINO PORT PLAISANCE", new URL("https://goo.gl/maps/ZAHXZPSidsC2"));
            affilieGoogleMapsUrls.put("CASINO VALLEE DES COLONS", new URL("https://goo.gl/maps/aPHgVVQBusw"));
            affilieGoogleMapsUrls.put("CHAMPION ALMA", new URL("https://goo.gl/maps/p2a9XoMuezF2"));
            affilieGoogleMapsUrls.put("CHAMPION MAGENTA", new URL("https://goo.gl/maps/MEQL15xVWcF2"));
            affilieGoogleMapsUrls.put("CHAMPION N'GEA", new URL("https://goo.gl/maps/dHp2bUbxaUp"));
            affilieGoogleMapsUrls.put("CHEZ LEA", new URL("https://goo.gl/maps/UAa16vaQu7p"));
            affilieGoogleMapsUrls.put("CHEZ MOKINY", new URL("https://goo.gl/maps/JnRFjvVvzsT2"));
            affilieGoogleMapsUrls.put("CHEZ VALERIE", new URL("https://goo.gl/maps/s8y2t8ewrbC2"));
            affilieGoogleMapsUrls.put("D'UNE ILE A L'AUTRE", new URL("https://goo.gl/maps/XPVA4H185Co"));
            affilieGoogleMapsUrls.put("DUKE'S SURF CLUB", new URL("https://goo.gl/maps/4NxJ9ViyK2S2"));
            affilieGoogleMapsUrls.put("ENTRE TERRE ET MER", new URL("https://goo.gl/maps/DcqEBRrAbST2"));
            affilieGoogleMapsUrls.put("ESCALE DE KONE", new URL("https://goo.gl/maps/ysL7LJGEdUN2"));
            affilieGoogleMapsUrls.put("FARE PALM BEACH", new URL("https://goo.gl/maps/1VsU7xbGycv"));
            affilieGoogleMapsUrls.put("FENEPAZA", new URL("https://goo.gl/maps/TjHhuq1Bk2n"));
            affilieGoogleMapsUrls.put("FUN BEACH", new URL("https://goo.gl/maps/Z6QF8SRrWvj"));
            affilieGoogleMapsUrls.put("GEANT", new URL("https://goo.gl/maps/micBfWyqsAq"));
            affilieGoogleMapsUrls.put("HACIENDA", new URL("https://goo.gl/maps/YGrxVGcXBcp"));
            affilieGoogleMapsUrls.put("HOTE LA NEA", new URL("https://goo.gl/maps/fF2T8Agtdvx"));
            affilieGoogleMapsUrls.put("HOTEL DE TIETI", new URL("https://goo.gl/maps/GXkNaLYzxZS2"));
            affilieGoogleMapsUrls.put("HOTEL OURE LODGE", new URL("https://goo.gl/maps/7CdWAcu8CXw"));
            affilieGoogleMapsUrls.put("IL PICCOLO CAFFE", new URL("https://goo.gl/maps/zNZtShqs7Ms"));
            affilieGoogleMapsUrls.put("INDIAN GOURMET", new URL("https://goo.gl/maps/CqbwPKvNgpC2"));
            affilieGoogleMapsUrls.put("KANUA TERA ECOLODGE", new URL("https://goo.gl/maps/RBE63GaFNc52"));
            affilieGoogleMapsUrls.put("KOULNOUE VILLAGE", new URL("https://goo.gl/maps/g3BGJdeSC532"));
            affilieGoogleMapsUrls.put("KUENDU BEACH", new URL("https://goo.gl/maps/DPH2DrqLgPy"));
            affilieGoogleMapsUrls.put("L'ANNEXE", new URL("https://goo.gl/maps/93gTotS7swS2"));
            affilieGoogleMapsUrls.put("L'ARLEQUIN", new URL("https://goo.gl/maps/7Mzc5wHJEen"));
            affilieGoogleMapsUrls.put("L'ENTRECOTE 360", new URL("https://goo.gl/maps/ejeHby8cMNt"));
            affilieGoogleMapsUrls.put("L'ESSENCIEL", new URL("https://goo.gl/maps/8doqtGj3wsx"));
            affilieGoogleMapsUrls.put("L'INEDIT", new URL("https://goo.gl/maps/1QLvFCWZoRC2"));
            affilieGoogleMapsUrls.put("LA BALINAISE", new URL("https://goo.gl/maps/w3Tys1V9dF32"));
            affilieGoogleMapsUrls.put("LA BOITE A PIZZA", new URL("https://goo.gl/maps/iyNPj9m5nop"));
            affilieGoogleMapsUrls.put("LA CHAUMIERE", new URL("https://goo.gl/maps/hFDR8xH6v652"));
            affilieGoogleMapsUrls.put("LA DERNIERE CHANCE", new URL("https://goo.gl/maps/1soxwwKnnDy"));
            affilieGoogleMapsUrls.put("LA LUKATINE", new URL("https://goo.gl/maps/FMvnU5N8Mu82"));
            affilieGoogleMapsUrls.put("LA NICOISE", new URL("https://goo.gl/maps/hucXNRycwaA2"));
            affilieGoogleMapsUrls.put("LA PERGOLA", new URL("https://goo.gl/maps/bmGJL5bRCUy"));
            affilieGoogleMapsUrls.put("LA PERLE D'ERAM", new URL("https://goo.gl/maps/9RsUca7gdR82"));
            affilieGoogleMapsUrls.put("LA POPOTTE", new URL("https://goo.gl/maps/zUPtc4ncNWC2"));
            affilieGoogleMapsUrls.put("LA TABLE DE SANCHEZ", new URL("https://goo.gl/maps/U833idarhWN2"));
            affilieGoogleMapsUrls.put("LA TARTIFLETTE", new URL("https://goo.gl/maps/To98PVdmeaU2"));
            affilieGoogleMapsUrls.put("LE BALATA", new URL("https://goo.gl/maps/8tsy12cKgyQ2"));
            affilieGoogleMapsUrls.put("LE BILBOQUET VILLAGE", new URL("https://goo.gl/maps/i1Fjcwu3EmF2"));
            affilieGoogleMapsUrls.put("LE FARE DU QUAI FERRY", new URL("https://goo.gl/maps/6UHDZm62f9Q2"));
            affilieGoogleMapsUrls.put("LE ROCHER", new URL("https://goo.gl/maps/b484sWqu2bv"));
            affilieGoogleMapsUrls.put("LE ROOF", new URL("https://goo.gl/maps/xVNFuXKCXBL2"));
            affilieGoogleMapsUrls.put("LE SAINT HUB", new URL("https://goo.gl/maps/FMA4o8mqRy62"));
            affilieGoogleMapsUrls.put("LE SELF FOCH", new URL("https://goo.gl/maps/8n4HCJCTQvM2"));
            affilieGoogleMapsUrls.put("LE SERVIGNY", new URL("https://goo.gl/maps/8JAzu2zG4oC2"));
            affilieGoogleMapsUrls.put("LE TOTEM", new URL("https://goo.gl/maps/72aN3LSPsc42"));
            affilieGoogleMapsUrls.put("LES 3 BRASSEURS", new URL("https://goo.gl/maps/tUzfqi877y12"));
            affilieGoogleMapsUrls.put("LES FRERES GOURMANDS", new URL("https://goo.gl/maps/xBH5MCanLwN2"));
            affilieGoogleMapsUrls.put("LES PETITS CHOUX", new URL("https://goo.gl/maps/Ajcnz7S1bB32"));
            
            affilieGoogleMapsUrls.put("LES PRIMEURS DU CAILLOU", new URL("https://goo.gl/maps/XcZu3qh8LKH2"));
            affilieGoogleMapsUrls.put("MALECON CAFE", new URL("https://goo.gl/maps/moCzXiZ9U6F2"));
            affilieGoogleMapsUrls.put("MARMITE ET TIRE- BOUCHON", new URL("https://goo.gl/maps/HmtWkSXnYwq"));
            affilieGoogleMapsUrls.put("MAXI MARKET", new URL("https://goo.gl/maps/rTcMtF9FFFx"));
            affilieGoogleMapsUrls.put("MR BOEUF", new URL("https://goo.gl/maps/JazdmjzwJGU2"));
            affilieGoogleMapsUrls.put("NIKKO SUSHI BAR", new URL("https://goo.gl/maps/XJ5ro33oCX82"));
            affilieGoogleMapsUrls.put("NOUVATA", new URL("https://goo.gl/maps/5WeKbrgvjVN2"));
            
            affilieGoogleMapsUrls.put("O BOUCHER", new URL("https://goo.gl/maps/QuQrVYF1PKT2"));
            affilieGoogleMapsUrls.put("O GUSTO", new URL("https://goo.gl/maps/SQEWzm6TQi42"));
            affilieGoogleMapsUrls.put("O'JARDIN DE MARIE", new URL("https://goo.gl/maps/UfVto2Ee9232"));
            affilieGoogleMapsUrls.put("PARFUM DE LOTUS", new URL("https://goo.gl/maps/druTCGg8QrA2"));
            affilieGoogleMapsUrls.put("PERFECTA", new URL("https://goo.gl/maps/KUZiD7JTMUy"));
            affilieGoogleMapsUrls.put("PIZZA AND PASTA", new URL("https://goo.gl/maps/UpfEfaV6DjL2"));
            affilieGoogleMapsUrls.put("PIZZA NERA", new URL("https://goo.gl/maps/cihS9ZMwRWz"));
            
            affilieGoogleMapsUrls.put("PROLINEA", new URL("https://goo.gl/maps/rm2jGPeVJW92"));
            affilieGoogleMapsUrls.put("RESTAU SELF STEPHEN", new URL("https://goo.gl/maps/6fuN8nNpEkH2"));
            affilieGoogleMapsUrls.put("RESTO DU SUD", new URL("https://goo.gl/maps/57hWm2eDVno"));
            affilieGoogleMapsUrls.put("SAINT EXUPERY", new URL("https://goo.gl/maps/fRDWbYwUmDk"));
            affilieGoogleMapsUrls.put("SEL ET POIVRE", new URL("https://goo.gl/maps/fNmgXhidUAy"));
            affilieGoogleMapsUrls.put("SHELL KOUMAC", new URL("https://goo.gl/maps/43fVAG23f1B2"));
            affilieGoogleMapsUrls.put("SNACK D'AUTEUIL", new URL("https://goo.gl/maps/jJLJt3qf9oH2"));
            
            affilieGoogleMapsUrls.put("SNACK DE L'OLYMPIQUE", new URL("https://goo.gl/maps/qyHBCXgUX9R2"));
            affilieGoogleMapsUrls.put("SNACK ULYSSE", new URL("https://goo.gl/maps/QeDunsXFtpB2"));
            affilieGoogleMapsUrls.put("SO SUSHIS ET SALADES", new URL("https://goo.gl/maps/cqWyTSsXHgn"));
            affilieGoogleMapsUrls.put("SUPER U AUTEUIL", new URL("https://goo.gl/maps/EqKAtHQ5mxz"));
            affilieGoogleMapsUrls.put("SUPER YA", new URL("https://goo.gl/maps/GWctDxRQVk52"));
            affilieGoogleMapsUrls.put("TANUKI", new URL("https://goo.gl/maps/SDZn8Y9zxXF2"));
            affilieGoogleMapsUrls.put("TEXAS GRILL", new URL("https://goo.gl/maps/dxtdQVi2r8p"));
            
            affilieGoogleMapsUrls.put("TINA MARKET", new URL("https://goo.gl/maps/1ouAuuyndRo"));
            affilieGoogleMapsUrls.put("TOUSKIFAU", new URL("https://goo.gl/maps/Dpm41JmP31S2"));
            affilieGoogleMapsUrls.put("VIVAL KOUMAC", new URL("https://goo.gl/maps/b54pRV3wj932"));
            affilieGoogleMapsUrls.put("VIVAL KOUTIO", new URL("https://goo.gl/maps/f28aHBrsa4J2"));
            affilieGoogleMapsUrls.put("YAKI GRILL", new URL("https://goo.gl/maps/dH3bMqH6bZP2"));
            affilieGoogleMapsUrls.put("YOG AND CO", new URL("https://goo.gl/maps/Soow2KGVgw42"));
            
        }
        catch(MalformedURLException ex){
            //logger.error("Not able to parse URL : " + ex.getMessage());
            //throw ex;
        }
    }
    final static Logger logger = LoggerFactory.getLogger(Affilie.class);

    private PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();

    public final static URL getGmapsURL(String enseigne){
        if(enseigne == null){
            return null;
        }
        return affilieGoogleMapsUrls.get(enseigne);
    }
    public Affilie() {

    }
    
    @Override
    public int compareTo( final Affilie a) {
        return this.getEnseigne().compareTo(a.getEnseigne());
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.enseigne);
        return hash;
    }
    
    public Affilie(String enseigne, String categorie, String cuisine, String telephone, String adresse, String commune, String quartier) throws Exception
    {
        if(enseigne == null){
            logger.error("Affilie enseigne should not be null");
            throw new Exception("Affilie enseigne should not be null");
        }
        this.enseigne =enseigne;
        this.categorie = categorie;
        this.cuisine = cuisine;
        this.telephone = telephone;
        this.adresse = adresse;
        this.commune = commune;
        this.quartier = quartier;

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
            logger.debug("Parsed tel : " + ncNumberProto);
            logger.debug(phoneUtil.format(ncNumberProto, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL));
            return phoneUtil.format(ncNumberProto, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL);
        } catch (NumberParseException e) {
            logger.error("PhoneNumber NumberParseException was thrown: " + e.toString());
            return null;
        }
    
    }
    
    @Override
    public String toString() {
        if(getCommune().trim().length() > 0){
            return getEnseigne() + " (" + getCommune() + ")";
        }
        else{
            return getEnseigne();
        }
    }
    
    public String getFormattedAdress(){
        String lAdresse = "";
        String lCommune = "";
        String lQuartier = "";
        String out = "";
        if(getAdresse() != null){
            lAdresse = getAdresse();
        }
        if(getCommune() != null){
            lCommune = getCommune();
        }
        if(getQuartier() != null){
            lQuartier = getQuartier();
        }
        // we have no field set
        if (lAdresse.isEmpty() && getCommune().isEmpty() && lQuartier.isEmpty()){
            return "Nouvelle-Calédonie";
        }
        
        if(lAdresse.isEmpty()){
            // no address
            out = "";
        }
        else{
            //addresse is set
            out = lAdresse;
        }
        
        if(!lQuartier.isEmpty()){
            out += ", " + lQuartier;
        }
        if(!lCommune.isEmpty()){
            out += ", " + lCommune;
        }
        
        return out + ", Nouvelle-Calédonie";
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
