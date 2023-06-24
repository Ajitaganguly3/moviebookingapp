package com.moviebookingapp.convertor;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moviebookingapp.dto.SuccessResponse;

@Component
public class HeaderConvertor implements Converter<String, SuccessResponse> {

	@Override
	public SuccessResponse convert(String source) {
		// TODO Auto-generated method stub
		ObjectMapper objectMapper = new ObjectMapper();
		SuccessResponse successResponse = null;
		try {
			successResponse = objectMapper.readValue(source, SuccessResponse.class);
			System.out.println("Source" + source);
			System.out.println("SuccessResponse" + successResponse);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return successResponse;
	}

}
