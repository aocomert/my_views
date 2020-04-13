package tr.limonist.views;

import android.app.Dialog;
import android.media.MediaPlayer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.weiwangcn.betterspinner.library.BetterSpinner;
import com.yqritc.scalablevideoview.ScalableVideoView;

import java.io.IOException;
import java.util.ArrayList;

public class MyFilterDialog extends Dialog {

    AppCompatActivity m_activity;
    ArrayList<String> results = new ArrayList<>();
    public int selected_pos = -1;
    ListView list;

    public MyFilterDialog(AppCompatActivity activity, ArrayList<String> r,String title,int right_iaage_resorce) {
        super(activity, android.R.style.Theme_Black_NoTitleBar);
        m_activity = activity;
        results = r;
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        setContentView(R.layout.a_my_filter_dialog);

        TextView tv_title = findViewById(R.id.tv_title);
        tv_title.setTextColor(m_activity.getResources().getColor(R.color.a_black11));
        tv_title.setText(title);

        ImageView img_left = findViewById(R.id.img_left);
        img_left.setImageResource(R.drawable.b_ic_close_white);
        img_left.setColorFilter(m_activity.getResources().getColor(R.color.a_black11));

        ImageView img_right = findViewById(R.id.img_right);
        img_right.setImageResource(right_iaage_resorce);
        img_right.setColorFilter(m_activity.getResources().getColor(R.color.a_black11));

        LinearLayout top_left = findViewById(R.id.top_left);
        top_left.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dismiss();
            }

        });

        selected_pos = -1;

        ListView list = (ListView) findViewById(R.id.list);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                selected_pos = position;
                dismiss();
            }
        });

        lazy adapter = new lazy(results);
        list.setAdapter(adapter);

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

    public class lazy extends BaseAdapter {
        private LayoutInflater inflater = null;
        ArrayList<String> data;
        public lazy(ArrayList<String> d) {
            data=d;
            inflater = LayoutInflater.from(m_activity);
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public String getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public class ViewHolder {
            TextView title;
        }

        public View getView(final int position, View view, ViewGroup parent) {

            final ViewHolder holder;
            if (view == null) {
                holder = new ViewHolder();
                view = inflater.inflate(R.layout.a_my_filter_dialog_row, null);

                holder.title = view.findViewById(R.id.title);

                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

            holder.title.setText(data.get(position));

            return view;
        }
    }


}
