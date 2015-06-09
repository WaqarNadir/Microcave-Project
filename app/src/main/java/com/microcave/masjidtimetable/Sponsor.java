package com.microcave.masjidtimetable;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.microcave.masjidtimetable.util.classes.MasjidSQLContract;
import com.microcave.masjidtimetable.util.classes.MasjidSQLLiteOpenHelper;


public class Sponsor extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponsor);

        SQLiteDatabase db = new MasjidSQLLiteOpenHelper(this).getWritableDatabase();
        String Table = MasjidSQLContract.tables.Primary_Masjid.TableName;
        ContentValues cv= new ContentValues();
        cv.put(MasjidSQLContract.tables.Primary_Masjid.ID,"1");

        cv.put(MasjidSQLContract.tables.Primary_Masjid.Prayer_Time,"time");

        cv.put(MasjidSQLContract.tables.Primary_Masjid.Donation,"doantion");
        cv.put(MasjidSQLContract.tables.Primary_Masjid.Event, "event");

        db.insert(Table, null, cv);

    }

    public void show(View v)
    {

        String Table = MasjidSQLContract.tables.Primary_Masjid.TableName;
        SQLiteDatabase db1 = new MasjidSQLLiteOpenHelper(this).getReadableDatabase();

        String [] columns = {MasjidSQLContract.tables.Primary_Masjid.Event,

        };

        Cursor c = db1.query(Table,columns,null,null,null,null,null);
        c.moveToFirst();
        TextView t = (TextView)findViewById(R.id.myid);
        t.setText(c.getString(
                        c.getColumnIndex(MasjidSQLContract.tables.Primary_Masjid.Event)
                )
        );


    }
}
