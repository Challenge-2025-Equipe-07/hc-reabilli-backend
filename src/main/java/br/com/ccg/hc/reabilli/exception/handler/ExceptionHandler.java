package br.com.ccg.hc.reabilli.exception.handler;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ExceptionHandler implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception exception) {
        return switch (exception.getClass().getSimpleName()) {
            case "NotFoundException", "NoSuchElementException" -> Response.status(Response.Status.NOT_FOUND)
                    .entity(exception.getMessage())
                    .build();
            default -> Response.status(Response.Status.INTERNAL_SERVER_ERROR).
                    entity(exception.getMessage())
                    .build();
        };
    }
}
