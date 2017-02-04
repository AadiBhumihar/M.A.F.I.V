package com.example.bhumihar.mafiv;

/**
 * Created by bhumihar on 15/11/16.
 */
public class Notification_Data {

    String not_tit ;
    String not_tit_v ;
    String not_body ;
    String not_body_v ;
    String not_date ;
    String not_date_v ;
    String not_time ;
    String not_time_v ;
    int check;



    public Notification_Data(String not_tit_v, String not_body_v, String not_date_v, String not_time_v,int check) {
        this.not_tit_v = not_tit_v;
        this.not_body_v = not_body_v;
        this.not_date_v = not_date_v;
        this.not_time_v = not_time_v;
        this.check = check ;

        not_tit = "Tittle:";
        not_body = "Body:";
        not_date = "Date:";
        not_time = "Time:";

    }

}
