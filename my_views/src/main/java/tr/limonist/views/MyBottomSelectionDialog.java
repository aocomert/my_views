package tr.limonist.views;

import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;


public class MyBottomSelectionDialog extends Dialog {

    AppCompatActivity m_activity;
    ArrayList<String> results = new ArrayList<>();
    ArrayList<String> results2 = new ArrayList<>();
    public int selected_pos = -1;
    ListView list;

    public MyBottomSelectionDialog(AppCompatActivity activity, ArrayList<String> r,ArrayList<String> r2) {
        super(activity, android.R.style.Theme_Black_NoTitleBar);
        m_activity = activity;
        results = r;
        results2 = r2;
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        setContentView(R.layout.a_my_bottom_selection_dialog);

        selected_pos = -1;

        ListView list = (ListView) findViewById(R.id.list);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                selected_pos = position;
                dismiss();
            }
        });

        lazy adapter = new lazy(results,results2,results2.size()>0?true:false);
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
        ArrayList<String> data2;
        boolean have_image = false;
        public lazy(ArrayList<String> d,ArrayList<String> d2,boolean image) {
            have_image = image;
            data=d;
            data2=d2;
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
            SimpleDraweeView img;
        }

        public View getView(final int position, View view, ViewGroup parent) {

            final ViewHolder holder;
            if (view == null) {
                holder = new ViewHolder();
                view = inflater.inflate(R.layout.a_my_bottom_selection_dialog_row, null);

                holder.img = view.findViewById(R.id.img);
                holder.title = view.findViewById(R.id.title);

                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

            holder.title.setText(data.get(position));
            if(have_image)
            {
                holder.img.setVisibility(View.VISIBLE);
                holder.img.setImageURI(results2.get(position));
            }
            else
                holder.img.setVisibility(View.GONE);

            return view;
        }
    }


}
