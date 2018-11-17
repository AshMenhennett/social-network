package com.ashleymenhennett.socialnetwork.service

import groovy.util.logging.Slf4j

import com.ashleymenhennett.socialnetwork.people.Person
import com.ashleymenhennett.socialnetwork.network.Network

@Slf4j(value='logger', category='com.ashleymenhennett.socialnetwork.service.dijsktrasp.service')
class DijsktrasSPService {
	
	static Person getShortestPath(Person source) {
		
		logger.debug('Initializing Dijsktras SP Algorithm with source node, {}', source)

		source.distance = 0
		
		List<Person> settled = []
		List<Person> unsettled = []
		
		unsettled << source
		
		while (! unsettled.isEmpty()) {
			Person current = getLowestDistance(unsettled)
			unsettled.remove(current)
			
			current.friends.each { friend, weight ->
				
				if (! settled.contains(friend)) {
					setShortestDistance(friend, weight, current)
					unsettled << friend
				}
				
			}
			
			settled << current
		}
		
		logger.debug('Completed Dijsktras SP Algorithm with source node, {}', source)
		return source
	}
	
	static private Person getLowestDistance(List<Person> unsettled) {
		Person closest
		Long closestDistance = Long.MAX_VALUE
		
		logger.debug('Searching for next closest node')

		for (Person person : unsettled) {
			def distance = person.distance
			if (distance < closestDistance) {
				closestDistance = distance
				closest = person

				logger.debug('Found next closest node, {}', person)
			}
		}
		
		return closest
	}
	
	static private void setShortestDistance(Person friend, Long weight, Person source) {
		Long sourceDistance = source.distance
		
		if (sourceDistance + weight < friend.distance) {
			def updatedDistance = sourceDistance + weight
			logger.debug('Found shorter path from {} to {}, with length {}', source, friend, updatedDistance)
			
			friend.distance = updatedDistance
			List<Person> shortestPath = new ArrayList<>(source.shortestPath)
			shortestPath << source
			
			logger.debug('Setting shortest path from {} to {}, with length {} and path {}', source, friend, updatedDistance, shortestPath)
			friend.shortestPath = shortestPath
		}
	}

}