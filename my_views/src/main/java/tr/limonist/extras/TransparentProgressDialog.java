package tr.limonist.extras;

import android.app.Dialog;
import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import tr.limonist.views.R;

public class TransparentProgressDialog extends Dialog {

	public TransparentProgressDialog(Context context, String text, boolean cancel, int image) {
	super(context, R.style.TransparentProgressDialog);
    setContentView(R.layout.a_loading);	
    
    this.setCancelable(cancel);
    this.setCanceledOnTouchOutside(cancel);
    
    ImageView img=findViewById(R.id.img);
    img.setImageResource(image);
    
	}
	
	@Override
	public void show() {
		super.show();
	}
	
}
