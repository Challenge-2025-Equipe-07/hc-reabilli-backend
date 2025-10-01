package br.com.ccg.dto;

import br.com.ccg.enms.RelatedType;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
public class RelatedDTO {
    int id;
    String type;
    String url;
    String content;
    int articleId;
    int userId;
}
