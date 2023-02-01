package com.example.TechnicalAnalysis.service;

import com.example.TechnicalAnalysis.entity.CE;
import com.example.TechnicalAnalysis.model.Result;
import com.example.TechnicalAnalysis.repository.CERepositoryCustom;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Service
public class CERepositoryCustomImpl implements CERepositoryCustom {

    @PersistenceContext
    private EntityManager em;
    @Override
    public List<CE> getByDate(String input) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> q = cb.createQuery(Tuple.class);
        Root<CE> sRoot = q.from(CE.class);
        Root<Result> sgRoot = q.from(Result.class);

        return null;
    }
}
