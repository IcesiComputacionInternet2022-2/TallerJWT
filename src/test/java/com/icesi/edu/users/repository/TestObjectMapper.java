package com.icesi.edu.users.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.icesi.edu.users.config.jackson.LocalDateTimeDeserializer;
import com.icesi.edu.users.config.jackson.LocalDateTimeSerializer;
import com.icesi.edu.users.model.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

public class TestObjectMapper {


    @Test
    public void testObjectMapper() throws JsonProcessingException {
        String formato = "yyyy-MM-ddThh:mm:ss";
        SimpleModule module = new SimpleModule();
        module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        objectMapper.registerModule(module);

        String text = "{\"id\":\"9efab07b-b612-424c-b7b3-72a7bc82d836\",\"email\":\"someEmail@icesi.com\",\"phoneNumber\":\"123123123\",\"firstName\":\"Juan\",\"lastName\":\"Prada\",\"localDateTime\":\"2022-01-01T00:00:00\"}";
        var userFromText = objectMapper.readValue(text, User.class);
        System.out.println(userFromText);
        var user = User.builder().firstName("Juan").lastName("Prada").email("someEmail@icesi.com")
                .phoneNumber("123123123").id(UUID.randomUUID()).localDateTime(LocalDateTime.now()).build();
        System.out.println(user);
        System.out.println(objectMapper.writeValueAsString(user));

    }

}
