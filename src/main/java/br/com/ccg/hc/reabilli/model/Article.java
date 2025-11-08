package br.com.ccg.hc.reabilli.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "T_CCG_ARTICLE")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "article_seq")
    @SequenceGenerator(name = "article_seq", sequenceName = "SEQ_T_CCG_ARTICLE", allocationSize = 1)
    @Column(name = "ID_ARTICLE")
    private Integer articleId;

    @Column(name = "NM_ARTICLE")
    private String name;

    @Column(name = "T_CCG_USER_ID_USER")
    private int userId;

    @OneToMany(mappedBy = "article", fetch = FetchType.LAZY)
    private Set<Related> related;
}