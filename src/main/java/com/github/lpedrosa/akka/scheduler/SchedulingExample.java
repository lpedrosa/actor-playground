package com.github.lpedrosa.akka.scheduler;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class SchedulingExample {

    public static final Logger LOG = LoggerFactory.getLogger(SchedulingExample.class);

    public static void main(String[] args) throws InterruptedException {
        final Config config = ConfigFactory.load();
        final ActorSystem system = ActorSystem.create("schedulingExample", config);

        final ActorRef scheduler = system.actorOf(Props.create(Scheduler.class), "scheduler");
        scheduler.tell(SchedulerMessage.Schedule, ActorRef.noSender());

        LOG.info("Sleeping for a bit...");
        TimeUnit.SECONDS.sleep(5);
        LOG.info("Dying...");
        system.terminate();
    }

}
