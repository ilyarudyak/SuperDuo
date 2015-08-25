package it.jaschke.alexandria;

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import it.jaschke.alexandria.api.Book;
import it.jaschke.alexandria.utils.DbUtils;
import it.jaschke.alexandria.utils.NetworkUtils;
import it.jaschke.alexandria.utils.PrefUtils;


public class BookSearchFragment extends Fragment {

    private static final String TAG = BookSearchFragment.class.getSimpleName();
    private static final String EAN_CONTENT = "ean_content";
    public static final String TOAST_NOT_CONNECTED = "You are not connected. " +
            "Search is not available.";
    public static final String TOAST_NOT_FOUND = "The book is not found";
    public static final String TOAST_NOT_CORRECT_ISBN = "This ISBN is not correct. Please check";
    public static final String TOAST_INSERT_SUCCESSFULL = "This book is inserted into DB";
    public static final String TOAST_CAN_NOT_INSERT = "Can not insert this book";

    private View mRootView;
    private Book mBook;

    // search view and button
    private EditText mEanEditText;
    private Button mSearchButton;

    // book details view
    private TextView mBookTitle;
    private TextView mBookSubTitle;
    private TextView mBookAuthors;
    private TextView mBookCategories;
    private ImageView mBookCover;

    private Button mClearButton;
    private Button mAddButton;

    // flags
    private boolean isConnected;
    private boolean isLiveSearch;

    public BookSearchFragment(){
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

        // retain fragment after configuration change
        setRetainInstance(true);

        mRootView = inflater.inflate(R.layout.fragment_search_book, container, false);

        setEanEditText();
        setSearchButton();

        setEmptyBookDetails();
        setClearAddButtons();

        // fill book details view if mBook is available
        fillBookDetails();

        return mRootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        activity.setTitle(R.string.search_book_title);
    }

    // helper methods
    public void setEanEditText() {

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
                    //Once we have an ISBN, fetch the book
                    new FetchBookFromAPI().execute(ean);
                }
            });
        }
    }
    private void setSearchButton() {

        mSearchButton = (Button) mRootView.findViewById(R.id.search_button);

        // disable when no connection is available
        if (!isConnected) {
            mSearchButton.setEnabled(false);
        }

        // if live search is NOT set add listener
        if (!isLiveSearch) {
            mSearchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String ean = mEanEditText.getText().toString();
                    if (isISBNCorrect(ean)) {
                        new FetchBookFromAPI().execute(convertToEan13(ean));
                    } else {
                        Toast.makeText(getActivity(),
                                TOAST_NOT_CORRECT_ISBN, Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

        // make invisible if user choose live search
        if (isLiveSearch) {
            mSearchButton.setVisibility(View.INVISIBLE);
        }
    }
    private void setClearAddButtons() {

        mClearButton = (Button) mRootView.findViewById(R.id.clear_button);
        mClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEanEditText.setText("");
                clearBookDetails();
            }
        });

        mAddButton = (Button) mRootView.findViewById(R.id.add_button);
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearBookDetails();
                new AddBookToDb().execute(mBook);
            }
        });
    }
    private void setEmptyBookDetails() {
        mBookTitle = (TextView) mRootView.findViewById(R.id.book_title);
        mBookSubTitle = (TextView) mRootView.findViewById(R.id.book_subtitle);
        mBookAuthors = (TextView) mRootView.findViewById(R.id.book_authors);
        mBookCategories = (TextView) mRootView.findViewById(R.id.book_categories);
        mBookCover = (ImageView) mRootView.findViewById(R.id.book_cover);
    }
    private void fillBookDetails() {
        if (mBook != null) {
            mBookTitle.setText(mBook.getTitle());
            mBookSubTitle.setText(mBook.getSubtitle());
            mBookAuthors.setText(mBook.getAuthorsAsString());
            mBookCategories.setText(mBook.getCategoriesAsString());
            mBookCover.setVisibility(View.VISIBLE);
            mAddButton.setVisibility(View.VISIBLE);
            mClearButton.setVisibility(View.VISIBLE);
        }
        setBookCover();
    }
    private void setBookCover() {
        if (mBook != null) {
            Picasso.with(getActivity())
                    .load(mBook.getImgUrl())
                    .placeholder(R.raw.placeholder_detail)
                    .resize(320, 410)
                    .into(mBookCover);
        }
    }
    private void clearBookDetails(){
        mBookTitle.setText("");
        mBookSubTitle.setText("");
        mBookAuthors.setText("");
        mBookCategories.setText("");
        mBookCover.setVisibility(View.INVISIBLE);
        mAddButton.setVisibility(View.INVISIBLE);
        mClearButton.setVisibility(View.INVISIBLE);
    }
    private boolean isISBNCorrect(String ean) {
        if (ean.length() == 10 && !ean.startsWith("978")) {
            return true;
        } else if (ean.length() == 13) {
            return true;
        }
        return false;
    }
    private String convertToEan13(String ean) {
        if (ean.length() == 10) {
            return "978" + ean;
        } else {
            return ean;
        }
    }
    public void setEanText(String text) {
        mEanEditText.setText(text);
    }

    // ----------------- async tasks -----------------

    public class FetchBookFromAPI extends AsyncTask<String, Void, Book> {

        @Override
        protected Book doInBackground(String... params) {
            return NetworkUtils.getBookFromNetwork(params[0]);
        }

        @Override
        protected void onPostExecute(Book book) {

            // clear previous book view
            clearBookDetails();

            mBook = book;
            if (mBook != null) {
                fillBookDetails();
            } else {
                Toast.makeText(getActivity(), TOAST_NOT_FOUND, Toast.LENGTH_LONG).show();
            }
        }
    }
    public class AddBookToDb extends AsyncTask<Book, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Book... params) {
            try {
                DbUtils.insertBookIntoDb(getActivity(), params[0]);
            } catch (android.database.SQLException e) {
                Log.d(TAG, "Can not insert this book: ", e);
                return false;
            }

            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                Toast.makeText(getActivity(), TOAST_INSERT_SUCCESSFULL, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getActivity(), TOAST_CAN_NOT_INSERT, Toast.LENGTH_LONG).show();
            }
        }
    }
}
