package cc.zerovoid.photo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.zerovoid.lib.activity.BaseActivity;
import com.zerovoid.lib.util.ImageDownloader;
import com.zerovoid.zerovoidframe.R;


/**
 * 查看图片
 *
 * @author yw
 */
public class ImageActivity extends BaseActivity {
    private Intent intent;
//    private Button btnOK, btnRemove;
//    private String path;
//    private int position;
//    private ImageView image;
//    Bitmap bitmap;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_image);
//        intent = getIntent();
//        path = intent.getStringExtra("path");
//        position = intent.getIntExtra("position", 0);
//        initView();
//        findView();
//    }
//
//    private void findView() {
//        btnOK.setOnClickListener(this);
//        btnRemove.setOnClickListener(this);
//    }
//
//    private void initView() {
//        btnOK = (Button) findViewById(R.id.btn);
//        btnRemove = (Button) findViewById(R.id.btn2);
//        image = (ImageView) findViewById(R.id.iv_dialog);
//        if (path.contains("http://")) {
//            ImageDownloader.getImageDownloader()
//                    .download(path,image, ImageDownloader.ImageSize.IMAGE_SIZE_LARGE_PNG);
//        }else {
//            bitmap = Bimp.Compression(path);
//            image.setImageBitmap(bitmap);
//        }
//    }
//
//    /**
//     * 销毁图片文件
//     */
//    private void destoryBimap(Bitmap bitmap) {
//        if (bitmap != null && !bitmap.isRecycled()) {
//            bitmap.recycle();
//            bitmap = null;
//        }
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        destoryBimap(bitmap);
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.btn:
//                setResult(89);
//                finish();
//                break;
//            case R.id.btn2:
//                Intent intent = new Intent();
//                Bundle bundle = new Bundle();
//                bundle.putInt("position", position);
//                intent.putExtras(bundle);
//                setResult(88, intent);
//                finish();
//                break;
//
//            default:
//                break;
//        }
//    }
}
