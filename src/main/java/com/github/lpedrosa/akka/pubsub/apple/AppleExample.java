package com.github.lpedrosa.akka.pubsub.apple;

import akka.actor.ActorSystem;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class AppleExample {

    public static void maing(String[] args) {
        // FIXME(lpedrosa) read the proper config
        Config config = ConfigFactory.load();
        ActorSystem system = ActorSystem.create("appleSystem", config);

        // TODO(lpedrosa) wire up the actors and stuff
    }

}
