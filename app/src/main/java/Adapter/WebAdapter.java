package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.dictionary.R;

import java.util.List;

import entity.Web;

/**
 * Created by a on 2017/5/28.
 */

public class WebAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    // private ImageView photoView;
    private TextView wordView;
    private TextView expView;
    private List<Web> dataList;
    private List<String> explist;
    private Web web;

    public WebAdapter(Context context, List<Web> dataList){
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
        view = layoutInflater.inflate(R.layout.web_result, null);
        web = dataList.get(position);

        wordView = (TextView) view.findViewById(R.id.txt_webresultword);
        expView = (TextView) view.findViewById(R.id.txt_webex);

        wordView.setText(web.getKey());
        explist = web.getValue();
        if(explist.size()>0) {
            StringBuilder web = new StringBuilder();
            for(int i = 0;i<explist.size();i++)
            {
                web.append(explist.get(i)).append(";").append("\r\n");
            }
            expView.setText(web);
        }



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
