package br.com.caelum.vraptor.http.parameter;

import java.util.ArrayList;

import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

import static br.com.caelum.vraptor.test.http.parameter.FormEncodedTransformer.paramsFor;

public class FormEncodedTransformerTest {
	
	@Test
	public void should_retrieve_simple_object_fields_as_parameters() throws IllegalArgumentException, IllegalAccessException{
	
		Person person = new Person("Lucas Medeiros Leite", 23);
		Map<String, String> params = paramsFor(person);
		Assert.assertNotNull(params);
		
		String nameValue = params.get("person.name");
		Assert.assertNotNull(nameValue);
		Assert.assertEquals("Lucas Medeiros Leite", nameValue);
		
		String ageValue = params.get("person.age");
		Assert.assertNotNull(ageValue);
		Assert.assertEquals("23", ageValue);
		
	}
	
	@Test
	public void should_retrieve_object_with_non_primitive_and_non_collection_fields_as_parameters() throws IllegalArgumentException, IllegalAccessException{
	
		SingleHouse house = new SingleHouse();
		house.setNumber("01");
		Room room = new Room();
		room.setNumber("002");
		house.setRoom(room);
		
		Map<String, String> params = paramsFor(house);
		Assert.assertNotNull(params);
		
		String numberValue = params.get("singleHouse.number");
		Assert.assertNotNull(numberValue);
		Assert.assertEquals("01", numberValue);
		
		String roomNumber = params.get("singleHouse.room.number");
		Assert.assertNotNull(roomNumber);
		Assert.assertEquals("002", roomNumber);
		
	}
	
	@Test
	public void should_retrieve_object_with_collection_fields_as_parameters() throws IllegalArgumentException, IllegalAccessException{
	
		School school = new School();
		school.setName("School1");
		
		List<Room> rooms = new ArrayList<Room>();
		
		Room room1 = new Room();
		room1.setNumber("001");
		Room room2 = new Room();
		room2.setNumber("002");
		
		rooms.add(room1);
		rooms.add(room2);
		
		school.setRooms(rooms);
		
		
		Map<String, String> params = paramsFor(school);
		Assert.assertNotNull(params);
		
		String nameValue = params.get("school.name");
		Assert.assertNotNull(nameValue);
		Assert.assertEquals("School1", nameValue);
		
		String room1Value = params.get("school.rooms[0].number");
		Assert.assertNotNull(room1Value);
		Assert.assertEquals("001", room1Value);
		
		String room2Value = params.get("school.rooms[1].number");
		Assert.assertNotNull(room2Value);
		Assert.assertEquals("002", room2Value);
		
	}
	
	@Test
	public void should_retrieve_object_with__primitive_collection_fields_as_parameters() throws IllegalArgumentException, IllegalAccessException{
	
		Car car = new Car();
		car.setId(1);
		
		List<String> wheels = new ArrayList<String>();
		
		wheels.add("01");
		wheels.add("02");
		
		car.setWheels(wheels);
		
		
		Map<String, String> params = paramsFor(car);
		Assert.assertNotNull(params);
		
		String idValue = params.get("car.id");
		Assert.assertNotNull(idValue);
		Assert.assertEquals("1", idValue);
		
		String wheel1 = params.get("car.wheels[0]");
		Assert.assertNotNull(wheel1);
		Assert.assertEquals("01", wheel1);
		
		String wheel2 = params.get("car.wheels[1]");
		Assert.assertNotNull(wheel2);
		Assert.assertEquals("02", wheel2);
		
	}

}
