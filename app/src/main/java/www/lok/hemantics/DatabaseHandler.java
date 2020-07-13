package www.lok.hemantics;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static String DB_NAME = "math_logic";
    private static int DB_VERSION = 1;
    private SQLiteDatabase db;
    public static final String CART_TABLE = "math_data";

    public static final String AUTO_ID = "auto_id";
    public static final String COLUMN_VARIABLE = "variable";
    public static final String COLUMN_VALUE = "value";

    public DatabaseHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        this.db = sqLiteDatabase;

        String exe = "CREATE TABLE IF NOT EXISTS " + CART_TABLE
                + "(" + AUTO_ID + " integer primary key AUTOINCREMENT, "
                + COLUMN_VARIABLE + "  TEXT NOT NULL , "
                + COLUMN_VALUE + "  TEXT NOT NULL"
                + ")";

        db.execSQL(exe);

    }

    public boolean insert_dbdata(Data_Model data) {

            db = getWritableDatabase();

            ContentValues values = new ContentValues();

            values.put(COLUMN_VARIABLE, data.getVariable());
            values.put(COLUMN_VALUE, data.getValue());

            Log.e("DB INSERTION","   "+values);
            db.insert(CART_TABLE, null, values);
            return true;

    }

    public void delete_data(String id){
        db = getReadableDatabase();
        db.execSQL("delete from " + CART_TABLE + " where " + AUTO_ID + " = " + id);
    }

    public List<DB_data> get_dbdata() {
        List<DB_data>  list = new ArrayList<>();
        db = getReadableDatabase();
        String qry = "Select *  from " + CART_TABLE + " ORDER BY "+AUTO_ID+" ASC";
        Cursor cursor = db.rawQuery(qry, null);
        cursor.moveToFirst();

        for (int i = 0; i < cursor.getCount(); i++) {

            list.add(new DB_data( cursor.getString(cursor.getColumnIndex(AUTO_ID)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_VARIABLE)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_VALUE))));
            cursor.moveToNext();
        }
        return list;
    }

    public Boolean check_if_exists(String var) {

        Boolean chk = false;
        db = getReadableDatabase();

        String qry = "Select *  from " + CART_TABLE;
        Cursor cursor = db.rawQuery(qry, null);
        cursor.moveToFirst();

        if (cursor!=null) {

            for (int i = 0; i < cursor.getCount(); i++) {
                Log.e("DB DATA "," Variable DB = "+cursor.getString(cursor.getColumnIndex(COLUMN_VARIABLE))+"   ---- Variable Now = "+var);

                if (cursor.getString(cursor.getColumnIndex(COLUMN_VARIABLE)).equalsIgnoreCase(var)) {
                    cursor.moveToNext();
                    chk = true;
                    Log.e("YYES "," === "+chk);
                    break;
                }
                cursor.moveToNext();
            }
        }
        Log.e("Checking  "," === "+chk);
        return chk;
    }

    public String get_andwer(String code) {

        String chk = "";
        db = getReadableDatabase();

        String qry = "Select *  from " + CART_TABLE;
        Cursor cursor = db.rawQuery(qry, null);
        cursor.moveToFirst();

        if (cursor!=null) {

            for (int i = 0; i < cursor.getCount(); i++) {
                if (cursor.getString(cursor.getColumnIndex(COLUMN_VARIABLE)).equalsIgnoreCase(code)) {
                    Log.e("DB DATA "," Variable DB = "+cursor.getString(cursor.getColumnIndex(COLUMN_VARIABLE))+" = "+cursor.getString(cursor.getColumnIndex(COLUMN_VALUE)));
                    chk = chk + cursor.getString(cursor.getColumnIndex(COLUMN_VALUE));
                    Log.e("YYES Code "," === "+chk);
                    cursor.moveToNext();
                }else{
                    cursor.moveToNext();
                }
            }
        }
        Log.e("Checking Code answer "," === "+chk);
        return chk;
    }

    public void update_data(String _id, String var, String val){
        db = getWritableDatabase();
        if (isIndb(_id)) {
            db.execSQL("update " + CART_TABLE + " set " + COLUMN_VALUE + " = '" + val + "' where " + AUTO_ID + "=" + _id);
        }
    }

    public boolean isIndb(String id) {
        db = getReadableDatabase();
        String qry = "Select *  from " + CART_TABLE + " where " + AUTO_ID + " = " + id;
        Cursor cursor = db.rawQuery(qry, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) return true;

        return false;
    }

    public int get_count() {
        db = getReadableDatabase();
        String qry = "Select *  from " + CART_TABLE;
        Cursor cursor = db.rawQuery(qry, null);
        return cursor.getCount();
    }






    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


}
