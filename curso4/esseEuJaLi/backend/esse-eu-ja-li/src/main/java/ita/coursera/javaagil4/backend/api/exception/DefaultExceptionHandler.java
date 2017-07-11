/*
 * File:   ValidationExceptionMapper.java
 *
 * Created on 24/06/17, 22:55
 */
package ita.coursera.javaagil4.backend.api.exception;

import ita.coursera.javaagil4.backend.api.model.ApiResponse;

import javax.persistence.NoResultException;
import javax.security.auth.login.AccountException;
import javax.validation.ValidationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author higor
 */
@Provider
public class DefaultExceptionHandler implements ExceptionMapper<Exception> {
    private static final Logger logger = Logger.getLogger(DefaultExceptionHandler.class.getName());

    public Response toResponse(Exception exception) {
        Response.Status status = Response.Status.INTERNAL_SERVER_ERROR;
        String message = ":(";

        if (exception instanceof AccountException) {
            status = Response.Status.UNAUTHORIZED;
            message = exception.getMessage();
        } else if (exception instanceof ValidationException) {
            status = Response.Status.BAD_REQUEST;
            message = exception.getMessage();
        } else if (exception instanceof NoResultException) {
            status = Response.Status.NOT_FOUND;
        }
        logger.log(Level.SEVERE, exception.getMessage(), exception);
        return Response.status(status).
                entity(new ApiResponse<>(status.getStatusCode(), status.toString(), message))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
