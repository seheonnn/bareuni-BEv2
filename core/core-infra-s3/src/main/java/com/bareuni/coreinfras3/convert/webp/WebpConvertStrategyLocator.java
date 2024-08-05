package com.bareuni.coreinfras3.convert.webp;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.s3temp.convert.ImageFormat;

@Service
public class WebpConvertStrategyLocator {

	private final List<WebpConvertStrategy> webpConvertStrategy;

	public WebpConvertStrategyLocator(List<WebpConvertStrategy> webpConvertStrategy) {
		this.webpConvertStrategy = webpConvertStrategy;
	}

	public WebpConvertStrategy getStrategy(ImageFormat imageFormat) {
		return webpConvertStrategy.stream()
			.filter(service -> service.identify(imageFormat))
			.findFirst()
			.orElseThrow(IllegalArgumentException::new);
	}
}
