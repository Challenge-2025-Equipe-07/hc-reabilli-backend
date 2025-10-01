package br.com.ccg.dto;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@ToString
public class ArticleDTO {
    int articleId;
    String name;
    Set<RelatedDTO> related;
    int userId;
}
