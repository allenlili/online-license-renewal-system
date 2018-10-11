package au.edu.unsw.comp9322.REST.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import au.edu.unsw.comp9322.REST.model.SequenceId;

public interface SequenceIdRepository extends MongoRepository<SequenceId, String> {

}
