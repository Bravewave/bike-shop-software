package src.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QueryHandler {
    private static final String URL = "jdbc:mysql://stusql.dcs.shef.ac.uk/";
    private static final String DBNAME = "team051";
    private static final String USER = "team051";
    private static final String PASSWORD = "433dfa54";

    public Connection getConn() throws SQLException {
        Connection conn = DriverManager.getConnection(URL+DBNAME, USER, PASSWORD);
        // System.out.println("Connection successful!\n");
        return conn;
    }

    public List<String[]> queryDB(String query) throws SQLException{

        Connection conn = DriverManager.getConnection(URL+DBNAME, USER, PASSWORD);
        // System.out.println("Connection successful!\n");

        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(query);
        
        // System.out.println("rs: " + rs);

        // return a map of the results
        int nCol = rs.getMetaData().getColumnCount();
        List<String[]> table = new ArrayList<>();
        while( rs.next()) {
            String[] row = new String[nCol];
            for( int iCol = 1; iCol <= nCol; iCol++ ){
                Object obj = rs.getObject( iCol );
                row[iCol-1] = (obj == null) ?null:obj.toString();
            }
            table.add( row );
        }

        // System.out.println("closing connection");
        conn.close();
        return table;
    }

    public int updateDB(String query) throws SQLException{

        Connection conn = DriverManager.getConnection(URL+DBNAME, USER, PASSWORD);
        // System.out.println("Connection successful!\n");

        Statement st = conn.createStatement();

        // return the key of the inserted row
        int key = st.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
        ResultSet rs = st.getGeneratedKeys();
        if (rs.next()){
            key = rs.getInt(1);
        }

        // System.out.println("closing connection");
        conn.close();
        return key;
    }

    // public String returnQuery(String query) throws SQLException{
    //     ResultSet rs = queryDB(query);
    //     return rs.toString();
    // }
}
