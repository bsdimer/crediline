package com.crediline.dataimport;

import org.joda.time.LocalDateTime;

import javax.persistence.Entity;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by dimer on 4/29/14.
 */
public class RowMapper {

    private Class entitytClass;
    private Map<String, String> values;
    private Object entity = null;

    public RowMapper(Class<?> entitytClass, Map<String, String> values) {
        this.entitytClass = entitytClass;
        this.values = values;
    }

    public Class getEntitytClass() {
        return entitytClass;
    }

    public void setEntitytClass(Class entitytClass) {
        this.entitytClass = entitytClass;
    }

    public Object getEntity() throws IllegalAccessException, InstantiationException, InvocationTargetException {
        if (entitytClass.isAnnotationPresent(Entity.class)) {
            entity = entitytClass.newInstance();
        }
        Method[] methods = entitytClass.getMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(CSVEntity.class)) {
                // ToDo must map according to annotation parameter 'rowName'
                String value = values.get(method.getName());
                Object realValue = value;
                Class<?> parameterType = method.getParameterTypes()[0];
                // ToDo: should be changed to support dynamic multiparameter setter

                if (parameterType.equals(Long.class)) {
                    realValue = Long.parseLong(value);
                } else if (parameterType.equals(Integer.class)) {
                    realValue = Integer.parseInt(value);
                } else if (parameterType.equals(Boolean.class)) {
                    realValue = Boolean.parseBoolean(value);
                } else if (parameterType.equals(LocalDateTime.class)) {
                    realValue = LocalDateTime.parse(value);
                }

                method.invoke(entity, realValue);
            }
        }
        return entity;
    }

    public Map<String, String> getValues() {
        return values;
    }
}
