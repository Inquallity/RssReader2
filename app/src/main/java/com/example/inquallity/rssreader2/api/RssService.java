package com.example.inquallity.rssreader2.api;

import com.example.inquallity.rssreader2.content.Rss;

import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Inquallity on 12.09.2014.
 */
public interface RssService {

    @GET("/{id}")
    Rss feed(@Path("id") String id);


}
