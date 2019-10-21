/**
 * Created on Sun May 26 21:36:31 ICT 2019
 * HeartCore Robo Desktop v5.0.1 (Build No. 5.0.1-20190308.1)
 **/
package scripts;
import com.tplan.robot.scripting.*;
import com.tplan.robot.*;
import java.awt.*;

import java.net.*;
import java.io.*;
import java.util.*;
import java.text.*;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.swing.text.html.parser.DTD;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class SaveWebPageToFile extends DefaultJavaTestScript  {

    public void test() {
        try {

            String filename = "temp.html";
            String path = this.getClass().getClassLoader().getResource("").getPath();
            String fullPath = URLDecoder.decode(path, "UTF-8");
            fullPath = fullPath.replace("classes/",  "html_file/" + filename);

            File file = new File(fullPath);
            if(file.exists()){
                boolean success = file.delete();
            }

            URL u = new URL("https://ascii.jp/search/?cx=004761988070997154717%3Akxpchiveidg&cof=FORID%3A11&q=AI&sa=%E6%A4%9C%E7%B4%A2");

            OutputStream out = new FileOutputStream(fullPath);
            InputStream in = u.openStream();
            DTD html = DTD.getDTD("html");
            //System.out.println(html.getName());

            getContext().setVariable("check_detail", "html: " + html.getName());

            in.close();
            out.flush();
            out.close();

        } catch (StopRequestException ex) {
            throw ex;
        } catch (Exception e) {
            System.err.println("Usage: java PageSaver url local_file");
        }
    }
   
    public static void main(String args[]) {
        SaveWebPageToFile script = new SaveWebPageToFile();
        ApplicationSupport robot = new ApplicationSupport();
        AutomatedRunnable runnable = robot.createAutomatedRunnable(script, "SaveWebPageToFile@" + Integer.toHexString(script.hashCode()), args, System.out, false);
        new Thread(runnable).start();
    }
}
