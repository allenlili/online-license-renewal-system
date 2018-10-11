package au.edu.unsw.comp9322.REST.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * initial in shell: db.sequence.insert({_id: "license",seq: 0})
 */
@Document(collection = "sequenceId")
public class SequenceId {

	@Id
	private String id;

	private long seq;

	public SequenceId() {
		super();

	}

	public SequenceId(String id, long seq) {
		super();
		this.id = id;
		this.seq = seq;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getSeq() {
		return seq;
	}

	public void setSeq(long seq) {
		this.seq = seq;
	}

}
