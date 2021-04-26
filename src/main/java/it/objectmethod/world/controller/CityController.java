package it.objectmethod.world.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.objectmethod.world.dao.ICityDao;
import it.objectmethod.world.models.City;

@RestController
public class CityController {

	@Autowired
	private ICityDao cityDao;
	
	@PostMapping("/city")
	public ResponseEntity<City> getCityByName(@RequestParam(name = "cityName", required = false) String cityName) {
		City city = null;
		ResponseEntity<City> response = null;
		if(cityName == null) {
			response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		else {
			cityName = cityName.toUpperCase();
			city = cityDao.getCityByName(cityName);
			response = new ResponseEntity<>(city, HttpStatus.OK);
		}
		return response;
	}
	
	@GetMapping("/{countryCode}/cities")
	public ResponseEntity<List<City>> getCitiesByCountry(@PathVariable("countryCode") String countryCode) {
		ResponseEntity<List<City>> response = null;
		List<City> cities = cityDao.getCitiesByCountry(countryCode);
		response = new ResponseEntity<>(cities, HttpStatus.OK);
 		return response;
	}
}
