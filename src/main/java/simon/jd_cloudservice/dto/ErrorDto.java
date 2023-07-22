package simon.jd_cloudservice.dto;

import simon.jd_cloudservice.utils.IdGenerator;

import lombok.*;

@Data
public class ErrorDto {
    private final Integer id;
    private final String errorMessage;

    public ErrorDto(String message) {
        this.id = IdGenerator.generateId();
        this.errorMessage = message;
    }
}
