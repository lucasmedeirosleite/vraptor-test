package br.com.caelum.restfulie.contenttype;

/**
 * Shortcut to use when call restfulie
 * Ex: Restfulie.at("http://localhost:3000").as(json()).post(payload);
 * 
 * OBS: It must be moved to restfulie project

 * @author lucas medeiros
 */
public class ContentTypes {
	
	public static String json(){
		return "application/json";
	}
	
	public static String xml(){
		return "application/xml";
	}
	
	public static String form(){
		return "application/x-www-form-urlencoded";
	}

}
