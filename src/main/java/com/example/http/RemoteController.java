package com.example.http;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jdk.jfr.ContentType;
import lombok.RequiredArgsConstructor;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RemoteController  {
    Log logger = LogFactory.getLog(RemoteController.class);
    private RMICallService rmiCallService;

    public void setRmiCallService(RMICallService rmiCallService) {
        this.rmiCallService = rmiCallService;
    }

    @RequestMapping("/rmiCall.gs")
    public String search(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        logger.debug("controller :-)");
        String keyword = httpServletRequest.getParameter("keyword");
        String result = rmiCallService.call(keyword);

        ModelAndView mv = new ModelAndView();
        mv.setViewName("result");
        return result;
    }
}
