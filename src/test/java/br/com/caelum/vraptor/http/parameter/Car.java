package br.com.caelum.vraptor.http.parameter;

import java.util.List;

public class Car {

	private Integer id;
	private List<String> wheels;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public List<String> getWheels() {
		return wheels;
	}
	public void setWheels(List<String> wheels) {
		this.wheels = wheels;
	}
	
	
	
}
