package au.edu.unsw.comp9322.REST.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import au.edu.unsw.comp9322.REST.model.License;
import au.edu.unsw.comp9322.REST.model.Notice;
import au.edu.unsw.comp9322.REST.repository.LicenseRepository;
import au.edu.unsw.comp9322.REST.service.LicenseService;

@Service
public class LicenseServiceImpl implements LicenseService {
	@Autowired
	LicenseRepository licenseRepository;

	@Override
	public License getLicenseById(long id) {
		return licenseRepository.findById(id);
	}

	@Override
	public License updateLicense(License license) {
		System.out.println(license.toString());
		return licenseRepository.save(license);
	}

	@Override
	public List<License> getRenewableLicenses() {
		List<License> returnLicensesList = new ArrayList<License>();

		Date currentDate = new Date();
		List<License> licensesList = new ArrayList<License>();
		licensesList = licenseRepository.findAll();
		for (License license : licensesList) {
			String startDateString = license.getExpiredDate();
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			Date startDate;
			try {
				startDate = df.parse(startDateString);
				if ((startDate.getTime() - currentDate.getTime()) / (24 * 60 * 60 * 1000) <= 60
						&& (startDate.getTime() - currentDate.getTime()) / (24 * 60 * 60 * 1000) >= 0) {
					returnLicensesList.add(license);
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return returnLicensesList;
	}

	@Override
	public String updateExpireDate(License license, Notice notice) {
		String DateString = license.getExpiredDate();
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Date oldDate;
		try {
			oldDate = df.parse(DateString);
			Calendar c = Calendar.getInstance();
			c.setTime(oldDate);

			if (notice.getIsExtend().equals("extend")) {
				// extend 5 year
				System.out.println("extend!!!");
				c.add(Calendar.YEAR, 5);
			} else {
				System.out.println("NOT extend!!!");
				// extend 1 year
				c.add(Calendar.YEAR, 1);
			}
			Date newDate = c.getTime();
			String newDateString = df.format(newDate);
			return newDateString;

		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

}
