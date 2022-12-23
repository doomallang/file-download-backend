package com.innotium;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = ApplicationArguments.class)
public class Aes256Test {
	public static final String TRANSFOMATION = "AES/ECB/PKCS5Padding";
	public static final byte[] BYTE_IV = { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };

	public static void main(String[] args) throws Exception {
		String key = "1la093jamv1az05302zdlae98adfq321";
		String decryptStr = "Y14gN2omNk+B0Y638tt40wXNpUzciXiZpLpwCMr65wJA67nxXMy7fqggvwfqmd4KtWqtoXAdqXVSV7Wi8pP/6MTssBHFgyUaa/eYahomptqB2iM93Ce23gF6HHcnor8zI1XbIVVj6EJRxqMOI46l4Mn3SO5vrl3KtZ7ZTr+I4Qc=";
		String encryptStr = "compno=1087&domain=kct.kcmaterials.kr&loginid=innotium2&name=이노티움2&empno=12359&ctime=20210618162104&ip=210.107.35.230";
		//System.out.println(decrypt(key, decryptStr));
		System.out.println(encrypt(key, encryptStr));
	}

	/** 암호화 */
	public static String encrypt(String key, String str) throws Exception {
		byte[] byteText = str.getBytes("UTF-8");
		SecretKeySpec sks = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
		Cipher cipher = Cipher.getInstance(TRANSFOMATION);
		cipher.init(Cipher.ENCRYPT_MODE, sks);

		return Base64.encodeBase64String(cipher.doFinal(byteText));
	}

	/** 복호화 */
	public static String decrypt(String key, String str) throws Exception {
		// LOGGER.info("KEY: {}, TEXT: {}", key, str);
		byte[] byteText = Base64.decodeBase64(str);
		SecretKeySpec sks = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
		Cipher cipher = Cipher.getInstance(TRANSFOMATION);
		cipher.init(Cipher.DECRYPT_MODE, sks);

		return new String(cipher.doFinal(byteText), "UTF-8");
	}

}
