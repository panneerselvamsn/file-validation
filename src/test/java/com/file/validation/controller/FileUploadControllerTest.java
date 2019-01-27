package com.file.validation.controller;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;

import com.file.validation.exception.BadRequestException;
import com.file.validation.exception.UnsupportedFormatException;
import com.file.validation.model.RecordDesc;
import com.file.validation.service.FileUploadService;

@RunWith(MockitoJUnitRunner.class)
public class FileUploadControllerTest {

	@InjectMocks
	FileUploadController fileUploadController;

	@Mock
	FileUploadService fileUploadService;

	@Test
	public void valid_csv() throws Exception {
		File csvFile = new File(this.getClass().getResource("/records.csv").getFile());
		InputStream is = new FileInputStream(csvFile);
		MockMultipartFile multipartFile = new MockMultipartFile("csv", "records.csv", "application/octet-stream", is);
		is.close();
		List<RecordDesc> records = new ArrayList<RecordDesc>();
		Mockito.when(fileUploadService.validateInput(multipartFile, "csv")).thenReturn(records);
		List<RecordDesc> result = fileUploadController.handleFileUpload(multipartFile);
		assertEquals(0, result.size());
	}

	@Test
	public void valid_xml() throws Exception {
		File xmlFile = new File(this.getClass().getResource("/records.xml").getFile());
		InputStream is = new FileInputStream(xmlFile);
		MockMultipartFile multipartFile = new MockMultipartFile("xml", "records.xml", "text/xml", is);
		is.close();
		List<RecordDesc> records = new ArrayList<RecordDesc>();
		Mockito.when(fileUploadService.validateInput(multipartFile, "xml")).thenReturn(records);
		List<RecordDesc> result = fileUploadController.handleFileUpload(multipartFile);
		assertEquals(0, result.size());
	}	
	
	
	@Test(expected = UnsupportedFormatException.class)
	public void unsupportedFormat_Input() throws Exception {
		File txtFile = new File(this.getClass().getResource("/new.txt").getFile());
		InputStream is = new FileInputStream(txtFile);
		MockMultipartFile multipartFile = new MockMultipartFile("txt", "new.txt", "null", is);
		is.close();
		List<RecordDesc> result = fileUploadController.handleFileUpload(multipartFile);
	}

	@Test(expected = BadRequestException.class)
	public void fileEmpty_Input() throws Exception {
		File txtFile = new File(this.getClass().getResource("/records_emptyFile.xml").getFile());
		InputStream is = new FileInputStream(txtFile);
		MockMultipartFile multipartFile = new MockMultipartFile("xml", "records_emptyFile.xml", "text/xml", is);
		is.close();
		List<RecordDesc> result = fileUploadController.handleFileUpload(multipartFile);
	}

}
