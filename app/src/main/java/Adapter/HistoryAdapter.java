package Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.dictionary.R;
import com.example.administrator.dictionary.Result;

import java.util.List;

import entity.History;

/**
 * Created by a on 2017/5/19.
 */

public class HistoryAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private ImageView photoView;
    private TextView nameView;
    private List<History> dataList;
    private  History history;
    private String username;

    public HistoryAdapter(Context context,List<History> dataList,String username){
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.dataList = dataList;
        this.username = username;
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
    public View getView(final int position, View view, ViewGroup parent) {
        view = layoutInflater.inflate(R.layout.search_history, null);
        history = dataList.get(position);

        photoView = (ImageView) view.findViewById(R.id.imghistory);
        nameView = (TextView) view.findViewById(R.id.wordhistory);

        nameView.setText(history.getWord());

       view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Result.class);
                intent.putExtra("username",username);
                intent.putExtra("word",dataList.get(position).getWord());
                context.startActivity(intent);
            }
        });
        return view;
    }
}