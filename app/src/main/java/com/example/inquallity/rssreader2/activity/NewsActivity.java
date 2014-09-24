package com.example.inquallity.rssreader2.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.inquallity.rssreader2.R;
import com.example.inquallity.rssreader2.content.Channel;
import com.example.inquallity.rssreader2.fragment.NewsList;

/**
 * Created by Inquallity on 23.09.2014.
 */
public class NewsActivity extends Activity implements FragmentManager.OnBackStackChangedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_news);

        if (getActionBar() != null) {
            getActionBar().setTitle(getIntent().getStringExtra(Channel.Columns.TITLE));
        }
        if (savedInstanceState == null) {
            final NewsList newsList = new NewsList();
            newsList.setArguments(getIntent().getExtras());
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.frame, newsList)
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (android.R.id.home == item.getItemId()){
            getFragmentManager()
                    .popBackStackImmediate();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getFragmentManager().addOnBackStackChangedListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        getFragmentManager().removeOnBackStackChangedListener(this);
    }

    @Override
    public void onBackStackChanged() {
        if (getActionBar() != null) {
            if (getFragmentManager().getBackStackEntryCount() > 0) {
                getActionBar().setDisplayHomeAsUpEnabled(true);
            } else {
                getActionBar().setDisplayHomeAsUpEnabled(false);
            }
        }
    }
}
