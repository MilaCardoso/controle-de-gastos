package com.mila.financialproject.api.services.impl;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mila.financialproject.api.dtos.TypeSumDto;
import com.mila.financialproject.api.entities.Type;
import com.mila.financialproject.api.enums.TransactionType;
import com.mila.financialproject.api.repositories.TypeRepository;
import com.mila.financialproject.api.services.TypeService;

@Service
public class TypeServiceImpl implements TypeService {

	private static final Logger log = LoggerFactory.getLogger(TypeServiceImpl.class);
	
	@Autowired
	private TypeRepository typeRepository;
	
	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public Type persist(Type type) {
		log.info("Persistindo type: {}", type);
		return this.typeRepository.save(type);
	}

	public Optional<Type> getTypeById(Long id) {
		log.info("Buscando type pelo ID {}", id);
		return Optional.ofNullable(this.typeRepository.findOne(id));
	}

	@Override
	public Optional<Type> getByType(String name) {
		log.info("Buscando name {}", name);
		return Optional.ofNullable(this.typeRepository.findByName(name));
	}
	
	public void remove(Long id) {
		log.info("Removendo type ID {}", id);
		this.typeRepository.delete(id);
	}

	@Override
	public Optional<List<Type>> getAllType() {
		log.info("Getting all registers type");
		return Optional.ofNullable(this.typeRepository.findAll());
	}
	
	public Double sumValuesByTransactionType(TransactionType transactionType, Integer mes){
	    TypedQuery<Double> query = manager.createQuery("select sum(p.valor) from Transactions p join p.tipo t "
	    		                 + "where t.transactionType = :transactionType and month(p.data) = :mes", Double.class);
	    query.setParameter("transactionType", transactionType);
	    query.setParameter("mes", mes);

	    return query.getSingleResult();
	}
	
	public List<TypeSumDto> sumValuesByType(TransactionType transactionType, Integer mes){
	   Query query = manager.createQuery("select t, sum(p.valor) from Transactions p join p.tipo t "
	    		                 + " where t.transactionType = :transactionType and month(p.data) = :mes "
	    		                 + " group by t");
	    query.setParameter("transactionType", transactionType);
	    query.setParameter("mes", mes);

	    return query.getResultList();
	}
	
}
