package com.criptografia;

import java.security.PrivateKey;
import java.security.PublicKey;

import org.apache.logging.log4j.util.Strings;


public class CriptografiaService extends Criptografia {

	public static String decriptografar(String texto) {
		PrivateKey chavePrivada = null;
		try {
			 chavePrivada = CriptografiaSingleton.getInstance().getPrivateKey();
		} catch (Exception e) {
			e.printStackTrace();
			return Strings.EMPTY;
		}
		byte[] decodedString = Criptografia.base64ToByteArray(texto);
		return Criptografia.decriptografa(decodedString, chavePrivada);
	}
	
	public static byte[] criptografar(String texto) {
		PublicKey chavePublica = null;
		try {
			 chavePublica = CriptografiaSingleton.getInstance().getPublicKey();
		} catch (Exception e) {
			e.printStackTrace();
			return Strings.EMPTY.getBytes();
		}
		byte[] encryptString = Criptografia.criptografa(texto, chavePublica);
		return Criptografia.byteArrayToBase64(encryptString);
	}
}
