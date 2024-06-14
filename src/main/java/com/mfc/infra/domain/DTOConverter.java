package com.mfc.infra.domain;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class DTOConverter {

    public static <T, D> D convertToDTO(T entity, Class<D> dtoClass) {
        try {
            D dto = dtoClass.getDeclaredConstructor().newInstance();
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

    public static <T, D> T convertToEntity(D dto, Class<T> entityClass) {
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

    public static <T, D> List<D> convertToDTOList(List<T> entities, Class<D> dtoClass) {
        List<D> dtoList = new ArrayList<>();
        for (T entity : entities) {
            dtoList.add(convertToDTO(entity, dtoClass));
        }
        return dtoList;
    }

    public static <T, D> List<T> convertToEntityList(List<D> dtoList, Class<T> entityClass) {
        List<T> entityList = new ArrayList<>();
        for (D dto : dtoList) {
            entityList.add(convertToEntity(dto, entityClass));
        }
        return entityList;
    }
}

