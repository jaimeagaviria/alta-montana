package com.altamontana.common.json;

import com.altamontana.common.Constantes;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by gaviriaj on 1/02/2017.
 */
public class CustomDateTimeSerializer extends JsonSerializer<Date> {

    public static final SimpleDateFormat FORMATTER = new SimpleDateFormat(Constantes.FORMATO_FECHA_HORA_DB);

    @Override
    public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (date == null) {
            jsonGenerator.writeNull();
        } else {
            jsonGenerator.writeString(FORMATTER.format(date));
        }
    }
}
