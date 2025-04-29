package com.example.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.animation.ValueAnimator;
import android.view.animation.LinearInterpolator;

public class CompassView extends View {
    private Paint paint;
    private Paint circlePaint;
    private Paint markerPaint;
    private Paint textPaint;
    private Paint degreePaint;
    private float direction = 0;
    private float targetDirection = 0;
    private Path northArrow;
    private ValueAnimator rotationAnimator;
    private static final int CIRCLE_COLOR = Color.rgb(50, 50, 50);
    private static final int MARKER_COLOR = Color.rgb(255, 0, 0);
    private static final String[] DIRECTIONS = {"П", "ПнС", "С", "ПдС", "Пд", "ПдЗ", "З", "ПнЗ"};
    private Paint phoneArrowPaint;
    private Path phoneArrow;

    public CompassView(Context context) {
        super(context);
        init();
    }

    public CompassView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        paint.setColor(Color.WHITE);

        circlePaint = new Paint();
        circlePaint.setAntiAlias(true);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(2);
        circlePaint.setColor(CIRCLE_COLOR);

        markerPaint = new Paint();
        markerPaint.setAntiAlias(true);
        markerPaint.setStyle(Paint.Style.FILL);
        markerPaint.setColor(MARKER_COLOR);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(40);
        textPaint.setTextAlign(Paint.Align.CENTER);

        degreePaint = new Paint();
        degreePaint.setAntiAlias(true);
        degreePaint.setStyle(Paint.Style.FILL);
        degreePaint.setColor(Color.LTGRAY);
        degreePaint.setTextSize(25);
        degreePaint.setTextAlign(Paint.Align.CENTER);

        northArrow = new Path();

        phoneArrowPaint = new Paint();
        phoneArrowPaint.setAntiAlias(true);
        phoneArrowPaint.setStyle(Paint.Style.FILL);
        phoneArrowPaint.setColor(Color.GREEN);

        phoneArrow = new Path();

        setupAnimator();
    }

    private void setupAnimator() {
        rotationAnimator = ValueAnimator.ofFloat(0, 0);
        rotationAnimator.setDuration(100);
        rotationAnimator.setInterpolator(new LinearInterpolator());
        rotationAnimator.addUpdateListener(animation -> {
            direction = (float) animation.getAnimatedValue();
            invalidate();
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();
        int centerX = width / 2;
        int centerY = height / 2;
        int radius = Math.min(width, height) / 2 - 20;

        // Draw outer circle
        canvas.drawCircle(centerX, centerY, radius, paint);
        
        // Draw inner circle
        canvas.drawCircle(centerX, centerY, radius - 20, circlePaint);

        // Draw direction markers and degree numbers
        for (int i = 0; i < 360; i += 5) { // Змінено з 15 на 5 для більшої точності
            float angle = (float) Math.toRadians(i - direction);
            int markerLength;
            
            if (i % 45 == 0) {
                markerLength = 30; // Головні напрямки
            } else if (i % 15 == 0) {
                markerLength = 20; // Проміжні напрямки
            } else {
                markerLength = 10; // Малі позначки
            }
            
            float startX = (float) (centerX + (radius - 40) * Math.sin(angle));
            float startY = (float) (centerY - (radius - 40) * Math.cos(angle));
            float endX = (float) (centerX + (radius - 40 + markerLength) * Math.sin(angle));
            float endY = (float) (centerY - (radius - 40 + markerLength) * Math.cos(angle));
            
            canvas.drawLine(startX, startY, endX, endY, circlePaint);

            // Draw degree numbers every 30 degrees
            if (i % 30 == 0) {
                float textX = (float) (centerX + (radius - 80) * Math.sin(angle));
                float textY = (float) (centerY - (radius - 80) * Math.cos(angle));
                canvas.drawText(String.valueOf(i), textX, textY, degreePaint);
            }
        }

        // Draw cardinal directions
        for (int i = 0; i < 8; i++) {
            float angle = (float) Math.toRadians(i * 45 - direction);
            float x = (float) (centerX + (radius - 100) * Math.sin(angle));
            float y = (float) (centerY - (radius - 100) * Math.cos(angle));
            
            canvas.drawText(DIRECTIONS[i], x, y + 15, textPaint);
        }

        // Draw compass arrow
        northArrow.reset();
        float arrowLength = radius - 100;
        
        // Створюємо більш складну стрілку
        northArrow.moveTo(centerX, centerY - arrowLength);
        northArrow.lineTo(centerX - 25, centerY);
        northArrow.lineTo(centerX, centerY + 30);
        northArrow.lineTo(centerX + 25, centerY);
        northArrow.close();

        canvas.save();
        canvas.rotate(-direction, centerX, centerY);
        canvas.drawPath(northArrow, markerPaint);
        
        // Додаємо обведення стрілки
        Paint strokePaint = new Paint(markerPaint);
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setStrokeWidth(2);
        strokePaint.setColor(Color.WHITE);
        canvas.drawPath(northArrow, strokePaint);
        
        canvas.restore();

        // Draw phone orientation arrow (always pointing up)
        phoneArrow.reset();
        float phoneArrowLength = radius - 50;
        phoneArrow.moveTo(centerX, centerY - phoneArrowLength);
        phoneArrow.lineTo(centerX - 15, centerY - phoneArrowLength + 30);
        phoneArrow.lineTo(centerX + 15, centerY - phoneArrowLength + 30);
        phoneArrow.close();

        canvas.drawPath(phoneArrow, phoneArrowPaint);
        
        // Add outline to phone arrow
        Paint phoneArrowStroke = new Paint(phoneArrowPaint);
        phoneArrowStroke.setStyle(Paint.Style.STROKE);
        phoneArrowStroke.setStrokeWidth(2);
        phoneArrowStroke.setColor(Color.WHITE);
        canvas.drawPath(phoneArrow, phoneArrowStroke);
    }

    public void updateDirection(float newDirection) {
        // Анімуємо поворот стрілки
        if (rotationAnimator.isRunning()) {
            rotationAnimator.cancel();
        }

        // Визначаємо найкоротший шлях повороту
        float diff = newDirection - direction;
        if (Math.abs(diff) > 180) {
            if (diff > 0) {
                diff -= 360;
            } else {
                diff += 360;
            }
        }

        rotationAnimator.setFloatValues(direction, direction + diff);
        rotationAnimator.start();
        
        this.targetDirection = newDirection;
    }
} 