package cc.zerovoid.test;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.animation.RotateAnimation;

import com.zerovoid.zerovoidframe.R;

/**
 * 学习使用SurfaceView
 * <p/>
 * Created by 绯若虚无 on 2016/1/7 0007.
 */
public class TestSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private DrawClock drawClock;

    private int width;

    public void setScreentWidth(int width) {
        this.width = width;
    }

    public TestSurfaceView(Context context) {
        super(context);
        getHolder().addCallback(this);
    }

    public TestSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this);
    }

    /*#####callback start####*/
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        drawClock = new DrawClock(getHolder(), getResources());
        drawClock.setRunning(true);
        drawClock.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        drawClock.setRunning(false);
        while (retry) {
            try {
                drawClock.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
    }
    /*#####callback end####*/

    /*#####start#####*/
    /*#####end#####*/

    /*#####绘制线程 Thread start#####*/
    class DrawClock extends Thread {
        private boolean runFlag = false;
        private SurfaceHolder surfaceHolder;
        private Bitmap picture;
        private Matrix matrix;
        private Paint painter;
        private Paint paintText;
        private Camera camera;
        private int mDepthZ;
        private float centerX;
        private float centerY;

        public DrawClock(SurfaceHolder surfaceHolder, Resources resources) {
            this.surfaceHolder = surfaceHolder;
            //图片资源
            picture = BitmapFactory.decodeResource(resources,
                    R.drawable.ic_coin);
            //矩阵
            matrix = new Matrix();
            //画笔
            this.painter = new Paint();


            this.painter.setStyle(Paint.Style.FILL);
            //抗锯齿
            this.painter.setAntiAlias(true);
            this.painter.setFilterBitmap(true);

            this.paintText = new Paint();
            // 设置画笔颜色
            paintText.setColor(Color.BLACK);
            // 设置文字大小
            paintText.setTextSize(80);
            // 消除锯齿
            paintText.setFlags(Paint.ANTI_ALIAS_FLAG);
            camera = new Camera();
            mDepthZ = 1;
            centerX = picture.getWidth() / 2;
            centerY = picture.getHeight() / 2;
        }

        float degrees = 0;

        /**
         * 控制绘制线程是否运行
         */
        public void setRunning(boolean run) {
            runFlag = run;
        }

        @Override
        public void run() {
            Canvas canvas;
            while (runFlag) {
//                matrix.preTranslate(picture.getWidth() / 2,
//                        picture.getHeight() / 2);
//                matrix.preRotate(1.0f, picture.getWidth() / 2,
//                        picture.getHeight() / 2);
                canvas = null;
                try {
                    canvas = surfaceHolder.lockCanvas(null);
                    synchronized (surfaceHolder) {
                        float[] f = new float[9];
                        matrix.getValues(f);
                        float y = f[Matrix.MTRANS_Y];


                        camera.save();
//                        float degrees = 0 + ((180 - 0) * 1);
                        degrees = degrees + 5;
                        camera.translate(0.0f, 0.0f, mDepthZ * 1);
                        camera.rotateY(degrees);
                        camera.getMatrix(matrix);
                        camera.restore();


//                        matrix.postTranslate(0, -10);
//                        matrix.setTranslate(getWidth() / 2 - picture.getWidth() / 2, getHeight() / 2 - picture.getHeight() / 2);
                        matrix.preTranslate(-centerX, -centerY);
                        matrix.postTranslate(centerX, centerY);
                        canvas.drawColor(0, android.graphics.PorterDuff.Mode.CLEAR);
                        canvas.drawBitmap(picture, matrix, this.painter);
                        canvas.drawText("快来抢红包啊", getWidth() / 2 - 6 * 80 / 2, getHeight() / 2, paintText);
                        float[] f1 = new float[9];
                        matrix.getValues(f1);
                        float y1 = f1[Matrix.MTRANS_Y];
                        if (y1 < 10) {
                            matrix.setTranslate(getWidth() / 2 - picture.getWidth() / 2, 800);
                        }
//                        Thread.sleep(1000);
                    }
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } finally {
                    if (canvas != null) {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }

            }
        }
    }
}
    /*#####Tread end#####*/