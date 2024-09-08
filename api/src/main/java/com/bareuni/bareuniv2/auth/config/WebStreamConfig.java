package com.bareuni.bareuniv2.auth.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.RequiredArgsConstructor;

// Content-Type 'application/octet-stream' is not supported 처리를 위한 WebConfig
// 기존 방식의 이미지 로직을 위함 -> 개선 완료
@RequiredArgsConstructor
@Configuration
public class WebStreamConfig implements WebMvcConfigurer {

	private OctetStreamReadMsgConverter octetStreamReadMsgConverter;

	@Autowired
	public void WebConfig(OctetStreamReadMsgConverter octetStreamReadMsgConverter) {
		this.octetStreamReadMsgConverter = octetStreamReadMsgConverter;
	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(octetStreamReadMsgConverter);
	}
}
