package com.example.bhumihar.mafiv;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by bhumihar on 10/11/16.
 */
public class SaveUserData {


    SharedPreferences user_data_h ;
    public static final String Shared_Pref_key = "User_Data";


    public SaveUserData(Context context) {

        user_data_h = context.getSharedPreferences(Shared_Pref_key , 0) ;

    }

    public void Store_User_Data(UserData user_data ) {

        SharedPreferences.Editor editor = user_data_h.edit();

        editor.putString("id" ,user_data.id);
        editor.putString("name" ,user_data.name);
        editor.putString("sem" , user_data.sem) ;
        editor.putString("branch",user_data.branch);
        editor.commit();
    }

    public UserData Logged_User_Data() {


        String l_userid = user_data_h.getString("id" , " ");
        String l_username = user_data_h.getString("name" ," ");
        String l_usersem = user_data_h.getString("sem"," ");
        String l_userbranch = user_data_h.getString("branch"," ");


        UserData Logged_User = new UserData(l_userid ,l_username ,l_usersem,l_userbranch);

        return Logged_User ;
    }

    public void LoggedIn (boolean logged) {

        SharedPreferences.Editor editor = user_data_h.edit();
        editor.putBoolean("logged_in",logged );
        editor.commit();

    }

    public boolean User_Logged_in() {

        if (user_data_h.getBoolean("logged_in",false)==true)
            return true ;
        else
            return false ;
    }

    public void Clear_Data () {
        SharedPreferences.Editor editor = user_data_h.edit();
        editor.clear();
        editor.commit();

    }


}
