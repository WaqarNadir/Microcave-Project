package com.microcave.masjidtimetable.util.classes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Vicky on 6/4/2015.
 */
public class MasjidSQLLiteOpenHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "MasjidDetail.db";




    public MasjidSQLLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

     //   Log.e("Create query", "" + MasjidSQLContract.commands.Primary_Masjid_Commands.Create_Table);
      //  Log.e("Create query", "" + MasjidSQLContract.commands.Masjid_Detail_Commands.Create_Table);
       // Log.e("Create query", "" + MasjidSQLContract.commands.Prayer_Time_command.Create_Table);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(MasjidSQLContract.commands.Primary_Masjid_Commands.Create_Table);
            db.execSQL(MasjidSQLContract.commands.Masjid_Detail_Commands.Create_Table);
            db.execSQL(MasjidSQLContract.commands.Prayer_Time_command.Create_Table);
            Log.e("Create query", "" + MasjidSQLContract.commands.Primary_Masjid_Commands.Create_Table);
            Log.e("Create query", "" + MasjidSQLContract.commands.Masjid_Detail_Commands.Create_Table);
            Log.e("Create query", "" + MasjidSQLContract.commands.Prayer_Time_command.Create_Table);
        }catch (Exception e)
        {
            Log.e("Create query excpt", e.getMessage());
        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
