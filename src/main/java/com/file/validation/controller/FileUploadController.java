package com.file.validation.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.file.validation.exception.BadRequestException;
import com.file.validation.exception.UnsupportedFormatException;
import com.file.validation.model.RecordDesc;
import com.file.validation.service.FileUploadService;

import io.swagger.annotations.ApiImplicitParam;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/FileUpload")
public class FileUploadController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUploadController.class);

    private static final String xmlFormat="text/xml";
    private static final String csvFormat="application/octet-stream";

    private static final String xmlValue = "xml";
    private static final String csvValue = "csv";

    @Autowired
    FileUploadService fileUploadService;

    @CrossOrigin(origins = "http://localhost:4200")
    @ApiImplicitParam(dataType = "file", name = "records", required = true, paramType = "form")
    @PostMapping(value = "/upload", headers = "Content-Type= multipart/form-data", produces = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public List<RecordDesc> handleFileUpload(@RequestParam(value = "file", required = true) MultipartFile file) throws IOException, FileUploadException, BadRequestException {

        List<RecordDesc> reportList = null;
        LOGGER.info("FileUploadController::handleFileUpload");        

        String fileName = file.getOriginalFilename();
        String fileType = file.getContentType();
        
        LOGGER.info("FileUploadController::fileName:{}"+fileName);
        LOGGER.info("FileUploadController::fileType:{}"+fileType);

        Map<String, String> result = new HashMap<>();

        if (file.isEmpty()) {
            result.put("msg", "Empty File:{} " + fileName);
            throw new BadRequestException("Empty File Exception");
        }

        if (fileType.equalsIgnoreCase(xmlFormat)){
            reportList = fileUploadService.validateInput(file, xmlValue);
        }else if (fileType.equalsIgnoreCase(csvFormat)){
            reportList = fileUploadService.validateInput(file, csvValue);
        }else{
            throw new UnsupportedFormatException("UnsupportedFormat Exception");
        }

        LOGGER.info("FileUploadController::reportList:{}", reportList);   
        return reportList;
    }
    
    @PostMapping("/post/{firstName}/{lastName}")
    @ResponseStatus(HttpStatus.OK)
    public List<RecordDesc> getRecords(@PathVariable String firstName, @PathVariable String lastName) {
        LOGGER.info("Inside FileUploadController::getRecords:::firstName{}"+firstName);
        LOGGER.info("Inside FileUploadController::getRecords:::lastName{}"+lastName);
        List<RecordDesc> reportList = null;        
        reportList = fileUploadService.getRecords();       
        return reportList;
    }
}

