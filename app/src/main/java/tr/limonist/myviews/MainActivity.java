package tr.limonist.myviews;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import tr.limonist.extras.TransparentProgressDialog;
import tr.limonist.views.MyCallDialog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new TransparentProgressDialog(MainActivity.this,"",true,R.mipmap.ic_launcher).show();
    }
}
