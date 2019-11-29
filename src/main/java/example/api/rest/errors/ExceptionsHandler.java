package example.api.rest.errors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handleApiException(ApiException ex) {
        ErrorType errorType = ex.getErrorType();
        ErrorResponse errorResponse =  new ErrorResponse(errorType.getErrorStatus(), ex.getMessage(), ex.getMessage());
        return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getAllErrors().stream()
                .map(objectError -> objectError.getDefaultMessage()).collect(Collectors.toList());
        ErrorResponse errorResponse = new ErrorResponse(ErrorType.VALIDATION_ERRORS.getErrorStatus(),
                ErrorType.VALIDATION_ERRORS.getMessageErrorTemplate(), errors);
        return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
    }

}
