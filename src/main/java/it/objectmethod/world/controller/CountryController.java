package it.objectmethod.world.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.objectmethod.world.dao.ICountryDao;
import it.objectmethod.world.models.Country;

@RestController
public class CountryController {

	@Autowired
	private ICountryDao countryDao;
	
	@PostMapping("/countries")
	public ResponseEntity<List<Country>> getCountriesByCountryNameContinentName(
			@RequestParam(name = "countryName", required = false) String countryName, @RequestParam(name = "continentName", required = false) String continentName) {
		List<Country> countriesList = new ArrayList<>();
		ResponseEntity<List<Country>> response = null;
		if(countryName == null || continentName == null) {
			response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		else {
			countryName = countryName.toUpperCase();
			continentName = continentName.toUpperCase();
			countriesList = countryDao.getCountriesByCountryNameContinentName(countryName, continentName);
			response = new ResponseEntity<>(countriesList, HttpStatus.OK);
		}
		return response;
	}
	
	@GetMapping("/continents")
	public ResponseEntity<List<String>> getContinents() {
		List<String> continentsList = new ArrayList<>();
		ResponseEntity<List<String>> response = null;
		continentsList = countryDao.getContinents();
		response = new ResponseEntity<>(continentsList, HttpStatus.OK);
		return response;
	}
	
	@GetMapping("/countriesByContinent")
	public ResponseEntity<List<Country>> getCountriesByContinent(@RequestParam(name = "continentName", required = false) String continentName) {
		List<Country> countries = new ArrayList<>();
		ResponseEntity<List<Country>> response = null;
		if(continentName == null) {
			response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		else {
			countries = countryDao.getCountriesbyContinentName(continentName);
			response = new ResponseEntity<>(countries, HttpStatus.OK); 
		}
		return response;
	}
}
