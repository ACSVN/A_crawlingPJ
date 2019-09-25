/**
 * Created on Fri May 24 12:12:00 ICT 2019
 * HeartCore Robo Desktop v5.0.1 (Build No. 5.0.1-20190308.1)
 **/
package JP;
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

public class GetHref extends DefaultJavaTestScript  {

    public Map<String, String> map_item = new HashMap<String, String>();
    public ArrayList<String> arr_hrefs = new ArrayList<String>();
    public ArrayList<String> arr_name_com = new ArrayList<String>();
    public ArrayList<String> arr_title = new ArrayList<String>();

    public void test() {
        try {
            String str_url = getContext().getVariableAsString("url");
            String web_name = getContext().getVariableAsString("web_name");
            String out_file = getContext().getVariableAsString("out_file");
            String path_file_out_put = getContext().getVariableAsString("str_output");

            String class_href = getContext().getVariableAsString("class_href");
            String class_title = getContext().getVariableAsString("class_title");
            String class_date_post = getContext().getVariableAsString("class_date_post");
            String class_source = getContext().getVariableAsString("class_source");
            String class_content = getContext().getVariableAsString("class_content");
            String class_next_page = getContext().getVariableAsString("class_next_page");
            String domain = getContext().getVariableAsString("domain");
            String replace_nxt_p = getContext().getVariableAsString("replace_nxt_p");
            String case_display = getContext().getVariableAsString("case_display");
            String class_tr_dl = getContext().getVariableAsString("class_tr_dl");
            String keyword = getContext().getVariableAsString("keyword");

            String href_file_name = "href.xlsx";
            this.deleteFile(href_file_name);

            String fileNameDS = "data_storge/" + web_name.toLowerCase() + ".xlsx";
            String sheetName = keyword;

            ArrayList<String> href_storge = this.loadDataToArrayList(sheetName, fileNameDS, 0);

            int ipage = 1;
            this.arr_hrefs = new ArrayList<String>();
            
            this.getHrefFromHTML(class_href,  class_title, domain, href_storge);

            this.copyFile(href_file_name);

            //get top 20
            this.arr_hrefs = this.GetTop20(this.arr_hrefs);
            this.arr_title = this.GetTop20(this.arr_title);

            this.writeListHrefToFile(arr_hrefs, arr_title, href_file_name);
            //getContext().setVariable("check_detail", "total_page count: " +  this.arr_hrefs.size());
            getContext().setVariable("total_href", this.arr_hrefs.size());
        } catch (StopRequestException ex) {
            throw ex;
        } 
    }
    
    public ArrayList<String> GetTop20(ArrayList<String> arr_str){
        ArrayList<String> arr_top20 = new ArrayList<String>();
        int loop = 5;
        for(int i = 0; (i < arr_str.size()) && (i < loop); i++){
          arr_top20.add(arr_str.get(i));
        }
        int count = arr_top20.size();
        while(count < 20){
           arr_top20.add("null");
           count++;
        }

        //top 1
        //for(int i = 0; (i < arr_str.size()) && (i < 5); i++){
            //arr_top20.add(arr_str.get(i));
        //}
        //int count = arr_top20.size();
        //while(count < 20){
            //arr_top20.add("null");
            //count++;
        //}
        return arr_top20;
    }

