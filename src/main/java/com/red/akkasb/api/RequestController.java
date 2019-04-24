package com.red.akkasb.api;

import akka.actor.ActorSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RequestController {


    @Autowired
    private ActorSystem actorSystem;


    @RequestMapping("/request")
    public String myRequest(@RequestParam(value="id", defaultValue="123") String requestId) {
        return requestId + " ...Done";
    }
}