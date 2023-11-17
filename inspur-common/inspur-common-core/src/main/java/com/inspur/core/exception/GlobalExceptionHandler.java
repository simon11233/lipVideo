package com.inspur.core.exception;

import com.inspur.core.web.domain.AjaxResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author simon
 */
//@ControllerAdvice
//@Slf4j
//public class GlobalExceptionHandler {
//    @ExceptionHandler(GuliException.class)
//    @ResponseBody
//    public AjaxResult error(Exception e){
//        e.printStackTrace();
//        log.error(e.getMessage());
//        return AjaxResult.error();
//    }
//
//    @ExceptionHandler(GuliException.class)
//    @ResponseBody
//    public AjaxResult error(GuliException e){
//        e.printStackTrace();
//        return AjaxResult.error(e.getMsg(),e.getCode());
//        }
//}
