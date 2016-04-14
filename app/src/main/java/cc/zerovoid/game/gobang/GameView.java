package cc.zerovoid.game.gobang;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.zerovoid.zerovoidframe.R;

/**
 * Created by Administrator on 2016/3/21.
 */
public class GameView extends View {
    /** 屏幕的宽 */
    private static int screenWidth = Screen.screenWidth;
    /** 屏幕的高 */
    private static int screenHeight = Screen.screenHeight;
    /** 棋盘的行数 */
    public static final int ROWS_NUM = 15;
    /** 棋盘的列数 */
    public static final int COLS_NUM = 15;

    /** 电脑的棋子颜色 */
    private ChessType computerType = ChessType.BLACK;
    /** 玩家的棋子颜色 */
    private ChessType playerType = ChessType.WHITE;

    /** 棋子在棋盘的分布0为无子，1为白子,2为黑子 ，可以有一个专门维护数据的，以与界面剥离开 */
    private ChessType[][] chessMap = new ChessType[ROWS_NUM][COLS_NUM];

    private static float PADDING = ((float) (screenWidth) / (COLS_NUM - 1)) / 2;
    private static float PADDING_LEFT = ((float) (screenWidth) / (COLS_NUM - 1)) / 2;
    private static float PADDING_TOP = ((float) (screenHeight) / (ROWS_NUM - 1)) / 2;
    private static float ROW_MARGIN = (screenHeight - PADDING * 2)
            / (ROWS_NUM - 1);
    private static float COL_MARGIN = (screenWidth - PADDING * 2)
            / (COLS_NUM - 1);
    private static float MARGIN;

    // 判断游戏是否结束
    private boolean isGameOver = false;
    Paint mPaint;
    private android.content.Context mContext;

    private AiPlayer computerPlayer = new AiPlayer(chessMap,
            computerType, playerType);

    public GameView(Context context) {
        super(context);
        mContext = context;
        initBackground();
        initPaint();
        initScreenParam();
        initChess();
    }

    /** 初始化背景，棋盘的背景 */
    private void initBackground() {
        setBackgroundResource(R.drawable.gobang_bg);
    }

    /** 初始化画笔 */
    private void initPaint() {
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
    }

    private void initScreenParam() {
        PADDING_LEFT = ((screenWidth) / (COLS_NUM - 1)) / 2;
        PADDING_TOP = ((screenHeight) / (ROWS_NUM - 1)) / 2;
        PADDING = PADDING_LEFT < PADDING_TOP ? PADDING_LEFT : PADDING_TOP;
        ROW_MARGIN = ((screenHeight - PADDING * 2)) / (ROWS_NUM - 1);
        COL_MARGIN = ((screenWidth - PADDING * 2)) / (COLS_NUM - 1);
        MARGIN = ROW_MARGIN < COL_MARGIN ? ROW_MARGIN : COL_MARGIN;
        PADDING_LEFT = (screenWidth - (COLS_NUM - 1) * MARGIN) / 2;
        PADDING_TOP = (screenHeight - (ROWS_NUM - 1) * MARGIN) / 2;
    }

    /** 对棋子进行初始化 */
    public void initChess() {
        for (int i = 0; i < ROWS_NUM; i++) {
            for (int j = 0; j < COLS_NUM; j++) {
                chessMap[i][j] = ChessType.NONE;
            }
        }
        invalidate();
    }

    /** 游戏重新开始 */
    public void restartGame() {
        initChess();
        isGameOver = false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawChessBoard(canvas);
        drawChessPieces(canvas);
    }

    /** 绘制棋盘 */
    private void drawChessBoard(Canvas canvas) {

        /* 行*/
        for (int i = 0; i < ROWS_NUM; i++) {
            Log.e("Go","drawChessBoard() ROWS_NUM");
            canvas.drawLine(PADDING_LEFT, i * MARGIN + PADDING_TOP, (COLS_NUM - 1)
                    * MARGIN + PADDING_LEFT, i * MARGIN + PADDING_TOP, mPaint);
        }
        /* 列*/
        for (int i = 0; i < COLS_NUM; i++) {
            Log.e("Go","drawChessBoard() COLS_NUM");
            canvas.drawLine(PADDING_LEFT + i * MARGIN, PADDING_TOP,
                    PADDING_LEFT + i * MARGIN, MARGIN * (ROWS_NUM - 1)
                            + PADDING_TOP, mPaint);
        }
    }

