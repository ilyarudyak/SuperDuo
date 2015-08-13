package it.jaschke.alexandria;

import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import it.jaschke.alexandria.data.AlexandriaContract;
import it.jaschke.alexandria.services.BookService;
import it.jaschke.alexandria.services.DownloadImage;
import it.jaschke.alexandria.utils.NetworkUtils;
import it.jaschke.alexandria.utils.PrefUtils;


public class SearchBookFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = SearchBookFragment.class.getSimpleName();
    private static final int LOADER_ID = 1;
    private static final String EAN_CONTENT = "ean_content";
    public static final String TOAST_NOT_CONNECTED = "You are not connected. " +
            "Search is not available.";

    private View mRootView;
    private EditText mEanEditText;
    private boolean isConnected;
    private boolean isLiveSearch;

    public SearchBookFragment(){
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // check if network is available and notify user
        isConnected = NetworkUtils.isNetworkAvailable(getActivity());
        if (!isConnected) {
            Toast.makeText(getActivity(), TOAST_NOT_CONNECTED, Toast.LENGTH_LONG).show();
        }

        // check live search prefs
        isLiveSearch = PrefUtils.isSearchLive(getActivity());
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.fragment_add_book, container, false);

        setEanEditText();
        setSearchButton();

        mRootView.findViewById(R.id.add_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEanEditText.setText("");
            }
        });

        mRootView.findViewById(R.id.clear_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bookIntent = new Intent(getActivity(), BookService.class);
                bookIntent.putExtra(BookService.EAN, mEanEditText.getText().toString());
                bookIntent.setAction(BookService.DELETE_BOOK);
                getActivity().startService(bookIntent);
                mEanEditText.setText("");
            }
        });

        if(savedInstanceState != null){
            mEanEditText.setText(savedInstanceState.getString(EAN_CONTENT));
            mEanEditText.setHint("");
        }

        return mRootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        activity.setTitle(R.string.search_book);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(mEanEditText !=null) {
            outState.putString(EAN_CONTENT, mEanEditText.getText().toString());
        }
    }

    // helper methods
    private void setEanEditText() {

        mEanEditText = (EditText) mRootView.findViewById(R.id.ean_edit_text);

        // disable when no connection is available
        if (!isConnected) {
            mEanEditText.setEnabled(false);
        }

        // add listener only if user choose live search option
        if (isLiveSearch) {
            mEanEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    //no need
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    //no need
                }

                @Override
                public void afterTextChanged(Editable s) {
                    String ean = s.toString();
                    //catch isbn10 numbers
                    if (ean.length() == 10 && !ean.startsWith("978")) {
                        ean = "978" + ean;
                    }
                    if (ean.length() < 13) {
                        clearBookDetails();
                        return;
                    }
                    //Once we have an ISBN, start a book intent
                    Intent bookIntent = new Intent(getActivity(), BookService.class);
                    bookIntent.putExtra(BookService.EAN, ean);
                    bookIntent.setAction(BookService.FETCH_BOOK);
                    getActivity().startService(bookIntent);
                    SearchBookFragment.this.restartLoader();
                }
            });
        }
    }
    private void setSearchButton() {

        Button searchButton = (Button) mRootView.findViewById(R.id.search_button);

        // disable when no connection is available
        if (!isConnected) {
            searchButton.setEnabled(false);
        }

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Context context = getActivity();
                CharSequence text = "we're going to search on click";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

            }
        });

        // make invisible if user choose live search
        if (isLiveSearch) {
            searchButton.setVisibility(View.INVISIBLE);
        }
    }


    // ----------------- loader methods -----------------

    private void restartLoader(){
        getLoaderManager().restartLoader(LOADER_ID, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if(mEanEditText.getText().length()==0){
            return null;
        }
        String eanStr= mEanEditText.getText().toString();
        if(eanStr.length()==10 && !eanStr.startsWith("978")){
            eanStr="978"+eanStr;
        }
        return new CursorLoader(
                getActivity(),
                AlexandriaContract.BookEntry.buildFullBookUri(Long.parseLong(eanStr)),
                null,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (!data.moveToFirst()) {
            return;
        }

        String bookTitle = data.getString(data.getColumnIndex(AlexandriaContract.BookEntry.TITLE));
        ((TextView) mRootView.findViewById(R.id.bookTitle)).setText(bookTitle);

        String bookSubTitle = data.getString(data.getColumnIndex(AlexandriaContract.BookEntry.SUBTITLE));
        ((TextView) mRootView.findViewById(R.id.bookSubTitle)).setText(bookSubTitle);

        String authors = data.getString(data.getColumnIndex(AlexandriaContract.AuthorEntry.AUTHOR));
        String[] authorsArr = authors.split(",");
        ((TextView) mRootView.findViewById(R.id.authors)).setLines(authorsArr.length);
        ((TextView) mRootView.findViewById(R.id.authors)).setText(authors.replace(",","\n"));
        String imgUrl = data.getString(data.getColumnIndex(AlexandriaContract.BookEntry.IMAGE_URL));
        if(Patterns.WEB_URL.matcher(imgUrl).matches()){
            new DownloadImage((ImageView) mRootView.findViewById(R.id.bookCover)).execute(imgUrl);
            mRootView.findViewById(R.id.bookCover).setVisibility(View.VISIBLE);
        }

        String categories = data.getString(data.getColumnIndex(AlexandriaContract.CategoryEntry.CATEGORY));
        ((TextView) mRootView.findViewById(R.id.categories)).setText(categories);

        mRootView.findViewById(R.id.add_button).setVisibility(View.VISIBLE);
        mRootView.findViewById(R.id.clear_button).setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    // helper methods
    private void clearBookDetails(){
        ((TextView) mRootView.findViewById(R.id.bookTitle)).setText("");
        ((TextView) mRootView.findViewById(R.id.bookSubTitle)).setText("");
        ((TextView) mRootView.findViewById(R.id.authors)).setText("");
        ((TextView) mRootView.findViewById(R.id.categories)).setText("");
        mRootView.findViewById(R.id.bookCover).setVisibility(View.INVISIBLE);
        mRootView.findViewById(R.id.add_button).setVisibility(View.INVISIBLE);
        mRootView.findViewById(R.id.clear_button).setVisibility(View.INVISIBLE);
    }


}
