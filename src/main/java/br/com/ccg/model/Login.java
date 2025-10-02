package br.com.ccg.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Login {
    private String username;
    private String token;
}

