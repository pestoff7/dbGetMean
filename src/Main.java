import java.io.IOException;

public class Main {
private static final String url = "jdbc:postgresql://lt.uxcrowd.ru:54322/uxtest";
private static final String user = "student";
private static final String password = "f8rndk";
public static void main(String[] args) throws IOException {
        String query = "select * from pg_stat_statements where calls>10 order by mean_time desc limit 25";

        DBConnect db = new DBConnect(args[0], args[1], args[2], args[3], Integer.parseInt(args[4]), Integer.parseInt(args[5]));
        db.connect();
        db.getTable();
    }
}
