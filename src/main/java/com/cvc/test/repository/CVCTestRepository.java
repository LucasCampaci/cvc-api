package com.cvc.test.repository;

import java.time.OffsetDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.cvc.test.domain.model.Transfer;

@Component
public class CVCTestRepository {
	
	@PersistenceContext
	private EntityManager manager;
	
	public List<Transfer> list() {
		return manager.createQuery("from Transfer", Transfer.class).getResultList();
	}
	
	public Transfer findById(Long id) {
		return manager.find(Transfer.class, id);
	}

	@Transactional
	public Transfer save(Transfer transfer) {
		return manager.merge(transfer);
	}

	@Transactional
	public void remove(Transfer transfer) {
		transfer = findById(transfer.getId());
		manager.remove(transfer);
	}

	public List<Transfer> findTransferType(OffsetDateTime transferDate, OffsetDateTime dateSchedule, Double value) {

		return manager.createNamedQuery("findTransferType")
	    .setParameter("transferDate", transferDate)
	    .setParameter("dateSchedule", dateSchedule)
	    .setParameter("value", value).getResultList();
	}
}
