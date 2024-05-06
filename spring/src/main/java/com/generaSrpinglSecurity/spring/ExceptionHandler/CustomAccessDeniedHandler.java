package com.generaSrpinglSecurity.spring.ExceptionHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
public class CustomAccessDeniedHandler extends RuntimeException implements AccessDeniedHandler {
    public CustomAccessDeniedHandler() {
    }

    public CustomAccessDeniedHandler(String message) {
        super(message);
    }

    public CustomAccessDeniedHandler(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomAccessDeniedHandler(Throwable cause) {
        super(cause);
    }

    public CustomAccessDeniedHandler(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setStatus(403);
    }
}
