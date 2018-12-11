package cn.tgw.user.filter;

import cn.tgw.common.utils.TGWStaticString;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
 * @Project:tgw
 * @Description:ensure user in session
 * @Author:TjSanshao
 * @Create:2018-11-30 16:07
 *
 **/
public class UserAuthenticationFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;

        Object sessionUser = request.getSession().getAttribute("user");

        if (sessionUser == null){
            response.setContentType("application/json");
            response.getWriter().print("{\"" + TGWStaticString.TGW_RESULT_STATUS + "\":\"" + TGWStaticString.TGW_RESULT_STATUS_AUTH + "\", \"" + TGWStaticString.TGW_RESULT_MESSAGE + "\":\"LOGIN FIRST\"}");
            response.getWriter().flush();
            return;
        }

        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
