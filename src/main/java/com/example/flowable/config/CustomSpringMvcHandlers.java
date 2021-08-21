package com.example.flowable.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author Chris
 * @version 1.0.0
 * @since 2021/08/21
 */
@EnableWebMvc
@Configuration
public class CustomSpringMvcHandlers implements WebMvcConfigurer {
    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
        returnValueHandlers.add(new FlowableReturnValueHandler());
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // gson
        Gson gson = new GsonBuilder()
                .serializeNulls()
                .disableHtmlEscaping()
                .enableComplexMapKeySerialization()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        GsonHttpMessageConverter converter = new GsonHttpMessageConverter();
        converter.setGson(gson);

        // fastjson
        // FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        // FastJsonConfig config = new FastJsonConfig();
        // config.setDateFormat("yyyy-MM-dd HH:mm:ss");
        // // 保留空的字段
        // config.setSerializerFeatures(SerializerFeature.WriteMapNullValue,
        //         // string null -> ""
        //         SerializerFeature.WriteNullStringAsEmpty,
        //         // number null -> 0
        //         SerializerFeature.WriteNullNumberAsZero
        // );
        // converter.setFastJsonConfig(config);
        converter.setDefaultCharset(StandardCharsets.UTF_8);
        converters.add(converter);
    }
}

