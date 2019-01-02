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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;

import com.file.validation.exception.UnsupportedFormatException;
import com.file.validation.model.RecordDesc;
import com.file.validation.service.FileUploadService;

@RunWith(SpringRunner.class)
@SpringBootTest
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
		ResponseEntity<List<RecordDesc>> result = fileUploadController.handleFileUpload(multipartFile);
		assertEquals(HttpStatus.OK, result.getStatusCode());
    }
    
    @Test
    public void valid_xml() throws Exception {
    	File xmlFile = new File(this.getClass().getResource("/records.xml").getFile());
		InputStream is = new FileInputStream(xmlFile);
		MockMultipartFile multipartFile = new MockMultipartFile("xml", "records.xml", "text/xml", is);
		is.close();
		List<RecordDesc> records = new ArrayList<RecordDesc>();
		Mockito.when(fileUploadService.validateInput(multipartFile,"xml")).thenReturn(records);
		ResponseEntity<List<RecordDesc>> result = fileUploadController.handleFileUpload(multipartFile);
		assertEquals(HttpStatus.OK, result.getStatusCode());
    }
    
    @Test(expected = UnsupportedFormatException.class)
    public void unsupportedFormat_Input() throws Exception {
    	File txtFile = new File(this.getClass().getResource("/new.txt").getFile());
		InputStream is = new FileInputStream(txtFile);
		MockMultipartFile multipartFile = new MockMultipartFile("txt", "new.txt", "null", is);
		is.close();
		ResponseEntity<List<RecordDesc>> result = fileUploadController.handleFileUpload(multipartFile);		
	}  
    
    @Test
    public void fileEmpty_Input() throws Exception {
    	File txtFile = new File(this.getClass().getResource("/records_emptyFile.xml").getFile());
		InputStream is = new FileInputStream(txtFile);
		MockMultipartFile multipartFile = new MockMultipartFile("xml", "records_emptyFile.xml", "text/xml", is);
		is.close();
		ResponseEntity<List<RecordDesc>> result = fileUploadController.handleFileUpload(multipartFile);		
		assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
	} 
    
   
   


}
