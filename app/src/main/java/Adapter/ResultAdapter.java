package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.dictionary.R;

import java.util.List;

/**
 * Created by a on 2017/5/28.
 */

public class ResultAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
   // private ImageView photoView;
    private TextView wordView;
    private List<String> dataList;
 //   private Basic basic;

    public ResultAdapter(Context context, List<String> dataList){
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.dataList = dataList;
    }
    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        view = layoutInflater.inflate(R.layout.result_list, null);
       String res = dataList.get(position);
        wordView = (TextView) view.findViewById(R.id.txt_resultword);

        wordView.setText(res);

     /*   view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, xxxxxxxxxxx.class);
                context.startActivity(intent);
            }
        });*/
        return view;
    }
}
