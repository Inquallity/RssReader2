package com.example.inquallity.rssreader2.content;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Inquallity on 12.09.2014.
 */
@Root(name = "rss", strict = false)
public class Rss {

    @Element(name = "channel")
    Channel channel;

    public Channel getChannel() {
        return channel;
    }
}
