package com.microcave.masjidtimetable.util.classes;

import android.provider.BaseColumns;
import android.util.Log;

/**
 * Created by Vicky on 6/4/2015.
 */
public abstract  class MasjidSQLContract {
    public static abstract  class  tables
    {
        public  static class Primary_Masjid implements BaseColumns
        {
            public final static   String TableName="Primary_Masjid";
            public final static   String ID="Primary_ID";
            public final static   String Prayer_Time="Prayer_Time";
            public final static   String Event="Event";
            public final static   String Donation="Donation";
            public final static   String Notes="Notes";
        }
        public  static class Masjid_Detail implements BaseColumns
        {
            public final static   String TableName="Masjid_Datail";

            public final static String masjid_name="Name" ;
            public final static String masjid_ID ="ID";
            public final static String  masjid_add_1="Address";
            public final static String masjid_local_area="Local_Area";
            public final static String  masjid_larger_area="Larger_Area";
            public final static String  masjid_post_code="Postcode";
            public final static String  masjid_telephone="Phone";
            public final static String  masjid_country="Country";
            public final static String  masjid_created="Masjid_Created";
            public final static String  masjid_modified="masjid_modified";
            public final static String  status="status";
            public final static String  code="code";
            public final static String  full_name="full_name";
        }


        public  static class PrayerTime implements BaseColumns
        {
            public final static   String TableName="Prayer_Time";

            public final static String DATE="DATE" ;
            public final static String SubhaSadiq ="SubhaSadiq";
            public final static String  Sunrise="Sunrise";
            public final static String Fajar_J="Fajar_J";
            public final static String  Zohar_j="Zohar_j";
            public final static String  Zohar_b="Zohar_b";
            public final static String  Asar_b="Asar_b";
            public final static String  Asar_j="Asar_j";
            public final static String  Maghrib_b="Maghrib_b";
            public final static String  Maghrib_j="Maghrib_j";
            public final static String  Esha_b="Esha_b";
            public final static String  Esha_j="Esha_j";
            public final static String  Type="Type";

        }



    }
    public static abstract  class  commands
    {
        private static final String TEXT_TYPE = " TEXT";
        private static final String FLOAT_TYPE = " REAL";
        private static final String COMMA_SEP = ",";
        public  static class Primary_Masjid_Commands
        {
            public final static   String Create_Table=
                    "CREATE TABLE "+MasjidSQLContract.tables.Primary_Masjid.TableName
                    +"("+
                            tables.Primary_Masjid._ID +" TEXT PRIMARY KEY,"+
                            MasjidSQLContract.tables.Primary_Masjid.ID +TEXT_TYPE
                    +COMMA_SEP+
                            MasjidSQLContract.tables.Primary_Masjid.Prayer_Time+TEXT_TYPE
                    +COMMA_SEP+
                            MasjidSQLContract.tables.Primary_Masjid.Event+TEXT_TYPE
                    +COMMA_SEP+
                            MasjidSQLContract.tables.Primary_Masjid.Donation+TEXT_TYPE
                            +COMMA_SEP+
                            tables.Primary_Masjid.Notes+TEXT_TYPE
                    +")";


        }

        public static  class  Masjid_Detail_Commands
        {
            public final static  String Create_Table=
                    "CREATE TABLE "+    tables.Masjid_Detail.TableName
                    +"("+
                            tables.Masjid_Detail._ID +" TEXT PRIMARY KEY,"+

                            tables.Masjid_Detail.masjid_ID                  +TEXT_TYPE
                            +COMMA_SEP+
                            tables.Masjid_Detail.masjid_name                +TEXT_TYPE
                            +COMMA_SEP+
                            tables.Masjid_Detail.masjid_local_area          +TEXT_TYPE
                            +COMMA_SEP+tables.Masjid_Detail.masjid_larger_area+TEXT_TYPE
                            +COMMA_SEP+tables.Masjid_Detail.masjid_country  +TEXT_TYPE
                            +COMMA_SEP+tables.Masjid_Detail.code+           TEXT_TYPE
                            +COMMA_SEP+tables.Masjid_Detail.full_name       +TEXT_TYPE
                            +COMMA_SEP+tables.Masjid_Detail.masjid_add_1    +TEXT_TYPE
                            +COMMA_SEP+tables.Masjid_Detail.masjid_telephone+TEXT_TYPE
                            +COMMA_SEP+tables.Masjid_Detail.masjid_post_code+TEXT_TYPE
                            +COMMA_SEP+tables.Masjid_Detail.status          +TEXT_TYPE
                            +COMMA_SEP+tables.Masjid_Detail.masjid_created  +TEXT_TYPE
                            +COMMA_SEP+tables.Masjid_Detail.masjid_modified +TEXT_TYPE
                            +")";


                    ;


        }
        public static class Prayer_Time_command
         {
             public final static  String Create_Table=
                     "CREATE TABLE "+tables.PrayerTime.TableName
                     +"("+
                             tables.PrayerTime._ID + " TEXT PRIMARY KEY ,"
                     +
                             tables.PrayerTime.SubhaSadiq +TEXT_TYPE
                     +COMMA_SEP+
                             tables.PrayerTime.Fajar_J+TEXT_TYPE
                             +COMMA_SEP+
                             tables.PrayerTime.DATE+TEXT_TYPE
                             +COMMA_SEP+
                             tables.PrayerTime.Sunrise+TEXT_TYPE
                             +COMMA_SEP+
                             tables.PrayerTime.Zohar_b+TEXT_TYPE
                             +COMMA_SEP+
                             tables.PrayerTime.Zohar_j+TEXT_TYPE
                             +COMMA_SEP+
                             tables.PrayerTime.Asar_b+TEXT_TYPE
                             +COMMA_SEP+
                             tables.PrayerTime.Asar_j+TEXT_TYPE
                             +COMMA_SEP+
                             tables.PrayerTime.Maghrib_b+TEXT_TYPE
                             +COMMA_SEP+
                             tables.PrayerTime.Maghrib_j+TEXT_TYPE
                             +COMMA_SEP+
                             tables.PrayerTime.Esha_b+TEXT_TYPE
                             +COMMA_SEP+
                             tables.PrayerTime.Esha_j+TEXT_TYPE
                             +COMMA_SEP+
                             tables.PrayerTime.Type+TEXT_TYPE
                     +")";


        }

    }
}
