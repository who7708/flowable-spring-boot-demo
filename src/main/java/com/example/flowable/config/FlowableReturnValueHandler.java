package com.example.flowable.config;

import io.reactivex.Flowable;
import io.reactivex.Single;
import org.springframework.core.MethodParameter;
import org.springframework.lang.NonNull;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.context.request.async.WebAsyncUtils;
import org.springframework.web.method.support.AsyncHandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @author Chris
 * @version 1.0.0
 * @since 2021/08/21
 */
class FlowableReturnValueHandler implements AsyncHandlerMethodReturnValueHandler {
    @Override
    public boolean isAsyncReturnValue(Object returnValue, @NonNull MethodParameter returnType) {
        return returnValue != null && supportsReturnType(returnType);
    }

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return Flowable.class.isAssignableFrom(returnType.getParameterType())
                || Single.class.isAssignableFrom(returnType.getParameterType());
    }

    @Override
    public void handleReturnValue(Object returnValue,
                                  @NonNull MethodParameter returnType,
                                  @NonNull ModelAndViewContainer mavContainer,
                                  @NonNull NativeWebRequest webRequest) throws Exception {
        if (returnValue == null) {
            mavContainer.setRequestHandled(true);
        }

        if (returnValue instanceof Flowable) {
            final Flowable<?> flowable = (Flowable<?>) returnValue;
            WebAsyncUtils.getAsyncManager(webRequest)
                    .startDeferredResultProcessing(new FlowableAdapter<>(flowable), mavContainer);
        } else if (returnValue instanceof Single) {
            final Single<?> single = (Single<?>) returnValue;
            WebAsyncUtils.getAsyncManager(webRequest)
                    .startDeferredResultProcessing(new SingleAdapter<>(single), mavContainer);
        }
    }

    public static class SingleAdapter<T> extends DeferredResult<T> {
        private SingleAdapter(Single<T> singleSource) {
            singleSource.subscribe(this::setResult, this::setErrorResult);
        }
    }

    public static class FlowableAdapter<T> extends DeferredResult<T> {
        private FlowableAdapter(Flowable<T> flowableSource) {
            // flowableSource.firstOrError().subscribe(this::setResult, this::setErrorResult);
            flowableSource.subscribe(this::setResult, this::setErrorResult);
        }
    }

}
