package br.com.ccg.hc.reabilli.model;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
public class Related {
    int id;
    String type;
    String url;
    String content;
    int articleId;
    int userId;
}
