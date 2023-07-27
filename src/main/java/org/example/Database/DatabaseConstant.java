package org.example.Database;

class DatabaseConstant {

    static final String DATABASE_FILE_NAME = "database.db";

    static final String TABLE_USER = "USER";
    static final String TABLE_USER_SCHEMA = "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
            "FIRST_NAME TEXT," +
            "LAST_NAME TEXT," +
            "EMAIL TEXT," +
            "PASSWORD TEXT," +
            "ROLE TEXT," +
            "UNIQUE (ID, EMAIL)";


}
