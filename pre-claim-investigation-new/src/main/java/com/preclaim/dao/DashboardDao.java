package com.preclaim.dao;

import java.util.HashMap;

import com.preclaim.models.UserDetails;

public interface DashboardDao {

	HashMap<String, Integer> getCaseCount(UserDetails user);
}
