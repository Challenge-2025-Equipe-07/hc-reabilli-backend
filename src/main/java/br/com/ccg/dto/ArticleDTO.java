package br.com.ccg.dto;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ArticleDTO {
    int articleId;
    String name;
    Set<RelatedDTO> related;
}
