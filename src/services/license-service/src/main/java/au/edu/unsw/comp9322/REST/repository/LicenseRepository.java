package au.edu.unsw.comp9322.REST.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import au.edu.unsw.comp9322.REST.model.License;

public interface LicenseRepository extends MongoRepository<License, Long> {

	License findById(Long id);

}
