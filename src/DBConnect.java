import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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
    private int countOfSnapshots;
    private int minutesOfLogCollector;

    public DBConnect(String url, String user, String password, String query, int countOfSnapshots, int minutesOfLogCollector) {
        this.url = url;
        this.user = user;
        this.password = password;
        this.query = query;
        this.countOfSnapshots = countOfSnapshots;
        this.minutesOfLogCollector = minutesOfLogCollector;
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

    public int getCountOfSnapshots() {
        return countOfSnapshots;
    }

    public void setCountOfSnapshots(int countOfSnapshots) {
        this.countOfSnapshots = countOfSnapshots;
    }

    public int getMinutesOfLogCollector() {
        return minutesOfLogCollector;
    }

    public void setMinutesOfLogCollector(int minutesOfLogCollector) {
        this.minutesOfLogCollector = minutesOfLogCollector;
    }

    public long getThreadSleep(int minutes){
        return minutes*60L*1000L/(long)getCountOfSnapshots();
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
    }
    public void getTable() throws IOException {
        StringBuilder executed = new StringBuilder();
        String singleString;
        int i = 0;
        BufferedWriter bw = new BufferedWriter(new FileWriter("C:\\Users\\User\\Desktop\\log.csv"));
        try {
            while (true){
                date = new Date();
                stmt = con.createStatement();
                rs = stmt.executeQuery(this.query);
                while(rs.next()) {
                    executed.append(date.toString()+"|"+rs.getString(4).replace('\n', ' ').replaceAll("\\s\\s", " ")+"|"+rs.getFloat(9)+"\n");
                }
                Thread.sleep(getThreadSleep(getMinutesOfLogCollector()));
                i++;
                if (i == this.countOfSnapshots){
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
        finally {
            try {
                con.close();
                stmt.close();
                rs.close();
            }
            catch (SQLException e){
                e.printStackTrace();
            }
        }
        singleString = executed.toString();

        bw.write("datetime|query|mean\n");
        bw.write(singleString);
        if (bw != null){
            bw.close();
        }
    }
}


