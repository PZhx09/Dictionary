package asynctask;

import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import entity.Word;

/**
 * Created by 55303 on 2017/4/27.
 */

//调用youdao线程，传入要翻译的单词，类的word存得到的结果
public class youdao extends Thread{
    private String Queryword;
    public Word word=new Word();
    public String json;
    private Handler handler;
    public youdao(String Queryword, Handler handler)
    {
        super();
        this.Queryword=Queryword;
        this.word=new Word();
        this.handler=handler;
        System.out.println("zzzzzz");
    }

    public void run() {
        try {

            System.out.println("xxxxx");
            URL url = new URL("http://fanyi.youdao.com/openapi.do");
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.addRequestProperty("encoding", "UTF-8");
            connection.setDoInput(true);
            connection.setDoOutput(true);

            connection.setRequestMethod("POST");

            OutputStream os = connection.getOutputStream();
            System.out.println("cccccc");
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);

            System.out.println("vvvvvv");
            bw.write("keyfrom=NKUDictionary&key=295824146&type=data&doctype=json&version=1.1&q="+Queryword);
            bw.flush();

            InputStream is = connection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is,"UTF-8");
            BufferedReader br = new BufferedReader(isr);
            System.out.println("bbbb");
            String line;
            StringBuilder builder = new StringBuilder();
            while((line = br.readLine()) != null){
                builder.append(line);
            }

            bw.close();
            osw.close();
            os.close();

            br.close();
            isr.close();
            is.close();
            System.out.println("nnnnnn");
            Gson gson=new Gson();
            System.out.println(builder.toString());
            json=builder.toString();
            json=json.replaceAll("-","_");
            System.out.println(json);
            word=gson.fromJson(json,Word.class);
            Message message=new Message();
            if (word!=null)
            {

                System.out.println("ssss");
                message.what = 0;
                message.obj = word;
                handler.sendMessage(message);
            }
            else
            {
                message.what = 1;
            }
           // System.out.println("qqqqqqqqqqqqqqqqqqqqqqqqqq"+word.getTranslation().get(0));

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
