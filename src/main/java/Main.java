import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main {
    public static void main(String[] args) throws IOException {
        String key = "";
        String to = "";

        URL url = new URL("https://fcm.googleapis.com/fcm/send");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setUseCaches(false);
        conn.setDoInput(true);
        conn.setDoOutput(true);

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization","key=" + key);
        conn.setRequestProperty("Content-Type","application/json");

        JSONObject json = new JSONObject();
        json.put("to", to);
        JSONObject info = new JSONObject();
        info.put("title", "Title");
        info.put("body", "Body");
        json.put("notification", info);

        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

        try(BufferedWriter buffer = new BufferedWriter(wr)) {
            buffer.write(json.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        InputStreamReader rr = new InputStreamReader(conn.getInputStream());

        try(BufferedReader buffer = new BufferedReader(rr)) {
            String line;
            while ((line = buffer.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        wr.close();
        rr.close();

        conn.disconnect();
    }
}
