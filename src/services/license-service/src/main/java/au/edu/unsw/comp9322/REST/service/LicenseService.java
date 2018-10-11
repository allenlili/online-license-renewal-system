package au.edu.unsw.comp9322.REST.service;

import java.util.List;

import au.edu.unsw.comp9322.REST.model.License;
import au.edu.unsw.comp9322.REST.model.Notice;

public interface LicenseService {

	License getLicenseById(long id);

	License updateLicense(License license);

	List<License> getRenewableLicenses();

	String updateExpireDate(License license, Notice notice);

}
