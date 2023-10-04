package com.registration.comp;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class Compnents {

	public String encode (String password) {
		
		return BCrypt.hashpw(password,BCrypt.gensalt());
	}
	
	public  boolean dcode(String pasword,String hash) {
		return BCrypt.checkpw(pasword,hash);
	}

}
