package com.liyh.excelfile.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangwenjun
 * @version , v 1.0 2022/12/5 16:20 zhangwenjun
 */
@Slf4j
public class AesUtils {

    /**
     * 算法/模式/补码方式
     */
    public static final String CBC_ALGORITHM = "AES/CBC/PKCS5Padding";
    public static final String DEFAULT_IV = "1234567890123456";
    public static final String AES_ALGORITHM = "AES";
    public static final String CHARSET = "UTF-8";
    public static final String DB_KEY = "7h2cPI0OIEflPBWTXL/YdyMBnsDr+H818fPi42CmDGk=";
    public static final String PASSWORD = "DBPEaEliy3v9biyUVUGsOQ==";

    /**
     * aes加密
     *
     * @param password aes密码
     * @param content  待加密字符串
     * @return 加密字符串
     */
    public static String encrypt(String password, String content) {
        if (StringUtils.isBlank(content)) {
            return content;
        }
        if (StringUtils.isBlank(password)) {
            return null;
        }
        try {
            // 对密钥进行处理
            IvParameterSpec ivSpec = new IvParameterSpec(DEFAULT_IV.getBytes());
            byte[] enCodeFormat = org.apache.commons.codec.binary.Base64.decodeBase64(password);
            SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, AES_ALGORITHM);

            Cipher cipher = Cipher.getInstance(CBC_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivSpec);
            byte[] encryptedData = cipher.doFinal(content.getBytes(CHARSET));
            return org.apache.commons.codec.binary.Base64.encodeBase64String(encryptedData);
        } catch (Exception e) {
            return null;
        }

    }

    /**
     * aes解密
     *
     * @param password aes密码
     * @param content  待解密字符串
     * @return 解密字符串
     */
    public static String decrypt(String password, String content) {
        if (StringUtils.isBlank(content)) {
            return content;
        }
        if (StringUtils.isBlank(password)) {
            return null;
        }
        try {
            byte[] enCodeFormat = org.apache.commons.codec.binary.Base64.decodeBase64(password);
            SecretKeySpec spec = new SecretKeySpec(enCodeFormat, AES_ALGORITHM);
            IvParameterSpec ivSpec = new IvParameterSpec(DEFAULT_IV.getBytes());
            Cipher cipher = Cipher.getInstance(CBC_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, spec, ivSpec);
            byte[] result = cipher.doFinal(org.apache.commons.codec.binary.Base64.decodeBase64(content));
            return new String(result, CHARSET);
        } catch (Exception e) {
            return null;
        }
    }


    /*

SELECT `user_id` '用户编号', `nick_name` '用户昵称', `mobile` '手机加密', `mobile` '手机明文' , `gmt_create` '注册时间'
FROM `tph_user`
where DATE_FORMAT(gmt_create , '%Y-%m-%d') = DATE_FORMAT(NOW() - INTERVAL 1 DAY, '%Y-%m-%d')
ORDER BY `gmt_create`;

SELECT a.alipay_uid '用户编号', b.nick_name '用户昵称', b.mobile '手机加密', b.mobile '手机明文', a.action_type '行为编码', c.action_name '行为名称', SUM( a.energy) '减碳量'
FROM tph_user_base_energy_flow a
LEFT JOIN tph_user b ON b.user_id = a.alipay_uid
LEFT JOIN (SELECT DISTINCT action_type,
CASE
WHEN action_type = 'xingzou' THEN '行走'
ELSE action_name
END action_name
FROM `tph_user_base_energy_flow`
GROUP BY action_type, action_name) c ON a.action_type = c.action_type
WHERE a.del_flag = 0
GROUP BY a.alipay_uid, a.action_type, c.action_name
ORDER BY a.alipay_uid;

SELECT a.alipay_uid '用户编号', b.nick_name '用户昵称', b.mobile '手机加密', b.mobile '手机明文', a.action_type '行为编码', c.action_name '行为名称', SUM( a.energy) '减碳量'
FROM tph_user_base_energy_flow a
LEFT JOIN tph_user b ON b.user_id = a.alipay_uid
LEFT JOIN (SELECT DISTINCT action_type,
CASE
WHEN action_type = 'xingzou' THEN '行走'
ELSE action_name
END action_name
FROM `tph_user_base_energy_flow`
GROUP BY action_type, action_name) c ON a.action_type = c.action_type
WHERE a.del_flag = 0 AND DATE_FORMAT(a.gmt_create , '%Y-%m-%d') = DATE_FORMAT(NOW() - INTERVAL 1 DAY, '%Y-%m-%d')
GROUP BY a.alipay_uid, a.action_type, c.action_name
ORDER BY a.alipay_uid;


     SELECT * FROM `tph_user_base_energy_flow` WHERE `alipay_uid` = '20231226080424901000321290700451';

     */

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();

        list.add("r4zWM8WAE2GRj5oeVC2sow==");

        for (String phone : list) {
            System.out.println(decrypt(PASSWORD, phone));
        }

//        System.out.println(encrypt(PASSWORD, "18616663688"));
    }

}
