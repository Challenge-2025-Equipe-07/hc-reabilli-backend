package br.com.ccg.hc.reabilli.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "T_CCG_USER")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_USER")
    int userId;

    @Column(name = "NM_USER")
    @NotNull
    String name;

    @Column(name = "DS_USERNAME")
    @NotNull
    String username;

    @Column(name = "DS_TOKEN")
    @NotNull
    @JsonIgnore
    String token;
}