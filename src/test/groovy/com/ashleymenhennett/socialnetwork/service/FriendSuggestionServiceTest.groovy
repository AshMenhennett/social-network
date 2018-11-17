package com.ashleymenhennett.socialnetwork.service

import org.junit.Test

import com.ashleymenhennett.socialnetwork.people.Person
import com.ashleymenhennett.socialnetwork.network.Network
import com.ashleymenhennett.socialnetwork.people.relationships.RelationshipReason

class FriendSuggestionServiceTest {

	private FriendSuggestionService subject

	@Test
	public void "users can have 2nd and 3rd degree friends with interests"() {
		
		def network = createNetwork()
		def service = new FriendSuggestionService()
		def result = service.createRelationshipSuggestions(network.people[0])
				
		assert result.size() == 5
		
		assert result[0].personA.username == 'Bob'
		assert result[0].personB.username == 'Sam'
		assert result[0].relationshipReasons == [RelationshipReason.SIMILAR_INTEREST, RelationshipReason.SECOND_DEGREE_RELATIONSHIP]
		assert result[0].intersections == ['musicals']
		
		assert result[1].personA.username == 'Bob'
		assert result[1].personB.username == 'Bill'
		assert result[1].relationshipReasons == [RelationshipReason.SIMILAR_INTEREST, RelationshipReason.SECOND_DEGREE_RELATIONSHIP]
		assert result[1].intersections == ['musicals', 'going out']
		
		assert result[2].personA.username == 'Bob'
		assert result[2].personB.username == 'Gene'
		assert result[2].relationshipReasons == [RelationshipReason.SECOND_DEGREE_RELATIONSHIP]
		
		assert result[3].personA.username == 'Bob'
		assert result[3].personB.username == 'Samson'
		assert result[3].relationshipReasons == [RelationshipReason.SIMILAR_INTEREST, RelationshipReason.THIRD_DEGREE_RELATIONSHIP]
		assert result[3].intersections == ['skateboarding']
		
		assert result[4].personA.username == 'Bob'
		assert result[4].personB.username == 'Jessie'
		assert result[4].relationshipReasons == [RelationshipReason.SIMILAR_INTEREST]
		assert result[4].intersections == ['skateboarding']
		
	}
	
	@Test
	public void "users can have 2nd and 3rd degree friends with interests alt"() {
		
		def network = createNetwork()
		def service = new FriendSuggestionService()
		def result = service.createRelationshipSuggestions(network.people[7])
		
		assert result.size() == 3
		
		assert result[0].personA.username == 'Jessie'
		assert result[0].personB.username == 'Gene'
		assert result[0].relationshipReasons == [RelationshipReason.SIMILAR_INTEREST, RelationshipReason.SECOND_DEGREE_RELATIONSHIP]
		assert result[0].intersections == ['meeting new people']
		
		assert result[1].personA.username == 'Jessie'
		assert result[1].personB.username == 'Sarah'
		assert result[1].relationshipReasons == [RelationshipReason.SIMILAR_INTEREST, RelationshipReason.THIRD_DEGREE_RELATIONSHIP]
		assert result[1].intersections == ['movies']
		
		assert result[2].personA.username == 'Jessie'
		assert result[2].personB.username == 'Bob'
		assert result[2].relationshipReasons == [RelationshipReason.SIMILAR_INTEREST]
		assert result[2].intersections == ['skateboarding']
		
	}
	
	private List<Person> createPeople() {
		def people = new ArrayList<>()
		
		def bob = new Person()
		bob.id = 1
		bob.username = 'Bob'
		bob.interests = ['going out', 'musicals', 'skateboarding']
		
		people << bob
		
		def joan = new Person()
		joan.id = 2
		joan.username = 'Joan'
		joan.interests = ['reading', 'skate boarding', 'music']
		
		people << joan
		
		def sarah = new Person()
		sarah.id = 3
		sarah.username = 'Sarah'
		sarah.interests = ['skate boarding', 'reading', 'movies']
		
		people << sarah
		
		def sam = new Person()
		sam.id = 4
		sam.username = 'Sam'
		sam.interests = ['bike riding', 'musicals', 'cooking']
		
		people << sam
		
		def bill = new Person()
		bill.id = 5
		bill.username = 'Bill'
		bill.interests = ['reading', 'musicals', 'going out']
		
		people << bill
		
		def gene = new Person()
		gene.id = 6
		gene.username = 'Gene'
		gene.interests = ['meeting new people', 'bike riding', 'reading']
		
		people << gene
		
		def samson = new Person()
		samson.id = 7
		samson.username = 'Samson'
		samson.interests = ['skateboarding', 'knitting', 'meeting new people']
	
		people << samson
		
		def jessie = new Person()
		jessie.id = 8
		jessie.username = 'Jessie'
		jessie.interests = ['rowing', 'skateboarding', 'movies', 'meeting new people']
	
		people << jessie
		
		return people
	}
	
	private Network createNetwork() {
		def people = createPeople()
		def weighting = 1
		
		people[0].friends << [ (people[1]) : weighting ]
		people[0].friends << [ (people[2]) : weighting ]
		
		people[1].friends << [ (people[0]) : weighting ]
		people[1].friends << [ (people[3]) : weighting ]
		people[1].friends << [ (people[4]) : weighting ]
		
		people[2].friends << [ (people[0]) : weighting ]
		people[2].friends << [ (people[4]) : weighting ]
		people[2].friends << [ (people[5]) : weighting ]
		
		people[3].friends << [ (people[1]) : weighting ]
		
		people[4].friends << [ (people[1]) : weighting ]
		people[4].friends << [ (people[2]) : weighting ]
		
		people[5].friends << [ (people[2]) : weighting ]
		people[5].friends << [ (people[6]) : weighting ]
		
		people[6].friends << [ (people[5]) : weighting ]
		people[6].friends << [ (people[7]) : weighting ]
		
		people[7].friends << [ (people[6]) : weighting ]
		
		return new Network(people)
	}
	
}
