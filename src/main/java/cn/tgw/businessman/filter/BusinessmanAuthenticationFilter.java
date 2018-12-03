package cn.tgw.businessman.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
 * @Project:tgw
 * @Description:businessman authentication
 * @Author:TjSanshao
 * @Create:2018-12-03 09:43
 *
 **/
public class BusinessmanAuthenticationFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;

        Object sessionBusinessman = request.getSession().getAttribute("businessman");

        if (sessionBusinessman == null){
            response.setContentType("application/json");
            response.getWriter().print("{\"status\":\"authority\", \"message\":\"login first\"}");
            response.getWriter().flush();
            return;
        }

        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
