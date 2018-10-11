package au.edu.unsw.comp9322.REST.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import au.edu.unsw.comp9322.REST.model.Officer;
import au.edu.unsw.comp9322.REST.repository.OfficerRepository;
import au.edu.unsw.comp9322.REST.service.OfficerService;

@Service
public class OfficerServiceImpl implements OfficerService {
	@Autowired
	OfficerRepository officerRepository;

	@Override
	public Officer getOfficerById(Long id) {
		return officerRepository.findById(id);
	}

}
