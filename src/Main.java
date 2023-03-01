import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
private static final String url = "jdbc:postgresql://lt.uxcrowd.ru:54322/uxtest";
private static final String user = "student";
private static final String password = "f8rndk";
public static void main(String[] args) throws IOException {
        String query = "select * from pg_stat_statements where calls>10 order by mean_time desc limit 25";

        DBConnect db = new DBConnect(url, user, password, query);
        db.connect();
        BufferedWriter bw = new BufferedWriter(new FileWriter("C:\\Users\\User\\Desktop\\log.csv"));
        bw.write("datetime|query|mean\n");
        bw.write(db.getTable());
//        System.out.println(db.getTable());
    }
}
