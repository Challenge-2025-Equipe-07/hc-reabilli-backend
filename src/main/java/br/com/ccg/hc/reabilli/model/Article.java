package br.com.ccg.hc.reabilli.model;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@ToString
public class Article {
    int articleId;
    String name;
    Set<Related> related;
    int userId;
}
