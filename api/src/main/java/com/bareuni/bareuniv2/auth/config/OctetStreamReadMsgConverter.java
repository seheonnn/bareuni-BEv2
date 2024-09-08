package com.bareuni.bareuniv2.auth.config;

import java.lang.reflect.Type;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

// Content-Type 'application/octet-stream' is not supported 처리를 위한 WebConfig
// 기존 방식의 이미지 로직을 위함 -> 개선 완료
@Component
public class OctetStreamReadMsgConverter extends AbstractJackson2HttpMessageConverter {

	@Autowired
	public OctetStreamReadMsgConverter(ObjectMapper objectMapper) {
		super(objectMapper, MediaType.APPLICATION_OCTET_STREAM);
	}

	@Override
	public boolean canWrite(Class<?> clazz, MediaType mediaType) {
		return false;
	}

	@Override
	public boolean canWrite(Type type, Class<?> clazz, MediaType mediaType) {
		return false;
	}

	@Override
	protected boolean canWrite(MediaType mediaType) {
		return false;
	}
}
