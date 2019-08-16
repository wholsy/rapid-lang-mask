package com.yueny.rapid.lang.mask.util;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * java对象工具类
 *
 * @Author yueny09 <deep_blue_yang@126.com>
 * @Date 2019-08-16 11:26
 */
public class ObjectUtils {

    /**
     * 是否静态变量
     *
     * @param field
     * @return
     */
    public static boolean isStaticType(Field field) {
        return field.getModifiers() == 8 ? true : false;
    }


    /**
     * 排除基础类型、jdk类型、枚举类型的字段
     *
     * @param clazz
     * @param value
     * @param referenceCounter
     * @return
     */
    public static boolean isNotGeneralType(Class<?> clazz, Object value, Set<Integer> referenceCounter) {
        return !clazz.isPrimitive() && clazz.getPackage() != null && !clazz.isEnum()
                && !StringUtils.startsWith(clazz.getPackage().getName(), "javax.")
                && !StringUtils.startsWith(clazz.getPackage().getName(), "java.")
                && !StringUtils.startsWith(clazz.getName(), "javax.")
                && !StringUtils.startsWith(clazz.getName(), "java.") && referenceCounter.add(value.hashCode());
    }

    /**
     * 是否jdk类型变量
     *
     * @param clazz
     * @return
     * @throws IllegalAccessException
     */
    public static boolean isJDKType(Class<?> clazz) throws IllegalAccessException {
        return StringUtils.startsWith(clazz.getPackage().getName(), "javax.")
                || StringUtils.startsWith(clazz.getPackage().getName(), "java.")
                || StringUtils.startsWith(clazz.getName(), "javax.")
                || StringUtils.startsWith(clazz.getName(), "java.");
    }

    /**
     * 是否用户自定义类型
     *
     * @param clazz
     * @return
     */
    public static boolean isUserDefinedType(Class<?> clazz) {
        return clazz.getPackage() != null && !StringUtils.startsWith(clazz.getPackage().getName(), "javax.")
                && !StringUtils.startsWith(clazz.getPackage().getName(), "java.")
                && !StringUtils.startsWith(clazz.getName(), "javax.")
                && !StringUtils.startsWith(clazz.getName(), "java.");
    }

    /**
     * 获取包括父类所有的属性
     *
     * @param objSource
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static Field[] getAllFields(Object objSource) {
        /* 获得当前类的所有属性(private、protected、public) */
        List<Field> fieldList = new ArrayList<Field>();
        Class tempClass = objSource.getClass();
        while (tempClass != null && !tempClass.getName().toLowerCase().equals("java.lang.object")) {// 当父类为null的时候说明到达了最上层的父类(Object类).
            fieldList.addAll(Arrays.asList(tempClass.getDeclaredFields()));
            tempClass = tempClass.getSuperclass(); // 得到父类,然后赋给自己
        }
        Field[] fields = new Field[fieldList.size()];
        fieldList.toArray(fields);
        return fields;
    }

    /**
     * Don't let anyone instantiate this class.
     */
    private ObjectUtils() {
        //
    }
}
