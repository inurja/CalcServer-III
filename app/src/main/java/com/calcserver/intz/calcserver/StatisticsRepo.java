package com.calcserver.intz.calcserver;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Intz on 10.04.2016.
 */
public class StatisticsRepo extends Repo<Statistics> {

    private OperationTypesRepo opTypesRepo;

    public StatisticsRepo(SQLiteDatabase db, String tableName, String[] allColumns, OperationTypesRepo operationTypesRepo) {
        super(db, tableName, allColumns);
        this.opTypesRepo = operationTypesRepo;
    };

    @Override
    public Statistics cursorToEntity(Cursor cursor) {
        Statistics statistics = new Statistics();
        statistics.setId(cursor.getLong(0));
        statistics.setDaystamp(cursor.getInt(1));
        statistics.setOperantId(cursor.getInt(2));
        statistics.setDayCounter(cursor.getInt(3));
        statistics.setOperant(opTypesRepo.getOperantForResult(statistics.getOperantId()));

        return statistics;
    }

    @Override
    public ContentValues entityToContentValues(Statistics statistics) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(MySQLiteHelper.COLUMN_STATISTICS_DAYSTAMP, statistics.getDaystamp());
        contentValues.put(MySQLiteHelper.COLUMN_STATISTICS_OPERANTID, statistics.getOperantId());
        contentValues.put(MySQLiteHelper.COLUMN_STATISTICS_DAYCOUNTER, statistics.getDayCounter());

        return contentValues;
    }

    //get all the statistics that belong to that Operant (inputted by id)
    public List<Statistics> getForOperant(long operantId) {
        List<Statistics> listOfEntity = new ArrayList<Statistics>();

        Cursor cursor = getDatabase().query(getTableName(),
                getAllColumns(), "operantId = " + operantId, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Statistics entity = cursorToEntity(cursor);
            listOfEntity.add(entity);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();

        return listOfEntity;
    }

    private long getDateStamp() {
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
        return Long.parseLong(sdf.format(date));
    }

    public Statistics addOneToDayCounter(long id) {
        Statistics newEntity;
        long dayStamp = getDateStamp();
        Cursor cursor = database.query(tableName,
                allColumns, allColumns[3] + " = "+ id +" and " + allColumns[1] +" = "+dayStamp ,
                null, null, null, null);

        if (cursor == null || cursor.getCount()<1) {
            Statistics statistics = new Statistics();
            statistics.setDayCounter(1);
            statistics.setOperantId(id);
            statistics.setDaystamp(dayStamp);
            newEntity = add(statistics);
        } else {
            cursor.moveToFirst();
            newEntity = cursorToEntity(cursor);
            newEntity.setDayCounter(newEntity.getDayCounter() + 1);
            update(newEntity);
        }

        return newEntity;
    }

}
