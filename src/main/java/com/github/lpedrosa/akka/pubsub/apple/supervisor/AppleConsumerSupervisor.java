package com.github.lpedrosa.akka.pubsub.apple.supervisor;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.Scheduler;
import akka.actor.UntypedActor;
import com.github.lpedrosa.akka.pubsub.apple.subscriber.AppleSubscriber;
import com.github.lpedrosa.akka.pubsub.apple.common.AppleTopicGenerator;
import com.github.lpedrosa.akka.pubsub.apple.supervisor.message.CreateChild;
import scala.concurrent.ExecutionContext;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;

import java.util.concurrent.TimeUnit;

public class AppleConsumerSupervisor extends UntypedActor {

    private final AppleTopicGenerator topicGenerator;

    private AppleConsumerSupervisor(AppleTopicGenerator topicGenerator) {
        this.topicGenerator = topicGenerator;
    }

    public static Props props(final AppleTopicGenerator topicGenerator) {
        return Props.create(AppleConsumerSupervisor.class, () -> new AppleConsumerSupervisor(topicGenerator));
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof CreateChild) {
            createChild((CreateChild) message);
        }
    }

    private void createChild(CreateChild message) {
        ActorRef consumer = getContext().actorOf(AppleSubscriber.pros(message.getAppleBrand(), this.topicGenerator));

        // make the child blow up every 5 seconds (naughty!)
        scheduleException(consumer);
    }

    private void scheduleException(ActorRef consumer) {
        final Scheduler scheduler = getContext().system().scheduler();

        final FiniteDuration duration = Duration.create(5, TimeUnit.SECONDS);
        final ExecutionContext executionContext = getContext().dispatcher();

        scheduler.schedule(duration,
                           duration,
                           consumer,
                           new Exception("Boom! Ha ha!"),
                           executionContext,
                           ActorRef.noSender());
    }

}
