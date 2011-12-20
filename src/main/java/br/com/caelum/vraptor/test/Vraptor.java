package br.com.caelum.vraptor.test;

import org.apache.catalina.LifecycleException;

import br.com.caelum.restfulie.http.Request;

/**
 * Vraptor context
 * 
 * @author Lucas Medeiros
 *
 */
public interface Vraptor {

	void start() throws LifecycleException ;
	void stop() throws LifecycleException ;
	Request at(String relativeUrl); 
		
}
