package my.utils;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.commons.codec.binary.Base64;

/**
 * 加解密工具
 *
 */
public final class EncryptUtils {
	private final static Random random = new Random();

    public static void main(String[] args) throws Exception {
        
        Map<String, Object> map = generateRSAKeyPairs();
        System.out.println("publicKey:===>"+map.get("publicKey"));
        System.out.println("privateKey:===>"+map.get("privateKey"));
        
        System.out.println(keyCreate(24));
        
        byte[] data = "123".getBytes();
        String privateKeyStr = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCqKa38sWzjVkd8it5bxEhj0i+7mlUMc5NR3diofQAdQGetKb5TEU0CnnyHeUKAQMx+343JERGE76gGcK7F5LJVTMM3KSZ+8QVhFLruTE3UUyhbx9TB4vzVIoUHp8dlh5Xgv1+gwkPk4757lRF6egLO5qIzZSyk69mZsYl1wxD8nuRqHx8aNRoZ6siZ84Cqebz70eyA7YryURbvhGm0QdeJ9hPRsADgfmjIpGA4977S1HeAFRxkyBcNP/BV4RUyAqj6Pe7DNc6WJfATLvhlF9SmVG3HFRZiQ8we9X04klq2K4sQzRc1gcLr+Z8ZGoUy2YAslckEwolw2Tomizjk+lZ/AgMBAAECggEBAIx1pSGuDzTE1nNuaceUCTEkobQY29VQXa25EwtZS0vaCvp8N5d383qS+jYVmRgm5OZhgDcVeMGj1a2jPBEsCyywFYAl3pnwN8GADCGCKMh38Bt6oxt8U7lXKjo8ezJKriP7tGMNmF+KwoReczQHHOROqlsES8rCyDa+vrnPT+lFoYBo5DBBt+Ex4nDVieVJklQUDw3UDhMV3yaIPStwEvIp3l+OAMx5QVvLkCglEI3sxKMO6ZaoEL/hap4ERSn2Z4mQ10njnqnYpDDgOTnFyv+NatrdwBGybUPkZgTA7TN6nl8242Iq0XkVR9NjMg9JrbZMaL0W/oAWNAZjPJ3Z/sECgYEA4tfo6Xx8dieTmap8MpfAeDph8sbIIocfKEUnuwjjscqkX23tDpBB3yjPG6VA4nBwNBsaw0B8ynnDBWLhOTQ0XvojFhzmhwBXSeRBkBTcC0V5TQC1r091CHtiveDjN8padS4PZ2l+fD/YoLW8XQpAE6Sy+K23jGRNstbZD1EOCKECgYEAwAi+OHzk5oGKDUpfWgXawq1teyF6kxCk1a6cGIHquySeTYmErZSCHJ3wyAacrvzuTzw+s83XrKQPOhDECcn4jOLTIAlppliZVx1V7sZ6Wkha7gGOUYB19sKE2WllrF80LLDwCPAYXVhSc2YrT8KrCFlxdllwTsPTe1OUctssax8CgYEAlEWWWw9gdwaVZqrV9WfViYB3x22BIXh6uCLDvnAoDG0tFadPfOZz9/6/qQ1SJPBjlttkmd1L9Di0bY/dMTvbQ31yc6C+Wd2yMH0/EYNg9QkbKWbeWtGv6LRlrUESo8PjbpaFwQ1mfKZq3VkM3y+1BGHyDbPWtrBVTSE7zdQoPSECgYBYJetDPLlJwd6f2SrJxPQ09qzRn32HclVnrL4EVeZsvxNXXi+kCg5rmmhDOR3FCV9OycWyfWNp4OkIa1fBco+ABEKLayau7+57WYDvmPXi6WGr7NTqPoBno8Sdk87S/+SQOywDvkpA1uEKDbUF6MLfk0cM32M5FJTIAIac7lSGWwKBgEVRzmc+J2gfMoBP8rdQh72acA0mP4iiX8ui9eaj3E7lVTOh6Z/ngTw7G14zN6cRoTw6qx3gf0VbtSWC5W9av+su6LiavCpIANHyGpM2IzEyIeCB+i0YbFvuQ/fquf1y1Gv39aMqPVI8khUFrfrLy7uQyMWCvo0vuPT2O6R9uqvs";
        PrivateKey privateKey = getPrivateKeyFromString(privateKeyStr);
        
        String publicKeyStr = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqimt/LFs41ZHfIreW8RIY9Ivu5pVDHOTUd3YqH0AHUBnrSm+UxFNAp58h3lCgEDMft+NyRERhO+oBnCuxeSyVUzDNykmfvEFYRS67kxN1FMoW8fUweL81SKFB6fHZYeV4L9foMJD5OO+e5URenoCzuaiM2UspOvZmbGJdcMQ/J7kah8fGjUaGerImfOAqnm8+9HsgO2K8lEW74RptEHXifYT0bAA4H5oyKRgOPe+0tR3gBUcZMgXDT/wVeEVMgKo+j3uwzXOliXwEy74ZRfUplRtxxUWYkPMHvV9OJJatiuLEM0XNYHC6/mfGRqFMtmALJXJBMKJcNk6Jos45PpWfwIDAQAB";
        PublicKey publicKey = getPublicKeyFromString(publicKeyStr);
        
        
        byte[] signedData = signByPrivateKey(data, privateKey);
        
        boolean checkFlag = verifyByPublicKey(signedData, "456".getBytes(), publicKey);
        if (checkFlag) {
        	System.out.println("success");
        } else {
        	System.out.println("false");
        }
    }
    
