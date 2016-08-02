package com.github.lpedrosa.akka.pubsub.apple.common;

import java.util.Objects;

public class AppleTopicGenerator {
    public String toTopic(String appleBrand) {
        Objects.requireNonNull(appleBrand);

        return "apple:" + appleBrand;
    }
}
