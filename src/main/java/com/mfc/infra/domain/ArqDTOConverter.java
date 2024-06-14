package com.mfc.infra.domain;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ArqDTOConverter {

    public static <T, IDTO> IDTO convertToDTO(T entity, Class<IDTO> dtoClass) {
        try {
            IDTO dto = dtoClass.getDeclaredConstructor().newInstance();
            for (Field entityField : entity.getClass().getDeclaredFields()) {
                entityField.setAccessible(true);
                Object value = entityField.get(entity);
                for (Field dtoField : dtoClass.getDeclaredFields()) {
                    dtoField.setAccessible(true);
                    if (dtoField.getName().equals(entityField.getName()) &&
                            dtoField.getType().equals(entityField.getType())) {
                        dtoField.set(dto, value);
                        break;
                    }
                }
            }
            return dto;
        } catch (Exception e) {
            throw new RuntimeException("Error converting entity to DTO", e);
        }
    }

    public static <T, IDTO> T convertToEntity(IDTO dto, Class<T> entityClass) {
        try {
            T entity = entityClass.getDeclaredConstructor().newInstance();
            for (Field dtoField : dto.getClass().getDeclaredFields()) {
                dtoField.setAccessible(true);
                Object value = dtoField.get(dto);
                for (Field entityField : entityClass.getDeclaredFields()) {
                    entityField.setAccessible(true);
                    if (entityField.getName().equals(dtoField.getName()) &&
                            entityField.getType().equals(dtoField.getType())) {
                        entityField.set(entity, value);
                        break;
                    }
                }
            }
            return entity;
        } catch (Exception e) {
            throw new RuntimeException("Error converting DTO to entity", e);
        }
    }

    public static <T, IDTO> List<IDTO> convertToDTOList(List<T> entities, Class<IDTO> dtoClass) {
        List<IDTO> dtoList = new ArrayList<>();
        for (T entity : entities) {
            dtoList.add(convertToDTO(entity, dtoClass));
        }
        return dtoList;
    }

    public static <T, IDTO> List<T> convertToEntityList(List<IDTO> dtoList, Class<T> entityClass) {
        List<T> entityList = new ArrayList<>();
        for (IDTO dto : dtoList) {
            entityList.add(convertToEntity(dto, entityClass));
        }
        return entityList;
    }
}

