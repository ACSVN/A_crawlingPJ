/**
 * Created on Sun May 26 21:19:15 ICT 2019
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

import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;


public class LoadHtml extends DefaultJavaTestScript  {

    public void test() {
        try {
            String filename = "temp.html";
            String path = this.getClass().getClassLoader().getResource("").getPath();
            String fullPath = URLDecoder.decode(path, "UTF-8");
            fullPath = fullPath.replace("classes/",  "html_file");
            
            File folder = new File(fullPath);
            File[] listOfFiles = folder.listFiles();
            String content = "";
            for (int i = 0; i < listOfFiles.length; i++) {
                //String content = Jsoup.parse(new File(fullPath), "UTF-8").toString();
                File file = listOfFiles[i];
                if (file.isFile() && file.getName().endsWith(".html")) {
                    String path_file_html = fullPath + "/" + file.getName();
                    content = Jsoup.parse(new File(path_file_html), "UTF-8").toString();
                }
            }
            getContext().setVariable("check_detail", "content: " + content);
        } catch (StopRequestException ex) {
            getContext().setVariable("check_detail", "Error StopRequestException: " + ex.toString());
            throw ex;
        } catch(IOException e){
            getContext().setVariable("check_detail", "Error IOException: " + e.toString());
            throw new RuntimeException(e);
        }
    }
   
    public static void main(String args[]) {
        LoadHtml script = new LoadHtml();
        ApplicationSupport robot = new ApplicationSupport();
        AutomatedRunnable runnable = robot.createAutomatedRunnable(script, "LoadHtml@" + Integer.toHexString(script.hashCode()), args, System.out, false);
        new Thread(runnable).start();
    }
}
