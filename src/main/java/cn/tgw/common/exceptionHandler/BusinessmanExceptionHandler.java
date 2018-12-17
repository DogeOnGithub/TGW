package cn.tgw.common.exceptionHandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/*
 * @Project:tgw
 * @Description:businessman controller exception handler
 * @Author:TjSanshao
 * @Create:2018-12-14 10:57
 *
 **/
@Slf4j
@ControllerAdvice(basePackages = "cn.tgw.businessman.controller")
public class BusinessmanExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String businessmanControllerExceptionHandler(HttpServletRequest request, Exception e) {

        //打印堆栈日志到日志文件中
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        e.printStackTrace(new java.io.PrintWriter(buf, true));
        String  expMessage = buf.toString();
        try {
            buf.close();
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }

        log.error("BusinessmanExceptionHandler, catch exception:" + e.getMessage() + "; detail info:" + expMessage);

        request.setAttribute("javax.servlet.error.status_code", 500);
        return "forward:/error";
    }

}
