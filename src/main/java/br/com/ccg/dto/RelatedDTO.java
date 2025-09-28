package br.com.ccg.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RelatedDTO {
    String type;
    String url;
    String description;
    String content;
}
