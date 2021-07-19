package com.manage.util;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class MyFilter  implements Filter{

    public static final String COMMA = ",";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("AddressFilter is init....");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestPath = httpRequest.getRequestURI();
        System.out.println("requestPath:"+requestPath);
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        System.out.println("AddressFilter was destroyed....");
    }

}
