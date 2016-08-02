package com.github.lpedrosa.akka.pubsub.apple.subscriber.message;

import java.util.Objects;

public final class Apple {

    private final String brand;
    private final String color;

    public Apple(String brand, String color) {
        Objects.requireNonNull(brand);
        Objects.requireNonNull(color);
        this.brand = brand;
        this.color = color;
    }

    public String getBrand() {
        return brand;
    }

    public String getColor() {
        return color;
    }

    public String toTopic() {
        return "apple:" + this.brand;
    }
}
