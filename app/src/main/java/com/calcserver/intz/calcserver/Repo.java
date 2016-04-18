package com.calcserver.intz.calcserver;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Intz on 10.04.2016.
 */
public abstract class Repo<T extends IEntity> {
    // Database fields
    protected SQLiteDatabase database;
    protected String tableName;
    protected String[] allColumns;

    private static String TAG = Repo.class.getName();

    //Context context
    public Repo(SQLiteDatabase db, String tableName, String[] allColumns){
        this.database = db;
        this.tableName = tableName;
        this.allColumns = allColumns;
    }

    public SQLiteDatabase getDatabase(){
        return database;
    }

    public String getTableName(){
        return tableName;
    }

    public String[] getAllColumns(){
        return allColumns;
    }

    public List<T> getAll(){
        List<T> listOfEntity = new ArrayList<T>();

        Cursor cursor = database.query(tableName,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            T entity = cursorToEntity(cursor);
            listOfEntity.add(entity);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();

        return listOfEntity;
    }


    public Cursor getCursorAll(){
        Cursor cursor = database.query(tableName,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();

        return cursor;
    }


    public T getById(long id){
        Cursor cursor = database.query(tableName,
                allColumns, allColumns[0] + " = " + id,
                null, null, null, null);

        if (cursor == null){
            return null;
        }

        cursor.moveToFirst();
        T newEntity = cursorToEntity(cursor);

        return newEntity;
    }

    public T add(T entity){
        ContentValues values = entityToContentValues(entity);
        long insertId = database.insert(tableName, null, values);

        Cursor cursor = database.query(tableName,
                allColumns, allColumns[0] + " = " + insertId,
                null, null, null, null);

        cursor.moveToFirst();
        T newEntity = cursorToEntity(cursor);

        cursor.close();
        return newEntity;
    }

    public void update(T entity){
        ContentValues values = entityToContentValues(entity);
        database.update(tableName, values, allColumns[0] + "=" + entity.getId(), null);
    }

    public void delete(T entity){
        long id = entity.getId();
        System.out.println("Entity deleted from table "+tableName+" with id: " + id);
        database.delete(tableName, allColumns[0] + " = " + id, null);
    }

    public void delete(long id){
        System.out.println("Entity deleted from table "+tableName+" with id: " + id);
        database.delete(tableName, allColumns[0] + " = " + id, null);
    }

    public void deleteAll(){
        database.delete(tableName, null , null);
    }

    // this has to be implemented in child class
    public abstract T cursorToEntity(Cursor cursor);

    // this has to be implemented in child class
    public abstract ContentValues entityToContentValues(T entity);
}
