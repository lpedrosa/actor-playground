package com.github.lpedrosa.akka.pubsub.apple;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.github.lpedrosa.akka.pubsub.apple.common.AppleTopicGenerator;
import com.github.lpedrosa.akka.pubsub.apple.publisher.ApplePublisher;
import com.github.lpedrosa.akka.pubsub.apple.supervisor.AppleConsumerSupervisor;
import com.github.lpedrosa.akka.pubsub.apple.subscriber.message.Apple;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.util.concurrent.TimeUnit;

public class AppleExample {

    public static void main(String[] args) throws Exception {
        // FIXME(lpedrosa) read the proper config
        Config config = ConfigFactory.load();
        ActorSystem system = ActorSystem.create("appleSystem", config);

        AppleTopicGenerator topicGenerator = new AppleTopicGenerator();

        final Props supProps = AppleConsumerSupervisor.props(topicGenerator);
        final ActorRef appleSup = system.actorOf(supProps, "appleSupervisor");

        final ApplePublisher applePublisher = new ApplePublisher(system, topicGenerator);

        applePublisher.publish(new Apple("granny-smith", "green"));
        applePublisher.publish(new Apple("granny-smith", "green"));
        applePublisher.publish(new Apple("pink-lady", "reddish"));
        applePublisher.publish(new Apple("pink-lady", "yellowish"));

        TimeUnit.SECONDS.sleep(10);
    }

}
