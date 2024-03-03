package com.model.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.deser.DurationDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.DurationSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class JsonUtils {
    private static ObjectMapper mapper = mapperSetting(new ObjectMapper());

    private static final DateTimeFormatter DATETIME_FORMATTER  = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter DATE_FORMATTER  = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMATTER  = DateTimeFormatter.ofPattern("HH:mm:ss");

    public static ObjectMapper mapperSetting(ObjectMapper mapper) {
        SimpleModule module = new SimpleModule("LocalDateTime Serialiser", new Version(0,1,1,"FINAL", (String)null, (String)null));
        module.addDeserializer(LocalDate.class,new LocalDateDeserializer(DATETIME_FORMATTER));
        module.addSerializer(LocalDate.class,new LocalDateSerializer(DATETIME_FORMATTER));
        module.addDeserializer(LocalDate.class,new LocalDateDeserializer(DATE_FORMATTER));
        module.addSerializer(LocalDate.class,new LocalDateSerializer(DATE_FORMATTER));
        module.addDeserializer(LocalDate.class,new LocalDateDeserializer(TIME_FORMATTER));
        module.addSerializer(LocalDate.class,new LocalDateSerializer(TIME_FORMATTER));
        module.addDeserializer(Duration.class,DurationDeserializer.INSTANCE);
        module.addSerializer(Duration.class, DurationSerializer.INSTANCE);
        mapper.registerModule(module);
        mapper.disable(SerializationFeature.INDENT_OUTPUT);
        mapper.disable(SerializationFeature.FAIL_ON_SELF_REFERENCES);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        mapper.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
        mapper.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
        mapper.enable(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS);
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS);
        mapper.enable(new JsonGenerator.Feature[]{JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN});
        return mapper;
   }

    /**
     * 序列化
     * @param object
     * @return
     * @throws IOException
     */
   public static String serializeToString(Object object) throws IOException {
        return mapper.writeValueAsString(object);
   }
    public static String serializeToStringSensitive(Object object) throws IOException {
        return mapper.writeValueAsString(object);
    }

    /**
     * 反序列化
     * @param jsonString
     * @param cls
     * @return
     * @param <T>
     * @throws IOException
     */
    public static <T> T deserialize(String jsonString, Class<T> cls) throws IOException {
        String a = "123";
        return mapper.readValue(jsonString, cls);
    }
}
