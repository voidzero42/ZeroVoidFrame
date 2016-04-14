package cc.zerovoid.photo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;


import java.util.List;

/**
 * 获取图片用的gridview
 * @author Administrator
 *
 */
public class MyImageViewGridAdapter extends BaseAdapter {
	private List<String> paths;
	private Context context;
	DisplayMetrics display;
	private int num;

	public MyImageViewGridAdapter(Context context, List<String> paths, int num) {
		this.context = context;
		this.paths = paths;
		this.num = num;
	}

	public void resetImage(List<String> paths) {
		this.paths = paths;
	}

	@Override
	public int getCount() {
		if(paths.size()==num){
			return num;
		}
		return (paths.size() + 1);
	}

	@Override
	public Object getItem(int position) {
		return paths.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("NewApi")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (convertView == null) {
//			convertView = View.inflate(context, R.layout.item, null);
			viewHolder = new ViewHolder();
//			viewHolder.imageView = (ImageView) convertView
//					.findViewById(R.id.iv);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.imageView.setScaleType(ScaleType.FIT_XY);
		// 绑定图片原始尺寸，方便以后应用
		if (position == paths.size()) {
//			viewHolder.imageView.setImageBitmap(BitmapFactory.decodeResource(
//					context.getResources(), R.drawable.ic_add_photo));
			
			if (position == num) {
				viewHolder.imageView.setVisibility(View.GONE);
			}
		} else {
			String path=paths.get(position);
			if(path.contains("http://")){
//				ImageDownloader.getImageDownloader()
//						.download(path,viewHolder.imageView, ImageDownloader.ImageSize.IMAGE_SIZE_EXTRA_SMALL_SQUARE_PNG);
			}else {
				Bitmap bitmap = Bimp.Compression(
						path, 60, 60);
				viewHolder.imageView.setImageBitmap(bitmap);
			}
//			destoryBimap(bitmap);
		}
		return convertView;
	}
	class ViewHolder {
		ImageView imageView;
	}

}
