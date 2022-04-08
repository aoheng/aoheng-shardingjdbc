package com.snowalker.shardingjdbc.snowalker.demo.common.page;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.Map;

public class V {
    private static final Logger log = LoggerFactory.getLogger(V.class);

    /***
     * 默认分隔符 ,
     */
    public static final String SEPARATOR = ",";

    /***
     * 对象是否为空
     * @param obj
     * @return
     */
    public static boolean isEmpty(Object obj) {
        if (obj instanceof String) {
            return isEmpty((String) obj);
        } else if (obj instanceof Collection) {
            return isEmpty((Collection) obj);
        } else if (obj instanceof Map) {
            return isEmpty((Map) obj);
        } else if (obj instanceof String[]) {
            return isEmpty((String[]) obj);
        } else {
            return obj == null;
        }
    }

    /***
     * 字符串是否为空
     * @param value
     * @return
     */
    public static boolean isEmpty(String value) {
        return StringUtils.isBlank(value);
    }

    /***
     * 字符串数组是否不为空
     * @param values
     * @return
     */
    public static boolean isEmpty(String[] values) {
        return values == null || values.length == 0;
    }

    /***
     * 集合为空
     * @param list
     * @return
     */
    public static <T> boolean isEmpty(Collection<T> list) {
        return list == null || list.isEmpty();
    }

    /***
     * Map为空
     * @param obj
     * @return
     */
    public static boolean isEmpty(Map obj) {
        return obj == null || obj.isEmpty();
    }

    /***
     * 对象是否为空
     * @param obj
     * @return
     */
    public static boolean notEmpty(Object obj) {
        if (obj instanceof String) {
            return notEmpty((String) obj);
        } else if (obj instanceof Collection) {
            return notEmpty((Collection) obj);
        } else if (obj instanceof Map) {
            return notEmpty((Map) obj);
        } else if (obj instanceof String[]) {
            return notEmpty((String[]) obj);
        } else {
            return obj != null;
        }
    }

    /***
     * 字符串是否不为空
     * @param value
     * @return
     */
    public static boolean notEmpty(String value) {
        return StringUtils.isNotBlank(value);
    }

    /***
     * 字符串数组是否不为空
     * @param values
     * @return
     */
    public static boolean notEmpty(String[] values) {
        return values != null && values.length > 0;
    }

    /***
     * 集合不为空
     * @param list
     * @return
     */
    public static <T> boolean notEmpty(Collection<T> list) {
        return list != null && !list.isEmpty();
    }

    /***
     * 对象不为空且不为0
     * @param longObj
     * @return
     */
    public static boolean notEmptyOrZero(Long longObj) {
        return longObj != null && longObj.longValue() != 0;
    }

    /***
     * 对象不为空且不为0
     * @param intObj
     * @return
     */
    public static boolean notEmptyOrZero(Integer intObj) {
        return intObj != null && intObj.intValue() != 0;
    }

    /***
     * Map为空
     * @param obj
     * @return
     */
    public static boolean notEmpty(Map obj) {
        return obj != null && !obj.isEmpty();
    }


    /***
     * 判定两个对象是否不同类型或不同值
     * @param source
     * @param target
     * @return
     */
    public static boolean notEquals(Object source, Object target) {
        return !equals(source, target);
    }

    /***
     * 判定两个对象是否类型相同值相等
     * @param source
     * @param target
     * @return
     */
    public static <T> boolean equals(T source, T target) {
        if (source == null && target == null) {
            return true;
        } else if (source == null || target == null) {
            return false;
        }
        // 不为空，调用equals比较
        else if (source instanceof Comparable) {
            return (source).equals(target);
        } else if (source instanceof Collection) {
            Collection sourceList = (Collection) source, targetList = (Collection) target;
            // size不等
            if (sourceList.size() != targetList.size()) {
                return false;
            }
            for (Object obj : sourceList) {
                if (!targetList.contains(obj)) {
                    return false;
                }
            }
            for (Object obj : targetList) {
                if (!sourceList.contains(obj)) {
                    return false;
                }
            }
            return true;
        } else {
            log.warn("暂未实现类型 " + source.getClass().getSimpleName() + "-" + target.getClass().getSimpleName() + " 的比对！");
            return false;
        }
    }

    /***
     * 转换成小写蛇形命名（用于Java属性转换为小写数据库列名）
     * @param camelCaseStr
     * @return
     */
    public static String toSnakeCase(String camelCaseStr) {
        if (isEmpty(camelCaseStr)) {
            return null;
        }
        // 全小写
        if (camelCaseStr.toLowerCase().equals(camelCaseStr)) {
            return camelCaseStr;
        }
        // 全大写直接return小写
        if (camelCaseStr.toUpperCase().equals(camelCaseStr)) {
            return camelCaseStr.toLowerCase();
        }
        // 大小写混合，则遇“大写”转换为“_小写”
        char[] chars = camelCaseStr.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char c : chars) {
            if (Character.isUpperCase(c)) {
                if (sb.length() > 0) {
                    sb.append("_");
                }
            }
            sb.append(Character.toLowerCase(c));
        }
        return sb.toString();
    }

    /***
     * 按,拆分字符串
     * @param joinedStr
     * @return
     */
    public static String[] split(String joinedStr) {
        if (joinedStr == null) {
            return null;
        }
        return joinedStr.split(SEPARATOR);
    }

    /***
     * 按传入的拆分
     * @param joinedStr
     * @return
     */
    public static String[] split(String joinedStr, String str) {
        if (joinedStr == null) {
            return null;
        }
        return joinedStr.split(str);
    }

    /**
     * 非0除
     *
     * @param frontPart
     * @param endPart
     * @return
     */
    public static BigDecimal nullZeroDivide(BigDecimal frontPart, BigDecimal endPart) {
        if (BigDecimal.ZERO.compareTo(endPart) == 0 || frontPart == null) {
            return BigDecimal.ZERO;
        }
        return frontPart.divide(endPart, 2, RoundingMode.HALF_DOWN);
    }

    /**
     * Long 转 BigDecimal
     *
     * @param input
     * @return
     */
    public static BigDecimal convertToBigDecimal(Long input) {
        return BigDecimal.valueOf(input == null ? 0 : input);
    }
}
