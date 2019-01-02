# file-validation

Technology stack:
	Used spring boot and swagger for the assignment

For the validations:
	Unique reference no - The "csv" file have duplicate reference no, have taken the 1st unique reference value for validation
	End balance - Both the "csv" and "xml" file are having the correct validation(start Balance (+/-) mutation = End Balance), have changed the "end balance" value for the report generations

To run the applications:
	Get the source code from the github and start the application through the command prompt by running the command
	Ex: "c:\file-validation>gradle bootRun" and open the url in the port "http://localhost:9090/swagger-ui.html"
	Upload the respective "csv/xml - records_invalidData.csv / records_invalidData.xml" file to get the failed report generation.

To check the unsupported file validation:
    Upload the "new.txt" to get the unsupportedFileFormat Exception.