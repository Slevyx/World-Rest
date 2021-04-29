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
	
	private Country generateCountry(ResultSet result) throws SQLException {
		Country country = null;
		country = new Country();
		country.setName(result.getString("Name"));
		country.setCode(result.getString("Code"));
		country.setContinent(result.getString("Continent"));
		country.setPopulation(result.getInt("Population"));
		country.setSurfaceArea(result.getFloat("SurfaceArea"));
		return country;
	}

	public List<Country> getCountriesByCountryNameContinentName(String countryName, String continentName) {
		List<Country> countriesList = new ArrayList<>();
		countryName = countryName.toUpperCase();
		continentName = continentName.toUpperCase();
		String sqlQuery = "SELECT Name, Code, Continent, Population, SurfaceArea FROM country WHERE ('' = ? OR UPPER(Name) = ?) AND ('' = ? OR UPPER(Continent) = ?)";
		try{
			Connection connection = dataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			statement.setString(1, countryName);
			statement.setString(2, countryName);
			statement.setString(3, continentName);
			statement.setString(4, continentName);
			ResultSet result = statement.executeQuery();
			while(result.next()) {
				countriesList.add(generateCountry(result));
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
		String sqlQuery = "SELECT Name, Code, Continent, Population, SurfaceArea FROM country WHERE Continent = ?";
		try{
			Connection connection = dataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			statement.setString(1, continentName);
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				countries.add(generateCountry(result));
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
