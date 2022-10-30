package com.icesi.edu.users.config.jackson;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.var;

import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeDeserializer extends StdDeserializer<LocalDateTime> {

    public LocalDateTimeDeserializer(){
        super(LocalDateTime.class);
    }

    @Override
    public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        String text = p.getText();
        try{
            var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'hh:mm:ss");
            return LocalDate.from(formatter.parse(text)).atStartOfDay(ZoneOffset.UTC).toLocalDateTime();

        }catch (DateTimeException dateTimeException){
            throw new InvalidFormatException(p ,"", text, LocalDateTime.class);
        }
    }
}