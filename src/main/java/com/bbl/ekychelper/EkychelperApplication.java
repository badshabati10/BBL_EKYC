package com.bbl.ekychelper;

import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;

import com.bbl.ekychelper.ApiService.ApiConsume;


@SpringBootApplication
public class EkychelperApplication {

	public static void main(String[] args) {
		SpringApplication.run(EkychelperApplication.class, args);
		
		
		ApiConsume apiService = new ApiConsume();
		String accountNo = "12131313";
		
		 ResponseEntity<Map<String, Object>> responseEntity = apiService.consumeApi(accountNo);

         // Retrieve and print the response body
         Map<String, Object> responseBody = responseEntity.getBody();
         
         System.out.println("Response Body: " + responseBody);
         
	}

}
