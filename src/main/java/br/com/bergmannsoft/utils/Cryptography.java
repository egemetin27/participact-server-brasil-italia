package br.com.bergmannsoft.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import com.google.gson.Gson;

public class Cryptography {
	public static final String IDENTITY_KEY = "ATzIL8m6KblQ33WD";
	private static SecretKeySpec secretKey;
	private static byte[] key;

	public static void setKey(String myKey) {
		MessageDigest sha = null;
		try {
			key = myKey.getBytes("UTF-8");
			sha = MessageDigest.getInstance("SHA-1");
			key = sha.digest(key);
			key = Arrays.copyOf(key, 16);
			secretKey = new SecretKeySpec(key, "AES");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public static String encrypt(String strToEncrypt) {
		try {
			setKey(IDENTITY_KEY);
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			return Base64.encodeBase64String(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
		} catch (Exception e) {
			System.out.println("Error while encrypting: " + e.toString());
		}
		return null;
	}

	public static String decrypt(String strToDecrypt) {
		try {
			setKey(IDENTITY_KEY);
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			return new String(cipher.doFinal(Base64.decodeBase64(strToDecrypt)));
		} catch (Exception e) {
			System.out.println("Error while decrypting: " + e.toString());
		}
		return null;
	}

	public static String encryptToken(Long userId, UUID uuid, String role) {
		// Hashmap
		Map<String, Object> myMap = new HashMap<String, Object>();
		myMap.put("userId", userId);
		myMap.put("uuid", uuid);
		myMap.put("role", role);
		// Json
		Gson gson = new Gson();
		String json = gson.toJson(myMap);
		return encrypt(json);
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String, Object> decryptToken(String token) {
		//Json
		String json = decrypt(token);
		// Hashmap
		Map<String, Object> myMap = new HashMap<String, Object>();
		//Deserializable
		Gson gson = new Gson();
		myMap = gson.fromJson(json, myMap.getClass());
		return myMap;
	}
}
