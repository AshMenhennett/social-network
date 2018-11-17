package com.ashleymenhennett.socialnetwork.people.relationships

import com.ashleymenhennett.socialnetwork.people.Person

class RelationshipSuggestion {
	
	Person personA
	Person personB
	List<String> intersections
	List<Person> connectingPath
	List<RelationshipReason> relationshipReasons
	
	RelationshipSuggestion() {	
		this.intersections = []
		this.relationshipReasons = []
	}
	
	String toString() {
		def sb = new StringBuilder()
		sb.append('Friend A=').append(personA).append(', Friend B=').append(personB).append(' ')
		sb.append('Relationship Reasons=').append(relationshipReasons)	
		intersections?.size() ? sb.append(' ').append('Intersections=').append(intersections) : null
		return sb
	}
	
}
