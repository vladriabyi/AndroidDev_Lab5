package com.example.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class LevelView extends View {
    private Paint paint;
    private float pitch = 0;
    private float roll = 0;
    private float tilt = 0;

    public LevelView(Context context) {
        super(context);
        init();
    }

    public LevelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
    }

    public void updateOrientation(float pitch, float roll, float tilt) {
        this.pitch = pitch;
        this.roll = roll;
        this.tilt = tilt;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        
        int width = getWidth();
        int height = getHeight();
        int centerX = width / 2;
        int centerY = height / 2;

        // Draw horizon line
        paint.setColor(Color.WHITE);
        float horizonY = centerY + (pitch * height / 180);
        canvas.drawLine(0, horizonY, width, horizonY, paint);

        // Draw level indicator
        paint.setColor(Color.GREEN);
        float indicatorSize = Math.min(width, height) / 4;
        float indicatorX = centerX + (roll * width / 180);
        float indicatorY = horizonY;

        // Draw crosshair
        canvas.drawLine(indicatorX - indicatorSize, indicatorY, 
                       indicatorX + indicatorSize, indicatorY, paint);
        canvas.drawLine(indicatorX, indicatorY - indicatorSize, 
                       indicatorX, indicatorY + indicatorSize, paint);

        // Draw tilt indicator
        paint.setColor(Color.RED);
        float tiltLength = indicatorSize / 2;
        float tiltX = indicatorX + (float) (tiltLength * Math.sin(Math.toRadians(tilt)));
        float tiltY = indicatorY - (float) (tiltLength * Math.cos(Math.toRadians(tilt)));
        canvas.drawLine(indicatorX, indicatorY, tiltX, tiltY, paint);
    }
} 