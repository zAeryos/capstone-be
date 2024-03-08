package it.epicode.capstonebe.models.responseDTO;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
public class DeleteRes {
    private Timestamp   timestamp;
    private int         statusCode = HttpStatus.OK.value();
    private String      message;

    public DeleteRes(String message) {
        this.message    = message;
        timestamp       = Timestamp.valueOf(LocalDateTime.now());
    }
}
