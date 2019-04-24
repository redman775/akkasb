package com.red.akkasb.actor;


import akka.persistence.AbstractPersistentActorWithTimers;
import com.red.akkasb.service.RequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component("workloadActor")
@Scope("prototype")
public class WorkloadActor extends AbstractPersistentActorWithTimers {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractPersistentActorWithTimers.class);

    @Autowired
    private RequestService requestService;

    private int myCount = 0;

    private String persistenceId;

    public WorkloadActor(String persistenceId) {
        this.persistenceId = persistenceId;
    }

    @Override
    public String persistenceId() {
        return persistenceId;

    }

    @Override
    public Receive createReceiveRecover() {
        LOG.debug("createReceiveRecover...");
        return receiveBuilder()
        .match(RequestEvent.class, (re) -> {
            myCount++;
            requestService.show(myCount);
        } ).build();
    }

    @Override
    public Receive createReceive() {
        LOG.debug("createReceive");
        return receiveBuilder()
        .match(Request.class, (r) -> {
            myCount++;
            requestService.show(myCount);
        } )
        .match(Response.class, (rs) -> {
            getSender().tell(myCount, getSelf());
        }).build();
    }

    public static class Request implements Serializable {
    }

    public static class RequestEvent implements Serializable {
    }

    public static class Response implements Serializable{
    }
}
