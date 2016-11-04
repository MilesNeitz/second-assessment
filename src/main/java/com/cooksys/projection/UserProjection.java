package com.cooksys.projection;

import java.sql.Timestamp;

public interface UserProjection {

	String getUsername();
	
	String getFirstName();
	
	String getLastName();
	
	String getEmail();
	
	String getPhone();
	
	Timestamp getJoined();
}
