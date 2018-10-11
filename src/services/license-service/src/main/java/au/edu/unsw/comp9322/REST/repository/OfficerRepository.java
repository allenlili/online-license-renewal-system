package au.edu.unsw.comp9322.REST.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import au.edu.unsw.comp9322.REST.model.Officer;

public interface OfficerRepository extends MongoRepository<Officer, Long> {

	Officer findById(Long id);

}
