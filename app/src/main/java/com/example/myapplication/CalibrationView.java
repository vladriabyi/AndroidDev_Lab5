package com.example.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class CalibrationView extends View {
    private Paint paint;
    private Paint pointPaint;
    private Paint textPaint;
    private float[] magneticField = new float[3];
    private Path calibrationPath;
    private static final int MAX_POINTS = 100;
    private float maxMagneticValue = 60.0f; // Максимальне значення для нормалізації

    public CalibrationView(Context context) {
        super(context);
        init();
    }

    public CalibrationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        paint.setColor(Color.WHITE);

        pointPaint = new Paint();
        pointPaint.setAntiAlias(true);
        pointPaint.setStyle(Paint.Style.FILL);
        pointPaint.setColor(Color.RED);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(30);

        calibrationPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();
        int centerX = width / 2;
        int centerY = height / 2;
        int radius = Math.min(width, height) / 3;

        // Малюємо сферу калібрування
        canvas.drawCircle(centerX, centerY, radius, paint);
        canvas.drawCircle(centerX, centerY, radius * 0.7f, paint);
        canvas.drawCircle(centerX, centerY, radius * 0.4f, paint);

        // Малюємо осі координат
        canvas.drawLine(centerX - radius, centerY, centerX + radius, centerY, paint);
        canvas.drawLine(centerX, centerY - radius, centerX, centerY + radius, paint);

        // Малюємо поточну точку магнітного поля
        float normalizedX = (magneticField[0] / maxMagneticValue) * radius;
        float normalizedY = (magneticField[1] / maxMagneticValue) * radius;

        float pointX = centerX + normalizedX;
        float pointY = centerY + normalizedY;
        canvas.drawCircle(pointX, pointY, 10, pointPaint);

        // Малюємо значення
        String magneticInfo = String.format("X: %.1f Y: %.1f Z: %.1f µT", 
            magneticField[0], magneticField[1], magneticField[2]);
        canvas.drawText(magneticInfo, 20, height - 40, textPaint);
    }

    public void updateMagneticField(float[] values) {
        System.arraycopy(values, 0, magneticField, 0, 3);
        
        // Оновлюємо максимальне значення якщо потрібно
        for (float value : values) {
            if (Math.abs(value) > maxMagneticValue) {
                maxMagneticValue = Math.abs(value) * 1.2f;
            }
        }
        
        invalidate();
    }
} 