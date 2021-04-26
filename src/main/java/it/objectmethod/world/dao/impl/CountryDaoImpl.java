package it.objectmethod.world.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.objectmethod.world.dao.ICountryDao;
import it.objectmethod.world.models.Country;

@Component
public class CountryDaoImpl implements ICountryDao{
	
	@Autowired
	DataSource dataSource;

	public List<Country> getCountriesByCountryNameContinentName(String countryName, String continent) {
		List<Country> countriesList = new ArrayList<>();
		Country country = null;
		String sqlQuery = "SELECT Name, Code, Continent, Population, SurfaceArea FROM country WHERE ('' = ? OR UPPER(Name) = ?) AND ('' = ? OR UPPER(Continent) = ?)";
		try{
			Connection connection = dataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			statement.setString(1, countryName);
			statement.setString(2, countryName);
			statement.setString(3, continent);
			statement.setString(4, continent);
			ResultSet result = statement.executeQuery();
			while(result.next()) {
				country = new Country();
				country.setName(result.getString("Name"));
				country.setCode(result.getString("Code"));
				country.setContinent(result.getString("Continent"));
				country.setPopulation(result.getInt("Population"));
				country.setSurfaceArea(result.getFloat("SurfaceArea"));
				countriesList.add(country);
			}
			result.close();
			statement.close();
			connection.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return countriesList;
	}

	@Override
	public List<String> getContinents() {
		List<String> continentsList = new ArrayList<>();
		String sqlQuery = "SELECT DISTINCT c.Continent FROM country c";
		try{
			Connection connection = dataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			ResultSet result = statement.executeQuery();
			while(result.next()) {
				continentsList.add(result.getString("Continent"));
			}
			result.close();
			statement.close();
			connection.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return continentsList;
	}

	@Override
	public List<Country> getCountriesbyContinentName(String continentName) {
		List<Country> countries = new ArrayList<>();
		Country country = null;
		String sqlQuery = "SELECT Name, Code, Continent, Population, SurfaceArea FROM country WHERE Continent = ?";
		try{
			Connection connection = dataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			statement.setString(1, continentName);
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				country = new Country();
				country.setName(result.getString("Name"));
				country.setCode(result.getString("Code"));
				country.setContinent(result.getString("Continent"));
				country.setPopulation(result.getInt("Population"));
				country.setSurfaceArea(result.getFloat("SurfaceArea"));
				countries.add(country);
			}
			result.close();
			statement.close();
			connection.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return countries;
	}
}
