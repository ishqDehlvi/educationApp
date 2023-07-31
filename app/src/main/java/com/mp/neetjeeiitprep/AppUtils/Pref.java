package com.mp.neetjeeiitprep.AppUtils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class Pref {

    private Context context;
    private String PREF_FILE;
    private SharedPreferences.Editor mPrefEditor;
    private SharedPreferences pref;

    public Pref(Context context) {
        this.context=context;
        PREF_FILE=context.getPackageName();
        pref=context.getSharedPreferences(PREF_FILE,Context.MODE_PRIVATE);
        mPrefEditor=pref.edit();
    }

    public void putString(String key,String value){
        mPrefEditor.putString(key,value);
        mPrefEditor.apply();
    }

    public void putBoolean(String key,boolean value){
        mPrefEditor.putBoolean(key,value);
        mPrefEditor.apply();
    }
    public String getString(String key){
        return pref.getString(key,"");
    }

    public Boolean getBoolean(String key){
        return pref.getBoolean(key,false);
    }

    public void putMultiString(HashMap<String,String> hashMap){

        for(String key:hashMap.keySet()){
            mPrefEditor.putString(key,hashMap.get(key));
        }
        mPrefEditor.apply();
    }

    public void clearAll(){
        mPrefEditor.clear();
        mPrefEditor.apply();
    }

}
