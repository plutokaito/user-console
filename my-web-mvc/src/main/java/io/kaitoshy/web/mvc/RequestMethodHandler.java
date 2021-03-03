package io.kaitoshy.web.mvc;

import java.lang.reflect.Method;
import java.util.Set;

/**
 *  请求方式处理类
 *
 * @since 1.0
 */
public class RequestMethodHandler {

    private final String requestPath;

    private final Method handlerMethod;

    private final Set<String> supportedHttpMethod;

    public RequestMethodHandler(String requestPath, Method handlerMethod, Set<String> supportedHttpMethod) {
        this.requestPath = requestPath;
        this.handlerMethod = handlerMethod;
        this.supportedHttpMethod = supportedHttpMethod;
    }

    public String getRequestPath() {
        return requestPath;
    }

    public Method getHandlerMethod() {
        return handlerMethod;
    }

    public Set<String> getSupportedHttpMethod() {
        return supportedHttpMethod;
    }
}
