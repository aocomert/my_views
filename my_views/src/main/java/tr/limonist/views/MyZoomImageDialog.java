/*
 * opyright 2018 A.Orcun Cömert. All rights reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package tr.limonist.views;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


import tr.limonist.extras.TouchImageView;


public class MyZoomImageDialog extends Dialog {
    Activity m_activity;
    TouchImageView img;
    TextView title;
    LinearLayout lay_close;

    public MyZoomImageDialog(Activity activity,String message,String url,Bitmap bitmap,int image_type) {
        super(activity, android.R.style.Theme_Black_NoTitleBar);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        m_activity = activity;
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.a_my_zoom_image_dialog);

        lay_close = (LinearLayout) findViewById(R.id.lay_close);
        lay_close.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
            }

        });

        title = (TextView) findViewById(R.id.title);
        title.setText(message);

        img = (TouchImageView)findViewById(R.id.img);

        if(image_type == 1)
            new Connection().execute(url);
        else if(image_type == 0)
            img.setImageBitmap(bitmap);
        else if(image_type == 2)
        {
            img.setImageURI(Uri.fromFile(new File(url)));
        }
        img.setOnTouchImageViewListener(new TouchImageView.OnTouchImageViewListener() {

            @Override
            public void onMove() {
                PointF point = img.getScrollPosition();
                RectF rect = img.getZoomedRect();
                float currentZoom = img.getCurrentZoom();
                boolean isZoomed = img.isZoomed();

            }
        });

    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            return null;
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

    class Connection extends AsyncTask<String, Void, Bitmap> {

        protected Bitmap doInBackground(String... args) {

            Bitmap bitmap =null;

            bitmap = getBitmapFromURL(args[0]);

            return bitmap;
        }

        protected void onPostExecute(Bitmap result) {
            if(result!=null)
                img.setImageBitmap(result);
        }
    }
}
