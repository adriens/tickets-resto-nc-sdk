/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.adriens.tickets.resto.nc.cli;

import com.github.adriens.tickets.resto.nc.api.Affilie;
import com.github.adriens.tickets.resto.nc.api.TicketsRestaurantsServiceWrapper;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import javafx.scene.transform.Affine;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author salad74
 */
public class ExcelWrapper {
    
    private XSSFWorkbook workbook;
    
    private XSSFSheet affiliesSheet;
    
    /*
    public ExcelWrapper(){
        
    }*/
    
    public ExcelWrapper(){
        //this.fileName = fileName;
        this.workbook = new XSSFWorkbook();
        
        
    }
    
    public void create(String fileName) throws FileNotFoundException, IOException, Exception{
            generateAffiliesSheet();
            FileOutputStream outputStream = new FileOutputStream(fileName);
            workbook.write(outputStream);
            workbook.close();
    }
    
    
    
    public  void generateAffiliesSheet() throws IOException, Exception{
         affiliesSheet = workbook.createSheet("Affilies");
         /*Object[][] datatypes = {
                {"Enseigne", "Catégorie", "Cuisine", "Téléphone", "Adresse", "Commune", "Quartier"}
        };
        */
         ArrayList<Affilie> affList = TicketsRestaurantsServiceWrapper.getAffilies();
         int rowNum = 0;
         Iterator<Affilie> affIter = affList.iterator();
         Affilie lAff;
         String gmapsUrl = "";
         FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
         while(affIter.hasNext()){
             Row row = affiliesSheet.createRow(rowNum++);
             lAff = affIter.next();
             
             Cell cellEnseigne = row.createCell(0);
             cellEnseigne.setCellValue(lAff.getEnseigne());
             
             Cell cellCateg = row.createCell(1);
             cellCateg.setCellValue(URLDecoder.decode(lAff.getCategorie(), "UTF-8"));
             
             Cell cellCuisi = row.createCell(2);
             cellCuisi.setCellValue(lAff.getCuisine());
             
             Cell cellTel = row.createCell(3);
             cellTel.setCellValue(Affilie.getFormattedTelephoneNumber(lAff.getTelephone()));
             
             Cell cellAdr = row.createCell(4);
             cellAdr.setCellValue(lAff.getAdresse());
             
             Cell cellComm = row.createCell(5);
             cellComm.setCellValue(lAff.getCommune());
             
             Cell cellQuar = row.createCell(6);
             cellQuar.setCellValue(lAff.getQuartier());
             
             Cell cellFullAdress = row.createCell(7);
             gmapsUrl = "http://maps.google.com/maps?q=" + lAff.getFormattedAdress();
             cellFullAdress.setCellFormula("HYPERLINK(\"" + gmapsUrl+ "\",\"Voir sur Google Maps\")");
             
    
         }

    affiliesSheet.autoSizeColumn(0);
    affiliesSheet.autoSizeColumn(1);
    affiliesSheet.autoSizeColumn(2);
    affiliesSheet.autoSizeColumn(3);
    affiliesSheet.autoSizeColumn(4);
    affiliesSheet.autoSizeColumn(5);
    affiliesSheet.autoSizeColumn(6);
    affiliesSheet.autoSizeColumn(7);
    }
}
