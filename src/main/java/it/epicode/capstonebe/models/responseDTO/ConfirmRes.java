package it.epicode.capstonebe.models.responseDTO;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
public class ConfirmRes {

    private Timestamp   timestamp;
    private int         statusCode;
    private String      message;

    public ConfirmRes(String message, HttpStatus httpStatus) {
        this.message    = message;
        timestamp       = Timestamp.valueOf(LocalDateTime.now());
        statusCode      = httpStatus.value();
    }
}
