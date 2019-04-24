package com.red.akkasb;


import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.pattern.Patterns;
import akka.util.Timeout;
import com.red.akkasb.actor.WorkloadActor;
import com.red.akkasb.actor.SpringExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;

import java.util.concurrent.TimeUnit;

@Component
class Runner implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ActorSystem actorSystem;

    @Autowired
    private SpringExtension springExtension;


    @Override
    public void run(String[] args) throws Exception {
        try {
            ActorRef workerActor = actorSystem.actorOf(springExtension.props("workloadActor"), "workload-actor");

            workerActor.tell(new WorkloadActor.Request(), null);
            workerActor.tell(new WorkloadActor.Request(), null);
            workerActor.tell(new WorkloadActor.Request(), null);

            FiniteDuration duration = FiniteDuration.create(1, TimeUnit.SECONDS);
            Future<Object> awaitable = Patterns.ask(workerActor, new WorkloadActor.Response(), Timeout.durationToTimeout(duration));

            logger.info("Response: " + Await.result(awaitable, duration));
        } finally {
            actorSystem.terminate();
            Await.result(actorSystem.whenTerminated(), Duration.Inf());
        }
    }
}