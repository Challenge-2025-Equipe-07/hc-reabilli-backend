package br.com.ccg.hc.reabilli.dao;

import br.com.ccg.hc.reabilli.model.Article;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public  class ArticleRepository implements PanacheRepository<Article> {

    public Optional<Set<Article>> findAllWithRelated() {
        return Optional.of(find("SELECT a FROM Article a LEFT JOIN FETCH a.related").stream().collect(Collectors.toSet()));
    }

    public Optional<Article> findByIdWithRelated(int id) {
        return find("SELECT a FROM Article a LEFT JOIN FETCH a.related WHERE a.articleId = ?1", id)
                .firstResultOptional();
    }
}
