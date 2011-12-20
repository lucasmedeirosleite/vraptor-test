package br.com.caelum.vraptor.test.http.parameter;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.caelum.vraptor.reflection.ReflectionUtil;

/**
 * 
 * Transforms model fields into a Map of string to be sent to restfulie request
 * 
 * @author Lucas Medeiros
 *
 */
public class FormEncodedTransformer{

	private static Map<String, String> params = new HashMap<String, String>();
	
	public static Map<String, String> paramsFor(Object payload) throws IllegalArgumentException, IllegalAccessException {
		String payloadClassname= ReflectionUtil.getClazzName(payload);
		List<Field> fields = ReflectionUtil.getNonStaticAndNotNullFields(payload);
		for (Field field : fields) {
			buildParamsFromField(field, payload, payloadClassname);
		}
		return params;
	}
	
	private static void buildParamsFromField(Field field, Object payload, String keyToBeCompleted) throws IllegalArgumentException, IllegalAccessException{
		
		Map<String, String> parameters = new HashMap<String, String>();

		if(ReflectionUtil.isPrimitive(field.getType())){
			parameters.put(keyToBeCompleted + "." + field.getName(), ReflectionUtil.getFieldValueAsString(payload, field));
		}
		
		if(ReflectionUtil.isPrimitive(field.getType()) == false && ReflectionUtil.isCollection(field.getType()) == false){
			field.setAccessible(true);
			Object subPayload = field.get(payload);
			String subPayloadClassname = field.getName().toLowerCase();
			for (Field afield : ReflectionUtil.getNonStaticAndNotNullFields(subPayload) ) {
				buildParamsFromField(afield, subPayload, keyToBeCompleted + "." + subPayloadClassname);
			}
			field.setAccessible(false);
		}
		
		if(ReflectionUtil.isCollection(field.getType())){
			field.setAccessible(true);
			
			Collection<?> collection = (Collection<?>) field.get(payload);
			String subPayloadClassname = field.getName().toLowerCase();
			
			Class<?> collectionClass = ReflectionUtil.getActualClassForField(field);
			
			for(int index = 0; index < collection.size(); index++){
				
				String key = keyToBeCompleted + "." + subPayloadClassname + "[" + index + "]";
				Object[] collectionAsArray = collection.toArray();
				
				if(ReflectionUtil.isPrimitive(collectionClass) == false){
					for (Field aField : ReflectionUtil.getNonStaticAndNotNullFields(collectionAsArray[index])) {
						buildParamsFromField(aField, collectionAsArray[index], key);
					}
				}else{
					parameters.put(key, collectionAsArray[index].toString());
				}
				
			}
			
			field.setAccessible(false);
		}
		
		params.putAll(parameters);
	}
	
}
