package cn.tgw.businessman.filter;

import cn.tgw.common.utils.TGWStaticString;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
 * @Project:tgw
 * @Description:businessman settle filter
 * @Author:TjSanshao
 * @Create:2018-12-12 10:20
 *
 **/
public class BusinessmanSettledFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;

        Object settleStatus = request.getSession().getAttribute(TGWStaticString.TGW_BUSINESSMAN_SETTLE);

        if (settleStatus == null) {
            response.setContentType("application/json");
            response.getWriter().print("{\"" + TGWStaticString.TGW_RESULT_STATUS + "\":\"" + TGWStaticString.TGW_RESULT_STATUS_AUTH + "\",\"" + TGWStaticString.TGW_RESULT_MESSAGE + "\":\"SETTLE FIRST\"}");
            response.getWriter().flush();
            return;
        }

        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
