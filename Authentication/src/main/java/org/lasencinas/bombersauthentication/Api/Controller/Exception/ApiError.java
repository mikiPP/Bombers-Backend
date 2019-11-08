package org.lasencinas.bombersauthentication.Api.Controller.Exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
public class ApiError extends ApiModelItem {

    private String id = null;
    private String code = null;
    private int httpStatus = 0;
    private String httpErrorMessage;
    private String message;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date = null;


    public ApiError(String id, String code, HttpStatus httpStatus, String message) {
        this.id = id;
        this.code = code;
        this.date = LocalDateTime.now();
        this.httpStatus = httpStatus.value();
        this.httpErrorMessage = httpStatus.getReasonPhrase();
        this.message = message;
    }


}
