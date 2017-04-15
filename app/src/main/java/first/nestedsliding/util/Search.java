package first.nestedsliding.util;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by dell on 2016/10/25.
 */
public class Search extends AsyncTask<Void,Void,Void>{

    private static String url = "http://pingtcss.qq.com/pingd?dm=lol.qq.com&pvi=7594039296&si=s618103808&url=/web201310/info-defail.shtml&arg=id%3DAatrox&ty=1&rdm=lol.qq.com&rurl=/web201310/info-heros.shtml&rarg=&adt=&r2=22024406&r3=-1&r4=1&fl=20.0&scr=1366x768&scl=24-bit&lg=zh-cn&jv=&tz=-8&ct=&ext=adid=&pf=&random=1477407310298";
    private String str;
    @Override
    protected Void doInBackground(Void... params) {

        Log.d("TAG","doInBackground");
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            if(connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStreamReader reader = new InputStreamReader(connection.getInputStream(),"GB2312");
                StringBuilder sb = new StringBuilder();
                int i;
                while ((i = reader.read()) != -1) {
                    sb.append((char) i);
                }
                str = sb.toString();
                Log.d("TAG",sb.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

    }
}
