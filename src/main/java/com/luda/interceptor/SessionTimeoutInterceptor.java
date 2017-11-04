package com.luda.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class SessionTimeoutInterceptor implements HandlerInterceptor{
    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception
    {
    }

    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
            throws Exception
    {
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2)
            throws Exception
    {
        String contextPath = request.getContextPath();
        if (request.getSession().getAttribute("sessionInfo") == null) {
        if (isAjax(request)) {
            response.setStatus(419);
        } else {
            response.sendRedirect(request.getContextPath() + "/login");
            request.getRequestDispatcher("/WEB-INF/views/jsp/session_timeout.jsp").forward(request, response);
        }
        return false;
    }
        return true;
    }

    boolean isAjax(HttpServletRequest request)
    {
        String header = request.getHeader("X-Requested-With");
        return ((request.getHeader("X-Requested-With") != null) && ("XMLHttpRequest".equals(request.getHeader("X-Requested-With").toString()))) ||
                (request.getRequestURI().startsWith(request.getContextPath() + "/rest/"));
    }
}