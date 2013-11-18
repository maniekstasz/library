package pl.styall.library.core.rest.ext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

public class EntityDtmMapper {

	private Logger log = Logger.getLogger(EntityDtmMapper.class);

	public Map<String, Object> mapEntityToDtm(Object entity, Class<?> clazz,
			List<String> propertiesToMap) {
		Map<String, Object> dtm = new HashMap<String, Object>(
				propertiesToMap.size());
		for (String prop : propertiesToMap) {
			try {
				decideForNestedObject(prop, dtm, entity, clazz);
			} catch (SecurityException e) {
				log.fatal(e.getMessage(), e);
			} catch (IllegalArgumentException e) {
				log.fatal(e.getMessage(), e);
			} catch (IllegalAccessException e) {
				log.fatal(e.getMessage(), e);
			} catch (InvocationTargetException e) {
				log.fatal(e.getMessage(), e);
			}

		}
		return dtm;
	}

	public List<Object> mapEntitiesToDtm(List<?> entities,
			Class<?> clazz, List<String> propertiesToMap) {
		List<Object> result = new ArrayList<Object>(
				entities.size());
		for (Object entity : entities) {
			result.add(mapEntityToDtm(entity, clazz, propertiesToMap));
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	private void decideForNestedObject(String prop, Map<String, Object> dtm,
			Object entity, Class<?> clazz) throws SecurityException,
			IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		if (prop.contains(".")) {
			int dotIndex = prop.indexOf(".");
			String fieldName = prop.substring(0, dotIndex);
			String subProp = prop.substring(dotIndex + 1);

			try {
				Method fieldMethod = getGetterMethod(fieldName, clazz);
				Object fieldObject = fieldMethod.invoke(entity);
				if (dtm.containsKey(fieldName)) {
					decideForNestedObject(subProp,
							(Map<String, Object>) dtm.get(fieldName),
							fieldObject, fieldMethod.getReturnType());
				} else {
					Map<String, Object> childDtm = new HashMap<String, Object>();
					dtm.put(fieldName, childDtm);
					decideForNestedObject(subProp, childDtm, fieldObject,
							fieldMethod.getReturnType());
				}
			} catch (NoSuchMethodException e2) {
				log.fatal(e2.getMessage(), e2);
			}

		} else {
			try {
				Method fieldMethod = getGetterMethod(prop, clazz);
				Object value = fieldMethod.invoke(entity);
				dtm.put(prop, value);
			} catch (NoSuchMethodException e) {
				log.fatal(e);
			}
		}
	}

	private Method getGetterMethod(String fieldName, Class<?> clazz)
			throws SecurityException, NoSuchMethodException {
		return clazz.getMethod("get" + StringUtils.capitalize(fieldName));
	}
}
