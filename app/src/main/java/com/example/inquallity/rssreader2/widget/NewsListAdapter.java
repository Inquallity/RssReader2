package com.example.inquallity.rssreader2.widget;

import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.inquallity.rssreader2.R;
import com.example.inquallity.rssreader2.content.News;
import com.squareup.picasso.Picasso;

/**
 * Created by Inquallity on 23.09.2014.
 */
public class NewsListAdapter extends CursorAdapter {

    public NewsListAdapter(Context context) {
        super(context, null, FLAG_REGISTER_CONTENT_OBSERVER);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.li_channel, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        final TextView title = (TextView) view.findViewById(R.id.title);
        final ImageView image = (ImageView) view.findViewById(R.id.image);
        final String imageUrl = cursor.getString(cursor.getColumnIndex(News.Columns.IMAGE_URL));
        if (!TextUtils.isEmpty(imageUrl)) {
            Picasso.with(context).load(imageUrl).into(image);
        }
        title.setText(cursor.getString(cursor.getColumnIndex(News.Columns.TITLE)));
    }
}
