package com.fefu.dns.renju;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

class RenjuFieldView extends View {
    private float lastX = 0;
    private float lastY = 0;
    private boolean isBlack = true;
    private List<StonePos.Stone_Pos> stones = new ArrayList<>();

   // public RenjuFieldView(MainActivity activity) {
     //   super(activity);


    //}

    public RenjuFieldView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private boolean SameColor(StonePos.Stone_Pos[] sps) {
        boolean c = sps[0].isBlack;
        for (int i = 1; i < sps.length; i++) {
            if (sps[i].isBlack != c)
                return false;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.rgb(101, 57, 33));
        drawField(canvas);
    }

    private void drawField(Canvas canvas) {
        int w = canvas.getWidth();
        int h = canvas.getWidth();
        int wmargin = 3 * w / 100;
        int hmargin = 3 * h / 100;
        int wf = w - 2 * wmargin;
        int hf = h - 2 * hmargin;
        int dw = wf / 14;
        int dh = hf / 14;
        Paint p = new Paint();
        p.setColor(Color.BLACK);
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(5);
        for (int i = 1; i < 14; i++) {
            canvas.drawLine(
                    wmargin + dw * i, hmargin,
                    wmargin + dw * i, h - hmargin, p);
            canvas.drawLine(wmargin, hmargin + dh * i, w - wmargin, hmargin + dh * i, p);
        }
        p.setStyle(Paint.Style.STROKE);
        canvas.drawRect(wmargin, hmargin, w - wmargin, h - hmargin, p);
        if ((lastX != 0) && (lastY != 0)) {
            int r = (w > h ? h : w) * 3 / 100;
            StonePos.Stone_Pos spNew = calcStonePos(lastX, lastY, dw, wmargin, hmargin, isBlack);
            lastX = 0;
            lastY = 0;
            isBlack = !isBlack;
            stones.add(spNew);

            for (StonePos.Stone_Pos sp : stones) {
                p.setColor(sp.isBlack ? Color.BLACK : Color.WHITE);
                canvas.drawCircle(sp.x * dw + wmargin, sp.y * dh + hmargin, r, p);
            }

        }
    }

    private StonePos.Stone_Pos calcStonePos(float x, float y, int dw, int wmargin, int hmargin, boolean isBlack) {
        return new StonePos.Stone_Pos(Math.round((x - wmargin) / dw),
                Math.round((y - hmargin) / dw), isBlack);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            lastX = event.getX();
            lastY = event.getY();
            invalidate();
            return true;
        } else return false;
    }
}
