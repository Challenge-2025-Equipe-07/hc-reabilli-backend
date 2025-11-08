package br.com.ccg.hc.reabilli.service;

import br.com.ccg.hc.reabilli.dao.RelatedRepository;
import br.com.ccg.hc.reabilli.model.Related;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.Set;

@ApplicationScoped
public class RelatedService {

    @Inject
    private RelatedRepository relatedRepository;

    @Transactional
    public void persist(Set<Related> entity) {
        for (Related related :entity){
            relatedRepository.persist(related);
        }
    }
}
