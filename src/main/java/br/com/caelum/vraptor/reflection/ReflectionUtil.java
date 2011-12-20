package br.com.caelum.vraptor.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
/**
 * 
 * Helper to simplify the reflection usage
 * 
 * @author Lucas Medeiros
 *
 */
public class ReflectionUtil {
	
	public static List<Field> getNonStaticAndNotNullFields(Object object) throws IllegalArgumentException, IllegalAccessException {

		List<Field> fieldsList = new ArrayList<Field>();

		Field[] fields = object.getClass().getDeclaredFields();

		for (Field field : fields) {

			if (Modifier.isStatic(field.getModifiers()) == false) {
				field.setAccessible(true);
				if(field.get(object) != null){
					fieldsList.add(field);
				}
				field.setAccessible(false);
			}

		}

		if (fieldsList.size() == 0) {
			throw new IllegalArgumentException(
					"This class has no non static fields");
		}

		return fieldsList;
	}
	
	public static String getClazzName(Object object){
		String completeClassName = object.getClass().getName();
		String[] parts = completeClassName.split("\\."); 
		
		String className = parts[parts.length-1];
				
		char[] stringArray = className.toCharArray();
		stringArray[0] = Character.toLowerCase(stringArray[0]);
		className = new String(stringArray);
		
		return className;
	}
	
	public static String getFieldValueAsString(Object object, Field field) throws IllegalArgumentException, IllegalAccessException{
		
		field.setAccessible(true);
		String fieldValue = field.get(object).toString();
		field.setAccessible(false);
		return fieldValue;
		
	}
	
	public static boolean isCollection(Class<?> type){
		return Collection.class.isAssignableFrom(type);
	}
	
	public static boolean isPrimitive(Class<?> type) {
		return type.isPrimitive() || type.isEnum() || Number.class.isAssignableFrom(type) 
								  || type.equals(String.class) || Date.class.isAssignableFrom(type) 
								  || Calendar.class.isAssignableFrom(type)
								  || Boolean.class.equals(type)
								  || Character.class.equals(type);
	}
	
	public static Class<?> getActualClassForField(Field field){
		Type genericType = field.getGenericType();  
        if (genericType instanceof ParameterizedType) {  
            ParameterizedType type = (ParameterizedType) genericType;  
            if (isTypeOfCollection(type)) {
				return (Class<?>) type.getActualTypeArguments()[0];
			}
        }  
		return (Class<?>) genericType;
	}
	
	private static boolean isTypeOfCollection(Type type) {
		if (type instanceof ParameterizedType) {
			ParameterizedType ptype = (ParameterizedType) type;
			return Collection.class.isAssignableFrom((Class<?>) ptype.getRawType())
			  || Map.class.isAssignableFrom((Class<?>) ptype.getRawType());
		}
		return Collection.class.isAssignableFrom((Class<?>) type);
	}

}
