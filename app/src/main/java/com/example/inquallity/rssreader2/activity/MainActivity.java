package com.example.inquallity.rssreader2.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.inquallity.rssreader2.R;
import com.example.inquallity.rssreader2.fragment.ChannelList;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_main);

        if(savedInstanceState == null){
            getFragmentManager().beginTransaction()
                    .add(R.id.frame, new ChannelList())
                    .commit();
        }
    }
}
