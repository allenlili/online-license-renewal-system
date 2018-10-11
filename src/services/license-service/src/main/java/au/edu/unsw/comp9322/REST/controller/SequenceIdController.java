package au.edu.unsw.comp9322.REST.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import au.edu.unsw.comp9322.REST.model.SequenceId;
import au.edu.unsw.comp9322.REST.repository.SequenceIdRepository;

@RestController
@RequestMapping("/api/sequenceId")
public class SequenceIdController {

	@Autowired
	private SequenceIdRepository sequenceIdRepository;

	/**
	 * initiate sequenceId for resources.
	 * 
	 * usage: ../api/sequenceId/ POST { "id":"license", "seq":"0" }
	 * 
	 * @param sequenceId
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public String createNotice(@RequestBody SequenceId sequenceId) {
		try {
			sequenceId = sequenceIdRepository.save(sequenceId);
			return "success to inital sequenceId for " + sequenceId.getId();
		} catch (Exception e) {
			e.printStackTrace();
			return "fail to inital sequenceId for " + sequenceId.getId();
		}

	}

}
