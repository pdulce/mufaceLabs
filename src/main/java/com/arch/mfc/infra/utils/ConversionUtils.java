package com.arch.mfc.infra.utils;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConversionUtils {

    private static ObjectMapper objectMapper = new ObjectMapper();

    private ConversionUtils() {}

    public static String map2Jsonstring( Map<String, Object> map ) {
        if ( map == null ) return "{}";

        try {
            return objectMapper.writeValueAsString( map );
        } catch (JsonProcessingException e) {
           e.printStackTrace();
        }

        return "{}";
    }
    
    public static Map<String, Object> jsonstring2Map( String json ) {
        if ( json == null ) return new HashMap<String, Object>();

        try {
            return objectMapper.readValue( json, Map.class);
        } catch (JsonProcessingException e) {
           e.printStackTrace();
        }

        return new HashMap<String, Object>();
    }

    public static <T> Map<String, Object> convertToMap(T obj) {
        Map<String, Object> resultMap = new HashMap<>();
        Class<?> clazz = obj.getClass();

        // Iterar sobre todos los campos declarados en la clase
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true); // Permite el acceso a campos privados
            try {
                resultMap.put(field.getName(), field.get(obj));
            } catch (IllegalAccessException e) {
                e.printStackTrace(); // Manejar la excepción adecuadamente
            }
        }
        return resultMap;
    }

    public static LocalDate addDaysToADate(final String fechaInicialString) {
        // Fecha inicial
        //String fechaInicialString = "28/01/2024";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fechaInicial = LocalDate.parse(fechaInicialString, formatter);

        // Sumar 12 semanas
        LocalDate fechaResultado = fechaInicial.plusWeeks(3);
        // Imprimir el resultado
        //System.out.println("Fecha inicial: " + fechaInicial.format(formatter));
        //System.out.println("Fecha después de sumar x semanas: " + fechaResultado.format(formatter));

        return fechaResultado;
    }

}
