package com.OBDMachine.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by loki on 5/15/15.
 */
public class GuageView extends View {
    public GuageView(Context context) {
        super(context);
    }

    public GuageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GuageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private int max=180;
    private int progress;



    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

        float canvasWidth = getMeasuredWidth();
        float canvasHeight = getMeasuredHeight();
        float canvasLine = (canvasHeight>canvasWidth)?canvasWidth:canvasHeight;

//        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        Paint paint = new Paint();
        paint.setAntiAlias(true);                       //设置画笔为无锯齿
        paint.setColor(Color.GREEN);                    //设置画笔颜色
        paint.setStrokeWidth((float) 2.0);              //线宽
        paint.setStyle(Paint.Style.STROKE);
        paint.setTextSize(20f);
//        RectF oval = new RectF(0,0,canvasWidth,canvasHeight*2); //半圆矩形
//        canvas.drawArc(oval, 180, 180, false, paint);


        Paint tmpPaint = new Paint(paint); //小刻度画笔对象
        tmpPaint.setStrokeWidth(1);
        tmpPaint.setTextSize(5f);
        Paint bigPaint = new Paint(paint);
        bigPaint.setStrokeWidth(2);

        int count = 180; //总刻度数

        canvas.save();

        //画刻度
        canvas.translate(canvasWidth / 2, canvasHeight - 10);
        canvas.rotate(180f);
        for(int i=0 ; i <count/2+1 ; i++){
            if(i%5 == 0){
                canvas.drawLine(canvasWidth/2-15, 0f, canvasWidth/2, 0f, paint);
                if(i%10==0){
                    canvas.drawLine(canvasWidth/2-18, 0f, canvasWidth/2, 0f, bigPaint);
                }

            }else{
                canvas.drawLine(canvasWidth/2-10,0f,canvasWidth/2,0f,tmpPaint);
            }
            canvas.rotate(360/count); //旋转画纸
        }

        canvas.restore();
        canvas.translate(canvasWidth/2-15, canvasHeight-10);
        //画数字
        for (float j=-180; j<=0; j=j+10){
            if(j%20==0) {
                canvas.drawText(String.valueOf((int) j + 180), (float) Math.cos( Math.toRadians((double) j)) * (canvasWidth/2-40), (float) Math.sin(Math.toRadians((double) j)) * (canvasWidth/2-40), paint);
            }
        }
        tmpPaint.setColor(Color.GRAY);
        tmpPaint.setStrokeWidth(4);
        canvas.drawCircle(0, 0, 7, tmpPaint);
        tmpPaint.setStyle(Paint.Style.FILL);
        tmpPaint.setColor(Color.YELLOW);
        canvas.drawCircle(0, 0, 5, tmpPaint);
        progress-=180;
        canvas.drawLine(0, 0,   (float) Math.cos( Math.toRadians((double) progress)) * (canvasWidth/2-40), (float) Math.sin(Math.toRadians((double) progress)) * (canvasWidth/2-40),  paint);



    }


    public synchronized int getMax() {
        return max;
    }

    public synchronized void setMax(int max) {
        if(max < 0){
            throw new IllegalArgumentException("max not less than 0");
        }
        this.max = max;
    }

    public synchronized int getProgress() {
        return progress;
    }

    public synchronized void setProgress(int progress) {
        if(progress < 0){
            throw new IllegalArgumentException("progress not less than 0");
        }
        if(progress > max){
            progress = max;
        }
        if(progress <= max){
            this.progress = progress;
            postInvalidate();
        }

    }
}
