package br.com.ccg.hc.reabilli.dao;

import br.com.ccg.hc.reabilli.model.Article;
import br.com.ccg.hc.reabilli.model.Related;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class RelatedRepository implements PanacheRepository<Related> {

    public List<Related> findByArticle(Article article) {
        return find("article", article).list();
    }

    @Transactional
    public void deleteByArticle(Article article) {
        delete("article", article);
    }
}