    /** 绘制棋子 */
    private void drawChessPieces(Canvas canvas) {

        for (int r = 0; r < ROWS_NUM; r++) {
            for (int c = 0; c < COLS_NUM; c++) {
                Log.e("Go","drawChessPieces()");
                // System.out.print(chessMap[r][c] + " ");
                if (chessMap[r][c] == ChessType.NONE)
                    continue;
                if (chessMap[r][c] == ChessType.BLACK) {
                    mPaint.setColor(Color.BLACK);
                    canvas.drawCircle(r * MARGIN + PADDING_LEFT, c * MARGIN
                            + PADDING_TOP, MARGIN / 2, mPaint);
                } else if (chessMap[r][c] == ChessType.WHITE) {
                    mPaint.setColor(Color.WHITE);
                    canvas.drawCircle(r * MARGIN + PADDING_LEFT, c * MARGIN
                            + PADDING_TOP, MARGIN / 2, mPaint);
                }
            }
        }
    }

    /** 核心算法 */
    private boolean hasWin(int r, int c) {
        int count = 1;
        ChessType chessType = chessMap[r][c];
        // 纵向搜索
        for (int i = r + 1; i < r + 5; i++) {
            if (i >= GameView.ROWS_NUM) {
                break;
            }
            if (chessMap[i][c] == chessType) {
                count++;
            } else {
                break;
            }
        }
        for (int i = r - 1; i > r - 5; i--) {
            if (i < 0) {
                break;
            }
            if (chessMap[i][c] == chessType) {
                count++;
            } else {
                break;
            }
        }
        // System.out.println(count +" "+"1");
        if (count >= 5) {
            return true;
        }
        // 横向搜索
        count = 1;
        for (int i = c + 1; i < c + 5; i++) {
            if (i >= GameView.COLS_NUM) {
                break;
            }
            if (chessMap[r][i] == chessType) {
                count++;
            } else {
                break;
            }
        }
        for (int i = c - 1; i > c - 5; i--) {
            if (i < 0) {
                break;
            }
            if (chessMap[r][i] == chessType) {
                count++;
            } else {
                break;
            }
        }
        // System.out.println(count +" " +"2");
        if (count >= 5) {
            return true;
        }
        // 斜向"\"
        count = 1;
        for (int i = r + 1, j = c + 1; i < r + 5; i++, j++) {
            if (i >= GameView.ROWS_NUM || j >= GameView.COLS_NUM) {
                break;
            }
            if (chessMap[i][j] == chessType) {
                count++;
            } else {
                break;
            }
        }
        for (int i = r - 1, j = c - 1; i > r - 5; i--, j--) {
            if (i < 0 || j < 0) {
                break;
            }
            if (chessMap[i][j] == chessType) {
                count++;
            } else {
                break;
            }
        }
        // System.out.println(count +" " +"3");
        if (count >= 5) {
            return true;
        }
        // 斜向"/"
        count = 1;
        for (int i = r + 1, j = c - 1; i < r + 5; i++, j--) {
            if (i >= GameView.ROWS_NUM || j < 0) {
                break;
            }
            if (chessMap[i][j] == chessType) {
                count++;
            } else {
                break;
            }
        }
        for (int i = r - 1, j = c + 1; i > r - 5; i--, j++) {
            if (i < 0 || j >= GameView.COLS_NUM) {
                break;
            }
            if (chessMap[i][j] == chessType) {
                count++;
            } else {
                break;
            }
        }
        // System.out.println(count +" " +"4");
        if (count >= 5) {
            return true;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        int r = Math.round((x - PADDING_LEFT) / MARGIN);
        int c = Math.round((y - PADDING_TOP) / MARGIN);
        /*大于棋盘，是不做处理的*/
        if (!(r >= 0 && r < ROWS_NUM && c >= 0 && c < COLS_NUM)) {
            return false;
        }
        handleLayDownChessPieces(r, c);
        this.invalidate();
        return super.onTouchEvent(event);

    }

    /** 落子后的处理 */
    private void handleLayDownChessPieces(int r, int c) {
        if (isGameOver) {
            showGameOverDialog();
        } else {
            if (chessMap[r][c] == ChessType.NONE) {
                chessMap[r][c] = this.playerType;
                if (this.hasWin(r, c)) {
                    this.isGameOver = true;
                    showWinDialog();
                    return;
                }
                Point p = computerPlayer.startLayDownChessPieces();
                chessMap[p.x][p.y] = this.computerType;
                if (this.hasWin(p.x, p.y)) {
                    // 电脑胜利
                    this.isGameOver = true;
                    showFailDialog();
                }
            }
        }
    }

    /** 胜利 */
    private void showWinDialog() {
        new AlertDialog.Builder(mContext).setTitle("提示")
                .setMessage("玩家胜利").setPositiveButton("确定", null)
                .show();
    }

    /** 失败 */
    private void showFailDialog() {
        new AlertDialog.Builder(mContext).setTitle("提示")
                .setMessage("电脑胜利").setPositiveButton("确定", null)
                .show();
    }

    /** 游戏结束提示框 */
    private void showGameOverDialog() {
        new AlertDialog.Builder(mContext)
                .setTitle("提示")
                .setMessage("游戏已结束,是否重新开始?")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        restartGame();
                    }
                })
                .setNegativeButton("取消", null).show();
    }
}
