package com.zapcorp.db;

import java.sql.Connection;

public interface DBConnection {

    Connection getConnection();
}
