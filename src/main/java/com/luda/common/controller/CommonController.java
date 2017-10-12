package com.luda.common.controller;

import com.luda.comm.po.ResultHandle;
import com.luda.user.exception.AdminUserException;
import com.luda.user.model.AdminUserModel;
import com.luda.user.service.AdminUserService;
import com.luda.util.CommonUtils;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class CommonController extends BaseController{
    private Logger log = Logger.getLogger(CommonController.class);

    @Autowired
    private AdminUserService adminUserService;

    @PostConstruct
    public void init() {

    }

    @RequestMapping({ "/", "/login" })
    public String showLoginPage() {
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showPersonalLoginPage(HttpSession session) {
        if (session.getAttribute("sessionInfo") == null) {
            return "login";
        }
        return "index";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        if (session.getAttribute("sessionInfo") != null) {
            session.invalidate();
        }
        return "login";
    }

    @RequestMapping("index")
    public String showIndexPage(HttpSession session) {
        if (session.getAttribute("sessionInfo") == null) {
            return "login";
        }
        return "index";
    }

    @RequestMapping(value = "/doLogin", method = RequestMethod.POST)
    public @ResponseBody String personalLogin(@RequestBody AdminUserModel loginModel, HttpSession session,
            HttpServletResponse httpServletResponse) {

        ResultHandle<AdminUserModel> resultHandle = adminUserService.login(loginModel);
        String result;
        if(resultHandle.isSuccess()){
            result = CommonUtils.convertBeanToJson(resultHandle.getReturnContent(), "yyyy-MM-dd").toString();
            session.setAttribute("sessionInfo", resultHandle.getReturnContent());
        }else {
            result = returnErrorResult(httpServletResponse, resultHandle.getMsg());
        }
        return result;
    }

    @RequestMapping("/business_operation")
    public String showBusinessOperationPage(HttpSession session,
                                            RedirectAttributes redirectAttributes) {
        return "business_operation/business_operation";
    }
}