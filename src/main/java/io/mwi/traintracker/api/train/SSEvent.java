package io.mwi.traintracker.api.train;

record SSEvent<T>(String type, T value) {}
