package com.bam.note_v2.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class RepostStatusManager {

    private  boolean[] __statusData;
    private final SharedPreferences __sharedPreferences;


    public RepostStatusManager(Context context)
    {
        __sharedPreferences = context.getSharedPreferences("repost", Context.MODE_PRIVATE);
    }

    public void isRepostSuccess(int position)
    {
        getStatusData()[position] = true;
        SharedPreferences.Editor _editor = __sharedPreferences.edit();
        _editor.putBoolean(String.valueOf(position), __statusData[position]);
        _editor.apply();

    }


    public boolean[] getStatusData()
    {
        if (__statusData == null)
        {
            __statusData = new boolean[5];

            for (int i = 0; i< 5; i ++)
            {
                __statusData[i] = __sharedPreferences.getBoolean(String.valueOf(i), false);
            }
        }

        return __statusData;
    }

}
