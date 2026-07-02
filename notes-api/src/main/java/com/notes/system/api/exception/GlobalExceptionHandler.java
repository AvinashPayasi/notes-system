package com.notes.system.api.exception;

import com.notes.system.api.ApiResponse;
import com.notes.system.api.ApiStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoteNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleNoteNotFound(NoteNotFoundException exception){
        ApiResponse<Object> response= new ApiResponse<>(ApiStatus.ERROR, exception.getMessage());

        //Status Code: 404
        return new ResponseEntity<ApiResponse<Object>>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ApiResponse<Object>> handleInvalidCredentialsException(InvalidCredentialsException exception){
        ApiResponse<Object> response= new ApiResponse<>(ApiStatus.ERROR, exception.getMessage());

        //Status Code: 401
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UserAlreadyExistsExcpetion.class)
    public ResponseEntity<ApiResponse<Object>> handleUserAlreadyExistsException(UserAlreadyExistsExcpetion exception){
        ApiResponse<Object> response= new ApiResponse<>(ApiStatus.ERROR, exception.getMessage());

        //Status Code: 409
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse<Object>> handleBadCredentialsException(BadCredentialsException exception){
        ApiResponse<Object> response= new ApiResponse<>(ApiStatus.ERROR, "Invalid username or password");

        //Status Code: 401
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(EmptyNoteException.class)
    public ResponseEntity<ApiResponse<Object>> handleEmptyNoteException(EmptyNoteException exception){
        ApiResponse response= new ApiResponse(ApiStatus.ERROR, exception.getMessage());

        //Status Code: 400
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidNoteStateException.class)
    public ResponseEntity<ApiResponse<Object>> handleInvalidNoteStateException(InvalidNoteStateException exception){
        ApiResponse<Object> response=new ApiResponse<>(ApiStatus.ERROR, exception.getMessage());

        //Status Code: 409
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidPaginationException.class)
    public ResponseEntity<ApiResponse<Object>> handleInvalidPaginationException(InvalidPaginationException exception){
        ApiResponse<Object> response= new ApiResponse<>(ApiStatus.ERROR, exception.getMessage());
        //Status Code: 400
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception){
        ApiResponse<Object> response= new ApiResponse<>(ApiStatus.ERROR, "Validation failed", "Enter valid email or password");
        //Status Code: 400
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleNoHandlerFoundException(NoResourceFoundException exception){
        ApiResponse<Object> response= new ApiResponse<>(ApiStatus.ERROR, "The requested endpoint doesn't exist");
        //Status Code:404
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse<Object>> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception){
        ApiResponse<Object> response = new ApiResponse<>(ApiStatus.ERROR, "Path parameter 'notesId' must be an integer");
        //Status Code:400
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGeneralException(Exception exception){
        ApiResponse<Object> response= new ApiResponse<>(ApiStatus.ERROR, "Something went wrong");

        exception.printStackTrace();
        //Status Code: 500
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
