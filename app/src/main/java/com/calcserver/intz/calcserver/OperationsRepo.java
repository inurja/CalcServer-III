package com.calcserver.intz.calcserver;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Intz on 10.04.2016.
 */
public class OperationsRepo extends Repo<Operations> {

    private OperationTypesRepo opTypesRepo;

    public OperationsRepo(SQLiteDatabase db, String tableName, String[] allColumns, OperationTypesRepo operationTypesRepo) {
        super(db, tableName, allColumns);
        this.opTypesRepo = operationTypesRepo;
    };

    @Override
    public Operations cursorToEntity(Cursor cursor) {
        Operations operations = new Operations();
        operations.setId(cursor.getLong(0));
        operations.setOperantId(cursor.getInt(1));
        operations.setNum1(cursor.getFloat(2));
        operations.setNum2(cursor.getFloat(3));
        operations.setRes(cursor.getFloat(4));
        operations.setTimestamp(cursor.getInt(5));
        operations.setOperant(opTypesRepo.getOperantForResult(operations.getOperantId()));

        return operations;
    }

    @Override
    public ContentValues entityToContentValues(Operations operations) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(MySQLiteHelper.COLUMN_OPERATIONS_OPERANTID, operations.getOperantId());
        contentValues.put(MySQLiteHelper.COLUMN_OPERATIONS_NUM1, operations.getNum1());
        contentValues.put(MySQLiteHelper.COLUMN_OPERATIONS_NUM2, operations.getNum2());
        contentValues.put(MySQLiteHelper.COLUMN_OPERATIONS_RES, operations.getRes());
        contentValues.put(MySQLiteHelper.COLUMN_OPERATIONS_TIMESTAMP, operations.getTimestamp());

        return contentValues;
    }
}
