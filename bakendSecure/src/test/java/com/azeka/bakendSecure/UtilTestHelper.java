package com.azeka.bakendSecure;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class UtilTestHelper {
	 


	public static String convertObjectToJson(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
         
        return ( (object !=null) ? mapper.writeValueAsString(object) : "");
    }
}
