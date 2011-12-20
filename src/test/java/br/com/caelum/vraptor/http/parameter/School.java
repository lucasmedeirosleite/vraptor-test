package br.com.caelum.vraptor.http.parameter;

import java.util.List;

public class School {
	
	private String name;
	private List<Room> rooms;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Room> getRooms() {
		return rooms;
	}
	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}
	
}
