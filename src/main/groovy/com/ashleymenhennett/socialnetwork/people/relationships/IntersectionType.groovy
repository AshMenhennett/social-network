package com.ashleymenhennett.socialnetwork.people.relationships

enum IntersectionType {

	INTERESTS("interests")
	
	String type
	
	private IntersectionType(String type) { this.type = type }
	
	String getType() { return this.type }

}