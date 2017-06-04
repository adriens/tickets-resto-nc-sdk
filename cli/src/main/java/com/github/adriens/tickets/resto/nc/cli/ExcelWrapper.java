/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.adriens.tickets.resto.nc.cli;

import com.github.adriens.tickets.resto.nc.api.Affilie;
import com.github.adriens.tickets.resto.nc.api.TicketsRestaurantsServiceWrapper;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author salad74
 */
public class ExcelWrapper {

    private XSSFWorkbook workbook;

    private XSSFSheet affiliesSheet;
    final static Logger logger = LoggerFactory.getLogger(ExcelWrapper.class);

    /*
    public ExcelWrapper(){
        
    }*/
    public ExcelWrapper() {
        //this.fileName = fileName;
        this.workbook = new XSSFWorkbook();

    }

    public void create(String fileName) throws FileNotFoundException, IOException, Exception {
        generateAffiliesSheet();
        FileOutputStream outputStream = new FileOutputStream(fileName);
        workbook.write(outputStream);
        workbook.close();
    }

    public void createOrUpdateSolde(String filename, String login, String password) throws IOException,
            InvalidFormatException, 
            Exception{

        TicketsRestaurantsServiceWrapper ticketsService = new TicketsRestaurantsServiceWrapper(null, login, password);
        int soldeBalance = ticketsService.getAccountBalance();
        logger.info("SOLDE détecté : " + soldeBalance);
        
        // load workbook
        File inputSoldeFile = new File(filename);
        XSSFWorkbook soldeWorkbook;
        XSSFSheet soldeSheet;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        if (!inputSoldeFile.exists()) {

            soldeWorkbook = new XSSFWorkbook();
            FileOutputStream tmpOut = new FileOutputStream("solde.xlsx");
            soldeWorkbook.createSheet("Solde");
            soldeWorkbook.write(tmpOut);
            tmpOut.flush();
            tmpOut.close();
        }
        // here file exists
        
        

        FileInputStream fileIn = new FileInputStream(new File("solde.xlsx"));
        soldeWorkbook = new XSSFWorkbook(fileIn);
        soldeSheet = soldeWorkbook.getSheet("Solde");
        
        // prepare date cell style
        CellStyle cellStyle = soldeWorkbook.createCellStyle();
        CreationHelper createHelper = soldeWorkbook.getCreationHelper();
        cellStyle.setDataFormat(
        //createHelper.createDataFormat().getFormat("m/d/yy h:mm"));
        createHelper.createDataFormat().getFormat("yyyy/m/d/ hh:mm"));
        
        
        int lastRowNum = soldeSheet.getPhysicalNumberOfRows();
        logger.info("nb rows found : " + lastRowNum);
        if (lastRowNum == 0) {
            Row soldeRow = soldeSheet.createRow(0);
            Cell soldeCell = soldeRow.createCell(0);
            soldeCell.setCellValue("Solde");

            Cell updateCell = soldeRow.createCell(1);
            updateCell.setCellValue("Update Date");
            updateCell.setCellStyle(cellStyle);
        } else {
            // shift down rows from 1
            soldeSheet.shiftRows(1, soldeSheet.getLastRowNum(), 1);
        }

        Row soldeRow = soldeSheet.createRow(1);

        Cell soldeCell = soldeRow.createCell(0);
        // get cuurent online solde
        soldeCell.setCellValue(soldeBalance);

        Cell updateCell = soldeRow.createCell(1);
        updateCell.setCellValue(sdf.format(new Date()));

        soldeSheet.autoSizeColumn(0);
        soldeSheet.autoSizeColumn(1);
        fileIn.close();

        FileOutputStream fileOut = new FileOutputStream("solde.xlsx");
        soldeWorkbook.write(fileOut);
        fileOut.flush();
        fileOut.close();
    }

    public void generateAffiliesSheet() throws IOException, Exception {
        //affiliesSheet = workbook.createSheet("Affilies");
        FileOutputStream fileOut = new FileOutputStream("Affilies.xls");
	HSSFWorkbook workbook = new HSSFWorkbook();
	HSSFSheet affiliesSheet = workbook.createSheet("Affilies");
        
        
        ArrayList<Affilie> affList = TicketsRestaurantsServiceWrapper.getAffilies();
        int rowNum = 0;
        Iterator<Affilie> affIter = affList.iterator();
        Affilie lAff;
        FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
        URL gmapsURL ;
        while (affIter.hasNext()) {
            Row row = affiliesSheet.createRow(rowNum++);
            lAff = affIter.next();

            // setup enseigne
            Cell cellEnseigne = row.createCell(0);
            //cellEnseigne.setCellValue(lAff.getEnseigne());
            gmapsURL = Affilie.getGmapsURL(lAff.getEnseigne());
            if(gmapsURL != null){
                logger.info("Found gmapsURL : <" + gmapsURL.toString() + "> for <" + lAff.getEnseigne() + ">");
                //gmapsUrl = "http://maps.google.com/maps?q=" + lAff.getFormattedAdress();
                cellEnseigne.setCellFormula("HYPERLINK(\"" + gmapsURL.toString() + "\",\"" + lAff.getEnseigne()+ "\")");
            }
            else {
             // no real matching URL : do nothing 
             logger.warn("Could not find gmapURL ;-(");
             cellEnseigne.setCellValue(lAff.getEnseigne());
            }

            
            
            
            // setup categorie
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

            /*Cell cellQuar = row.createCell(6);
            cellQuar.setCellValue(lAff.getQuartier());
            */
            
            /*Cell cellFullAdress = row.createCell(7);
            // check if we can a matching gmpas url
            gmapsURL = Affilie.getGmapsURL(lAff.getEnseigne());
            if(gmapsURL != null){
                logger.info("Found gmapsURL : <" + gmapsURL.toString() + "> for <" + lAff.getEnseigne() + ">");
                //gmapsUrl = "http://maps.google.com/maps?q=" + lAff.getFormattedAdress();
                cellFullAdress.setCellFormula("HYPERLINK(\"" + gmapsURL.toString() + "\",\"" + lAff.getEnseigne()+ "\")");
            }
            else {
             // no real matching URL : do nothing 
             logger.warn("Could not find gmapURL ;-(");
             cellFullAdress.setCellValue("");
            }*/
            

        }
        // Evaluate formulas so columns with formulas have the correct size
        HSSFFormulaEvaluator.evaluateAllFormulaCells(workbook);
        affiliesSheet.autoSizeColumn(0);
        affiliesSheet.autoSizeColumn(1);
        affiliesSheet.autoSizeColumn(2);
        affiliesSheet.autoSizeColumn(3);
        affiliesSheet.autoSizeColumn(4);
        affiliesSheet.autoSizeColumn(5);
        affiliesSheet.autoSizeColumn(6);
        //affiliesSheet.autoSizeColumn(7);
        
        workbook.write(fileOut);
			fileOut.flush();
			fileOut.close();
                        
    }
}
