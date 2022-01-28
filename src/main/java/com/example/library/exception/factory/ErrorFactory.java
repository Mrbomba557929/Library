package com.example.library.exception.factory;

import com.example.library.exception.ApplicationException;
import com.example.library.exception.UnknownExceptionClassException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;

@Component
public class ErrorFactory {

    public static Builder exceptionBuilder(ErrorMessage errorCode) {
        return new Builder(errorCode);
    }

    public static class Builder {
        private HttpStatus status;
        private int code;
        private String link;
        private String developerMessage;
        private final String message;

        public Builder(ErrorMessage errorCode) {
            this.message = errorCode.getMessage();
        }

        public Builder status(HttpStatus status) {
            this.status = status;
            return this;
        }

        public Builder developerMessage(String developerMessage) {
            this.developerMessage = developerMessage;
            return this;
        }

        public Builder link(String link) {
            this.link = link;
            return this;
        }

        public ApplicationException build(Class<? extends ApplicationException> eClass) {
            ApplicationException res;

            try {
                res = eClass.getDeclaredConstructor().newInstance();
                res.setLink(link);
                res.setMessage(message);
                res.setStatus(status);
                res.setDeveloperMessage(developerMessage);
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                throw ErrorFactory.exceptionBuilder(ErrorMessage.UNKNOWN_EXCEPTION_CLASS)
                        .status(HttpStatus.EXPECTATION_FAILED)
                        .build(UnknownExceptionClassException.class);
            }

            return res;
        }
    }
}
