package top.hlmy.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.hlmy.uitl.IpUtil;

import javax.servlet.http.HttpServletRequest;

@RestController
public class TestController {


    @GetMapping("/ip")
    public Object IP(HttpServletRequest request) {
        String byRequest = IpUtil.getRemoteIpByRequest(request);
        return IpUtil.getIp(byRequest);
    }
}
