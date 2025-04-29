package com.example.myapplication;

import java.util.Date;

public class CompassData {
    private float direction;
    private float accuracy;
    private long timestamp;

    public CompassData(float direction, float accuracy) {
        this.direction = direction;
        this.accuracy = accuracy;
        this.timestamp = new Date().getTime();
    }

    public float getDirection() {
        return direction;
    }

    public float getAccuracy() {
        return accuracy;
    }

    public long getTimestamp() {
        return timestamp;
    }
} 