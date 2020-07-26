package com.company.docanalyzer.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
public class ApiError implements Serializable {

    private HttpStatus status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime timestamp;

    private String message;

    private String debugMessage;

    private ApiError() {
        timestamp = LocalDateTime.now();
    }

    public ApiError(HttpStatus status) {
        this();
        this.status = status;
        this.debugMessage = "";
    }

    ApiError(HttpStatus status, Throwable ex) {
        this();
        this.status = status;
        this.message = "Unexpected error";
        this.debugMessage = ex.getLocalizedMessage();
    }

    ApiError(HttpStatus status, String message, Throwable ex) {
        this();
        this.status = status;
        this.message = message;
        this.debugMessage = ex.getLocalizedMessage();
    }

}

class CustomLocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

    @Override
    public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String dateTime = jsonParser.getText();
        return LocalDateTime.parse(dateTime, formatter);
    }
}