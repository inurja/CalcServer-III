package com.calcserver.intz.calcserver;

import android.database.Cursor;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Intz on 10.04.2016.
 */
public class OperationTypesRepo extends Repo<OperationTypes> {

    public OperationTypesRepo(SQLiteDatabase db, String tableName, String[] allColumns) {
        super(db, tableName, allColumns);
    };

    @Override
    public OperationTypes cursorToEntity(Cursor cursor) {
        OperationTypes operationTypes = new OperationTypes();
        operationTypes.setId(cursor.getLong(0));
        operationTypes.setOperant(cursor.getString(1));
        operationTypes.setLifetimeCounter(cursor.getInt(2));
        return operationTypes;
    }

    @Override
    public ContentValues entityToContentValues(OperationTypes operationTypes) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(MySQLiteHelper.COLUMN_OPERATIONTYPES_OPERANT, operationTypes.getOperant());
        contentValues.put(MySQLiteHelper.COLUMN_OPERATIONTYPES_LIFETIMECOUNTER, operationTypes.getLifetimeCounter());

        return contentValues;
    }

    //get all the statistics that belong to that Operant (inputted by id)
    public OperationTypes getCounterFromOperant(String operant) {
        OperationTypes newEntity;

        Cursor cursor = database.query(tableName,
                allColumns, allColumns[1] + " = '" + operant +"'",
                null, null, null, null);

        //cursor.moveToFirst();
        if(cursor == null || cursor.getCount()<1) {
            OperationTypes operationTypes = new OperationTypes();
            operationTypes.setOperant(operant);
            operationTypes.setLifetimeCounter(0);
            newEntity = add(operationTypes);
        } else {
            cursor.moveToFirst();
            newEntity = cursorToEntity(cursor);
        }
        // make sure to close the cursor
        cursor.close();

        return newEntity;

    }

    public String getOperantForResult(long id) {
        OperationTypes op = getById(id);
        return op.getOperant();
    }
}
