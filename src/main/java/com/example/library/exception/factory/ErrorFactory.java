package com.example.library.exception.factory;

import com.example.library.exception.business.ApplicationException;
import com.example.library.exception.business.UnknownExceptionClassException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;

@Component
public class ErrorFactory {

    public static Builder exceptionBuilder(ErrorMessage errorCode) {
        return new Builder(errorCode);
    }

    public static Builder exceptionBuilder(String message) {
        return new Builder(message);
    }

    public static class Builder {
        private HttpStatus status;
        private String link;
        private final String message;

        public Builder(ErrorMessage errorCode) {
            this.message = errorCode.getMessage();
        }

        public Builder(String message) {
            this.message = message;
        }

        public Builder status(HttpStatus status) {
            this.status = status;
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
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                throw new UnknownExceptionClassException();
            }

            return res;
        }
    }
}
