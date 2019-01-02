package com.file.validation.service;

import com.file.validation.model.Record;
import com.file.validation.model.RecordDesc;
import com.file.validation.model.Records;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class FileUploadService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUploadService.class);
    private static final NumberFormat formatter = new DecimalFormat("#0.00");
    private static final String CSV = "csv";

    public List<RecordDesc> manipulateCSV(MultipartFile file) throws IOException{
        LOGGER.info("FileUploadService:::manipulateCSV:::");

        List<RecordDesc> recordDescCSVList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            boolean firstLine = true;
            while((line = reader.readLine()) != null) {
                if(firstLine) {
                    firstLine = false;
                    continue;
                }
                String[] parts = line.split(",");
                String refNo = validateNullCheck(parts[0]);
                String accountNo = validateNullCheck(parts[1]);
                String desc = validateNullCheck(parts[2]);
                String startBalance = validateNullCheck(parts[3]);
                String mutation = validateNullCheck(parts[4]);
                String endBalance = validateNullCheck(parts[5]);

                RecordDesc recordDesc = new RecordDesc();
                recordDesc.setReference(Long.parseLong(refNo));
                recordDesc.setAccountNumber(accountNo);
                recordDesc.setDescription(desc);
                recordDesc.setStartBalance(Double.parseDouble(startBalance));
                recordDesc.setMutation(Double.parseDouble(mutation));
                recordDesc.setEndBalance(Double.parseDouble(endBalance));
                recordDescCSVList.add(recordDesc);
            }
        }
        return recordDescCSVList;
    }

    public List<RecordDesc> manipulateXML(MultipartFile file) throws IOException{
        LOGGER.info("FileUploadService:::manipulateXML:::");
        List<RecordDesc> recordDescXMLList = new ArrayList<>();
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Records.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            Records records = (Records) unmarshaller.unmarshal(file.getInputStream());

            for(Record record: records.getRecord()){
                RecordDesc recordDesc = new RecordDesc();
                recordDesc.setReference(Long.parseLong(validateNullCheck(record.getReference(), "0")));
                recordDesc.setAccountNumber(record.getAccountNumber());
                recordDesc.setDescription(record.getDescription());
                recordDesc.setStartBalance(Double.parseDouble(record.getStartBalance()));
                recordDesc.setMutation(Double.parseDouble(record.getMutation()));
                recordDesc.setEndBalance(Double.parseDouble(record.getEndBalance()));
                recordDescXMLList.add(recordDesc);
            }

        }catch (Exception e) {
            LOGGER.info("FileUploadService:::manipulateXML:::Exception", e);
        }
        return recordDescXMLList;
    }

    public List<RecordDesc> validateInput(MultipartFile file, String fileType) throws IOException {
        LOGGER.info("FileUploadService:::validateInput:::");

        List<RecordDesc> failedRecordList = new ArrayList<>();
        List<RecordDesc> recordDescList;
        Map<Long, RecordDesc> recordDescHashMap = new HashMap<>();
        if(CSV.equalsIgnoreCase(fileType)){
            recordDescList =  manipulateCSV(file);
        }else {
            recordDescList = manipulateXML(file);
        }

        //Identifying Unique reference number and adding those records in a HashMap
        for (RecordDesc recordDesc: recordDescList) {
            if (recordDescHashMap.get(recordDesc.getReference()) == null){
               recordDesc.setValid(Double.parseDouble(formatter.format((recordDesc.getStartBalance() + recordDesc.getMutation()))) == Double.parseDouble(formatter.format(recordDesc.getEndBalance())));
                recordDescHashMap.put(recordDesc.getReference(), recordDesc);
            }
        }

        //Iterating HashMap and validating the Records
        for(Iterator<Long> iterator = recordDescHashMap.keySet().iterator(); iterator.hasNext(); ){
            Long refNo = iterator.next();
            if (!recordDescHashMap.get(refNo).isValid()){
                failedRecordList.add(recordDescHashMap.get(refNo));
            }
        }
        return failedRecordList;
    }
    private String validateNullCheck(String param){
        return validateNullCheck(param, "");
    }
    private String validateNullCheck(String param, String defaultValue){
        return param == null?defaultValue:param;
    }

}
