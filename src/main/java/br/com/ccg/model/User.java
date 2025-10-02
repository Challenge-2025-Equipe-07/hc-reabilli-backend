package br.com.ccg.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class User {
    int userId;
    String name;
    String username;
    String token;
}
