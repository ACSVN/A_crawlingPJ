/**
 * Created on Fri Oct 04 13:43:32 ICT 2019
 * HeartCore Robo Desktop v5.0.1 (Build No. 5.0.1-20190308.1)
 **/
package scripts;
import com.tplan.robot.scripting.*;
import com.tplan.robot.*;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
public class CoppyFolderHTML extends DefaultJavaTestScript  {
        public  void copy(File sourceLocation, File targetLocation) throws IOException {
        if (sourceLocation.isDirectory()) {
            copyDirectory(sourceLocation, targetLocation);
        } else {
            copyFile(sourceLocation, targetLocation);
        }
    }

    private  void copyDirectory(File source, File target) throws IOException {
        if (!target.exists()) {
            target.mkdir();
        }

        for (String f : source.list()) {
            copy(new File(source, f), new File(target, f));
        }
    }

    private  void copyFile(File source, File target) throws IOException {        
        try (
                InputStream in = new FileInputStream(source);
                OutputStream out = new FileOutputStream(target)
        ) {
            byte[] buf = new byte[1024];
            int length;
            while ((length = in.read(buf)) > 0) {
                out.write(buf, 0, length);
            }
        }
    }

   public void test() {
      try {
                  File srcDir = new File("D:\\RPA\\ANA\\ANA_201908\\ANA2_20190823\\data_page_source\\HTML_TEMPLATE");
                  File destDir = new File("D:\\RPA\\ANA\\ANA_201908\\ANA2_20190823\\data_page_source\\HTML");
                       try {
                             copyDirectory(srcDir, destDir);
                           } catch (IOException e) {
                                e.printStackTrace();
                           }

      } catch (StopRequestException ex) {
         throw ex;
      } 
   }
   
   public static void main(String args[]) {
      CoppyFolderHTML script = new CoppyFolderHTML();
      ApplicationSupport robot = new ApplicationSupport();
      AutomatedRunnable runnable = robot.createAutomatedRunnable(script, "CoppyFolderHTML@" + Integer.toHexString(script.hashCode()), args, System.out, false);
      new Thread(runnable).start();
   }
}
