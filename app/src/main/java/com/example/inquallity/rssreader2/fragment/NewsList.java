package com.example.inquallity.rssreader2.fragment;

import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ListView;

import com.example.inquallity.rssreader2.R;
import com.example.inquallity.rssreader2.content.News;
import com.example.inquallity.rssreader2.loader.ChannelsLoader;
import com.example.inquallity.rssreader2.loader.NewsLoader;
import com.example.inquallity.rssreader2.widget.ChannelListAdapter;

/**
 * Created by Inquallity on 23.09.2014.
 */
public class NewsList extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {

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
        final Bundle bundle = new Bundle(getArguments());
        //bundle.putLong(News.Columns.CHANNEL_ID, 1);
        getLoaderManager().initLoader(R.id.news_loader, getArguments(), this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        if (R.id.news_loader == id) {
            setListShown(false);
            return new NewsLoader(getActivity().getApplicationContext(), args.getLong(News.Columns.CHANNEL_ID));
        }
        return null;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.frame, new NewsPage())
                .addToBackStack(NewsPage.class.getName())
                .commit();
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (R.id.news_loader == loader.getId()) {
            mListAdapter.swapCursor(data);
            setListShown(true);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        if (R.id.news_loader == loader.getId()) {
            mListAdapter.swapCursor(null);
        }
    }
}
