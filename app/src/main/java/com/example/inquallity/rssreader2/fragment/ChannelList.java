package com.example.inquallity.rssreader2.fragment;

import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ListView;

import com.example.inquallity.rssreader2.R;
import com.example.inquallity.rssreader2.activity.NewsActivity;
import com.example.inquallity.rssreader2.content.Channel;
import com.example.inquallity.rssreader2.content.News;
import com.example.inquallity.rssreader2.loader.ChannelsLoader;
import com.example.inquallity.rssreader2.widget.ChannelListAdapter;

/**
 * Created by Inquallity on 12.09.2014.
 */
public class ChannelList extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private CursorAdapter mListAdapter;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mListAdapter = new ChannelListAdapter(getActivity());
        setListAdapter(mListAdapter);
        setListShownNoAnimation(false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(R.id.rss_loader, Bundle.EMPTY, this);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        final Intent intent = new Intent(getActivity(), NewsActivity.class);
        final Cursor cursor = mListAdapter.getCursor();
        if (cursor.moveToPosition(position)){
            intent.putExtra(Channel.Columns.TITLE, cursor.getString(cursor.getColumnIndex(Channel.Columns.TITLE)));
        }
        intent.putExtra(News.Columns.CHANNEL_ID, id);
        startActivity(intent);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        if (R.id.rss_loader == id) {
            setListShown(false);
            return new ChannelsLoader(getActivity().getApplicationContext());
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (R.id.rss_loader == loader.getId()){
            mListAdapter.swapCursor(data);
            setListShown(true);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        if (R.id.rss_loader == loader.getId()){
            mListAdapter.swapCursor(null);
        }
    }
}
