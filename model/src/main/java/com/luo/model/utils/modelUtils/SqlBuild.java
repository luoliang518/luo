package com.luo.model.utils.modelUtils;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.jdbc.SqlBuilder;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class SqlBuild {
    public static void main(String[] args) {
        List<String> strings = generateSqlForPackage("com.luo.model.user.entity", "");
        for (String sql : strings) {
            System.out.println(sql);
        }
    }

    /**
     * 用来存储Java等属性类型与sql中属性类型的对照
     * </br>
     * 例如：java.lang.Integer 对应 integer
     */
    private static final String VARCHAR = "varchar(255)";
    private static final String INT = "int";
    private static final String INTEGER_UNSIGNED = "integer unsigned";
    private static final String BLOB = "blob";
    private static final String BIT = "bit";
    private static final String BIGINT_UNSIGNED = "bigint unsigned";
    private static final String FLOAT = "float";
    private static final String DOUBLE = "double";
    private static final String DATETIME = "datetime";
    private static final String TINYINT = "tinyint";
    private static final String DECIMAL = "decimal";
    private static final String AUTO_INCREMENT_TYPE = "AUTO_INCREMENT";
    private static final String AUTO_INCREMENT = " AUTO_INCREMENT";
    private static final String CHARACTER_SET_UTF8_COLLATE_UTF8_GENERAL_CI = " CHARACTER SET utf8 COLLATE utf8_general_ci";
    private static final String DEFAULT_NULL = " DEFAULT NULL";

    public static List<String> generateSqlForPackage(String packageName, String tablePrefix) {
        List<String> sqlList = new ArrayList<>();

        List<Class<?>> classes = getAllClasses(packageName);
        for (Class<?> clazz : classes) {
            TableName tableNameAnnotation = clazz.getAnnotation(TableName.class);
            String tableName = (tableNameAnnotation != null) ? tableNameAnnotation.value() : clazz.getSimpleName();
            String sql = generateCreateTableSql(clazz, tableName, tablePrefix);
            if (sql != null) {
                sqlList.add(sql);
            }
        }

        return sqlList;
    }

    public static List<Class<?>> getAllClasses(String packageName) {
        List<Class<?>> classList = new ArrayList<>();
        String basePath = SqlBuilder.class.getResource("/").getPath();
        String packagePath = packageName.replace(".", File.separator);

        File packageDir = new File(basePath, packagePath);
        if (packageDir.exists() && packageDir.isDirectory()) {
            File[] files = packageDir.listFiles();
            for (File file : files) {
                String fileName = file.getName();
                if (fileName.endsWith(".class")) {
                    String className = packageName + "." + fileName.substring(0, fileName.length() - 6);
                    try {
                        Class<?> clazz = Class.forName(className);
                        if (clazz.isAnnotationPresent(TableName.class)) {
                            classList.add(clazz);
                        }
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            log.debug("包路径未找到！");
        }
        return classList;
    }

    public static String generateCreateTableSql(Class<?> clazz, String tableName, String tablePrefix) {
        List<Field> fields = getNonSerialVersionUIDFields(clazz);
        if (fields.isEmpty()) {
            log.warn("No valid fields found in the class {}. Please check the class definition.", clazz.getSimpleName());
            return null;
        }

        StringBuilder sql = new StringBuilder();
        sql.append("CREATE TABLE ").append(tablePrefix).append(tableName).append(" (");

        boolean isFirstField = true;
        for (Field field : fields) {
            if (!isFirstField) {
                sql.append(",");
            }
            sql.append("\n");
            isFirstField = false;

            String fieldName = field.getName();
            String columnName = getStandardFields(fieldName);
            String columnType = mapFieldTypeToSql(field.getType().toString());

            sql.append("`").append(columnName).append("` ").append(columnType);

            // Check for ID field and add AUTO_INCREMENT and PRIMARY KEY
            if (field.isAnnotationPresent(TableId.class)) {
                sql.append(AUTO_INCREMENT);
                sql.append(" PRIMARY KEY");
            }

            // Add other annotations (e.g., NOT NULL, UNIQUE) if necessary
            if (field.isAnnotationPresent(TableField.class)) {
                TableField tableField = field.getAnnotation(TableField.class);
                if (!tableField.exist()) {
                    sql.append(DEFAULT_NULL);
                }
                // Add more annotations handling if needed (e.g., UNIQUE, NOT NULL, etc.)
            }

        }

        sql.append("\n);");
        return sql.toString();
    }

    /**
     * 获取类中所有非serialVersionUID字段
     */
    private static List<Field> getNonSerialVersionUIDFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<>();
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields) {
            if (!"serialVersionUID".equals(field.getName())) {
                fields.add(field);
            }
        }
        return fields;
    }

    /**
     * 转换为标准等sql字段 例如 adminUser → admin_user
     */
    private static String getStandardFields(String str) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if ((c >= 'A' && c <= 'Z')) {
                c = (char) (c + 32);
                if (i != 0) {
                    sb.append("_");
                }
            }
            sb.append(c);
        }
        return sb.toString();
    }

    /**
     * 根据Java属性类型映射为SQL中的数据类型
     */
    private static String mapFieldTypeToSql(String fieldType) {
        switch (fieldType) {
            case "class java.lang.String":
                return VARCHAR;
            case "class java.lang.Integer":
                return INT;
            case "class java.lang.Long":
                return INTEGER_UNSIGNED;
            case "class [B":
                return BLOB;
            case "class java.lang.Boolean":
                return BIT;
            case "class java.math.BigInteger":
                return BIGINT_UNSIGNED;
            case "class java.lang.Float":
                return FLOAT;
            case "class java.lang.Double":
                return DOUBLE;
            case "class java.sql.Date":
            case "class java.sql.Time":
            case "class java.sql.Timestamp":
            case "class java.util.Date":
            case "class java.time.LocalDateTime":
                return DATETIME;
            case "class java.lang.Byte":
                return TINYINT;
            case "class java.math.BigDecimal":
                return DECIMAL;
            default:
                log.warn("Unsupported field type: {}. Defaulting to VARCHAR(255).", fieldType);
                return VARCHAR;
        }
    }
}
