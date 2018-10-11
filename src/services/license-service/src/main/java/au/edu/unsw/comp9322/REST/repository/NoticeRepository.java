package au.edu.unsw.comp9322.REST.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;

import au.edu.unsw.comp9322.REST.model.Notice;

public interface NoticeRepository extends MongoRepository<Notice, Long> {

	Notice findByUuid(UUID uuid);

	Notice findById(Long id);

	Notice findByLicenseId(Long licenseId);

	void deleteByUuid(UUID uuid);

	List<Notice> findByStatus(String status);

	List<Notice> findByOfficerIdAndStatus(Long id, String status);

	List<Notice> findByStatusAndOfficerId(String status, long officerId);

}
