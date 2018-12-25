package cn.tgw.common.exceptionHandler;

import cn.tgw.common.utils.TGWStaticString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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

    @ExceptionHandler(BindException.class)
    public void businessmanControllerBindExceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception e) throws IOException {

        //打印错误日志
        log.error("businessmanControllerBindExceptionHandler, catch exception:" + e.getMessage());

        HashMap<String, Object> result = new HashMap<>();

        response.setStatus(200);

        try {
            response.setContentType("application/json");
            response.getWriter().print("{\"" + TGWStaticString.TGW_RESULT_STATUS + "\":\"" + TGWStaticString.TGW_RESULT_STATUS_FAIL + "\",\"" + TGWStaticString.TGW_RESULT_MESSAGE + "\":\"Open Time or Close Time Input Incorrect, The correct Input Format is 'HH:mm:ss'\"}");
            response.getWriter().flush();
        } catch (IOException e1) {
            response.getWriter().print("唔知乜嘢错");
            log.error(e1.getMessage());
        }
    }

    @ExceptionHandler(Exception.class)
    public void businessmanControllerExceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception e) throws IOException {

//        //打印堆栈日志到日志文件中
//        ByteArrayOutputStream buf = new ByteArrayOutputStream();
//        e.printStackTrace(new java.io.PrintWriter(buf, true));
//        String  expMessage = buf.toString();
//        try {
//            buf.close();
//        } catch (IOException ioEx) {
//            ioEx.printStackTrace();
//        }
        //打印错误日志
        log.error("BusinessmanExceptionHandler, catch exception:" + e.getMessage());

        HashMap<String, Object> result = new HashMap<>();

        response.setStatus(200);

        log.error("businessmanControllerBindExceptionHandler, catch exception:" + e.getMessage());

        try {
            response.setContentType("application/json");
            response.getWriter().print("{\"" + TGWStaticString.TGW_RESULT_STATUS + "\":\"" + TGWStaticString.TGW_RESULT_STATUS_FAIL + "\",\"" + TGWStaticString.TGW_RESULT_MESSAGE + "\":\"System Error, Please try again later\"}");
            response.getWriter().flush();
        } catch (IOException e1) {
            response.getWriter().print("唔知乜嘢错");
            log.error(e1.getMessage());
        }
    }

}
