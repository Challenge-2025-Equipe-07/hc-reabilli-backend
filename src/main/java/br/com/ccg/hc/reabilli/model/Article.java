package br.com.ccg.hc.reabilli.model;

import jakarta.persistence.*;
import lombok.*;
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
    @Column(name = "ID_ARTICLE")
    private int articleId;

    @Column(name = "NM_ARTICLE")
    private String name;

    @Column(name = "T_CCG_USER_ID_USER")
    private int userId;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Related> related;
}
