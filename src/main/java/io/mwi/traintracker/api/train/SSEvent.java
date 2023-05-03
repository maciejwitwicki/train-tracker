package io.mwi.traintracker.api.train;

public record SSEvent<T>(String type, T value) {}
