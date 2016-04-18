package com.calcserver.intz.calcserver;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Intz on 14.04.2016.
 */
public class StatisticsAdapter extends CursorAdapter {

    private final LayoutInflater layoutInflater;
    private UOW uow;
    private ViewGroup parentViewGroup;

    public StatisticsAdapter(Context context, Cursor c, UOW uow) {
        super(context, c, 0);
        layoutInflater = LayoutInflater.from(context);
        this.uow = uow;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        final View view = layoutInflater.inflate(R.layout.statistics, parent, false);
        parentViewGroup = parent;
        return view;
    }

    // this can be called several times by the system!!!
    // first pass - initial draw, get measurements
    // second pass - final draw
    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView textViewName =(TextView) view.findViewById(R.id.name);

        Statistics statistics = uow.statisticsRepo.cursorToEntity(cursor);
        textViewName.setText(statistics.toString());
        //displayOperationTypesView(view, context, statistics);
    }

    private void displayOperationTypesView(View view, Context context, Statistics statistics) {
/*        // get the contactsListView
        LinearLayout statisticsListView = (LinearLayout) view.findViewById(R.id.statisticsListView);

        // if this gets called multiple times, first clean all up
        // otherwise you will add same childs several times
        statisticsListView.removeAllViews();

        for (OperationTypes operationTypes :
                uow.operationTypesRepo.getIdByOperand(statistics.getOperantId())) {

            // load the xml structure of your row
            View child = layoutInflater.inflate(R.layout.statistics_details,parentViewGroup,false);

            TextView textViewOperant =(TextView) child.findViewById(R.id.contactValue);
            TextView textViewCounter =(TextView) child.findViewById(R.id.contactType);

            textViewOperant.setText(uow.operationTypesRepo.getById(statistics.getOperantId()).getOperant());
            textViewCounter.setText(statistics.getDayCounter());

            statisticsListView.addView(child);
        }*/


    }
}
