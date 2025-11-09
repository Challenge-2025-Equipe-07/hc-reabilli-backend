package br.com.ccg.hc.reabilli.exception.response;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@ApplicationScoped
public class ErrorResponse {

    private String error;
    private String details;

}
