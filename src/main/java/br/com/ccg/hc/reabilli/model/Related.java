package br.com.ccg.hc.reabilli.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "T_CCG_RELATED")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Related {

    @Id
    @Column(name = "ID_RELATED")
    private int id;

    @Column(name = "DS_TYPE")
    private String type;

    @Column(name = "DS_URL")
    private String url;

    @Column(name = "DS_CONTENT")
    private String content;

    @Column(name = "ID_USER")
    private int userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "T_CCG_ARTICLE_ID_ARTICLE")
    @JsonIgnore
    private Article article;
}
