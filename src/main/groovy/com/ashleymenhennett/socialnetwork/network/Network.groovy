package com.ashleymenhennett.socialnetwork.network

import com.ashleymenhennett.socialnetwork.people.Person

class Network {

	List<Person> people
	
	Network(List<Person> people) { this.people = people }
	
	String toString() {
		return this.people
	}
}