    /**
     * 生成RSA公、私钥对
     * 
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static Map<String, Object> generateRSAKeyPairs() throws NoSuchAlgorithmException {
        Map<String, Object> keyPairMap = new HashMap<String, Object>();
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        KeyPair keyPair = generator.genKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();
        keyPairMap.put("publicKey", Base64.encodeBase64String(publicKey.getEncoded()));
        keyPairMap.put("privateKey", Base64.encodeBase64String(privateKey.getEncoded()));
        return keyPairMap;
    }
    /**
     * 通过私钥对数据签名
     * @param data
     * @param privateKey
     */
    public static byte[] signByPrivateKey(byte[] data, PrivateKey privateKey)
            throws Exception {
        Signature sig = Signature.getInstance("SHA256withRSA");
        sig.initSign(privateKey);
        sig.update(data);
        byte[] ret = sig.sign();
        return ret;
    }
    /**
     * 通过公钥对数据验签
     * @param signedData 签名密文
     * @param plainText 签名原文
     * @param publicKey 公钥
     */
    public static boolean verifyByPublicKey(byte[] signedData, byte[] plainText, PublicKey publicKey)
            throws Exception {
        Signature sig = Signature.getInstance("SHA256withRSA");
        sig.initVerify(publicKey);
        // 设置原文
        sig.update(plainText);
        // 验证密文
        return sig.verify(signedData);
    }
    /**
     * 获得私钥对象
     * @param base64String 私钥字符串
     */
    public static PrivateKey getPrivateKeyFromString(String base64String)
            throws InvalidKeySpecException, NoSuchAlgorithmException {
        byte[] bt = Base64.decodeBase64(base64String.getBytes());
        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(bt);
        // algorithm
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);
        return privateKey;
    }
    
    /**
     * 获得公钥对象
     * @param base64String 公钥字符串
     */
    public static PublicKey getPublicKeyFromString(String base64String)
            throws InvalidKeySpecException, NoSuchAlgorithmException {
        byte[] bt = Base64.decodeBase64(base64String.getBytes());
        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(bt);
        //
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
        return publicKey;
    }
    
    /**
     * 生成16位AES随机密钥
     * AES是一种
     * @return
     */
    public static String getAESRandomKey(){
        long longValue = random.nextLong();
        return String.format("%016x", longValue);
    }
    
    public static String keyCreate(int KeyLength) {
        String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%&*:_+<>?~#$@";
        Random random = new Random();
        StringBuffer Keysb = new StringBuffer();
        // 生成指定位数的随机秘钥字符串
        for (int i = 0; i < KeyLength; i++) {
            int number = random.nextInt(base.length());
            Keysb.append(base.charAt(number));
        }
        return Keysb.toString();
    }
    
}

