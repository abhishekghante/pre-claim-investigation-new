package com.preclaim.dao;

import java.util.HashMap;

public interface DashboardDao {

	HashMap<String, Integer> getCaseCount(String username);
}
