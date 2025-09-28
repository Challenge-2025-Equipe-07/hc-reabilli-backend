package br.com.ccg.dto;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ContentDTO {
    String name;
    Set<RelatedDTO> related;
}
