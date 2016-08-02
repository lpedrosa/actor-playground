package com.github.lpedrosa.akka.pubsub.apple.supervisor.message;

public final class CreateChild {
    private final String appleBrand;

    public CreateChild(String appleBrand) {
        this.appleBrand = appleBrand;
    }

    public String getAppleBrand() {
        return appleBrand;
    }
}

