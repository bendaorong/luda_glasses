package com.luda.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class LogInterceptor implements HandlerInterceptor
{
    private static final Logger LOG = Logger.getLogger(LogInterceptor.class);
    private ThreadLocal<Long> systime = new ThreadLocal();

    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception
    {
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object arg2, ModelAndView arg3)
            throws Exception
    {
        Long startTime = (Long)this.systime.get();
        String uri = request.getRequestURI();
        if (uri.indexOf("rest") > -1)
            LOG.info("uri:" + uri + " ;  cost time :" + (System.currentTimeMillis() - startTime.longValue()) + "ms");
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handle)
            throws Exception
    {
        this.systime.set(Long.valueOf(System.currentTimeMillis()));
        return true;
    }
}