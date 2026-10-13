package com.hsf302.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // 1. TÊN BEAN BẮT BUỘC PHẢI LÀ "localeResolver" (DispatcherServlet tìm chính xác theo tên này)
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        // Lần đầu tiên mở trình duyệt (chưa chọn ngôn ngữ) -> mặc định là Tiếng Việt ("vi")
        slr.setDefaultLocale(Locale.forLanguageTag("vi"));
        return slr;
    }

    // 2. Đăng ký Interceptor bắt tham số ?lang=vi hoặc ?lang=en trên mọi URL
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        registry.addInterceptor(lci);
    }
}