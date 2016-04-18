package com.calcserver.intz.calcserver;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by Intz on 14.04.2016.
 */
public class OperationsAdapter extends CursorAdapter {

    private final LayoutInflater layoutInflater;
    private UOW uow;
    private ViewGroup parentViewGroup;

    public OperationsAdapter(Context context, Cursor c, UOW uow) {
        super(context, c, 0);
        layoutInflater = LayoutInflater.from(context);
        this.uow = uow;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        final View view = layoutInflater.inflate(R.layout.operations, parent, false);
        parentViewGroup = parent;
        return view;
    }

    // this can be called several times by the system!!!
    // first pass - initial draw, get measurements
    // second pass - final draw
    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView textViewName =(TextView) view.findViewById(R.id.name);

        Operations operations = uow.operationsRepo.cursorToEntity(cursor);
        textViewName.setText(operations.toString());
    }
}
