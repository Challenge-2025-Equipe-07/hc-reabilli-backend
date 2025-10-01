package br.com.ccg.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class LoginDTO {
    private String username;
    private String token;
}

