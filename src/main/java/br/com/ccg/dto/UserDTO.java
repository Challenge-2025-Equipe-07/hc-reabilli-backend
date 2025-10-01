package br.com.ccg.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UserDTO {
    int userId;
    String name;
    String username;
    String token;
}
