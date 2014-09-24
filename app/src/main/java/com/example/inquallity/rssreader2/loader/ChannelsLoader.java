package com.example.inquallity.rssreader2.loader;

import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;

import com.example.inquallity.rssreader2.api.RssService;
import com.example.inquallity.rssreader2.content.Channel;
import com.example.inquallity.rssreader2.content.News;

import java.util.List;

import retrofit.RestAdapter;
import retrofit.converter.SimpleXMLConverter;

public class ChannelsLoader extends CursorLoader {

    private List<Channel> mChannel;

    public ChannelsLoader(Context context) {
        super(context, Channel.URI, null, null, null, null);
    }

    @Override
    public Cursor loadInBackground() {
        final Channel channel = new RestAdapter.Builder()
                .setEndpoint("http://www.vesti.ru/")
                .setConverter(new SimpleXMLConverter())
                .build()
                .create(RssService.class)
                .feed("vesti.rss")
                .getChannel();
        final Uri uri = getContext().getContentResolver().insert(Channel.URI, channel.toValues());
        final String chanelId = uri.getLastPathSegment();
        for (final News news : channel.getNews()) {
            final ContentValues values = news.toValues();
            values.put(News.Columns.CHANNEL_ID, chanelId);
            getContext().getContentResolver().insert(News.URI, values);
        }
        return super.loadInBackground();
    }
}
