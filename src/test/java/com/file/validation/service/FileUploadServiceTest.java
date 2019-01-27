package com.file.validation.service;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;

import com.file.validation.model.RecordDesc;

@RunWith(MockitoJUnitRunner.class)
public class FileUploadServiceTest {

	@InjectMocks
	FileUploadService fileUploadService;

	@Test
	public void valid_csv() throws Exception {
		File csvFile = new File(this.getClass().getResource("/records.csv").getFile());
		InputStream is = new FileInputStream(csvFile);
		MockMultipartFile multipartFile = new MockMultipartFile("csv", "records.csv", "application/octet-stream", is);
		is.close();
		List<RecordDesc> result = fileUploadService.validateInput(multipartFile, "csv");
		assertEquals(0, result.size());

	}

	@Test
	public void valid_xml() throws Exception {
		File xmlFile = new File(this.getClass().getResource("/records.xml").getFile());
		InputStream is = new FileInputStream(xmlFile);
		MockMultipartFile multipartFile = new MockMultipartFile("xml", "records.xml", "text/xml", is);
		is.close();
		List<RecordDesc> result = fileUploadService.validateInput(multipartFile, "xml");
		assertEquals(2, result.size());

	}

	@Test
	public void validcsv_invalidData() throws Exception {
		File csvFile = new File(this.getClass().getResource("/records_invalidData.csv").getFile());
		InputStream is = new FileInputStream(csvFile);
		MockMultipartFile multipartFile = new MockMultipartFile("csv", "records_invalidData.csv",
				"application/octet-stream", is);
		is.close();
		List<RecordDesc> result = fileUploadService.validateInput(multipartFile, "csv");
		assertEquals(2, result.size());

	}

	@Test
	public void validxml_invalidData() throws Exception {
		File xmlFile = new File(this.getClass().getResource("/records_invalidData.xml").getFile());
		InputStream is = new FileInputStream(xmlFile);
		MockMultipartFile multipartFile = new MockMultipartFile("xml", "records_invalidData.xml", "text/xml", is);
		is.close();
		List<RecordDesc> result = fileUploadService.validateInput(multipartFile, "xml");
		assertEquals(4, result.size());

	}

}
