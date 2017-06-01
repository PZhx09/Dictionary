package Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.dictionary.R;
import com.example.administrator.dictionary.Result;

import java.util.List;

import entity.Vocabulary;

/**
 * Created by a on 2017/5/28.
 */

public class VocabularyAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private TextView nameView;
    private List<Vocabulary> dataList;
    private Vocabulary word;
    private String username;

    public VocabularyAdapter(Context context, List<Vocabulary> dataList,String username) {
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
        view = layoutInflater.inflate(R.layout.vol_list, null);
        word = dataList.get(position);

        nameView = (TextView) view.findViewById(R.id.txt_vol);

        nameView.setText(word.getWord());

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
