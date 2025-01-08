package ps.demo.upload.test;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Md5Test {

    public static void main(String [] args) throws IOException {
        File file = new File("C:\\Users\\yysyp\\Downloads\\chromium-win64.zip");
        try (FileInputStream fis = new FileInputStream(file)) {
            String md5 = DigestUtils.md5Hex(fis);
            System.out.println("md5 = " + md5);
        }

    }


}
