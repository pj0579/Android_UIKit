package com.github.xkk.android_uikit.main.nest;

import android.animation.TypeEvaluator;

public class PointTypeEvaluator implements TypeEvaluator<Point> {
    private Point point;

    public PointTypeEvaluator(Point point) {
        this.point = point;
    }

    // fraction 0->1
    @Override
    public Point evaluate(float t, Point startValue, Point endValue) {
        //贝塞尔二阶函数
        float x = (float) (Math.pow((1 - t), 2.0) * startValue.getX() + 2 * (1 - t) * t * point.getX() + Math.pow(t, 2.0) * endValue.getX()) + 80;
        float y = (float) (Math.pow((1 - t), 2.0) * startValue.getY() + 2 * (1 - t) * t * point.getY() + Math.pow(t, 2.0) * endValue.getY());
        return new Point(x, y);
    }
}
