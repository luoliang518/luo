//package com.luo.common.utils.ezPoiUtil.importAndExportAutomaticLogging;
//
//import com.luo.common.utils.ezPoiUtil.EzFiled;
//import com.luo.common.utils.ezPoiUtil.entity.ImportDataDto;
//import com.luo.common.utils.ezPoiUtil.entity.ImportResponseVo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.lang.reflect.Field;
//import java.util.UUID;
//import java.util.stream.Collectors;
//
///**
// * @author luoliang
// * @description
// * @date 2023/10/11 15:14
// */
//@Component
//public class ImportOrExportUtil {
//    @Autowired
//    private ZwyOssUtil zwyOssUtil;
//    @Autowired
//    private ImportExportLogRepository importExportLogRepository;
//    @Autowired
//    private ImportExportLogDetailRepository importExportLogDetailRepository;
//
//    /**
//     * @param logId:
//     * @return ImportResponseVo
//     * @author luoliang
//     * @description 通用 获取导入返回数据
//     * @date 2023/10/18 11:15
//     */
//    public ImportResponseVo getReturnData(String logId) {
//        ImportExportLog byId = importExportLogRepository.getById(logId);
//        List<ImportExportLogDetail> importExportLogDetails = importExportLogDetailRepository.getBaseMapper().selectList(
//                new LambdaQueryWrapper<ImportExportLogDetail>()
//                        .eq(ImportExportLogDetail::getLogId, logId));
//        Map<String, List<ImportExportLogDetail>> collect = importExportLogDetails.stream().collect(Collectors.groupingBy(ImportExportLogDetail::getUuid));
//        // 失败条数
//        int size = collect.size();
//        return ImportResponseVo.getImportMsg(byId.getName(), byId.getStatus(), byId.getCount(), size);
//    }
//
//    /**
//     * @param multipartFile:
//     * @param importOrExportEnum:
//     * @param excelData:
//     * @return String
//     * @author luoliang
//     * @description 保存文件日志 传入excel数据
//     * @date 2023/10/18 11:16
//     */
//    public <T> String saveFileLog(MultipartFile multipartFile, ImportOrExportEnum importOrExportEnum, List<ImportDataDto<T>> excelData) {
//        int count = 0;
//        for (ImportDataDto<T> excelDatum : excelData) {
//            int size = excelDatum.getExcelData().size();
//            count += size;
//        }
//        // 存入oss
//        try {
//            ImportExportLog importExportLog = ImportExportLog.builder()
//                    .name(multipartFile.getOriginalFilename())
//                    .path(zwyOssUtil.ossUploadAsFile(multipartFile.getName(), multipartFile.getInputStream()))
//                    .type(importOrExportEnum)
//                    .count(count)
//                    .status(StringPool.TRUE)
//                    .build();
//            // 入库
//            importExportLogRepository.save(importExportLog);
//            return importExportLog.getId();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    /**
//     * @param multipartFile:
//     * @param importOrExportEnum:
//     * @param count:
//     * @return String
//     * @author luoliang
//     * @description 保存文件日志 传入总条数
//     * @date 2023/10/18 11:16
//     */
//    public <T> String saveFileLog(MultipartFile multipartFile, ImportOrExportEnum importOrExportEnum, int count) {
//        // 存入oss
//        try {
//            ImportExportLog importExportLog = ImportExportLog.builder()
//                    .name(multipartFile.getOriginalFilename())
//                    .path(zwyOssUtil.ossUploadAsFile(multipartFile.getName(), multipartFile.getInputStream()))
//                    .type(importOrExportEnum)
//                    .count(count)
//                    .status(StringPool.TRUE)
//                    .build();
//            // 入库
//            importExportLogRepository.save(importExportLog);
//            return importExportLog.getId();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//    /**
//     * 保存错误日志并处理导入或导出操作的异常情况。
//     *
//     * @param logId        文件ID，用于标识日志与特定文件的关联。
//     * @param object       包含导入/导出数据的对象。
//     * @param errorMessage 错误信息
//     * @param clazz        对象的类类型，用于获取属性信息。
//     */
//    public void saveErrorLog(String logId, Object object, String errorMessage, Class clazz) {
//        String string = UUID.randomUUID().toString();
//        ImportExportLog byId = importExportLogRepository.getById(logId);
//        if (byId == null) {
//            throw new RuntimeException("传入文件日志id有误");
//        }
//        if (StringPool.TRUE.equals(byId.getStatus())) {
//            byId.setStatus(StringPool.FALSE);
//            byId.setClazz(clazz.getName());
//            importExportLogRepository.updateById(byId);
//        }
//        // 获取clazz类的所有声明字段
//        Field[] declaredFields = clazz.getDeclaredFields();
//        // 创建一个clazz类的实例，用于存储属性值
//        try {
//            // 从传入的object对象中获取属性值，并存储到instance实例中
//            for (Field field : declaredFields) {
//                field.setAccessible(true);
//                Object value = field.get(object);
//                if (value == null) {
//                    // 处理null值的情况
//                    value = "";
//                }
//                //获取字段名称
//                String englishFiledName = field.getName();
//                //获取字段中文名
//                EzFiled fieldAnnotationName = field.getAnnotation(EzFiled.class);
//                String chineseTitle = fieldAnnotationName.name();
//                ImportExportLogDetail build = ImportExportLogDetail.builder()
//                        .logId(logId)
//                        .uuid(string)
//                        .englishFiledName(englishFiledName)
//                        .chineseTitleName(chineseTitle)
//                        .value((String) value)
//                        .becauseOf(errorMessage)
//                        .build();
//                importExportLogDetailRepository.save(build);
//            }
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//    }
//}
