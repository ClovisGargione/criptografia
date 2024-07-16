package com.criptografia.domain.keys;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.AlgorithmParameters;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.MGF1ParameterSpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;


public class GerarChavesPublicaPrivada {

	public static final String ALGORITHM = "RSA/ECB/OAEPPadding";

	/**
	 * Local da chave privada no sistema de arquivos.
	 */
	public static final String PATH_CHAVE_PRIVADA = "keys/private.key";

	/**
	 * Local da chave pública no sistema de arquivos.
	 */
	public static final String PATH_CHAVE_PUBLICA = "keys/public.key";
	
	public static KeyPair generateRsaKey() {
		KeyPair keyPair;
		try {
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
			keyPairGenerator.initialize(2048);
			keyPair = keyPairGenerator.generateKeyPair();

			File chavePrivadaFile = new File(PATH_CHAVE_PRIVADA);
			File chavePublicaFile = new File(PATH_CHAVE_PUBLICA);

			// Cria os arquivos para armazenar a chave Privada e a chave Publica
			if (chavePrivadaFile.getParentFile() != null) {
				chavePrivadaFile.getParentFile().mkdirs();
			}

			chavePrivadaFile.createNewFile();

			if (chavePublicaFile.getParentFile() != null) {
				chavePublicaFile.getParentFile().mkdirs();
			}

			chavePublicaFile.createNewFile();

			// Salva a Chave Pública no arquivo
			ObjectOutputStream chavePublicaOS = new ObjectOutputStream(new FileOutputStream(chavePublicaFile));
			chavePublicaOS.writeObject(keyPair.getPublic());
			chavePublicaOS.close();

			// Salva a Chave Privada no arquivo
			ObjectOutputStream chavePrivadaOS = new ObjectOutputStream(new FileOutputStream(chavePrivadaFile));
			chavePrivadaOS.writeObject(keyPair.getPrivate());
			chavePrivadaOS.close();
		} catch (Exception ex) {
			throw new IllegalStateException(ex);
		}
		return keyPair;
	}
	
	/**
	 * Criptografa o texto puro usando chave pública.
	 */
	public static byte[] criptografa(String texto, PublicKey chave)
	{
		byte[] cipherText = null;

		try
		{
			final Cipher cipher = Cipher.getInstance(ALGORITHM);
			// Criptografa o texto puro usando a chave Púlica
			cipher.init(Cipher.ENCRYPT_MODE, chave);
			cipherText = cipher.doFinal(texto.getBytes());
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return cipherText;
	}

	/**
	 * Decriptografa o texto puro usando chave privada.
	 */
	public static String decriptografa(byte[] texto, PrivateKey chave)
	{
		byte[] dectyptedText = null;

		try
		{
			final Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, chave);
			dectyptedText = cipher.doFinal(texto);

		} catch (Exception ex)
		{
			ex.printStackTrace();
		}

		return new String(dectyptedText);
	}

	/**
	 * Verifica se o par de chaves Pública e Privada já foram geradas.
	 */
	public static boolean verificaSeExisteChavesNoSO() {

		File chavePrivada = new File(PATH_CHAVE_PRIVADA);
		File chavePublica = new File(PATH_CHAVE_PUBLICA);

		if (chavePrivada.exists() && chavePublica.exists()) {
			return true;
		}

		return false;
	}
	
	public static PublicKey getPublicKey(String filename) throws Exception
	{

		byte[] keyBytes = Files.readAllBytes(Paths.get(filename));
		String publicKeyAsString = new String(keyBytes);
		
		String publicKeyPem = publicKeyAsString
			    .replace("-----BEGIN PUBLIC KEY-----", "")
			    .replaceAll(System.lineSeparator(), "")
			    .replace("-----END PUBLIC KEY-----", "");
		byte[] keyContentAsBytes =  Base64.getDecoder().decode(publicKeyPem);
		X509EncodedKeySpec spec = new X509EncodedKeySpec(keyContentAsBytes);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		return kf.generatePublic(spec);
	}

	public static PrivateKey getPrivateKey(String filename) throws Exception
	{

		byte[] keyBytes = Files.readAllBytes(Paths.get(filename));
		String privateKeyAsString = new String(keyBytes, Charset.defaultCharset());
		
		String privateKeyPem = privateKeyAsString
			    .replace("-----BEGIN PRIVATE KEY-----", "")
			    .replaceAll(System.lineSeparator(), "")
			    .replace("-----END PRIVATE KEY-----", "");
		byte[] keyContentAsBytes =  Base64.getDecoder().decode(privateKeyPem);
		

		PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyContentAsBytes);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		return kf.generatePrivate(spec);
	}
}
