/**
 * Created on Sat May 25 12:20:05 ICT 2019
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

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Header; 
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Href extends DefaultJavaTestScript  {

    public Map<String, String> map_item = new HashMap<String, String>();
    public ArrayList<String> arr_hrefs = new ArrayList<String>();
    public ArrayList<String> arr_name_com = new ArrayList<String>();
    public ArrayList<String> arr_title = new ArrayList<String>();

    public void test() {
        try {
            //String url_item = "https://japanese.engadget.com/search/?q=AI#stq=AI&stp=1";
            //String class_href = "#st-results-container > li > div > article > a";
            //String domain = "https://japanese.engadget.com";
            //String class_title = "#st-results-container > li > div > article > div > div > div > div > div > div.th-title";
            
            //String url_item = "http://ainow.ai/?s=AI";
            //String class_href = "#main > div > article > a";
            //String domain = "http://ainow.ai";
            //String class_title = "#main > div > article > a > section > h1";
            
            //String url_item = "https://gigazine.net/search/results/5ad78c1a5c127ed9369fcedae57387b9/";
            //String class_href = "#search_results > section > h2 > a";
            //String domain = "https://gigazine.net";
            //String class_title = "#search_results > section > h2 > a";
            
            //String url_item = "https://www.gizmodo.jp/search?q=AI";
            //String class_href = "#searchResultList > ul > li > article > div > h3 > a";
            //String domain = "https://www.gizmodo.jp";
            //String class_title = "#searchResultList > ul > li > article > div > h3 > a";
            
            //String url_item = "http://kensaku.itmedia.co.jp/?q=AI&fq=all&sort=desc";
            //String class_href = "#popin_content_main > ul > li > div.popIn_ArticleTitle > a";
            //String domain = "https://www.itmedia.co.jp";
            //String class_title = "#popin_content_main > ul > li > div.popIn_ArticleTitle > a";
            
            //String url_item = "https://japan.cnet.com/search/?ie=UTF-8&q=AI&num=100";
            //String class_href = "#___gcse_0 > div > div > div > div > div > div > div > div > div > div.gsc-thumbnail-inside > div > a,#___gcse_0 > div > div > div > div > div > div > div > div > div > div > div.gsc-thumbnail-inside > div > a";
            //String domain = "https://japan.cnet.com";
            //String class_title = "#___gcse_0 > div > div > div > div > div > div > div > div > div > div.gsc-thumbnail-inside > div > a,#___gcse_0 > div > div > div > div > div > div > div > div > div > div > div.gsc-thumbnail-inside > div > a";
            
            //String url_item = "https://kyodonewsprwire.jp/search?s=AI";
            //String class_href = "body > main > div > div > div > div > div > h4 > a";
            //String domain = "https://kyodonewsprwire.jp";
            //String class_title = "body > main > div > div > div > div > div > h4 > a";
    
            //String url_item = "https://www.moguravr.com/?s=AI";
            //String class_href = "body > div.mg-main > div > div > div.column.is-8.mg-content > a";
            //String domain = "https://www.moguravr.com";
            //String class_title = "body > div.mg-main > div > div > div.column.is-8.mg-content > a > div > div > h4";
            //
            //String url_item = "https://techable.jp/?s=AI";
            //String class_href = "body > div> div > div > div > div > div > div > div > div > div> section > a";
            //String domain = "https://techable.jp";
            //String class_title = "body > div> div > div > div > div > div > div > div > div > div> section > a";
            String url_item = "https://ainow.ai/?s=%E3%83%8F%E3%83%97%E3%83%86%E3%82%A3%E3%82%AF%E3%82%B9";
            String class_href = "#left_col > div > ul > li";
            String domain = "https://sorae.info";
            String class_title = "#left_col > div > ul > li > a";
            
            
            
            ArrayList<String> href_storge = new ArrayList<String>();

            this.getLinkItem(url_item, class_href, domain, class_title, href_storge);
            
                    //getContext().setVariable("check_detail", "total_page count: " +  this.arr_hrefs.size());
        } catch (StopRequestException ex) {
            throw ex;
        }
    }
   
   public void getLinkItem(String url_item, String class_href, String domain, String class_title, ArrayList<String> href_storge){
        //ArrayList<String> arr_temp = new ArrayList<String>();
        try {
            //URL u;
            //Scanner s;
            //String content = "";
            //u = new URL(url_item);
            //Document doc = Jsoup.connect(url_item).get();
            //String charset = doc.charset().toString();
            //getContext().setVariable("charset", charset);
            ////getContext().setVariable("check_detail", "url_item: " + url_item);
            //s = new Scanner(u.openStream(), charset);
            //while(s.hasNext()){
                //content = content + s.nextLine();
            //}
            //getContext().setVariable("check_detail", "content: " + content);
            
            URL u;
            Scanner s;
            String content = "";
            u = new URL(url_item);
            Document doc = Jsoup.connect(url_item).get();
            
            //Document doc = Jsoup.connect(url_item).userAgent("Mozilla/5.0").get();
            String charset = doc.charset().toString();
            getContext().setVariable("charset", charset);
            getContext().setVariable("check_detail", "url_item: " + url_item);
            
            Document docpage = Jsoup.connect(url_item).get();
            Elements page = docpage.select(class_href);
            int p=0;
            if(page.size() > 0){
                p=page.size();
            }
            
            getContext().setVariable("check_detail", "page: " +p);
            String arr_class_href[] = class_href.split(",");
            String arr_class_title[] = class_title.split(",");
            getContext().setVariable("check_detail", "arr_class_href: " +  arr_class_href.length);
                    //getContext().setVariable("check_detail", "doc: " +  doc.html());
            
            for(int cls = 0; cls < arr_class_href.length; cls++) {       
                Elements hrefs = doc.select(arr_class_href[cls].toString());
                Elements titles = doc.select(arr_class_title[cls].toString());
                //getContext().setVariable("check_detail", "doc: " +  doc.toString());
                for(int i = 0; i <  hrefs.size(); i++){
                    String str_url_tmp = "";
                    String title_tmp = titles.get(i).text();
                    if(hrefs.get(i).attr("href").indexOf(domain) < 0){
                        str_url_tmp = domain + hrefs.get(i).attr("href");
                        //arr_temp.add(domain + href.attr("href"));
                    }else{
                        str_url_tmp = hrefs.get(i).attr("href");
                        //arr_temp.add(href.attr("href"));
                    }
                    if(str_url_tmp.indexOf("/msg/?ty") > 0){
                        str_url_tmp = str_url_tmp.substring(0, str_url_tmp.indexOf("/msg/?ty"));
                    }else if(str_url_tmp.indexOf("/?ty") > 0){
                        str_url_tmp = str_url_tmp.substring(0, str_url_tmp.indexOf("/?ty"));
                    }else if(str_url_tmp.indexOf("_message/") > 0){
                        str_url_tmp = str_url_tmp.replace("_message/", "_detail/");
                    }else if(str_url_tmp.indexOf("/-tab__pr/") > 0){
                        str_url_tmp = str_url_tmp.replace("/-tab__pr/", "/-tab__jd/");
                    }else if(str_url_tmp.indexOf("nx1_") > 0){
                        str_url_tmp = str_url_tmp.replace("nx1_", "nx2_");
                        str_url_tmp = str_url_tmp.replace("&list_disp_no=1", "");
                        str_url_tmp = str_url_tmp.replace("n_ichiran_cst_n5_ttl", "ngen_tab-top_info");
                    }else{
                        str_url_tmp = str_url_tmp.replace(",", "");
                    }
                    if(!href_storge.contains(str_url_tmp)){
                        //this.map_item.put(name_com, str_url_tmp);
                        this.arr_hrefs.add(str_url_tmp);
                        this.arr_title.add(title_tmp);
                    }
                    
                }
            }

        } catch (StopRequestException ex) {
            getContext().setVariable("check_detail", "Error StopRequestException: " + ex.toString());
            throw ex;
        } catch(IOException e){
            getContext().setVariable("check_detail", "Error IOException: " + e.toString());
            throw new RuntimeException(e);
        }
    }
   
    public static void main(String args[]) {
        Href script = new Href();
        ApplicationSupport robot = new ApplicationSupport();
        AutomatedRunnable runnable = robot.createAutomatedRunnable(script, "Href@" + Integer.toHexString(script.hashCode()), args, System.out, false);
        new Thread(runnable).start();
    }
}
