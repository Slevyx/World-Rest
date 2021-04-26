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

import it.objectmethod.world.dao.ICityDao;
import it.objectmethod.world.models.City;

@Component
public class CityDaoImpl implements ICityDao{
	
	@Autowired
	DataSource dataSource;

	@Override
	public City getCityByName(String cityName) {
		City city = null;
		String sqlQuery = "SELECT * FROM city WHERE UPPER(Name) = ?";
		try {
			Connection connection = dataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			statement.setString(1, cityName);
			ResultSet result = statement.executeQuery();
			while(result.next()) {
				city = new City();
				city.setId(result.getInt("ID"));
				city.setName(result.getString("Name"));
				city.setCountryCode(result.getString("CountryCode"));
				city.setDistrict(result.getString("District"));
				city.setPopulation(result.getInt("Population"));
			}
			result.close();
			statement.close();
			connection.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return city;
	}

	@Override
	public List<City> getCitiesByCountry(String countryCode) {
		List<City> cities = new ArrayList<>();
		City city = null;
		String sqlQuery = "SELECT * FROM city WHERE CountryCode = ?";
		try{
			Connection connection = dataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			statement.setString(1, countryCode);
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				city = new City();
				city.setId(result.getInt("ID"));
				city.setName(result.getString("Name"));
				city.setCountryCode(result.getString("CountryCode"));
				city.setDistrict(result.getString("District"));
				city.setPopulation(result.getInt("Population"));
				cities.add(city);
			}
			result.close();
			statement.close();
			connection.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return cities;
	}
}
