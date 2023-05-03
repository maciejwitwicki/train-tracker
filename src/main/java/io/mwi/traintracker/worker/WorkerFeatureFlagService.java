package io.mwi.traintracker.worker;

import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
class WorkerFeatureFlagService {

    private final AtomicInteger featureFlag = new AtomicInteger(0);

    int getFeatureFlagValue() {
        return featureFlag.get();
    }

    void setFeatureFlagValue(int value) {
        featureFlag.set(value);
    }

}