    public void getHrefFromHTML(String class_href, String class_title, String domain, ArrayList<String> href_storge){
        try {
            String web_name = getContext().getVariableAsString("web_name");
            getContext().setVariable("check_detail", "class_href11111111111: " + class_href);
            String path = this.getClass().getClassLoader().getResource("").getPath();
            String fullPath = URLDecoder.decode(path, "UTF-8");
            //fullPath = fullPath.replace("classes/",  "html_file");
            if(fullPath.indexOf("classes") >= 0){
                fullPath = fullPath.replace("classes/",  "html_file");
            }
            if(fullPath.indexOf("src") >= 0){
                fullPath = fullPath.replace("src/",  "html_file");
            }

            File folder = new File(fullPath);
            File[] listOfFiles = folder.listFiles();
            String content = "";

            for (int i = 0; i < listOfFiles.length; i++) {
                //String content = Jsoup.parse(new File(fullPath), "UTF-8").toString();
                File file = listOfFiles[i];
                if (file.isFile() && file.getName().endsWith(".html")) {
                    String path_file_html = fullPath + "/" + file.getName();
                    content = Jsoup.parse(new File(path_file_html), "UTF-8").toString();
                    getContext().setVariable("check_detail", "content: " + content);
                    Document doc = Jsoup.parse(content);

                    String arr_class_href[] = class_href.split(",");
                    String arr_class_title[] = class_title.split(",");

                    for(int cls = 0; cls < arr_class_href.length; cls++) {
                        Elements hrefs = doc.select(arr_class_href[cls]);
                        Elements titles = doc.select(arr_class_title[cls]);

                        for(int j = 0; j <  hrefs.size(); j++){
                            String str_url_tmp = "";
                            String title_tmp = titles.get(j).text();
                                                 
                            if(web_name.indexOf("itmedia") >= 0){
                                str_url_tmp = hrefs.get(j).attr("href");
                            }else{
                                if(hrefs.get(j).attr("href").indexOf(domain) < 0 && hrefs.get(j).attr("href").indexOf("tech.ascii.jp") < 0 && hrefs.get(j).attr("href").indexOf("special.nikkeibp.co.jp") < 0 && hrefs.get(j).attr("href").indexOf("https://active.nikkeibp.co.jp") < 0 ){
                                    str_url_tmp = domain + hrefs.get(j).attr("href");
                                }else{
                                    str_url_tmp = hrefs.get(j).attr("href");
                                }
                            }

                            if(!href_storge.contains(str_url_tmp) && !this.arr_hrefs.contains(str_url_tmp) && (str_url_tmp.indexOf("/tag/") < 0) && (str_url_tmp.indexOf("page=") < 0)){
                                this.arr_hrefs.add(str_url_tmp);
                                this.arr_title.add(title_tmp);
                            }
                        }
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
    
    public ArrayList<String> loadDataToArrayList(String sheetName, String fileName, int num_col){
        try{
            ArrayList<String> arr_data = new ArrayList<String>();

            //String basePath = new File(fileName).getAbsolutePath();
            String path = this.getClass().getClassLoader().getResource("").getPath();
            String fullPath = URLDecoder.decode(path, "UTF-8");
            //fullPath = fullPath.replace("classes/", fileName);
            if(fullPath.indexOf("classes") >= 0){
                fullPath = fullPath.replace("classes/",  fileName);
            }
            if(fullPath.indexOf("src") >= 0){
                fullPath = fullPath.replace("src/",  fileName);
            }

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
   
    public void writeListHrefToFile(ArrayList<String> arr_hrefs, ArrayList<String> arr_titles, String name_file){
        try{
            String path = this.getClass().getClassLoader().getResource("").getPath();
            String fullPath = URLDecoder.decode(path, "UTF-8");
            //fullPath = fullPath.replace("classes/",  "src/" + name_file);
            if(fullPath.indexOf("classes") >= 0){
                fullPath = fullPath.replace("classes/",  "src/" + name_file);
            }else if(fullPath.indexOf("src") >= 0){
                fullPath = fullPath.replace("src/",  "src/" + name_file);
            }
            getContext().setVariable("check_detail", "fullPath: " + fullPath);
            //String fullPath  = this.deleteContentOfSheet(name_file);

            File excelFile = new File(fullPath);
            FileInputStream fis = new FileInputStream(excelFile);

            // we create an XSSF Workbook object for our XLSX Excel File
            XSSFWorkbook workbook = new XSSFWorkbook(fis);

            // choose sheet name
            XSSFSheet sheet = workbook.getSheet("Sheet1");
            int lastRow=sheet.getLastRowNum();
            getContext().setVariable("check_detail", "lastRow: " + lastRow);
            int length = arr_hrefs.size();
            getContext().setVariable("check_detail", "length: " + length);
            for(int i = 0; i < arr_hrefs.size(); i++){
                Row row = sheet.createRow(++lastRow);
                row.createCell(0).setCellValue(arr_hrefs.get(i));
                row.createCell(1).setCellValue(arr_titles.get(i));
            }
            fis.close();
            FileOutputStream output_file =new FileOutputStream(new File(fullPath));
            //write changes
            workbook.write(output_file);
            output_file.close();
            getContext().setVariable("content", "write data href success");

        }catch(Exception e){
            getContext().setVariable("content", "write data href error. " + e.toString());
        }
    }
    
    public String copyFile(String name_file){
        try {
            String path = this.getClass().getClassLoader().getResource("").getPath();
            String fullPath = URLDecoder.decode(path, "UTF-8");
            //fullPath = fullPath.replace("classes/", name_file);
            if(fullPath.indexOf("classes") >= 0){
                fullPath = fullPath.replace("classes/",  name_file);
            }
            if(fullPath.indexOf("src") >= 0){
                fullPath = fullPath.replace("src/",  name_file);
            }

            FileInputStream fis = new FileInputStream(fullPath);

            String fullPathHref = fullPath.replace(name_file, "src/" + name_file);

            OutputStream fout = new FileOutputStream(fullPathHref);
            getContext().setVariable("content", fullPathHref);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                fout.write(buffer, 0, length);
            }

            fis.close();
            fout.close();
            return fullPathHref;
        }catch(IOException e){
            getContext().setVariable("content", "copy file excel error. " + e.toString());
            return "";
        }
    }
  
    public void deleteFile(String name_file){
        try {
            String path = this.getClass().getClassLoader().getResource("").getPath();
            String fullPath = URLDecoder.decode(path, "UTF-8");
            //fullPath = fullPath.replace("classes/", name_file);
            if(fullPath.indexOf("classes") >= 0){
                fullPath = fullPath.replace("classes/",  name_file);
            }
            if(fullPath.indexOf("src") >= 0){
                fullPath = fullPath.replace("src/",  name_file);
            }

            String fullPathHref = fullPath.replace(name_file, "src/" + name_file);

            File file = new File(fullPathHref);
            if(file.exists()){
                boolean success = file.delete();
            }
        }catch(IOException e){
            getContext().setVariable("content", "copy file excel error. " + e.toString());
        }
    }

    public  boolean isNullOrEmpty(String str) {
        if(str == null || str.isEmpty() || str == "" || str.equals("null"))
            return true;
        return false;
    }

    public ArrayList<String> mergeArrayString(ArrayList<String> arr_item, ArrayList<String> arr_dest){
        for(String item : arr_item){
            arr_dest.add(item);
        }
        return arr_dest;
    }
   
    public static void main(String args[]) {
        GetHref script = new GetHref();
        ApplicationSupport robot = new ApplicationSupport();
        AutomatedRunnable runnable = robot.createAutomatedRunnable(script, "GetHref@" + Integer.toHexString(script.hashCode()), args, System.out, false);
        new Thread(runnable).start();
    }
}
