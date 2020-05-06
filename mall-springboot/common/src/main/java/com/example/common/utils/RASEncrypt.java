package com.example.common.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * RAS加密算法
 */
public class RASEncrypt {
	private static Map<Integer,String> keyMap = new HashMap<Integer, String>();  //用于封装随机产生的公钥与私钥

	/**
	 * 随机生成密钥对
	 */
	public static Map<Integer,String> genKeyPair() throws NoSuchAlgorithmException {
		//KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		//初始化密钥对生成器，密钥大小为96-1024位
		keyPairGenerator.initialize(1024, new SecureRandom());
		// 生成一个密钥对，保存在keyPair中
		KeyPair keyPair = keyPairGenerator.generateKeyPair();
		RSAPrivateKey  privateKey = (RSAPrivateCrtKey) keyPair.getPrivate();//得到私钥
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();//得到公钥
		String publicKeyString = new String(Base64.encodeBase64(publicKey.getEncoded()));//对公钥进行编码
		String privateKeyString = new String(Base64.encodeBase64(privateKey.getEncoded()));//对密钥进行编码
		//将公钥密钥保存到map
		keyMap.put(0,publicKeyString);
		keyMap.put(1,privateKeyString);
		return keyMap;

	}
	/**
	 * RAS公钥加密
	 */
	public static String encrypt(String str, String publicKey ) throws Exception {
		//base64编码的公钥
		byte[] decoded = Base64.decodeBase64(publicKey);
		RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
		//RSA加密
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, pubKey);
		String outStr = Base64.encodeBase64String(cipher.doFinal(str.getBytes("UTF-8")));
		return outStr;//返回密文
	}
	/**
	 *
	 * RSA私钥解密
	 */
	public static String decrypt(String str, String privateKey) throws Exception {
		//64位解密加密后的字符串
		byte[] inpuByte = Base64.decodeBase64(str.getBytes("UTF-8"));
		//base64编码的私钥
		byte[] decoded = Base64.decodeBase64(privateKey);
		RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
		//RAS解密
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, priKey);
		String outStr = new String(cipher.doFinal(inpuByte));
		return outStr;//返回铭文
	}
}
