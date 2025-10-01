package br.com.ccg.dto;

import br.com.ccg.enms.RelatedType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RelatedDTO {
    RelatedType type;
    String url;
    String description;
    String content;
}
