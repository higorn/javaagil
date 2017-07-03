/*
 * File:   ValidationExceptionMapper.java
 *
 * Created on 24/06/17, 22:55
 */
package ita.coursera.javaagil4.backend.api;

import javax.validation.ValidationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * @author higor
 */
@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ValidationException> {

    public Response toResponse(ValidationException exception) {
        return Response.status(Response.Status.BAD_REQUEST).entity(":(").build();
    }
}
