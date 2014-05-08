package com.disankyo.database.util;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 * 
 * @author xjx
 * 
 */
public class ReflectUtil {

	/**
	 * 获取为主键id的属性
	 * 
	 * @param type
	 * @return
	 */
	public static Field getIdAccessor(Class<?> type) {
		for (Field field : type.getDeclaredFields()) {
			if (isIdAccessor(field)) {
				if (!field.isAccessible()) {
					field.setAccessible(true);
				}
				return field;
			}
		}

		throw new IllegalArgumentException(type.getName()
				+ " does not have a field or property annotated with @Id");
	}

	/** 检查是否是标注为@Id的属性 */
	public static boolean isIdAccessor(AnnotatedElement annotatedElement) {
		return annotatedElement.isAnnotationPresent(Id.class);
	}

	/**主键生成策略*/
	public static boolean isGeneratedValue(AccessibleObject accessibleObject){
		return accessibleObject.isAnnotationPresent(GeneratedValue.class);
	}
	
	/** 判断属性域是不需要插入数据库的 */
	public static boolean isTransient(AccessibleObject accessibleObject) {
		return Modifier.isTransient(((Member) accessibleObject).getModifiers())
				|| accessibleObject.isAnnotationPresent(Transient.class);
	}
	
	/** 判断属性域是 静态属性 */
	public static boolean isStatic(Member member) {
	    return Modifier.isStatic(member.getModifiers());
    }

	/**根据类获取所有的属性域*/
	public static List<Field> getPropertyDescriptorsFromFields(Class<?> c) {
		List<Field> fieldNames = new ArrayList<Field>();

		for (Field field : c.getDeclaredFields()) {
			fieldNames.add(field);
		}
		
		return fieldNames;
	}
}
