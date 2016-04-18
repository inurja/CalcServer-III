package com.calcserver.intz.calcserver;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Intz on 10.04.2016.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

    private static final String TAG = MySQLiteHelper.class.getName();
    private final Context context;

    public static final String TABLE_OPERATIONTYPES = "operationTypes";
    public static final String COLUMN_OPERATIONTYPES_ID ="_id";
    public static final String COLUMN_OPERATIONTYPES_OPERANT = "operant";
    public static final String COLUMN_OPERATIONTYPES_LIFETIMECOUNTER = "lifetimeCounter";
    public static final String[] ALLCOLUMNS_OPERATIONTYPES = {COLUMN_OPERATIONTYPES_ID, COLUMN_OPERATIONTYPES_OPERANT, COLUMN_OPERATIONTYPES_LIFETIMECOUNTER};

    public static final String TABLE_STATISTICS = "statistics";
    public static final String COLUMN_STATISTICS_ID ="_id";
    public static final String COLUMN_STATISTICS_DAYSTAMP = "daystamp";
    public static final String COLUMN_STATISTICS_OPERANTID = "operantId";
    public static final String COLUMN_STATISTICS_DAYCOUNTER = "dayCounter";
    public static final String[] ALLCOLUMNS_STATISTICS = {COLUMN_STATISTICS_ID, COLUMN_STATISTICS_DAYSTAMP, COLUMN_STATISTICS_OPERANTID, COLUMN_STATISTICS_DAYCOUNTER};

    public static final String TABLE_OPERATIONS = "operations";
    public static final String COLUMN_OPERATIONS_ID ="_id";
    public static final String COLUMN_OPERATIONS_OPERANTID = "operantId";
    public static final String COLUMN_OPERATIONS_NUM1 = "num1";
    public static final String COLUMN_OPERATIONS_NUM2 = "num2";
    public static final String COLUMN_OPERATIONS_RES = "res";
    public static final String COLUMN_OPERATIONS_TIMESTAMP = "timestamp";
    public static final String[] ALLCOLUMNS_OPERATIONS = {COLUMN_OPERATIONS_ID, COLUMN_OPERATIONS_OPERANTID, COLUMN_OPERATIONS_NUM1, COLUMN_OPERATIONS_NUM2, COLUMN_OPERATIONS_RES, COLUMN_OPERATIONS_TIMESTAMP};

    private static final String DATABASE_NAME = "mathOperations.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE_OPERATIONTYPES = "create table "
            + TABLE_OPERATIONTYPES + "("
            + COLUMN_OPERATIONTYPES_ID + " integer primary key autoincrement, "
            + COLUMN_OPERATIONTYPES_OPERANT + " text not null, "
            + COLUMN_OPERATIONTYPES_LIFETIMECOUNTER + " integer not null);";

    private static final String DATABSE_CREATE_STATISTICS = "create table "
            + TABLE_STATISTICS + "("
            + COLUMN_STATISTICS_ID + " integer primary key autoincrement, "
            + COLUMN_STATISTICS_DAYSTAMP + " integer not null, "
            + COLUMN_STATISTICS_OPERANTID + " integer, "
            + COLUMN_STATISTICS_DAYCOUNTER + " integer not null);";

    private static final String DATABASE_CREATE_OPERATIONS = "create table "
            + TABLE_OPERATIONS + "("
            + COLUMN_OPERATIONS_ID + " integer primary key autoincrement, "
            + COLUMN_OPERATIONS_OPERANTID + " integer, "
            + COLUMN_OPERATIONS_NUM1 + " double not null, "
            + COLUMN_OPERATIONS_NUM2 + " double not null, "
            + COLUMN_OPERATIONS_RES + " double not null, "
            + COLUMN_OPERATIONS_TIMESTAMP + " integer not null DEFAULT CURRENT_TIMESTAMP);";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    public void dropCreateDatabase(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OPERATIONTYPES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STATISTICS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OPERATIONS);

        db.execSQL(DATABASE_CREATE_OPERATIONTYPES);
        db.execSQL(DATABSE_CREATE_STATISTICS);
        db.execSQL(DATABASE_CREATE_OPERATIONS);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_OPERATIONTYPES);
        db.execSQL(DATABSE_CREATE_STATISTICS);
        db.execSQL(DATABASE_CREATE_OPERATIONS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.v(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion + ", all data will be deleted");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OPERATIONTYPES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STATISTICS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OPERATIONS);
        onCreate(db);
    }
}
