package com.github.lpedrosa.akka.pubsub.apple.subscriber;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.cluster.pubsub.DistributedPubSub;
import akka.cluster.pubsub.DistributedPubSubMediator;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.github.lpedrosa.akka.pubsub.apple.subscriber.message.Apple;
import com.github.lpedrosa.akka.pubsub.apple.common.AppleTopicGenerator;

public class AppleSubscriber extends UntypedActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);
    private final ActorRef mediator = DistributedPubSub.get(getContext().system()).mediator();

    private final String appleBrand;
    private final AppleTopicGenerator topicGenerator;

    private AppleSubscriber(String appleBrand, AppleTopicGenerator topicGenerator) {
        this.appleBrand = appleBrand;
        this.topicGenerator = topicGenerator;
    }

    public static Props pros(final String appleBrand, final AppleTopicGenerator topicGenerator) {
        return Props.create(AppleSubscriber.class, () -> new AppleSubscriber(appleBrand, topicGenerator));
    }

    @Override
    public void preStart() throws Exception {
        // I only care about apple events of this brand
        String topic = this.topicGenerator.toTopic(this.appleBrand);
        DistributedPubSubMediator.Subscribe subscribe = new DistributedPubSubMediator.Subscribe(topic, getSelf());
        mediator.tell(subscribe, getSelf());
    }

    @Override
    public void postStop() throws Exception {
        String topic = this.topicGenerator.toTopic(this.appleBrand);
        DistributedPubSubMediator.Unsubscribe unsubscribe = new DistributedPubSubMediator.Unsubscribe(topic, getSelf());
        mediator.tell(unsubscribe, getSelf());
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Apple) {
            processApple((Apple) message);
        } else if (message instanceof Exception) {
            throw (Exception) message;
        } else {
            unhandled(message);
        }
    }

    private void processApple(Apple message) {
        log.info("I love apples of the {} brand. This one is {}!", message.getBrand(), message.getColor());
    }

}
