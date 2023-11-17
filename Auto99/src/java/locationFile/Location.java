/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LocationFile;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.tomcat.util.http.fileupload.IOUtils;

/**
 *
 * @author Admin
 */
public class Location {

    //Ảnh chính cho web gồm(ảnh sản phẩm, ảnh blog, ảnh slider)
    public final String LOADIMAGETOFILEIMG = "img/";
    public final String LOADIMAGETOFILEIMGBLOG = "img/Blog/";
    public final String LOADIMAGETOFILEIMGCAR = "img/Xe/";
    public final String LOADIMAGETOFILEIMGBRAND = "img/Brand/";
    public final String LOADIMAGETOFILEIMGAUTOPART = "img/AutoPart/";
    public final String LOADIMAGETOFILEIMGEMPLOYEE = "img/EmployeeIMG/";
    //Ảnh linh tinh, ảnh người dùng( sử dụng trong quản lý người dùng, profile...)
    public final String LOADIMAGETOFILELOADIMG = "LoadImg/";

    public static StringBuilder getRelativePath(ServletContext context, String filePath) {
        String appPath = context.getRealPath("/");
        String link = convertPath(appPath + filePath);
        StringBuilder linkPath = editLocation(link);
        return linkPath;
    }
    
    public static StringBuilder getRelativePathFileBuild(ServletContext context, String filePath) {
        String appPath = context.getRealPath("/");
        String link = convertPath(appPath + filePath);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(link);
        return stringBuilder;
    }

    public static String convertPath(String path) {
        return path.replace("\\", "/");
    }

    public static StringBuilder editLocation(String path) {
        String[] splits = path.split("build/");
        StringBuilder stringBuilder = new StringBuilder();
        for (String item : splits) {
            stringBuilder.append(item);
        }
        return stringBuilder;
    }

    public void deleteImage(String URL) {
        File img = new File(URL);           //file to be delete  
        if (img.delete()) //returns Boolean value  
        {
            System.out.println(img.getName() + " deleted");   //getting and printing the file name  
        } else {
            System.out.println("failed");
        }
    }

    public void readImageLoaded(String absolutePath, String ImageUpload, Part partFileImageUpload) throws FileNotFoundException, IOException {
        OutputStream out = null;
        InputStream filecontent = null;
        File fileImageUpload = new File(absolutePath + ImageUpload);
        out = new FileOutputStream(fileImageUpload);
        filecontent = partFileImageUpload.getInputStream();
        int readImageUpload = 0;
        final byte[] bytesImageUpload = new byte[2048];
        while ((readImageUpload = filecontent.read(bytesImageUpload)) != -1) {
            out.write(bytesImageUpload, 0, readImageUpload);
        }
        IOUtils.closeQuietly(out);
    }

}
