package br.com.caelum.vraptor.test.tomcat;

import java.io.File;

import javax.servlet.ServletException;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.core.AprLifecycleListener;
import org.apache.catalina.core.StandardServer;
import org.apache.catalina.startup.Tomcat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.caelum.restfulie.Restfulie;
import br.com.caelum.restfulie.http.Request;
import br.com.caelum.vraptor.test.Vraptor;

public class VraptorTomcat implements Vraptor{

	private final static Logger LOG = LoggerFactory.getLogger(VraptorTomcat.class);
	
	private Tomcat server;
	private Integer port;
	
	public VraptorTomcat(File base, Integer port){
		this.port = port;
		this.server = new Tomcat();
		this.server.setPort(port);
		this.server.setBaseDir(base.getAbsolutePath());
		this.server.getHost().setAppBase(base.getAbsolutePath());
		
		StandardServer server = (StandardServer) this.server.getServer();
		AprLifecycleListener listener = new AprLifecycleListener();
		server.addLifecycleListener(listener);
		try {
			this.server.addWebapp("/", base.getAbsolutePath());
		} catch (ServletException e) {
			LOG.error("Unable to start embedded tomcat at " + port);
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void start() throws LifecycleException {
		LOG.info("Starting embedded tomcat at " + port);
		try{
			this.server.start();
		}catch(IllegalArgumentException ex){
			ex.printStackTrace();
			this.server.stop();
		}
		
	}

	@Override
	public void stop() throws LifecycleException {
		LOG.info("Shutting down tomcat");
		this.server.stop();
		this.server.destroy();
	}

	@Override
	public Request at(String relativeUrl) {
		return Restfulie.at("http://localhost:" + port + relativeUrl).accept("text/html");
	}

}
