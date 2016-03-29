package com.example.gcm.db;

public class DBSqlData
{
    public static final String SQL_DB_CREATE_TABLE
            = "CREATE TABLE db_table_test " +
                "(reg_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "message TEXT NOT NULL)";

    public static final String SQL_DB_INSERT_DATA
            = "INSERT INTO db_table_test " +
                "(message) " +
                "VALUES (?)";

    public static final String SQL_DB_SELECT_ALL
            = "SELECT * " + "FROM db_table_test ORDER BY reg_id DESC";
}
