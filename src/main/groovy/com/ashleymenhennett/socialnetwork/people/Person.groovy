package com.ashleymenhennett.socialnetwork.people

class Person {

	Long id
	Long distance
	String username
	List<String> interests
	List<Person> shortestPath
	Map<Person, Long> friends
	
	Person() {
		friends = [:]
		shortestPath = []
		distance = Long.MAX_VALUE
	}
	
	boolean equals(Object other) { return this.username == other?.username }
	
	String toString() { return this.username }

}