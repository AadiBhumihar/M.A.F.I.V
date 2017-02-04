package com.example.bhumihar.mafiv;

/**
 * Created by bhumihar on 10/11/16.
 */
public class UserData  {


    String id ;
    String name;
    String sem;
    String branch ;
    private String password;

    public UserData(String id, String name, String sem, String branch, String password) {
        this.id = id;
        this.name = name;
        this.sem = sem;
        this.branch = branch;
        this.password = password;
    }

    public UserData(String id, String name, String sem, String branch) {
        this.id = id;
        this.name = name;
        this.sem = sem;
        this.branch = branch;
    }

    public String getPassword() {
        return password;
    }
}
