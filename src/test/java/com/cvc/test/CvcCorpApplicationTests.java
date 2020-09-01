package com.cvc.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.OffsetDateTime;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cvc.test.domain.model.Transfer;
import com.cvc.test.service.CVCTestService;

@RunWith(SpringRunner.class)
@SpringBootTest
class CvcCorpApplicationTests {

	@Autowired
	private CVCTestService service;
	
//	@Test
	public void testNewAccount() {
		
	}
	
	@Test
	public void testTaxCalcZeroDay() {
		Transfer transfer = new Transfer();
		
		transfer.setDateSchedule(OffsetDateTime.now());
		transfer.setTransferDate(OffsetDateTime.now());
		transfer.setValue(10d);
		
		service.calculateTax(transfer);
		
		assertEquals(3.3, transfer.getTax());
	}
	
	@Test
	public void testTaxCalcOneDay() {
		Transfer transfer = new Transfer();;
		
		transfer.setDateSchedule(OffsetDateTime.now());
		transfer.setTransferDate(OffsetDateTime.now().plusDays(1));
		transfer.setValue(15d);
		
		service.calculateTax(transfer);
		
		assertEquals(12d, transfer.getTax());
	}
	
	@Test
	public void testTaxCalcTenDays() {
		Transfer transfer = new Transfer();
		
		transfer.setDateSchedule(OffsetDateTime.now());
		transfer.setTransferDate(OffsetDateTime.now().plusDays(10));
		transfer.setValue(20d);
		
		service.calculateTax(transfer);
		
		assertEquals(120d, transfer.getTax());
	}

	@Test
	public void testTaxCalcElevenDays() {
		Transfer transfer = new Transfer();
		
		transfer.setDateSchedule(OffsetDateTime.now());
		transfer.setTransferDate(OffsetDateTime.now().plusDays(11));
		transfer.setValue(25.5);
		
		service.calculateTax(transfer);
		
		assertEquals(2.04, transfer.getTax());
	}
	
	@Test
	public void testTaxCalcTwentynDays() {
		Transfer transfer = new Transfer();
		
		transfer.setDateSchedule(OffsetDateTime.now());
		transfer.setTransferDate(OffsetDateTime.now().plusDays(20));
		transfer.setValue(30.6);
		
		service.calculateTax(transfer);
		
		assertEquals(2.448, transfer.getTax());		
	}
	
	@Test
	public void testTaxCalcTwentyOneDays() {
		Transfer transfer = new Transfer();
		
		transfer.setDateSchedule(OffsetDateTime.now());
		transfer.setTransferDate(OffsetDateTime.now().plusDays(21));
		transfer.setValue(35.7);
		
		service.calculateTax(transfer);
		
		assertEquals(2.142, transfer.getTax());				
	}
	
	@Test
	public void testTaxCalcThirtyDays() {
		Transfer transfer = new Transfer();
		
		transfer.setDateSchedule(OffsetDateTime.now());
		transfer.setTransferDate(OffsetDateTime.now().plusDays(30));
		transfer.setValue(40.8);
		
		service.calculateTax(transfer);
		
		assertEquals(2.448, transfer.getTax());			
	}
	
	@Test
	public void testTaxCalcThirtyOneDays() {
		Transfer transfer = new Transfer();
		
		transfer.setDateSchedule(OffsetDateTime.now());
		transfer.setTransferDate(OffsetDateTime.now().plusDays(31));
		transfer.setValue(45.9);
		
		service.calculateTax(transfer);
		
		assertEquals(1.836, transfer.getTax());			
	}

	@Test
	public void testTaxCalcFortyDays() {
		Transfer transfer = new Transfer();
		
		transfer.setDateSchedule(OffsetDateTime.now());
		transfer.setTransferDate(OffsetDateTime.now().plusDays(40));
		transfer.setValue(50.15);
		
		service.calculateTax(transfer);
		
		assertEquals(2.006, transfer.getTax());		
	}
	
	@Test
	public void testTaxCalcFortyOneDaysValueBelow100() {
		Transfer transfer = new Transfer();
		
		transfer.setDateSchedule(OffsetDateTime.now());
		transfer.setTransferDate(OffsetDateTime.now().plusDays(41));
		transfer.setValue(99.9);
		
		service.calculateTax(transfer);
		
		assertEquals(0d, transfer.getTax());	
	}

	@Test
	public void testTaxCalcFortyOneDaysValue100() {
		Transfer transfer = new Transfer();
		
		transfer.setDateSchedule(OffsetDateTime.now());
		transfer.setTransferDate(OffsetDateTime.now().plusDays(41));
		transfer.setValue(100d);
		
		service.calculateTax(transfer);
		
		assertEquals(0d, transfer.getTax());	
	}

	@Test
	public void testTaxCalcFortyOneDaysValueOver100() {
		Transfer transfer = new Transfer();
		
		transfer.setDateSchedule(OffsetDateTime.now());
		transfer.setTransferDate(OffsetDateTime.now().plusDays(41));
		transfer.setValue(155d);
		
		service.calculateTax(transfer);
		
		assertEquals(3.1, transfer.getTax());	
	}
}
