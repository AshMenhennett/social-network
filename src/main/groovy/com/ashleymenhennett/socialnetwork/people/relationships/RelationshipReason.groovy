package com.ashleymenhennett.socialnetwork.people.relationships

enum RelationshipReason {

	SIMILAR_INTEREST("SIMILAR_INTEREST"),
	SECOND_DEGREE_RELATIONSHIP("SECOND_DEGREE_RELATIONSHIP"),
	THIRD_DEGREE_RELATIONSHIP("THIRD_DEGREE_RELATIONSHIP")
	
	String type
	
	private RelationshipReason(String type) { this.type = type }
	
	String getType() { return this.type }
	
}