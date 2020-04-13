package tr.limonist.views;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewStub;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class MyWebContentDialog extends Dialog {

	ImageView img_right;
	LinearLayout top_right;
	TextView tv_title;
	LinearLayout top_left;
	ImageView img_left;
	WebView mWebView;
	Context mContext;
	String title;

	public MyWebContentDialog(Context context, String title, final String detail, boolean share) {
		super(context, android.R.style.Theme_Black_NoTitleBar);
		mContext=context;
		setContentView(R.layout.a_my_webview_dialog);

		this.title = title;
		ViewStub stub = findViewById(R.id.lay_stub);
		stub.setLayoutResource(R.layout.b_top_img_txt_emp);
		stub.inflate();

		TextView tv_title = findViewById(R.id.tv_title);
		tv_title.setTextColor(mContext.getResources().getColor(R.color.a_black11));
		tv_title.setText(title);

		ImageView img_left = findViewById(R.id.img_left);
		img_left.setImageResource(R.drawable.b_ic_prew_white);
		img_left.setColorFilter(mContext.getResources().getColor(R.color.a_black11));

		LinearLayout top_left = findViewById(R.id.top_left);
		top_left.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dismiss();
			}

		});

		if(share)
		{
			img_right = (ImageView) findViewById(R.id.img_right);
			img_right.setImageResource(R.drawable.b_ic_send_mail_white);

			top_right = (LinearLayout) findViewById(R.id.top_right);
			top_right.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {

					takeScreenshot();

				}
			});
		}
		else
		{
			top_right = (LinearLayout) findViewById(R.id.top_right);
			top_right.setOnClickListener(null);
		}

		mWebView = (WebView) findViewById(R.id.mWebView);
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.loadDataWithBaseURL("", detail, "text/html", "UTF-8", "");

	}

	private void takeScreenshot() {

		String mPath = Environment.getExternalStorageDirectory().toString() + "/" + "temp.jpg";
		Bitmap bitmap;
//		View v1 = mWebView.getRootView(); // take the view from your webview
		View v1 = mWebView; // take the view from your webview
		v1.setDrawingCacheEnabled(true);
		bitmap = Bitmap.createBitmap(v1.getDrawingCache());
		v1.setDrawingCacheEnabled(false);

		OutputStream fout = null;
		File imageFile = new File(mPath);

		try {
			fout = new FileOutputStream(imageFile);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fout);
			ByteArrayOutputStream bytes = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
			String path = MediaStore.Images.Media.insertImage(mContext.getContentResolver(), bitmap, mPath, null);
			Uri uri = Uri.parse(path);

			Intent send_img = new Intent(Intent.ACTION_SEND);
			send_img.putExtra(Intent.EXTRA_STREAM, uri);
			send_img.putExtra(Intent.EXTRA_TEXT, mContext.getString(R.string.app_name)+"\n"+title);
			send_img.setType("text/plain");
			send_img.setType("image/*");
			mContext.startActivity(Intent.createChooser(send_img, mContext.getString(R.string.s_share)));
			fout.flush();
			fout.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	@Override
	public void show() {
		super.show();
	}

	@Override
	public void dismiss() {
		super.dismiss();
	}

}
