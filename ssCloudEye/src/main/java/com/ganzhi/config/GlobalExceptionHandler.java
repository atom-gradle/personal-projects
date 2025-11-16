package com.ganzhi.config;

import com.ganzhi.dto.response.ApiResponse;
import com.ganzhi.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;

/**
 * 全局异常处理类
 * 处理Spring框架异常和自定义业务异常
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // ============================ Spring框架异常处理 ============================

    /**
     * 处理 @RequestBody 参数校验异常
     * 触发时机：@Valid @RequestBody 参数校验失败
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiResponse<String>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e, HttpServletRequest request) {

        String errorMessage = e.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining("; "));

        log.warn("参数校验失败 - URL: {}, 错误: {}", request.getRequestURI(), errorMessage);

        return ApiResponse.error("400", "请求参数不合法: " + errorMessage);
    }


    /**
     * 处理数据绑定异常
     * 触发时机：表单提交数据绑定到对象时失败
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiResponse<String>> handleBindException(
            BindException e, HttpServletRequest request) {

        String errorMessage = e.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining("; "));

        log.warn("数据绑定失败 - URL: {}, 错误: {}", request.getRequestURI(), errorMessage);

        return ApiResponse.error("400", "数据绑定失败: " + errorMessage);
    }

    /**
     * 处理缺少必要参数异常
     * 触发时机：@RequestParam(required = true) 参数缺失
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiResponse<String>> handleMissingServletRequestParameterException(
            MissingServletRequestParameterException e, HttpServletRequest request) {

        String message = String.format("缺少必要参数: %s", e.getParameterName());
        log.warn("缺少参数 - URL: {}, {}", request.getRequestURI(), message);

        return ApiResponse.error("400", message);
    }

    /**
     * 处理参数类型不匹配异常
     * 触发时机：如将字符串转换为数字失败
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiResponse<String>> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException e, HttpServletRequest request) {

        String message = String.format("参数类型不匹配: %s，期望类型: %s",
                e.getName(), e.getRequiredType().getSimpleName());
        log.warn("参数类型不匹配 - URL: {}, {}", request.getRequestURI(), message);

        return ApiResponse.error(400, message);
    }

    // ============================ 自定义业务异常处理 ============================

    /**
     * 处理参数校验异常
     */
    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiResponse<String>> handleValidationException(
            ValidationException e, HttpServletRequest request) {

        log.warn("业务参数校验异常 - URL: {}, code: {}, message: {}",
                request.getRequestURI(), e.getCode(), e.getMessage());

        return ApiResponse.error(e.getCode(), e.getMessage());
    }

    /**
     * 处理认证异常
     */
    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ApiResponse<String>> handleAuthenticationException(
            AuthenticationException e, HttpServletRequest request) {

        log.warn("认证失败 - URL: {}, code: {}, message: {}",
                request.getRequestURI(), e.getCode(), e.getMessage());

        return ApiResponse.error(e.getCode(), e.getMessage());
    }

    /**
     * 处理授权异常
     */
    @ExceptionHandler(AuthorizationException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<ApiResponse<String>> handleAuthorizationException(
            AuthorizationException e, HttpServletRequest request) {

        log.warn("授权失败 - URL: {}, code: {}, message: {}",
                request.getRequestURI(), e.getCode(), e.getMessage());

        return ApiResponse.error(e.getCode(), e.getMessage());
    }

    /**
     * 处理资源未找到异常
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiResponse<String>> handleResourceNotFoundException(
            ResourceNotFoundException e, HttpServletRequest request) {

        log.warn("资源未找到 - URL: {}, code: {}, message: {}",
                request.getRequestURI(), e.getCode(), e.getMessage());

        return ApiResponse.error(e.getCode(), e.getMessage());
    }

    /**
     * 处理数据重复异常
     */
    @ExceptionHandler(DuplicateDataException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ApiResponse<String>> handleDuplicateDataException(
            DuplicateDataException e, HttpServletRequest request) {

        log.warn("数据重复 - URL: {}, code: {}, message: {}",
                request.getRequestURI(), e.getCode(), e.getMessage());

        return ApiResponse.error(e.getCode(), e.getMessage());
    }

    /**
     * 处理业务逻辑异常
     */
    @ExceptionHandler(BusinessLogicException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<ApiResponse<String>> handleBusinessLogicException(
            BusinessLogicException e, HttpServletRequest request) {

        log.warn("业务逻辑异常 - URL: {}, code: {}, message: {}",
                request.getRequestURI(), e.getCode(), e.getMessage());

        return ApiResponse.error(e.getCode(), e.getMessage());
    }

    /**
     * 处理系统繁忙异常
     */
    @ExceptionHandler(SystemBusyException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public ResponseEntity<ApiResponse<String>> handleSystemBusyException(
            SystemBusyException e, HttpServletRequest request) {

        log.warn("系统繁忙 - URL: {}, code: {}, message: {}",
                request.getRequestURI(), e.getCode(), e.getMessage());

        return ApiResponse.error(e.getCode(), e.getMessage());
    }

    /**
     * 处理其他自定义业务异常（兜底处理）
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ApiResponse<String>> handleBusinessException(
            BusinessException e, HttpServletRequest request) {

        log.error("业务异常 - URL: {}, code: {}, message: {}",
                request.getRequestURI(), e.getCode(), e.getMessage());

        return ApiResponse.error(e.getCode(), e.getMessage());
    }

    // ============================ 系统异常处理 ============================

    /**
     * 处理所有未捕获的异常（兜底处理）
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ApiResponse<String>> handleException(
            Exception e, HttpServletRequest request) {

        log.error("系统异常 - URL: {}, 异常类型: {}, 异常信息: {}",
                request.getRequestURI(), e.getClass().getName(), e.getMessage(), e);

        // 生产环境返回通用错误信息，开发环境返回详细错误信息
        String message = isProduction() ?
                "系统繁忙，请稍后重试" : e.getMessage();

        return ApiResponse.error("500", message);
    }

    /**
     * 判断是否为生产环境
     */
    private boolean isProduction() {
        String env = System.getProperty("spring.profiles.active");
        return "prod".equals(env) || "production".equals(env);
    }
}