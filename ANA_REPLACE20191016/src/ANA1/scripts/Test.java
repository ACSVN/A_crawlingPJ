/**
 * Created on Fri May 24 21:29:29 ICT 2019
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

public class Test extends DefaultJavaTestScript  {

    public void test() {
        //String str_url = "http://ainow.ai/2019/05/23/170449/";
        //String class_date_post = "#main > article > header > p > time";
        //String class_source = "null";
        //String class_content = "#main > article > section";
        
        //String str_url = "https://ascii.jp/elem/000/001/587/1587218/";
        //String class_date_post = "#articleHead > div.artdata > p.date";
        //String class_source = "null";
        //String class_content = "#mainC > p";
        
        //String str_url = "https://japanese.engadget.com/2019/05/23/fcc/";
        //String class_date_post = "body > div > div > div > main > div > div.grid.flex.fixto_top > div > article > header > div > div > section > div > div > div > div.th-meta";
        //String class_source = "#page_body > div > div > div > div > footer > div > div > div > section > div:nth-child(2) > span";
        //String class_content = "#page_body > div > div > div:nth-child(2) > div.o-article_block > div > div > div.article-text.c-gray-1";
        
        //String str_url = "https://www.gizmodo.jp/2019/05/huawei-p30-pro-us-review.html";
        //String class_date_post = "body > main > div > article > ul:nth-child(4) > li > time";
        //String class_source = "null";
        //String class_content = "body > main > div > article > div.p-post-content";
        
        String str_url = "https://newswitch.jp/p/17791";
        String class_date_post = "#contents > div > article > div.article_date > time";
        String class_source = "null";
        String class_content = "#contents > div > article > div.article_text";
              
        try {
            URL u;
            Scanner s;
            //String content = "";
            u = new URL(str_url);

            Document doc_charset = Jsoup.connect(str_url).get();
            String charset = doc_charset.charset().toString();

            String post_date = doc_charset.select(class_date_post).text();
            String source = doc_charset.select(class_source).text();
            Elements contents = doc_charset.select(class_content);

            String content = "";
            for(Element ele : contents){
                if(content.length() == 0){
                    content = content + ele.text();
                }else{
                    content = content + "\n\r" + ele.text();
                }
            }

            //String sheeName = "Sheet1";
            //this.writeDataToFile(path_write, sheeName, web_name, url_web, keyword, str_url, title, post_date, source, content);
            //getContext().setVariable("check_detail", "post_date: " + post_date + ", source: " + source + ", content: " + content);
            //this.writeDataToFile();
            
            String fileNameDS = "data_storge/ainow.xlsx";
            String sheetName = "AI";
            ArrayList<String> href_storge = this.loadDataToArrayList(sheetName, fileNameDS, 0);
            getContext().setVariable("check_detail", "data storage: " + href_storge.size());
    
        } catch (StopRequestException ex) {
            getContext().setVariable("check_detail", "error: " + str_url);
            throw ex;
        } catch(IOException e){
            getContext().setVariable("check_detail", "error: " + str_url);
            throw new RuntimeException(e);
        }
    }
    
    public ArrayList<String> loadDataToArrayList(String sheetName, String fileName, int num_col){
    try{
        ArrayList<String> arr_data = new ArrayList<String>();

        //String basePath = new File(fileName).getAbsolutePath();
        String path = this.getClass().getClassLoader().getResource("").getPath();
        String fullPath = URLDecoder.decode(path, "UTF-8");
        //fullPath = fullPath.replace("classes/", fileName);
        fullPath = fullPath.replace("src/", fileName);

        File excelFile = new File(fullPath);
        FileInputStream fis = new FileInputStream(excelFile);

        // we create an XSSF Workbook object for our XLSX Excel File
        XSSFWorkbook workbook = new XSSFWorkbook(fis);

        // choose sheet name
        XSSFSheet sheet = workbook.getSheet(sheetName);
        getContext().setVariable("check_detail", "Load data storge success. sheetName: " + sheetName);
        //we iterate on rows
        Iterator<Row> rowIt = sheet.iterator();

        while(rowIt.hasNext()) {
            Row row = rowIt.next();

            //get value of first column 0
            Cell cell = row.getCell(num_col);

            //add cell value to array string (storge data)
            arr_data.add(cell.getStringCellValue());
        }
        getContext().setVariable("check_detail", "Load data storge success.");
        fis.close();
        return arr_data;
    } catch(IOException e){
        getContext().setVariable("check_detail", "Load data storge error. " + e.toString());
        throw new RuntimeException(e);
    }
  }
  
    public void writeDataToFile(){
        try{
            String path_out_file = "D:/Quoc/2019/HeartCore/ANA/data_page_source/HTML/ainow/20190525_ainow.xlsx";
            String sheeName = "Sheet1";
            String url_web = "google.com";
            String keyword = "test test";
            String url_item = "";
            String title = "";
            String date_posts = "";
            String sources =  "";
            String contents = "";
            String web_name = "AAAAAAAAAAAA";

            String No = getContext().getVariableAsString("no_num");
            File excelFile = new File(path_out_file);
            FileInputStream fis = new FileInputStream(excelFile);

            // we create an XSSF Workbook object for our XLSX Excel File
            XSSFWorkbook workbook = new XSSFWorkbook(fis);

            // choose sheet name
            XSSFSheet sheet = workbook.getSheetAt(0);

            int lastRow = sheet.getLastRowNum();
            Row row = sheet.createRow(++lastRow);
            row.createCell(0).setCellValue(No);
            row.createCell(1).setCellValue(web_name);
            row.createCell(2).setCellValue(url_web);
            row.createCell(3).setCellValue(keyword);
            row.createCell(4).setCellValue(url_item);
            row.createCell(5).setCellValue(title);
            row.createCell(6).setCellValue(date_posts);
            row.createCell(7).setCellValue(sources);
            row.createCell(8).setCellValue(contents);

            fis.close();
            FileOutputStream output_file =new FileOutputStream(new File(path_out_file));
            //write changes
            workbook.write(output_file);
            output_file.close();

            getContext().setVariable("check_detail", "writeDataToFile success!!!!!!");
        }catch(Exception e){
            getContext().setVariable("check_detail", "write data content to excel file error. " + e.toString());
        }
    }
   
    public static void main(String args[]) {
        Test script = new Test();
        ApplicationSupport robot = new ApplicationSupport();
        AutomatedRunnable runnable = robot.createAutomatedRunnable(script, "Test@" + Integer.toHexString(script.hashCode()), args, System.out, false);
        new Thread(runnable).start();
    }
}
