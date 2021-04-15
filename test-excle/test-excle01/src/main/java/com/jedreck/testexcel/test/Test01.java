package com.jedreck.testexcel.test;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTSheetProtection;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Slf4j
public class Test01 {
    private static final String P = "D:\\Desktop\\金海路_excel模板.xlsx";

    /**
     * 数据验证、数据校验，单元格只能输入特定格式字符
     */
    @Test
    public void t4() throws IOException {
        String sheetName = "sheetlist";

        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheetList;

        if (wb.getSheet(sheetName) == null) {
            sheetList = wb.createSheet(sheetName);// 工作表对象
        } else {
            sheetList = wb.getSheet(sheetName);// 工作表对象
        }

        DataValidationHelper dataValidationHelper = sheetList.getDataValidationHelper();

        List<XSSFDataValidation> dataValidations = sheetList.getDataValidations();
        for (XSSFDataValidation dv : dataValidations) {
            // 已有的验证
        }

        // 1.1 - 只能填特定几个值
        CellRangeAddressList dstAddrList = new CellRangeAddressList(1, 500, 0, 0);
        // 特定值
        String[] textList = {"列表1", "列表2", "列表3", "列表4", "列表5"};
        DataValidation dstDataValidation = dataValidationHelper.createValidation(dataValidationHelper.createExplicitListConstraint(textList), dstAddrList);
        // 设置 鼠标移入 提示框的内容
        dstDataValidation.createPromptBox("提示头1", "提示内容1");
        dstDataValidation.setShowPromptBox(true);
        dstDataValidation.setSuppressDropDownArrow(true);
        // 设置 错误弹窗
        dstDataValidation.setShowErrorBox(true);
        dstDataValidation.setEmptyCellAllowed(false);
        sheetList.addValidationData(dstDataValidation);

        // 1.2 - 只能填特定几个值
        dstAddrList = new CellRangeAddressList(1, 500, 1, 1);
        dstDataValidation = dataValidationHelper.createValidation(dataValidationHelper.createExplicitListConstraint(textList), dstAddrList);
        // 设置 鼠标移入 提示框的内容
        dstDataValidation.createPromptBox("提示头2", "提示内容2");
        dstDataValidation.setShowPromptBox(true);
        // 设置 错误弹窗
        dstDataValidation.setShowErrorBox(true);
        dstDataValidation.setEmptyCellAllowed(false);
        sheetList.addValidationData(dstDataValidation);

        // 2 - 填值规则
        CellRangeAddressList dstAddrList2 = new CellRangeAddressList(0, 500, 2, 3);// 规则二单元格范围
        DataValidationConstraint dvc = dataValidationHelper.createNumericConstraint(DataValidationConstraint.ValidationType.INTEGER, DataValidationConstraint.OperatorType.BETWEEN, "0", "9999999999");
        DataValidation dstDataValidation2 = dataValidationHelper.createValidation(dvc, dstAddrList2);
        // 自定义错误弹窗
        dstDataValidation2.createErrorBox("填错输啦！", "只能填那个啥啥啥");
        dstDataValidation2.setShowErrorBox(true);
        dstDataValidation2.setEmptyCellAllowed(false);
        sheetList.addValidationData(dstDataValidation2);

        FileOutputStream out = new FileOutputStream(P);
        wb.write(out);
        out.close();
    }

