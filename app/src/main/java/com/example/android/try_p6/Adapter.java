package com.example.android.try_p6;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends BaseAdapter {

    Context context;
    List<News> list;

    public Adapter(Context context, List<News> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);

        // title
        TextView title = listItemView.findViewById(R.id.title);
        title.setText(list.get(position).getWebTitle());

        // date
        TextView date = listItemView.findViewById(R.id.date);
        date.setText(list.get(position).getWebPublicationDate());

        // section name
        TextView section_name = listItemView.findViewById(R.id.section_name);
        section_name.setText(list.get(position).getSectionName());

        // author name
        TextView author = listItemView.findViewById(R.id.author);
        author.setText(list.get(position).getAuthor());


        return listItemView;
    }
}