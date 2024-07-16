package com.criptografia;

import java.security.PrivateKey;
import java.security.PublicKey;

public class CriptografiaSingleton {

	private static CriptografiaSingleton Instance;
	
	private PublicKey publicKey;
	
	private PrivateKey privateKey;
	
	private CriptografiaSingleton() {        
    }
    
    public static CriptografiaSingleton getInstance() {
        if(Instance == null) {
        	Instance = new CriptografiaSingleton();
        }
        return Instance;
    }

	public PublicKey getPublicKey() throws Exception {
		publicKey = new Criptografia().getPublicKey(Criptografia.PATH_CHAVE_PUBLICA);
		return publicKey;
	}

	public PrivateKey getPrivateKey() throws Exception {
		
		privateKey = new Criptografia().getPrivateKey(Criptografia.PATH_CHAVE_PRIVADA);
		return privateKey;
	}
    
    
}
