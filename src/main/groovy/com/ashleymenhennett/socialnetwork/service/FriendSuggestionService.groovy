package com.ashleymenhennett.socialnetwork.service

import groovy.util.logging.Slf4j

import com.ashleymenhennett.socialnetwork.people.Person
import com.ashleymenhennett.socialnetwork.network.Network
import com.ashleymenhennett.socialnetwork.people.relationships.*

@Slf4j(value='logger', category='com.ashleymenhennett.socialnetwork.service.friendsuggestion.service')
class FriendSuggestionService {

	List<RelationshipSuggestion> suggestions
	
	FriendSuggestionService() {
		this.suggestions = []
	}
	
	List<RelationshipSuggestion> createRelationshipSuggestions(Person source) {
	
		def parent = DijsktrasSPService.getShortestPath(source)
				
		def queue = [parent] as Queue
		def visited = []
		
		logger.debug('Initiating BFS over updated network with source {}', source)
		
		while (queue) {
			Person v = queue.poll()
			Long totalDistance = 0
			if (! visited.contains(v)) {
				def distance = v.shortestPath.size()
				logger.debug('Visiting node, {} with shortest path, {}', v, v.shortestPath)
				
				suggestions << createSuggestion(parent, v, distance, v.shortestPath)
				visited << v
				for (def u : v.friends.keySet())
					queue.offer(u)
			}
		}
		
		logger.debug('Completed BFS over updated network with source {}', source)
		
		return suggestions.findAll {
			it.personA != it.personB && 
				(it.relationshipReasons.size() || 
					it.intersections.size())
		}
		
	}
	
	private RelationshipSuggestion createSuggestion(Person a, Person b, Long distance, List<Person> shortestPath) {
		def relationshipSuggestion = new RelationshipSuggestion()
		def intersections = getIntersections(a, b, IntersectionType.INTERESTS)
		
		logger.debug('Calculating Friend Suggestions with parent {} and friend {}', a, b)
		
		relationshipSuggestion.personA = a
		relationshipSuggestion.personB = b
		relationshipSuggestion.connectingPath = shortestPath

		if (distance > 1 && distance < 5 && intersections) {
			logger.debug('Found intersecting interests between {} and {}', a, b)
			relationshipSuggestion.intersections = intersections
			relationshipSuggestion.relationshipReasons << RelationshipReason.SIMILAR_INTEREST
		}
		
		if (distance == 2) {
			logger.debug('Found 2nd degree relationship between {} and friend {}', a, b)
			relationshipSuggestion.relationshipReasons << RelationshipReason.SECOND_DEGREE_RELATIONSHIP
		} else if (distance == 3) {
			logger.debug('Found 3rd degree relationship between {} and friend {}', a, b)
			relationshipSuggestion.relationshipReasons << RelationshipReason.THIRD_DEGREE_RELATIONSHIP
		}
		
		logger.debug('Completed Calculating Friend Suggestions with parent {} and friend {}, {}', a, b, relationshipSuggestion)
		return relationshipSuggestion
	}
	
	private List getIntersections(a, b, prop) {
		prop = prop.type // getting the string value associated with the prop Enum passed
		return (a."${prop}" && b."${prop}") ? a."${prop}".intersect(b."${prop}") : []
	}
	
}
