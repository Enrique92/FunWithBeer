package com.example.kike.funwithbeers.connectionSql;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.kike.funwithbeers.models.Beer;
import com.example.kike.funwithbeers.models.Brand;
import com.example.kike.funwithbeers.models.Flag;

import java.util.ArrayList;

public class DataBaseAccess {
    private SQLiteOpenHelper mOpenHelper;
    private SQLiteDatabase mDatabase;
    private static DataBaseAccess mInstance;
    private Cursor mCursor = null;
    private ArrayList<Beer> dataBeer;
    private ArrayList<Brand> dataBrand;
    private ArrayList<String> dataTypesBeers;

    private DataBaseAccess(Context mContext) {
        this.mOpenHelper = new DataBaseOpenHelper(mContext);
    }

    // Get the instance of the DataBase
    public static DataBaseAccess getInstance(Context mContext) {
        if (mInstance == null) {
            mInstance = new DataBaseAccess(mContext);
        }

        return mInstance;
    }

    // Open the connection to the DB
    public void openDataBaseConnection() {
        this.mDatabase = mOpenHelper.getWritableDatabase();
    }

    // Close the connection
    public void closeDataBaseConnection() {
        if (mDatabase == null) {
            this.mDatabase.close();
        }
    }

    /**
     * Get the info of the flags
     *
     * @param regionShortName
     * @return
     */
    public ArrayList<Flag> getInfoFlag(String regionShortName) {
        // ArrayList to save the information of the select
        ArrayList<Flag> dataFlag = new ArrayList<>();

        // Clean the cursor
        mCursor = null;

        mCursor = mDatabase.rawQuery("select * from "
                + Utilities.TABLE_FLAG + " where "
                + Utilities.FIELD_REGION_SHORT_NAME + " = '" + regionShortName + "' order by "
                + Utilities.FIELD_NAME + " ASC", new String[]{});

        while (mCursor.moveToNext()) {
            int mId = mCursor.getInt(0);
            String mName = mCursor.getString(1);
            String mRegion = mCursor.getString(2);
            String mRegionShortName = mCursor.getString(3);
            String mShortName = mCursor.getString(4);
            String url = mCursor.getString(5);
            dataFlag.add(new Flag(mId, mName, mShortName, mRegion, mRegionShortName, url));
        }

        return dataFlag;
    }

    /**
     * Get the info of the beers
     *
     * @param countryName
     * @return
     */
    public ArrayList<Beer> getInfoBeer(String countryName) {
        // ArrayList to save the information of the select
        dataBeer = new ArrayList<>();

        // Clean the cursor
        mCursor = null;

        mCursor = mDatabase.rawQuery("select * from "
                + Utilities.TABLE_BEER + " where "
                + Utilities.FIELD_COUNTRY_BEER + " = '" + countryName + "' order by "
                + Utilities.FIELD_NAME_BEER + " ASC", new String[]{});

        while (mCursor.moveToNext()) {
            int mId = mCursor.getInt(0);
            String mName = mCursor.getString(1);
            String mCountry = mCursor.getString(2);
            String mType = mCursor.getString(3);
            String mABV = mCursor.getString(4);
            String mDesc = mCursor.getString(5);
            float mScore = mCursor.getFloat(6);
            String mURL = mCursor.getString(7);
            dataBeer.add(new Beer(mId, mName, mCountry, mType, mABV, mDesc, mScore, mURL));
        }

        return dataBeer;
    }

    /**
     * Get the info of the brands
     *
     * @return
     */
    public ArrayList<Brand> getInfoBrand() {
        // ArrayList to save the information of the select
        dataBrand = new ArrayList<>();

        // Clean the cursor
        mCursor = null;

        mCursor = mDatabase.rawQuery("select * from "
                + Utilities.TABLE_BRAND + " order by "
                + Utilities.FIELD_NAME_BRAND + " ASC", new String[]{});

        while (mCursor.moveToNext()) {
            int mId = mCursor.getInt(0);
            String mName = mCursor.getString(1);
            String mFounded = mCursor.getString(2);
            String mCountry = mCursor.getString(3);
            String mDesc = mCursor.getString(4);
            float mLat = mCursor.getFloat(5);
            float mLog = mCursor.getFloat(6);
            String mURL = mCursor.getString(7);
            dataBrand.add(new Brand(mId, mName, mFounded, mCountry, mDesc, mLat, mLog, mURL));
        }

        return dataBrand;
    }

    /**
     * Get the number of countries that a continent have
     *
     * @param nameCont
     * @return
     */
    public int getNumCou(String nameCont) {
        // Clean the cursor
        mCursor = null;

        mCursor = mDatabase.rawQuery("select * from "
                + Utilities.TABLE_FLAG + " where "
                + Utilities.FIELD_REGION_SHORT_NAME + " = '" + nameCont + "'", new String[]{});

        return mCursor.getCount();
    }

    /**
     * Search the beers for type or continent
     *
     * @return
     */
    public ArrayList<String> getTypeOfBeers() {
        // ArrayList to save the information of the select
        dataTypesBeers = new ArrayList<>();

        // Clean the cursor
        mCursor = null;

        mCursor = mDatabase.rawQuery("select distinct "
                + Utilities.FIELD_TYPE_BEER + " from "
                + Utilities.TABLE_BEER + " order by "
                + Utilities.FIELD_TYPE_BEER + " ASC", new String[]{});

        while (mCursor.moveToNext()) {
            String mType = mCursor.getString(0);
            dataTypesBeers.add(mType);
        }

        return dataTypesBeers;
    }

    /**
     * Show the beers from type
     *
     * @param type
     * @return
     */
    public ArrayList<Beer> getBeersFromType(String type) {
        // ArrayList to save the information of the select
        dataBeer = new ArrayList<>();

        // Clean the cursor
        mCursor = null;

        mCursor = mDatabase.rawQuery("select * from "
                + Utilities.TABLE_BEER + " where "
                + Utilities.FIELD_TYPE_BEER + " = '" + type + "'" + " order by "
                + Utilities.FIELD_NAME_BEER + " ASC", new String[]{});

        while (mCursor.moveToNext()) {
            int mId = mCursor.getInt(0);
            String mName = mCursor.getString(1);
            String mCountry = mCursor.getString(2);
            String mType = mCursor.getString(3);
            String mABV = mCursor.getString(4);
            String mDesc = mCursor.getString(5);
            float mScore = mCursor.getFloat(6);
            String mURL = mCursor.getString(7);
            dataBeer.add(new Beer(mId, mName, mCountry, mType, mABV, mDesc, mScore, mURL));
        }

        return dataBeer;
    }

    /**
     * Update the info in the Database
     *
     * @param scoreUser
     * @param idBeer
     */
    public void updateScore(float scoreUser, int idBeer) {
        // Clean the cursor
        mCursor = null;

        mCursor = mDatabase.rawQuery("update "
                + Utilities.TABLE_BEER + " set "
                + Utilities.FIELD_SCORE_BEER + " = '" + scoreUser + "' where "
                + Utilities.FIELD_ID_BEER + " = '" + idBeer + "'", new String[]{});

        // Update the changes in the Database
        mCursor.moveToFirst();
        // Close the Database to stage all the scores
        mCursor.close();

        Log.i("TAG", "Update score:" + scoreUser);
    }
}
