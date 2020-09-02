package com.cvc.test.api.controller;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cvc.test.api.model.TransferModelInput;
import com.cvc.test.api.model.TransferModelOutput;
import com.cvc.test.domain.model.TaxEnum;
import com.cvc.test.domain.model.Transfer;
import com.cvc.test.service.CVCTestService;

@RestController
@RequestMapping("/transfers")
public class CVCTestController {

	@Autowired
	private CVCTestService service;

	@Autowired
	private ModelMapper modelMapper;

	@GetMapping
	public List<TransferModelOutput> list() {
		return toCollectionModel(service.list());
	} 
	
	@GetMapping("/{transferId}")
	public ResponseEntity<Transfer> findById(@PathVariable Long transferId) {
		Transfer transfer = service.findById(transferId);
		
		if(transfer != null) {
			return ResponseEntity.ok(transfer);
		}
		return ResponseEntity.notFound().build(); 
	}

	@GetMapping("/transferDate/{transferDate}/dateSchedule/{dateSchedule}/value/{value}")
	public ResponseEntity<List<TransferModelOutput>> findTranferType(@PathVariable String transferDate, 
			@PathVariable String dateSchedule, 
			@PathVariable Double value) {
		
		OffsetDateTime transferDateOffset = LocalDate.parse(transferDate).atStartOfDay(ZoneId.of(LocaleContextHolder.getTimeZone().getID())).toOffsetDateTime();
		OffsetDateTime dateScheduleOffset = LocalDate.parse(dateSchedule).atStartOfDay(ZoneId.of(LocaleContextHolder.getTimeZone().getID())).toOffsetDateTime();		
		
		List<Transfer> transfers = service.findTransferType(transferDateOffset, dateScheduleOffset, value);
				
		if(transfers != null && transfers.size() > 0) {
			return ResponseEntity.ok(toCollectionModel(transfers));
		}
		return ResponseEntity.notFound().build(); 
	}

	@PostMapping	
	@ResponseStatus(value = HttpStatus.CREATED)
	public ResponseEntity<Transfer> add(@Valid @RequestBody TransferModelInput transfer) {
		Transfer newTransfer = service.save(toEntity(transfer));
		return ResponseEntity.ok(newTransfer);
	}

	@DeleteMapping("/{transferId}")
	public ResponseEntity<Void> remove(@PathVariable Long transferId) {
		Transfer transfer = service.findById(transferId);
		
		if(transfer == null) {
			return ResponseEntity.notFound().build();
		}
		service.remove(transfer);
	
		return ResponseEntity.noContent().build();
	}
	
	private TransferModelOutput toModel(Transfer transfer) {
		return modelMapper.map(transfer, TransferModelOutput.class);
	}

	private List<TransferModelOutput> toCollectionModel(List<Transfer> transfers) {
		List<TransferModelOutput> transfersModelOutput = new ArrayList<TransferModelOutput>();
		for (Transfer transfer : transfers) {
			int intervalDays = transfer.getTransferDate().getDayOfYear()-transfer.getDateSchedule().getDayOfYear();
			TaxEnum taxCalc = TaxEnum.getValue(intervalDays);
			
		 TransferModelOutput model = toModel(transfer);
		 model.setType(taxCalc.getType());
		 
		 transfersModelOutput.add(model);
			
		}
		return transfersModelOutput;
	}

	private Transfer toEntity(TransferModelInput transferModelInput) {
		return modelMapper.map(transferModelInput, Transfer.class);
	}
}
