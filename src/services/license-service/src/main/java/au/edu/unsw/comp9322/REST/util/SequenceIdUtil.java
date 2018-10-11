package au.edu.unsw.comp9322.REST.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import au.edu.unsw.comp9322.REST.exception.SequenceIdException;
import au.edu.unsw.comp9322.REST.model.SequenceId;

@Repository
public class SequenceIdUtil {
	@Autowired
	private MongoOperations mongoOperation;

	public long getNextSequenceId(String key) {

		Query query = new Query(Criteria.where("_id").is(key));

		Update update = new Update();
		update.inc("seq", 1);

		FindAndModifyOptions options = new FindAndModifyOptions();
		options.returnNew(true);

		SequenceId seqId = mongoOperation.findAndModify(query, update, options, SequenceId.class);

		if (seqId == null) {
			throw new SequenceIdException("Unable to get sequence id for key : " + key);
		}

		return seqId.getSeq();

	}

	/*
	 * public long getPreviousSequenceId(String key) {
	 * 
	 * Query query = new Query(Criteria.where("_id").is(key));
	 * 
	 * Update update = new Update(); update.inc("seq", -1);
	 * 
	 * FindAndModifyOptions options = new FindAndModifyOptions();
	 * options.returnNew(true);
	 * 
	 * SequenceId seqId = mongoOperation.findAndModify(query, update, options,
	 * SequenceId.class);
	 * 
	 * if (seqId == null) { throw new
	 * SequenceIdException("Unable to get sequence id for key : " + key); }
	 * 
	 * return seqId.getSeq();
	 * 
	 * }
	 */
}
