package com.example.bhumihar.mafiv;

import android.util.Log;

/**
 * Created by bhumihar on 11/11/16.
 */

public class Json_Data {

    String[] value1;
    String[] value2 ;
    String[] value3;
    String[] value4 ;
    int[]  value5 ;

    public Json_Data(String[] value1, String[] value2, String[] value3, String[] value4, int[] value5) {
        this.value1 = value1;
        this.value2 = value2;
        this.value3 = value3;
        this.value4 = value4;
        this.value5 = value5;
    }

    public Json_Data(String[] value1, String[] value2, String[] value3, String[] value4) {
        this.value1 = value1;
        this.value2 = value2;
        this.value3 = value3;
        this.value4 = value4;
    }
}
