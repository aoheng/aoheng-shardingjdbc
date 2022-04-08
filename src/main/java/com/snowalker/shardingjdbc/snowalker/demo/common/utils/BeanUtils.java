package com.snowalker.shardingjdbc.snowalker.demo.common.utils;

import com.snowalker.shardingjdbc.snowalker.demo.common.page.V;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ResolvableType;

import java.lang.reflect.Field;
import java.util.*;


public class BeanUtils {
    private static final Logger log = LoggerFactory.getLogger(BeanUtils.class);

    /**
     * 获取类所有属性（包含父类中属性）
     *
     * @param clazz
     * @return
     */
    public static List<Field> extractAllFields(Class clazz) {
        List<Field> fieldList = new ArrayList<>();
        Set<String> fieldNameSet = new HashSet<>();
        while (clazz != null) {
            Field[] fields = clazz.getDeclaredFields();
            if (V.notEmpty(fields)) { //被重写属性，以子类override的为准
                Arrays.stream(fields).forEach((field) -> {
                    if (!fieldNameSet.contains(field.getName())) {
                        fieldList.add(field);
                        fieldNameSet.add(field.getName());
                    }
                });
            }
            clazz = clazz.getSuperclass();
        }
        return fieldList;
    }

    /**
     * 获取类的指定属性（包含父类中属性）
     *
     * @param clazz
     * @param fieldName
     * @return
     */
    public static Field extractField(Class clazz, String fieldName) {
        List<Field> allFields = extractAllFields(clazz);
        if (V.notEmpty(allFields)) {
            for (Field field : allFields) {
                if (field.getName().equals(fieldName)) {
                    return field;
                }
            }
        }
        return null;
    }

    /**
     * 从宿主类定义中获取泛型定义类class
     *
     * @param hostClass
     * @param index
     * @return
     */
    public static Class getGenericityClass(Class hostClass, int index) {
        ResolvableType resolvableType = ResolvableType.forClass(hostClass).getSuperType();
        ResolvableType[] types = resolvableType.getSuperType().getGenerics();
        if (V.notEmpty(types) && types.length > index) {
            return types[index].resolve();
        }
        log.warn("无法从 {} 类定义中获取泛型类{}", hostClass.getName(), index);
        return null;
    }

}

