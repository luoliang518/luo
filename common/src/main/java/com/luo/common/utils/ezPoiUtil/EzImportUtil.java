package com.luo.common.utils.ezPoiUtil;

import com.luo.common.utils.ezPoiUtil.entity.ImportDataDto;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author luoliang
 * @description
 * @date 2023/10/12 14:18
 */
public class EzImportUtil {
    private static final String XLS = ".xls";
    private static final String XLSX = ".xlsx";

    /**
     * @param excelFile:
     * @param clazz:
     * @return T
     * @author luoliang
     * @description 根据文件流 转换为List类型的T对象
     */
    public static <T> List<ImportDataDto<T>> getExcelData(MultipartFile excelFile, Class<T> clazz) {
        List<ImportDataDto<T>> importDataDtoList = new ArrayList<>();
        try {
            Workbook workbook;
            String fileName = excelFile.getOriginalFilename();
            if (fileName.endsWith(XLS)) {
                workbook = new HSSFWorkbook(excelFile.getInputStream());
            } else if (fileName.endsWith(XLSX)) {
                workbook = new XSSFWorkbook(excelFile.getInputStream());
            } else {
                throw new RuntimeException("不支持的文件格式");
            }
            int numSheets = workbook.getNumberOfSheets();
            for (int i = 0; i < numSheets; i++) {
                Sheet sheet = workbook.getSheetAt(i);
                ImportDataDto<T> importDataDto = new ImportDataDto<T>();
                importDataDto.setSheetName(sheet.getSheetName());
                List<T> objects = new ArrayList<>();
                for (int j = 0; j <= sheet.getLastRowNum(); j++) {
                    boolean allPropertiesNull = true;
                    T o = clazz.newInstance();
                    Field[] declaredFields = clazz.getDeclaredFields();
                    for (Field declaredField : declaredFields) {
                        EzFiled fieldAnnotation = declaredField.getAnnotation(EzFiled.class);
                        // 获取排序
                        int sort = fieldAnnotation.sort();
                        Cell cell = sheet.getRow(j).getCell(sort);
                        String cellValue;
                        if (cell == null) {
                            continue;
                        }
                        if (cell.getCellType() == CellType.NUMERIC) {
                            NumberFormat nf = NumberFormat.getInstance();
                            nf.setGroupingUsed(false);
                            cellValue = nf.format(cell.getNumericCellValue());
                        } else {
                            cellValue = cell.toString();
                        }
                        String fieldName = declaredField.getName();
                        String setterMethodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                        Method setterMethod = clazz.getMethod(setterMethodName, String.class);
                        setterMethod.invoke(o, cellValue);
                        // 如果当前属性不为null，将标志置为false
                        if (cellValue != null && !cellValue.trim().isEmpty()) {
                            allPropertiesNull = false;
                        }
                    }
                    // 如果所有属性都为null，跳过该对象
                    if (allPropertiesNull) {
                        continue;
                    }
                    if (j == 0) {
                        importDataDto.setSheetTitle(o);
                    } else if (j == 1) {
                        importDataDto.setSheetExample(o);
                    } else {
                        objects.add(o);
                    }
                }
                importDataDto.setExcelData(objects);
                importDataDtoList.add(importDataDto);
            }
            workbook.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return importDataDtoList;
    }
}
