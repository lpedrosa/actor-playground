package com.github.lpedrosa.akka.pubsub.apple.publisher;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.cluster.pubsub.DistributedPubSub;
import akka.cluster.pubsub.DistributedPubSubMediator;
import com.github.lpedrosa.akka.pubsub.apple.common.AppleTopicGenerator;
import com.github.lpedrosa.akka.pubsub.apple.subscriber.message.Apple;

public class ApplePublisher {
    private final ActorRef mediator;
    private final AppleTopicGenerator topicGenerator;

    public ApplePublisher(ActorSystem system, AppleTopicGenerator topicGenerator) {
        this.mediator = DistributedPubSub.get(system).mediator();
        this.topicGenerator = topicGenerator;
    }

    public void publish(Apple apple) {
        String topic = topicGenerator.toTopic(apple.getBrand());
        final DistributedPubSubMediator.Publish publish = new DistributedPubSubMediator.Publish(topic, apple);
        mediator.tell(publish, ActorRef.noSender());
    }
}
