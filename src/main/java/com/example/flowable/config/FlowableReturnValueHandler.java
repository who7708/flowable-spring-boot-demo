package com.example.flowable.config;

import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.AsyncHandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @author Chris
 * @version 1.0.0
 * @since 2021/08/21
 */
public class FlowableReturnValueHandler implements AsyncHandlerMethodReturnValueHandler {
    @Override
    public boolean isAsyncReturnValue(Object returnValue, MethodParameter returnType) {
        return returnValue != null && supportsReturnType(returnType);
    }

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return Flowable
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {

    }
}
