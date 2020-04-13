package tr.limonist.views;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import tr.limonist.extras.TransparentProgressDialog;


public class MyPdfDialog extends Dialog {

	private final TransparentProgressDialog pd;
	ImageView img_right;
	LinearLayout top_right;
	TextView tv_title;
	LinearLayout top_left;
	ImageView img_left;
	PDFView pdfView;
	Context mContext;
	String title;

	public MyPdfDialog(Context context, String title, final String detail, boolean share) {
		super(context, android.R.style.Theme_Black_NoTitleBar);
		mContext=context;

		pd = new TransparentProgressDialog(mContext, "", true,0);

		setContentView(R.layout.a_my_pdf_dialog);

		this.title = title;
		ViewStub stub = (ViewStub) findViewById(R.id.lay_stub);
		stub.setLayoutResource(R.layout.b_top_img_txt_img);
		stub.inflate();

		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_title.setText(title);
		tv_title.setTextColor(mContext.getResources().getColor(R.color.a_white11));

		img_left = (ImageView) findViewById(R.id.img_left);
		img_left.setImageResource(R.drawable.b_ic_close_white);
		img_left.setColorFilter(context.getResources().getColor(R.color.a_black11));


		top_left = (LinearLayout) findViewById(R.id.top_left);
		top_left.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
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

//					takeScreenshot();

				}
			});
		}
		else
		{
			top_right = (LinearLayout) findViewById(R.id.top_right);
			top_right.setOnClickListener(null);
		}

		pdfView = (PDFView) findViewById(R.id.pdfView);
		show();

		pd.show();
		new getPdfStream().execute(detail);

	}

	@Override
	public void show() {
		super.show();
	}

	@Override
	public void dismiss() {
		super.dismiss();
	}

	class getPdfStream extends AsyncTask<String,Void, InputStream>
	{
		@Override
		protected InputStream doInBackground(String... strings) {

			InputStream input_stream= null;

			try {
				URL url = new URL(strings[0]);
				HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
				if(urlConnection.getResponseCode() == 200)
				{
					input_stream = new BufferedInputStream(urlConnection.getInputStream());
				}
			} catch (IOException e) {
				return null;
			}
			return input_stream;
		}

		@Override
		protected void onPostExecute(InputStream ınputStream) {
			pdfView.fromStream(ınputStream).onLoad(new OnLoadCompleteListener() {
				@Override
				public void loadComplete(int nbPages) {
					if(pd!=null)
						pd.dismiss();
				}
			}).load();
		}
	}

}
