package com.mfc.infra.utils;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConversionUtils {

    Logger logger = LoggerFactory.getLogger(ConversionUtils.class);
    private static ObjectMapper objectMapper = new ObjectMapper();

    private ConversionUtils() {}

    public static String formatTimestampToSpanish(Timestamp timestamp) {
        // Convertir Timestamp a LocalDateTime
        LocalDateTime localDateTime = timestamp.toLocalDateTime();

        // Definir el formato deseado
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, d 'de' MMMM 'de' yyyy HH:mm:ss",
                new Locale("es", "ES"));

        // Formatear la fecha
        String formattedDate = localDateTime.format(formatter);

        return formattedDate;
    }
    public static String map2Jsonstring( Map<String, Object> map ) {
        if ( map == null ) return "{}";

        try {
            return objectMapper.writeValueAsString( map );
        } catch (JsonProcessingException e) {
           e.printStackTrace();
        }
        return "{}";
    }

    public static <T> T jsonStringToObject(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> Map<String, Object> objectToMap(T object) {
        try {

            return objectMapper.convertValue(object, Map.class);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T convertMapToObject(LinkedHashMap<String, Object> map, Class<T> clazz) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper.convertValue(map, clazz);
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

    public static <T> Map<String, Object> convertLinkedHashMapToMap(T obj) {
        Map<String, Object> resultMap = new HashMap<>();
        Iterator<Map.Entry> iteratorMap = ((LinkedHashMap) obj).entrySet().iterator();
        while (iteratorMap.hasNext()) {
            Map.Entry entry = iteratorMap.next();
            resultMap.put((String) entry.getKey(), entry.getValue());
        }
        return resultMap;
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
