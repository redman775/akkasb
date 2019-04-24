package com.red.akkasb.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RequestController {

    @RequestMapping("/request")
    public String myRequest(@RequestParam(value="id", defaultValue="123") String requestId) {
        return requestId + " ...Done";
    }
}