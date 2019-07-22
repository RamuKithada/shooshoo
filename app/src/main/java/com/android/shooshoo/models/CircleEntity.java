package com.android.shooshoo.models;

import android.graphics.PointF;

public class CircleEntity {
    PointF pointF;
    int type=0;
    int distance=0;

    public CircleEntity(PointF pointF, int type) {
        this.pointF = pointF;
        this.type = type;
    }

    public PointF getPointF() {
        return pointF;
    }

    public void setPointF(PointF pointF) {
        this.pointF = pointF;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}
