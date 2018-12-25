package cn.tgw.common.exceptionHandler;

import cn.tgw.common.utils.TGWStaticString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/*
 * @Project:tgw
 * @Description:global exception handler
 * @Author:TjSanshao
 * @Create:2018-12-25 11:25
 *
 **/
@Slf4j
@ControllerAdvice(basePackages = {"cn.tgw.user.controller", "cn.tgw.order.controller"})
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public void globalExceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception e) throws IOException {
        //打印错误日志
        log.error("globalExceptionHandler, catch exception:" + e.getMessage());

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
