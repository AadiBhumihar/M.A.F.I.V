package com.example.bhumihar.mafiv;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Diksha on 27-10-2016.
 */
public class bookListAdapter extends BaseAdapter {

    private Context mContext;
    List<book> mBookList;

    public bookListAdapter(Context mContext, List<book> mBookList) {
        this.mContext = mContext;
        this.mBookList = mBookList;
    }

    @Override
    public int getCount() {
        return mBookList.size();
    }

    @Override
    public Object getItem(int position) {
        return mBookList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v=View.inflate(mContext,R.layout.listview,null);

        TextView book_name=(TextView)v.findViewById(R.id.book_name);
        TextView author=(TextView)v.findViewById(R.id.author);
        TextView type=(TextView)v.findViewById(R.id.type);
        TextView availability=(TextView)v.findViewById(R.id.availability);
        TextView issue_by=(TextView)v.findViewById(R.id.issue_by);
        TextView return_on=(TextView)v.findViewById(R.id.return_on);

        //set text for above

        book_name.setText(mBookList.get(position).getBook_name());
        author.setText(mBookList.get(position).getAuthor());
        type.setText(mBookList.get(position).getType());
        availability.setText(mBookList.get(position).getAvailability());
        issue_by.setText(mBookList.get(position).getIssue_by());
        return_on.setText(mBookList.get(position).getReturn_by());


        //save product id to tag
        v.setTag(mBookList.get(position).getId());
        return v;
    }
}
