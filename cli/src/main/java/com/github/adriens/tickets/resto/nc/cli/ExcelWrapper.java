/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.adriens.tickets.resto.nc.cli;

import com.github.adriens.tickets.resto.nc.api.Affilie;
import com.github.adriens.tickets.resto.nc.api.ServiceType;
import com.github.adriens.tickets.resto.nc.api.TicketsRestaurantsServiceWrapper;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

/**
 *
 * @author salad74
 */
public class ExcelWrapper {

    private XSSFWorkbook workbook;
    private final static Logger logger = LoggerFactory.getLogger(ExcelWrapper.class);

    /*
    public ExcelWrapper(){
        
    }*/
    public ExcelWrapper() {
        this.workbook = new XSSFWorkbook();

    }

    public void create(String fileName) throws Exception {
        generateAffiliesSheet();
        FileOutputStream outputStream = new FileOutputStream(fileName);
        workbook.write(outputStream);
        workbook.close();
    }

    public void createOrUpdateSolde(String filename, String login, String password) throws Exception {

        TicketsRestaurantsServiceWrapper ticketsService = new TicketsRestaurantsServiceWrapper(login, password, ServiceType.SOLDE);
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
        cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy/m/d/ hh:mm"));


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

    public void generateAffiliesSheet() throws Exception {
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
            gmapsURL = Affilie.getGmapsURL(lAff.getEnseigne());
            if(gmapsURL != null){
                logger.info("Found gmapsURL : <" + gmapsURL.toString() + "> for <" + lAff.getEnseigne() + ">");
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

        workbook.write(fileOut);
        fileOut.flush();
        fileOut.close();

    }
}
