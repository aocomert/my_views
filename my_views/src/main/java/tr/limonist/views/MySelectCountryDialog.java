package tr.limonist.views;

import android.app.Dialog;
import android.media.MediaPlayer;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.weiwangcn.betterspinner.library.BetterSpinner;
import com.yqritc.scalablevideoview.ScalableVideoView;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import tr.limonist.extras.TransparentProgressDialog;


public class MySelectCountryDialog extends Dialog {

    private final TransparentProgressDialog pd;
    ScalableVideoView video_view;
    AppCompatActivity m_activity;
    ArrayList<CountryListItem> results = new ArrayList<>();
    BetterSpinner spinner1;
    public int selected_pos = -1;

    public MySelectCountryDialog(AppCompatActivity activity,ArrayList<CountryListItem> r) {
        super(activity, android.R.style.Theme_Black_NoTitleBar);
        m_activity = activity;
        results = r;
        pd = new TransparentProgressDialog(m_activity, "", true,0);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        setContentView(R.layout.a_my_select_country_dialog);

        video_view = findViewById(R.id.video_view);
        try {
            video_view.setRawData(R.raw.video_init);
            video_view.setVolume(0, 0);
            video_view.setLooping(true);
            video_view.prepare(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    video_view.start();
                }
            });
        } catch (IOException ioe) { }

        String[] items = new String[results.size()];
        for (int i = 0; i < results.size(); i++) {
            items[i] = results.get(i).getCountryName();
        }

        spinner1 = findViewById(R.id.spinner1);
        spinner1.setCompoundDrawables(null, null, null, null);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(m_activity, android.R.layout.simple_dropdown_item_1line, items);
        spinner1.setAdapter(adapter);
        spinner1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selected_pos = position;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dismiss();
                    }
                },250);
            }
        });

        selected_pos = -1;
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    public void setCustomDismissListener(OnDismissListener listener) {
        this.setOnDismissListener(listener);
    }

    class CountryListItem implements Serializable {

        private String countryId;
        private String countryName;
        private String countryLanguageCode;
        private String countryLanguageId;
        private String countryCurrencyId;
        private String countryCurrencySymbol;

        public CountryListItem(
                String countryId,
                String countryName,
                String countryLanguageCode,
                String countryLanguageId,
                String countryCurrencyId,
                String countryCurrencySymbol) {

            this.countryId = countryId;
            this.countryName = countryName;
            this.countryLanguageCode = countryLanguageCode;
            this.countryLanguageId = countryLanguageId;
            this.countryCurrencyId = countryCurrencyId;
            this.countryCurrencySymbol = countryCurrencySymbol;

        }

        public String getCountryId() {
            return countryId;
        }

        public String getCountryName() {
            return countryName;
        }

        public String getCountryLanguageCode() {
            return countryLanguageCode;
        }

        public String getCountryLanguageId() {
            return countryLanguageId;
        }

        public String getCountryCurrencyId() {
            return countryCurrencyId;
        }

        public String getCountryCurrencySymbol() {
            return countryCurrencySymbol;
        }
    }

}
