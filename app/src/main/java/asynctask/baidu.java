package asynctask;

/**
 * Created by a on 2017/5/29.
 */

import android.os.Handler;
import android.os.Message;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;

/**
 * Created by 55303 on 2017/5/29.
 */

public class baidu extends Thread {

    Handler handler;
    String Queryword;
    public baidu(String Queryword, Handler handler )
    {
        this.handler=handler;
        this.Queryword=Queryword;
    }
    public void run() {
        //返回0成功查询，返回1错误
        try {

            URL url = new URL("http://api.fanyi.baidu.com/api/trans/vip/translate");
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

            bw.write("q="+Queryword+"&from=en&to=zh&appid=20170529000049521&salt=65535&sign="+getMD5("20170529000049521"+Queryword+"65535E20aRaGzdUHdK8zvomka"));
            bw.flush();

            System.out.println("ssssss:"+getMD5("20170529000049521"+Queryword+"65535E20aRaGzdUHdK8zvomka"));
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
            System.out.println(builder.toString());
            String result=builder.toString();

            result=result.substring(result.indexOf("dst")+6,result.indexOf('}')-1);


            StringBuffer string = new StringBuffer();

            String[] hex = result.split("\\\\u");

            for (int i = 1; i < hex.length; i++) {

                // 转换出每一个代码点
                int data = Integer.parseInt(hex[i], 16);

                // 追加成string
                string.append((char) data);
            }

            System.out.println("hh:"+string);

            Message message=new Message();

            message.what = 0;
            message.obj=string;
            handler.sendMessage(message);


        } catch (MalformedURLException e) {
            Message message=new Message();

            message.what = 1;
            handler.sendMessage(message);
            e.printStackTrace();
        } catch (IOException e) {
            Message message=new Message();

            message.what = 1;
            handler.sendMessage(message);
            e.printStackTrace();
        }
    }
    public static String getMD5(String str) {
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(str.getBytes());
            // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            System.out.println("md5error");
        }
        return "error";
    }
}
