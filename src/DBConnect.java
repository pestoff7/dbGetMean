import java.sql.*;
import java.util.Date;

public class DBConnect {
    private String url;
    private String user;
    private String password;
    private String query;
    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;
    private Date date;
    private int i = 0;

    public DBConnect(String url, String user, String password, String query) {
        this.url = url;
        this.user = user;
        this.password = password;
        this.query = query;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public void connect(){
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver is not found. Include it in your library path ");
            e.printStackTrace();
        }
        try {
            con = DriverManager.getConnection(this.url, this.user, this.password);
        }
        catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
//        finally {
//            try {
//                con.close();
//            } catch (SQLException se) {
//                se.printStackTrace();
//            }
//
//            try {
//                stmt.close();
//            } catch (SQLException se) {
//                se.printStackTrace();
//            }
//
//            try {
//                rs.close();
//            } catch (SQLException se) {
//                se.printStackTrace();
//            }
//        }
    }
    public String getTable(){
        StringBuilder executed = new StringBuilder();
        String singleString;
        try {
            while (true){
                date = new Date();
                stmt = con.createStatement();
                rs = stmt.executeQuery(this.query);
                while(rs.next()) {
                    executed.append(date.toString()+"|"+rs.getString(4).replace('\n', ' ').replaceAll("\\s\\s", " ")+"|"+rs.getFloat(9)+"\n");
                }
                Thread.sleep(1000L);
                i++;
                if (i == 5){
                    break;
                }
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        singleString = executed.toString();
        return singleString;
    }
}

