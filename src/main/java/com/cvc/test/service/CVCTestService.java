package com.cvc.test.service;

import java.time.OffsetDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cvc.test.domain.exception.CVCException;
import com.cvc.test.domain.model.TaxEnum;
import com.cvc.test.domain.model.Transfer;
import com.cvc.test.repository.CVCTestRepository;

@Service
public class CVCTestService {
	
	@Autowired
	private CVCTestRepository repository;
	
	public List<Transfer> list() {
		return repository.list();
	}
	
	public Transfer findById(Long id) {
		return repository.findById(id);
	}

	@Transactional
	public Transfer save(Transfer transfer) {
		transfer.setDateSchedule(OffsetDateTime.now());
		validateFieds(transfer);
		calculateTax(transfer);
		return repository.save(transfer);
	}

	public List<Transfer> findTransferType(OffsetDateTime transferDate, OffsetDateTime dateSchedule, Double value) {
		return repository.findTransferType(transferDate, dateSchedule, value);
	}

	private void validateFieds(Transfer transfer) {
		if(transfer.getValue() == null || transfer.getValue() < 1) {
			throw new CVCException("valueIsNotDefinedOrZero");
		}
	}

	@Transactional
	public void remove(Transfer transfer) {
		repository.remove(transfer);
	}
	
	public void calculateTax(Transfer transfer) {
		Double taxCalculeted = 0d;
		if(transfer != null) {
			int intervalDays = transfer.getTransferDate().getDayOfYear()-transfer.getDateSchedule().getDayOfYear();
			TaxEnum taxCalc = TaxEnum.getValue(intervalDays);
			
			//if different from the constants ONE_TO_TEN_DAYS or OVER_40. The rates are fixed as defined in ENUM.
			if(!taxCalc.equals(TaxEnum.ONE_TO_TEN_DAYS) && !taxCalc.equals(TaxEnum.OVER_40)) {
				taxCalculeted = taxCalc.getTax() + (transfer.getValue() * taxCalc.getTaxPercent());
			//if it is equal to constant ONE_TO_TEN_DAYS, the fee will be $ 12 multiplied by the interval of days.
			} else if(taxCalc.equals(TaxEnum.ONE_TO_TEN_DAYS))  {
				taxCalculeted = taxCalc.getTax() * intervalDays;
			//if it is equal to the constant OVER_40, the value of the transaction should be considered, 
		    //if it is above $ 100, the rate used is defined in ENUM.
			} else if(taxCalc.equals(TaxEnum.OVER_40)) {
				if(transfer.getValue() > 100) {
					taxCalculeted = transfer.getValue() * taxCalc.getTaxPercent();
				} else {
					taxCalculeted = 0d;
				}
			}
		}
		
		transfer.setTax(taxCalculeted);
	}
}
