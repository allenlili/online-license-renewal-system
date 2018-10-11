package au.edu.unsw.comp9322.REST.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import au.edu.unsw.comp9322.REST.model.Payment;

public interface PaymentRepository extends MongoRepository<Payment, Long> {

	Payment findByNoticeId(long noticeId);

	Payment findById(long id);

}
