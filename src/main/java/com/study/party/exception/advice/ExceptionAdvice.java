package com.study.party.exception.advice;

import com.study.party.comm.vo.CommResponseVo;
import com.study.party.exception.BadRequestException;
import com.study.party.exception.InternalServerErrorException;
import com.study.party.exception.NotFoundException;
import com.study.party.exception.UnauthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity badRequestException(HttpServletRequest request, BadRequestException e) {
        return CommResponseVo.builder().body(e.getMessage()).build().badRequest();
    }

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity internalServerErrorException(HttpServletRequest request, InternalServerErrorException e) {
        return CommResponseVo.builder().body(e.getMessage()).build().internalServerError();
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity notFoundException(HttpServletRequest request, NotFoundException e) {
        return CommResponseVo.builder().body(e.getMessage()).build().notFound();
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity unauthorizedException(HttpServletRequest request, UnauthorizedException e) {
        return CommResponseVo.builder().body(e.getMessage()).build().unauthorized();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity defaultException(HttpServletRequest request, Exception e) {
        log.error(" ::::: Exception ::::: START ::::: ");
        log.error(e.getLocalizedMessage());
        log.error(e.getMessage());
        log.error(" ::::: Exception :::::  END  ::::: ");
        e.printStackTrace();
        return CommResponseVo.builder().body("서버에서 에러가 발생하였습니다").build().internalServerError();
    }

}
