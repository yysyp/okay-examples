package ps.demo.jpademo.test;

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;

import java.util.Scanner;

public class JasyptTest {

    public static void main(String[] args) {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        Scanner in = new Scanner(System.in);
        String JasyptPass = in.nextLine();
        in.close();
        System.out.println("Your input JasyptPass=["+JasyptPass+"]");
        config.setPassword(JasyptPass);
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setStringOutputType("base64");
        encryptor.setConfig(config);

        String encrypted = encryptor.encrypt("Hello world, this is app pass");
        System.out.println("==>>ENC(" + encrypted + ")");
    }

}
