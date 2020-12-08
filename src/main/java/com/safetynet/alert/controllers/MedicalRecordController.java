package com.safetynet.alert.controllers;

import javax.inject.Singleton;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alert.models.MedicalRecord;
import com.safetynet.alert.services.impl.MedicalRecordServiceImpl;

@RestController
@Singleton
public class MedicalRecordController {

	private MedicalRecordServiceImpl medicalRecordService;

	private String messageStarting = "{\"message\" : \"";

	private String messageEnding = "\" }";

	public MedicalRecordController(MedicalRecordServiceImpl medicalRecordService) {
		super();
		this.medicalRecordService = medicalRecordService;
	}

	@PostMapping("/medicalRecord")
	public String post(@RequestBody MedicalRecord newMedicalRecord) {
		return messageStarting + medicalRecordService.httpPostMedicalRecord(newMedicalRecord) + messageEnding;

	}

	@PutMapping("/medicalRecord")
	public String put(@RequestBody MedicalRecord medicalRecord) {
		return messageStarting + medicalRecordService.httpPutMedicalRecord(medicalRecord) + messageEnding;

	}

	@DeleteMapping("medicalRecord/{id}")
	public String delete(@PathVariable Integer id) {
		return messageStarting + medicalRecordService.httpDeleteMedicalRecord(id) + messageEnding;

	}

}
