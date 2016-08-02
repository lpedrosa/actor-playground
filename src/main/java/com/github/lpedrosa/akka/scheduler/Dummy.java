package com.github.lpedrosa.akka.scheduler;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Dummy extends UntypedActor {
    
    private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    @Override
    public void onReceive(Object message) throws Exception {
        if (message == DummyMessage.Timeout) {
            log.info("Got timeout message!");
        } else {
            unhandled(message);
        }
    }

}
