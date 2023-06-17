package simon.jd_cloudservice.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import simon.jd_cloudservice.dto.ErrorDto;
import simon.jd_cloudservice.exception.WrongDataException;

@Slf4j
@ControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler({
            MaxUploadSizeExceededException.class,
            WrongDataException.class,
            MissingServletRequestParameterException.class})
    public ResponseEntity<ErrorDto> handleClientException(Exception ex) {
        log.error(ex.getMessage());
        return ResponseEntity.badRequest().body(new ErrorDto(ex.getMessage()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorDto> handleAccessDeniedException(Exception ex) {
        log.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorDto(ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleFileProcessingException(Exception ex) {
        log.error(ex.getMessage());
        return ResponseEntity.internalServerError().body(new ErrorDto(ex.getMessage()));
    }
}
