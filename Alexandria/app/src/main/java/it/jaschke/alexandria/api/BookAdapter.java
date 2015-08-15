package it.jaschke.alexandria.api;


import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import it.jaschke.alexandria.R;
import it.jaschke.alexandria.data.AlexandriaContract;
import it.jaschke.alexandria.services.DownloadImage;

/**
 * Created by saj on 11/01/15.
 */
public class BookAdapter extends CursorAdapter {


    public static class ViewHolder {
        public final ImageView bookCover;
        public final TextView bookTitle;
        public final TextView bookAuthors;

        public ViewHolder(View view) {
            bookCover = (ImageView) view.findViewById(R.id.fullBookCover);
            bookTitle = (TextView) view.findViewById(R.id.listBookTitle);
            bookAuthors = (TextView) view.findViewById(R.id.list_book_authors);
        }
    }

    public BookAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.book_list_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        ViewHolder viewHolder = (ViewHolder) view.getTag();

        String imgUrlStr = cursor.getString(cursor.getColumnIndex(AlexandriaContract.BookEntry.IMAGE_URL));
        new DownloadImage(viewHolder.bookCover).execute(imgUrlStr);

        Picasso.with(mContext)
                .load(imgUrlStr)
                .placeholder(R.raw.placeholder)
                .error(R.raw.placeholder)
                .resize(130, 170)
                .into(viewHolder.bookCover);

        String bookTitleStr = cursor.getString(cursor.getColumnIndex(AlexandriaContract.BookEntry.TITLE));
        viewHolder.bookTitle.setText(bookTitleStr);

        String bookAuthorsStr = cursor.getString(cursor.getColumnIndex(AlexandriaContract.AuthorEntry.AUTHOR));
        viewHolder.bookAuthors.setText(bookAuthorsStr);
    }
}
