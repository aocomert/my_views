package tr.limonist.views;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAlertDialog extends Dialog {

	TextView title, content;
	TextView negative, positive;
	ImageView img;

	public MyAlertDialog(Context context, int img, String title, String content, String pos_text, String neg_text,
			boolean cancel) {
		super(context, android.R.style.Theme_Black_NoTitleBar);
		getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		setContentView(R.layout.a_my_alert_dialog);

		this.setCancelable(cancel);
		this.setCanceledOnTouchOutside(cancel);

		this.img = (ImageView) findViewById(R.id.img);
		if(img!=0)
			this.img.setImageResource(img);
		else
			this.img.setVisibility(View.GONE);

		this.title = (TextView) findViewById(R.id.title);
		this.title.setText(title);

		this.content = (TextView) findViewById(R.id.content);
		this.content.setText(content);

		this.negative = (TextView) findViewById(R.id.negative);
		this.negative.setText(neg_text);

		this.positive = (TextView) findViewById(R.id.positive);
		this.positive.setText(pos_text);

	}

	public void setNegativeClicl(View.OnClickListener neg_listener) {
		this.negative.setOnClickListener(neg_listener);

	}

	public void setPositiveClicl(View.OnClickListener pos_listener) {
		this.positive.setOnClickListener(pos_listener);

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
