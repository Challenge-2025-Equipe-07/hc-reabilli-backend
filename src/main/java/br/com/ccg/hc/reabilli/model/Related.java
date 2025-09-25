package br.com.ccg.hc.reabilli.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Related {
    RelatedType type;
    String url;
    String description;
    String content;
}
