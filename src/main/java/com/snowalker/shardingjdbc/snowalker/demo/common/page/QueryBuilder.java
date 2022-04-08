package com.snowalker.shardingjdbc.snowalker.demo.common.page;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.snowalker.shardingjdbc.snowalker.demo.common.annotation.BindQuery;
import com.snowalker.shardingjdbc.snowalker.demo.common.annotation.Sum;
import com.snowalker.shardingjdbc.snowalker.demo.common.enums.Comparison;
import com.snowalker.shardingjdbc.snowalker.demo.common.utils.BeanUtils;
import io.shardingsphere.core.constant.DatabaseType;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class QueryBuilder {

    private static Logger log = LoggerFactory.getLogger(QueryBuilder.class);

    public static <T, DTO> QueryWrapper<T> toQueryWrapperSum(DatabaseType databaseType, DTO dto, Class sumFields) {
        QueryWrapper<T> wrapper = new QueryWrapper<>();
        return (QueryWrapper<T>) dtoToWrapper(wrapper, dto, null).select(getSumCols(databaseType, sumFields));
    }

    public static <T, DTO> QueryWrapper<T> toQueryWrapperSumByGroup(DatabaseType databaseType, DTO dto, Class sumFields, String[] groupBy) {
        QueryWrapper<T> wrapper = new QueryWrapper<>();
        return (QueryWrapper<T>) dtoToWrapper(wrapper, dto, null).select(getSumCols(databaseType, sumFields) + "," + String.join(",", groupBy)).groupBy(groupBy);
    }

    /**
     * Entity或者DTO对象转换为QueryWrapper
     *
     * @param dto
     * @param <T>
     * @param <DTO>
     * @return
     */
    public static <T, DTO> QueryWrapper<T> toQueryWrapperPage(DTO dto, Pagination pagination) {
        QueryWrapper<T> wrapper = new QueryWrapper<>();
        return (QueryWrapper<T>) dtoToWrapper(wrapper, dto, null).last("order by " + pagination.getOrderBy() + " limit " + pagination.getPageSize() + " offset " + pagination.getCurrentPosition());
    }

    public static <T, DTO> QueryWrapper<T> toQueryWrapperSumByGroupPage(DatabaseType databaseType, DTO dto, Class sumFields, Pagination pagination, String[] group) {
        QueryWrapper<T> wrapper = new QueryWrapper<>();
        String selectJoin = "";
        if (ArrayUtils.isNotEmpty(group)) {
            selectJoin = "," + String.join(",", group);
        }
        return (QueryWrapper<T>) dtoToWrapper(wrapper, dto, null).select(getSumCols(databaseType, sumFields) + selectJoin).last("order by " + pagination.getOrderBy() + " limit " + pagination.getPageSize() + " offset " + pagination.getCurrentPosition());
    }

    /**
     * Entity或者DTO对象转换为QueryWrapper
     *
     * @param dto
     * @param <T>
     * @param <DTO>
     * @return
     */
    public static <T, DTO> QueryWrapper<T> toQueryWrapper(DTO dto) {
        QueryWrapper<T> wrapper = new QueryWrapper<>();
        return (QueryWrapper<T>) dtoToWrapper(wrapper, dto, null);
    }

    /**
     * Entity或者DTO对象转换为QueryWrapper
     *
     * @param dto
     * @param fields 指定参与转换的属性值
     * @param <T>
     * @param <DTO>
     * @return
     */
    public static <T, DTO> QueryWrapper<T> toQueryWrapper(DTO dto, Collection<String> fields) {
        QueryWrapper<T> wrapper = new QueryWrapper<>();
        return (QueryWrapper<T>) dtoToWrapper(wrapper, dto, fields);
    }

    /**
     * Entity或者DTO对象转换为LambdaQueryWrapper
     *
     * @param dto
     * @param <T>
     * @return
     */
    public static <T, DTO> LambdaQueryWrapper<T> toLambdaQueryWrapper(DTO dto) {
        return (LambdaQueryWrapper<T>) toQueryWrapper(dto).lambda();
    }


    /**
     * Entity或者DTO对象转换为LambdaQueryWrapper
     *
     * @param dto
     * @param fields 指定参与转换的属性值
     * @param <T>
     * @return
     */
    public static <T, DTO> LambdaQueryWrapper<T> toLambdaQueryWrapper(DTO dto, Collection<String> fields) {
        return (LambdaQueryWrapper<T>) toQueryWrapper(dto, fields).lambda();
    }

    public static String getSumCols(DatabaseType databaseType, Class cls) {
        List<String> fieldList = new ArrayList<>();
        List<Field> declaredFields = BeanUtils.extractAllFields(cls);
        for (Field field : declaredFields) {
            Sum sum = field.getAnnotation(Sum.class);
            if (Objects.isNull(sum)) {
                continue;
            }

            String fieldName = "`" + field.getName() + "`";
            if (!StringUtils.isEmpty(sum.totalMark())) {
                fieldList.add(sum.totalMark() + " AS " + V.toSnakeCase(fieldName));
                continue;
            }
            String[] colums = sum.field();
            if (Objects.equals(DatabaseType.MySQL, databaseType)) {
                if (Objects.isNull(colums) || colums.length == 0 || colums.length == 1) {
                    fieldList.add("round(IFNULL(sum(" + V.toSnakeCase(fieldName) + "),0),4) AS " + V.toSnakeCase(fieldName));
                } else {
                    fieldList.add("round(IF(IFNULL(sum(" + V.toSnakeCase(colums[1]) + "),0) = 0,0," + "(IFNULL(sum(" + V.toSnakeCase(colums[0]) + "),0) / IFNULL(sum(" + V.toSnakeCase(colums[1]) + "),0))),4) AS " + V.toSnakeCase(fieldName));
                }
            } else {
                if (Objects.isNull(colums) || colums.length == 0 || colums.length == 1) {
                    fieldList.add("dround(IFNULL(sum(" + V.toSnakeCase(fieldName) + "),0),4) AS " + V.toSnakeCase(fieldName));
                } else {
                    fieldList.add("dround(IF(IFNULL(sum(" + V.toSnakeCase(colums[1]) + "),0) = 0,0," + "(IFNULL(sum(" + V.toSnakeCase(colums[0]) + "),0) / IFNULL(sum(" + V.toSnakeCase(colums[1]) + "),0))),4) AS " + V.toSnakeCase(fieldName));
                }
            }
        }
        return fieldList.stream().collect(Collectors.joining(","));
    }

    /**
     * 转换具体实现
     *
     * @param wrapper
     * @param dto
     * @param <T>
     * @return
     */
    public static <T, DTO> QueryWrapper<T> dtoToWrapper(QueryWrapper wrapper, DTO dto, Collection<String> fields) {
        // 转换
        List<Field> declaredFields = BeanUtils.extractAllFields(dto.getClass());
        for (Field field : declaredFields) {
            // 非指定属性，非逻辑删除字段，跳过
            if (fields != null && !fields.contains(field.getName()) && !"deleted".equals(field.getName())) {
                continue;
            }
            //忽略static，以及final，transient
            boolean isStatic = Modifier.isStatic(field.getModifiers());
            boolean isFinal = Modifier.isFinal(field.getModifiers());
            boolean isTransient = Modifier.isTransient(field.getModifiers());
            if (isStatic || isFinal || isTransient) {
                continue;
            }
            //忽略注解 @TableField(exist = false) 的字段
            TableField tableField = field.getAnnotation(TableField.class);
            if (tableField != null && tableField.exist() == false) {
                continue;
            }
            BindQuery query = field.getAnnotation(BindQuery.class);
            if (query != null && query.ignore()) { //忽略字段
                continue;
            }
            //打开私有访问 获取值
            field.setAccessible(true);
            Object value = null;
            try {
                value = field.get(dto);
            } catch (IllegalAccessException e) {
                log.error("通过反射获取属性值出错：" + e);
            }
            if (value == null) {
                continue;
            }
            // 对比类型
            Comparison comparison = (query != null) ? query.comparison() : Comparison.EQ;
            // 是否允许空值
            boolean nullable = (query == null) || query.nullable();
            // 转换条件
            String columnName = getColumnName(field);
            switch (comparison) {
                case EQ:
                    wrapper.eq(columnName, value);
                    break;
                case IN:
                    if (value.getClass().isArray()) {
                        Object[] valueArray = (Object[]) value;
                        if (valueArray.length == 1) {
                            wrapper.in(columnName, valueArray[0]);
                        } else if (valueArray.length >= 2) {
                            wrapper.in(columnName, valueArray);
                        }
                    } else {
                        wrapper.in(columnName, value);
                    }
                    break;
                case CONTAINS:
                    wrapper.like(columnName, value);
                    break;
                case LIKE:
                    wrapper.like(columnName, value);
                    break;
                case STARTSWITH:
                    wrapper.likeRight(columnName, value);
                    break;
                case GT:
                    wrapper.gt(columnName, value);
                    break;
                case BETWEEN_BEGIN:
                    wrapper.ge(columnName, value);
                    break;
                case GE:
                    wrapper.ge(columnName, value);
                    break;
                case LT:
                    wrapper.lt(columnName, value);
                    break;
                case BETWEEN_END:
                    wrapper.le(columnName, value);
                    break;
                case LE:
                    wrapper.le(columnName, value);
                    break;
                case GROUP_BY:
                    if (value.getClass().isArray()) {
                        Object[] groupByValues = (Object[]) value;
                        wrapper.groupBy((Object[]) value);
                        if (!nullable) {
                            for (Object groupByValue : groupByValues) {
                                wrapper.isNotNull(groupByValue);
                            }
                        }
                    } else {
                        wrapper.groupBy(value);
                        if (!nullable) {
                            wrapper.isNotNull(value);
                        }
                    }
                    break;
                case BETWEEN:
                    if (value.getClass().isArray()) {
                        Object[] valueArray = (Object[]) value;
                        if (valueArray.length == 1) {
                            wrapper.ge(columnName, valueArray[0]);
                        } else if (valueArray.length >= 2) {
                            wrapper.between(columnName, valueArray[0], valueArray[1]);
                        }
                    }
                    // 支持逗号分隔的字符串
                    else if (value instanceof String && ((String) value).contains(",")) {
                        Object[] valueArray = ((String) value).split(",");
                        wrapper.between(columnName, valueArray[0], valueArray[1]);
                    } else {
                        wrapper.ge(columnName, value);
                    }
                    break;
                default:
            }
        }
        return wrapper;
    }

    /**
     * 获取数据表的列名（驼峰转下划线蛇形命名）
     * <br>
     * 列名取值优先级： @BindQuery.field > @TableField.value > field.name
     *
     * @param field
     * @return
     */
    private static String getColumnName(Field field) {
        String columnName = null;
        if (field.isAnnotationPresent(BindQuery.class)) {
            columnName = field.getAnnotation(BindQuery.class).field();
        } else if (field.isAnnotationPresent(TableField.class)) {
            columnName = field.getAnnotation(TableField.class).value();
        }
        return V.notEmpty(columnName) ? columnName : V.toSnakeCase(field.getName());
    }

}