    /**
     * 写
     *
     * @throws IOException
     */
    @Test
    public void t3() throws IOException {
        XSSFWorkbook wb = new XSSFWorkbook();

        XSSFSheet sheet = wb.createSheet();
        // 锁定单元格1 -- 设置密码锁定所有单元格
        sheet.protectSheet("1234");
        // 设置列格式
        CellStyle columnStyle = wb.createCellStyle();
        DataFormat format = wb.createDataFormat();
        /**
         * 设置列格式为文本
         * 具体格式看 {@link org.apache.poi.ss.usermodel.BuiltinFormats}
         */
//        columnStyle.setDataFormat(format.getFormat("@"));
        columnStyle.setDataFormat(format.getFormat("@"));
        // 锁定单元格2 -- 解锁特定列
        columnStyle.setLocked(false);
        sheet.setDefaultColumnStyle(0, columnStyle);
        sheet.setDefaultColumnStyle(1, columnStyle);
        sheet.setDefaultColumnStyle(2, columnStyle);
        sheet.setDefaultColumnStyle(3, columnStyle);
        // 列宽
        sheet.setColumnWidth(0, 20 * 256);
        sheet.setColumnWidth(1, 20 * 256);
        sheet.setColumnWidth(2, 20 * 256);
        sheet.setColumnWidth(3, 20 * 256);

        // 固定4列1行
        sheet.createFreezePane(4, 1);
        // 第一行作为表头
        Row row = sheet.createRow(0);
        CellStyle headerStyle = wb.createCellStyle();
        // 颜色
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        // 下底框
        headerStyle.setBorderBottom(BorderStyle.DOUBLE);
        // 文字居中
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        // 锁定单元格3 -- 锁定表头单元格
        headerStyle.setLocked(false);
        // 设置文字
        Cell cell;
        cell = row.createCell(0);
        cell.setCellStyle(headerStyle);
        cell.setCellValue("第一级");
        cell = row.createCell(1);
        cell.setCellStyle(headerStyle);
        cell.setCellValue("第二级");
        cell = row.createCell(2);
        cell.setCellStyle(headerStyle);
        cell.setCellValue("第三级");
        cell = row.createCell(3);
        cell.setCellStyle(headerStyle);
        cell.setCellValue("第四级");

        // 锁定单元格4 -- 开启锁定，禁止点击被锁定的单元格
        sheet.enableLocking();
        CTSheetProtection sheetProtection = sheet.getCTWorksheet().getSheetProtection();
        sheetProtection.setSelectLockedCells(true);
        sheetProtection.setSelectUnlockedCells(false);

        FileOutputStream out = new FileOutputStream(P);
        wb.write(out);
        out.close();
        wb.close();
        log.info("读取excel文档--结束");
    }

    /**
     * 读
     *
     * @throws IOException
     */
    @Test
    public void t2() throws IOException {
        log.info("读取excel文档--开始");

        InputStream inputStream = new FileInputStream(P);
        Workbook wb = new XSSFWorkbook(inputStream);
        Sheet sheet = wb.getSheetAt(0);
        int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
        log.info("physicalNumberOfRows:{}", physicalNumberOfRows);
        for (int i = 1; i < physicalNumberOfRows; i++) {
            // 获取第i行
            Row row = sheet.getRow(i);
            Cell c0 = row.getCell(0);
            c0.setCellType(CellType.STRING);
            Cell c1 = row.getCell(1);
            c1.setCellType(CellType.STRING);
            Cell c2 = row.getCell(2);
            c2.setCellType(CellType.STRING);
            Cell c3 = row.getCell(3);
            c3.setCellType(CellType.STRING);
            log.info("第{}行: {}->{}->{}->{}", i
                    , c0.getStringCellValue()
                    , c1.getStringCellValue()
                    , c2.getStringCellValue()
                    , c3.getStringCellValue()
            );
        }

        inputStream.close();

        log.info("读取excel文档--结束");
    }

    @Test
    public void t1() throws IOException {
        log.info("读取excel文档--开始");
        //创建一个输入流读取单元格
        InputStream inputStream = new FileInputStream(P);
        //包装类，将读取的内容放入内存中
        Workbook wb = new XSSFWorkbook(inputStream);
        //获取第1个sheet页
        Sheet sheet = wb.getSheetAt(0);
        //获取第2行
        Row row = sheet.getRow(1);
        //获取第1个单元格
        Cell cell = row.getCell(3);
        if (cell == null) {
            cell = row.createCell(3);
            cell.setCellType(CellType.STRING);
            cell.setCellValue("测试单元格");
        }

        log.info(cell.getStringCellValue());

        FileOutputStream out = new FileOutputStream(P);
        wb.write(out);
        out.close();
        wb.close();
        inputStream.close();
        log.info("读取excel文档--结束");
    }

}
