package com.preclaim.dao;

import java.util.List;

import com.preclaim.models.Location;

public interface LocationDao {

	String addLocation(Location location);
	List<Location> locationList(int status);
	String deleteLocation(int locationId);
	String updateLocation(int locationId, String city, String state, String zone, String updatedBy);
    String updateLocationStatus(int locationId, int status, String username);
    Location getLocationById(int locationId);
    List<Location> getActiveLocationList();
    Location getActiveLocationList(String city);
}
