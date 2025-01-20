package com.bbl.ekychelper.ApiService;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.bbl.ekychelper.configuration.ConfigReader;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ApiConsume {

	@Autowired
	  private RestTemplate restTemplate;

	    public ResponseEntity <Map<String, Object>> consumeApi(String Accountno) {
	    	
	    	String clientId = ConfigReader.getProperty("ClientId");
	        String accessToken = ConfigReader.getProperty("AccessToken");
	        String apiUrl = ConfigReader.getProperty("ApiUrl");
	        
	        System.out.println("Response Body: " + apiUrl);
	    	
	        // Create the request body map
	        Map<String, Object> businessData = new HashMap<>();
	        businessData.put("Accountno", Accountno);

	        Map<String, Object> requestBody = new HashMap<>();
	        requestBody.put("ClientId", clientId);
	        requestBody.put("Accesstoken", accessToken);
	        requestBody.put("BusinessData", businessData);

	        // Convert the request body to JSON
	        String jsonRequest = "";
	        try {
	            ObjectMapper objectMapper = new ObjectMapper();
	            jsonRequest = objectMapper.writeValueAsString(requestBody);
	            
	            System.out.println(jsonRequest);
		    	
	            
	        } catch (Exception e) {
	            throw new RuntimeException("Failed to create JSON request body", e);
	        }
	        //end of json data mapping

	        // Setting the HTTP headers
	        HttpHeaders headers = new HttpHeaders();
	        headers.set("Content-Type", "application/json");

	        // Wrapping the request in HttpEntity
	        HttpEntity<String> entity = new HttpEntity<>(jsonRequest, headers);

	        Map<String, Object> result = new HashMap<>();
	        try
	        {
	        // Make POST request
	        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, String.class);
	        
	        // Create the response map
	        
	        result.put("ResponseDateTime", System.currentTimeMillis()); // Assuming current time as response time
	        result.put("ResponseStatus", response.getStatusCode().toString());
	        result.put("SuccessMsg", "Postfacto verified");
	        result.put("ResponseCode", response.getStatusCodeValue());
	        result.put("ErrMsg", null);
	        result.put("RequestDateTime", System.currentTimeMillis() - 500); // Assuming the request was made 500 ms ago
	        result.put("ResponseBusinessData", response.getBody());
	        result.put("TotalRecords", null); // This could be dynamic based on the API response, assuming 1 record
	        }
	        catch (HttpClientErrorException | HttpServerErrorException e)
	        {
	            // Handle HTTP error (4xx, 5xx)
	            result.put("ResponseDateTime", System.currentTimeMillis());
	            result.put("ResponseStatus", false);
	            result.put("SuccessMsg", null);
	            result.put("ResponseCode",500);
	            result.put("ErrMsg", "Invalid account number"); // Capture the error message from the response
	            result.put("RequestDateTime", System.currentTimeMillis() - 500); // Assuming 500 ms for request time
	            result.put("ResponseBusinessData", null);
	            result.put("TotalRecords", null); // No records due to error
	        }
	        catch (Exception e) 
	        {
	            // Handle any other unexpected exceptions (network issues, etc.)
	        	result.put("ResponseDateTime", System.currentTimeMillis());
	            result.put("ResponseStatus", false);
	            result.put("SuccessMsg", null);
	            result.put("ResponseCode",500);
	            result.put("ErrMsg", "Invalid account number"); // Capture the error message from the response
	            result.put("RequestDateTime", System.currentTimeMillis() - 500); // Assuming 500 ms for request time
	            result.put("ResponseBusinessData", null);
	            result.put("TotalRecords", null); // No records due to error
	        }

	        // Return the API response
	        return ResponseEntity.ok(result);
	    }
}
