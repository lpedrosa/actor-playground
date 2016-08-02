package com.github.lpedrosa.akka.scheduler;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.actor.Props;

import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;


public class Scheduler extends UntypedActor {

    private ActorRef child;

    @Override
    public void preStart() {
        this.child = getContext().actorOf(Props.create(Dummy.class), "dummy");
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message == SchedulerMessage.Schedule) {
            schedule();
        } else {
            unhandled(message);
        }
    }

    private void schedule() {
        akka.actor.Scheduler scheduler = getContext().system().scheduler();

        scheduler.scheduleOnce(Duration.create(1, TimeUnit.SECONDS), 
                this.child, 
                DummyMessage.Timeout,
                getContext().system().dispatcher(),
                ActorRef.noSender());
    }
}
