package com.example.inquallity.rssreader2.loader;

import android.content.Context;
import android.content.CursorLoader;

import com.example.inquallity.rssreader2.content.News;

/**
 * Created by Inquallity on 23.09.2014.
 */
public class NewsLoader extends CursorLoader {

    public NewsLoader(Context context, long channelId) {
        super(context, News.URI, null, News.Columns.CHANNEL_ID + "=?", new String[]{String.valueOf(channelId)}, null);
    }
}
