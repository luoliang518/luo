package com.luo.common.utils.ezPoiUtil;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.hutool.core.util.StrUtil;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @author: luoliang
 * @description: 导入导出excel工具类
 * @date: 2023/8/8 16:29
 */
public class EzExportUtil {
    public static MultipartFile exportExcel(String excelName, String tableName, List<?> list, Class<?> clazz) throws IOException {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = attributes.getResponse();
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        // 假设fileName是原始的文件名
        String encodedFileName;
        try {
            encodedFileName = URLEncoder.encode(excelName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // 处理编码异常
            e.printStackTrace();
            return null;
        }
        response.setHeader("Content-Disposition", "attachment;filename=" + encodedFileName + ".xlsx");
        // 批量导出
        ExportParams exportParams = new ExportParams();
        exportParams.setSheetName(tableName);
        int totalPage = (list.size() / 1000) + 1;
        int pageSize = 1000;
        Workbook workbook = ExcelExportUtil.exportBigExcel(exportParams, clazz, (queryParams, page) -> {
            if (page > totalPage) {
                return null;
            }
            // fromIndex开始索引，toIndex结束索引
            int fromIndex = (page - 1) * pageSize;
            int toIndex = page != totalPage ? fromIndex + pageSize : list.size();
            return new ArrayList<>(list.subList(fromIndex, toIndex));
        }, totalPage);
        // 设置响应头
//        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
//        response.setHeader("content-disposition",
//                "attachment;fileName=" + URLEncoder.encode(excelName + "-" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + ".xlsx", "UTF-8"));
        ServletOutputStream outputStream = response.getOutputStream();
        // 输出
        workbook.write(outputStream);
        outputStream.flush();
        MultipartFile multipartFile = workbookToMultipartFile(workbook, excelName);
        // 关闭资源
        outputStream.close();
        return multipartFile;
    }

    /**
     * <p>@Description: 根据 所选动态列，以及数据集进行导出excel</p >
     * <p>@param [exportBeanFrom, dataList, fileName]</p >
     * <p>@return void</p >
     * <p>@throws </p >
     */
    public static <T> MultipartFile exportByTitleAndData(Object exportBeanFrom, List<T> dataList, String fileName, String sheetName, Class<T> clazz) {
        // 处理参数: 需要导出的英文字段名 exportFieldArr, 中文表头 title
        List<EzFiledRo> filter = EzMappingUtil.filter(exportBeanFrom);
        String[] title = EzMappingUtil.getExportTitleAndSortArr(filter, clazz);
        String[] exportFieldArr = EzMappingUtil.getExportFieldAndSortArr(filter, clazz);
        // 根据参数 生成工作簿，并写入文件流
        if (title.length > 0 && exportFieldArr.length > 0) {
            Workbook workbook = EzExportUtil.getWorkbook(dataList, exportFieldArr, title, sheetName);
            MultipartFile multipartFile = EzExportUtil.writeToResponse(workbook, fileName);
            return multipartFile;
        }
        return null;
    }

    /**
     * @param exportBeanFrom:
     * @param dataList:
     * @param fileName:
     * @param sheetName:
     * @param clazz:
     * @return MultipartFile
     * @author luoliang
     * @description 大数据量导出
     * @date 2023/10/18 15:50
     */
    public static <T> MultipartFile exportBigData(Object exportBeanFrom, List<T> dataList, String fileName, String sheetName, Class<T> clazz) {
        // 处理参数: 需要导出的英文字段名 exportFieldArr, 中文表头 title
        List<EzFiledRo> filter = EzMappingUtil.filter(exportBeanFrom);
        String[] title = EzMappingUtil.getExportTitleAndSortArr(filter, clazz);
        String[] exportFieldArr = EzMappingUtil.getExportFieldAndSortArr(filter, clazz);
        // 根据参数 生成工作簿，并写入文件流
        if (title.length > 0 && exportFieldArr.length > 0) {
            MultipartFile multipartFile;
            try {
                multipartFile = exportExcel(fileName, sheetName, dataList, clazz);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return multipartFile;
        }
        return null;
    }

    /**
     * <p>@Description: 将文件流写会回 response 中</p >
     * <p>@param [workbook, fileName]</p >
     * <p>@return void</p >
     * <p>@throws </p >
     */
    public static MultipartFile writeToResponse(Workbook workbook, String fileName) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = attributes.getResponse();
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        // 假设fileName是原始的文件名
        String encodedFileName;
        try {
            encodedFileName = URLEncoder.encode(fileName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // 处理编码异常
            e.printStackTrace();
            return null;
        }
        response.setHeader("Content-Disposition", "attachment;filename=" + encodedFileName + ".xlsx");
        OutputStream outputStream = null;
        try {
//            outputStream = new FileOutputStream("D:/"+fileName+".xlsx");
            outputStream = response.getOutputStream();
            workbook.write(outputStream);
            outputStream.flush();
            MultipartFile multipartFile = workbookToMultipartFile(workbook, fileName);
            outputStream.close();
            return multipartFile;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public static MultipartFile workbookToMultipartFile(Workbook workbook, String fileName) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        workbook.write(byteArrayOutputStream);
        byteArrayOutputStream.close();

        byte[] bytes = byteArrayOutputStream.toByteArray();

        // 创建MockMultipartFile对象
        MultipartFile multipartFile = new MockMultipartFile(fileName + ".xlsx", fileName + ".xlsx", "application/vnd.ms-excel", bytes);

        return multipartFile;
    }

    /**
     * <p>@Description: 根据 数据集、导出列、表头汉字 创建工作簿</p >
     * <p>@param [dataSet 数据集, exportFieldArr 需要导出的字段列, titles 导出列对应的中文表头]</p >
     * <p>@return org.apache.poi.xssf.usermodel.XSSFWorkbook</p >
     * <p>@date 15:19 15:19</p >
     */
    public static <T> XSSFWorkbook getWorkbook(Collection<T> dataSet, String[] exportFieldArr, String[] titles, String sheetName) {
        // 校验变量和预期输出excel列数是否相同
        if (exportFieldArr.length != titles.length) {
            return null;
        }
        // 存储每一行的数据
        List<String[]> list = new ArrayList<>();
        if (dataSet != null && !dataSet.isEmpty()) {
            for (Object obj : dataSet) {
                // 获取到每一行的属性值数组
                list.add(getValues(obj, exportFieldArr));
            }
        }
        return getWorkbook(titles, list, sheetName);
    }

    public static XSSFWorkbook getWorkbook(String[] titles, List<String[]> list, String sheetName) {
        // 定义表头
        String[] title = titles;
        // 创建excel工作簿
        XSSFWorkbook workbook = new XSSFWorkbook();
        // 创建工作表sheet
        XSSFSheet sheet = workbook.createSheet(sheetName);
        // 创建第一行
        XSSFRow row = sheet.createRow(0);
        XSSFCell cell = null;
        // 插入第一行数据的表头
        row.setHeight((short) (24 * 20));
        CellStyle headerCommonStyle = getHeaderCommonStyle(workbook);
        for (int i = 0; i < title.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(headerCommonStyle);
        }
        // 数据行渲染
//        ExportCommonService.getAllExcelField()
        CellStyle bodyStyle = getBodyStyle(workbook);
        int idx = 1;
        if (list != null && !list.isEmpty()) {
            for (String[] strings : list) {
                XSSFRow nrow = sheet.createRow(idx++);
                XSSFCell ncell = null;
                for (int i = 0; i < strings.length; i++) {
                    ncell = nrow.createCell(i);
                    ncell.setCellValue(strings[i]);
                    ncell.setCellStyle(bodyStyle);
                }
            }
        }
        // 设置固定列宽
        setColumnWidth(titles, sheet, list);
        return workbook;
    }

    // 设置固定列宽
    public static void setColumnWidth(String[] titles, Sheet sheet, List<String[]> list) {
        for (int i = 0; i < titles.length; i++) {
//            sheet.setColumnWidth(i, 20 * 256);
            sheet.autoSizeColumn(i);
        }
//        for (int i = 0; i < titles.length; i++) {
//            int maxWidth = titles[i].length(); // 默认宽度为列标题的长度
//            // 计算每一列的最大宽度
//            for (String[] row : list) {
//                if (row.length > i && row[i] != null) {
//                    maxWidth = Math.max(maxWidth, row[i].length());
//                }
//            }
//            // 设置列宽，再加上一些额外的宽度，确保内容不会被裁剪
//            sheet.setColumnWidth(i, (maxWidth + 2) * 256);
//        }
    }

    private static CellStyle getHeaderCommonStyle(Workbook workbook) {
        CellStyle header = workbook.createCellStyle();
        header.setWrapText(false);
        Font font = workbook.createFont();
        font.setBold(Boolean.TRUE);
        font.setFontHeightInPoints((short) 14);
        font.setFontName("宋体");
        header.setFont(font);
        header.setBorderTop(BorderStyle.THIN);
        header.setBorderLeft(BorderStyle.THIN);
        header.setBorderBottom(BorderStyle.THIN);
        header.setBorderRight(BorderStyle.THIN);
        header.setAlignment(HorizontalAlignment.CENTER);
        header.setVerticalAlignment(VerticalAlignment.CENTER);
        header.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//        header.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        return header;
    }

    private static CellStyle getBodyStyle(Workbook workbook) {
        CellStyle body = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 12);
        font.setFontName("宋体");
        body.setFont(font);
        body.setWrapText(false);
        body.setBorderTop(BorderStyle.THIN);
        body.setBorderLeft(BorderStyle.THIN);
        body.setBorderBottom(BorderStyle.THIN);
        body.setBorderRight(BorderStyle.THIN);
        body.setAlignment(HorizontalAlignment.LEFT);
        body.setVerticalAlignment(VerticalAlignment.CENTER);
        body.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        body.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        return body;
    }

    /**
     * <p>@Description: object就是每一行的数据</p >
     * <p>@param [rowData, exportFieldArr]</p >
     * <p>@return java.lang.String[]</p >
     * <p>@throws </p >
     */
    public static String[] getValues(Object rowData, String[] exportFieldArr) {
        String[] values = new String[exportFieldArr.length];
        try {
            for (int i = 0; i < exportFieldArr.length; i++) {

                Field field = null;

                try {
                    field = rowData.getClass().getDeclaredField(exportFieldArr[i]);
                } catch (Exception e) {
                    field = rowData.getClass().getField(exportFieldArr[i]);
                }
                // 设置访问权限为true
                field.setAccessible(true);
                values[i] = setCellValue(field, rowData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return values;
    }

    public static String setCellValue(Field field, Object data) {
        Class<?> fieldType = field.getType();
        String result = "";
        try {
            Method method = data.getClass().getMethod(getMethodNameByCamel("get", field.getName()));
            Object fieldValue = method.invoke(data);
            if (fieldType == String.class) {
                result = (method.invoke(data) == null ? null : method.invoke(data).toString());
            } else if (fieldType == Short.class) {
                result = returnStringFromNumber(fieldValue);
            } else if (fieldType == Integer.class) {
                result = returnStringFromNumber(fieldValue);
            } else if (fieldType == Long.class) {
                result = returnStringFromNumber(fieldValue);
            } else if (fieldType == Float.class) {
                result = returnStringFromNumber(fieldValue);
            } else if (fieldType == Double.class) {
                result = returnStringFromNumber(fieldValue);
            } else if (fieldType == BigDecimal.class) {
                result = returnStringFromNumber(fieldValue);
            } else if (fieldType == Boolean.class) {
                result = returnStringFromNumber(fieldValue);
            } else if (fieldType == LocalDate.class) {
                String pattern = "yyyy-MM-dd";
                LocalDate date = method.invoke(data) == null ? null : (LocalDate) method.invoke(data);
                if (date != null) {
                    result = (date.format(DateTimeFormatter.ofPattern(pattern)));
                }
            } else if (fieldType == LocalDateTime.class) {
                String pattern = "yyyy-MM-dd HH:mm:ss";
                LocalDateTime date = method.invoke(data) == null ? null : (LocalDateTime) method.invoke(data);
                if (date != null) {
                    result = (date.format(DateTimeFormatter.ofPattern(pattern)));
                }
            } else {
                result = (method.invoke(data) == null ? null : method.invoke(data).toString());
            }
            return result;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return result;
    }

    // 将数字转换为 保留两位小数的字符串
    public static String returnStringFromNumber(Object data) {
        if (data == null || StrUtil.isBlank(data.toString())) {
            return "";
        }
        Double aDouble = Double.valueOf(data.toString());
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        String result = decimalFormat.format(aDouble);
        return result;
    }

    /**
     * @param response:  传个response
     * @param tableName: excel中的表名
     * @param list:      数据源
     * @param clazz:     配置的映射对象
     * @return void
     * @author luoliang
     * @description 批量导出
     * @date 2023/8/8 16:30
     */
    public static void exportExcel(HttpServletResponse response, String excelName, String tableName, List<?> list, Class<?> clazz) throws IOException {
        // 批量导出
        ExportParams exportParams = new ExportParams();
        exportParams.setSheetName(tableName);
        int totalPage = (list.size() / 1000) + 1;
        int pageSize = 1000;
        Workbook workbook = ExcelExportUtil.exportBigExcel(exportParams, clazz, (queryParams, page) -> {
            if (page > totalPage) {
                return null;
            }
            // fromIndex开始索引，toIndex结束索引
            int fromIndex = (page - 1) * pageSize;
            int toIndex = page != totalPage ? fromIndex + pageSize : list.size();
            return new ArrayList<>(list.subList(fromIndex, toIndex));
        }, totalPage);
        // 设置响应头
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("content-disposition",
                "attachment;fileName=" + URLEncoder.encode(excelName + "-" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + ".xlsx", "UTF-8"));
        ServletOutputStream outputStream = response.getOutputStream();
        // 输出
        workbook.write(outputStream);
        // 关闭资源
        outputStream.close();
    }

    /**
     * <p>@Description: 拼接前缀以及方法名，驼峰形式</p >
     * <p>@param [prefix, fieldName]</p >
     * <p>@return java.lang.String</p >
     * <p>@throws </p >
     */
    private static String getMethodNameByCamel(String prefix, String fieldName) {
        StringBuilder builder = new StringBuilder()
                .append(prefix)
                .append(fieldName.substring(0, 1).toUpperCase())
                .append(fieldName.substring(1));
        return builder.toString();
    }
}
