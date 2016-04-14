package cc.zerovoid.view.drawLine;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * 绘制一条线
 * Created by zv on 2016/3/22.
 *
 * @author zv
 */
public class ZvLine extends View {
    Paint paint;
    Path path;

    public ZvLine(Context context) {
        super(context);
        initBackground();
        initPaint();
        initPath();
    }

    public ZvLine(Context context, AttributeSet attr) {
        super(context, attr);
        initBackground();
        initPaint();
        initPath();
    }

    private void initBackground() {
        setBackgroundColor(Color.YELLOW);
//        setBackgroundResource(R.drawable.gobang_bg);
    }

    private void initPaint() {
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);//STROKE
        paint.setColor(Color.BLACK);
//        paint.setStrokeWidth(1);
        paint.setAntiAlias(true);
//        PathEffect effects = new DashPathEffect(new float[]{5, 5, 5, 5}, 1);
//        paint.setPathEffect(effects);
    }

    private void initPath() {
        path = new Path();
//        path.moveTo(0, 0);/*移动画笔到屏幕向下10px的位置*/
//        path.lineTo(480, 10);/*从(0,10)->(480,10)*/
//        path.quadTo(10,10,500,500);
//        path.quadTo(25,0,25,25);
//        path.quadTo(25,50,0,50);

//        path.arcTo(new RectF(200, 200, 600, 600), -90, 0);
//
//        path.lineTo(300, 300);
//        path.arcTo(new RectF(-200, -200, 200, 200), -90, 0);
//        path.rQuadTo(50,0,80,0);
    }

    private void genHalfCirclePath(int i) {
        int length = 6;
        RectF rectF = new RectF(length - 2 * length, i * (length * 2), length, (i + 1) * (length * 2));
//        path.moveTo(0, i * length);
        path.arcTo(rectF, 0, -90);
        path.lineTo(0, 2 * length);
        path.moveTo(length, i * length + length);
        path.arcTo(rectF, 0, 90);
//        path.moveTo(0, length*2+10);
        for (int j = 0; j < 10; j++) {
            path.addPath(path, 0, ((length * 2 + (length)) * (j + 1)));
        }

    }

    private void genAntiPath() {
        int length = 30;
        RectF rectF = new RectF(0, 0, length * 2, length * 2);
        path.arcTo(rectF, 180, -90);
        path.lineTo(length, 0);
        path.lineTo(length, length * 2);
        path.arcTo(rectF, 180, 90);
//        for (int j = 0; j < 10; j++) {
//            path.addPath(path, 0, ((length * 2 + (length)) * (j + 1)));
//        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        genHalfCirclePath(0);
        genAntiPath();
        drawLine(canvas);
    }

    private void drawLine(Canvas canvas) {
        canvas.drawPath(path, paint);
    }
}
