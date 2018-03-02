package com.ceiba.parking.service;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ceiba.parking.model.Admin;
import com.ceiba.parking.model.Parking;
import com.ceiba.parking.model.VehicleType;
import com.ceiba.parking.repository.AdminRepository;
import com.ceiba.parking.repository.ParkingRepository;
import com.ceiba.parking.repository.VehicleTypeRepository;

@Service
public class ParkingServiceImpl implements ParkingService {

	@Autowired
	protected ParkingRepository parkingRepository;

	@Autowired
	protected AdminRepository adminRepository;

	@Autowired
	protected VehicleTypeRepository vehicleTypeRepository;

	@Override
	public Parking save(Parking parking) {
		return this.parkingRepository.save(parking);
	}

	@Override
	public boolean validateCapacity(Parking parking) {

		if (parking.isNew()) {
			Admin admin = this.adminRepository.findByVehicleType(parking.getVehicleType().getId());
			List<Parking> parkingList = this.parkingRepository.findAllByType(parking.getVehicleType().getId());
			return parkingList.size() < admin.getCapacity();
		}
		return true;
	}

	@Override
	public boolean validateIfPlaqueIsPermitted(Parking parking) {

		if (parking.isNew()) {
			Admin admin = this.adminRepository.findByVehicleType(parking.getVehicleType().getId());

			Calendar calendar = GregorianCalendar.getInstance();
			calendar.setTime(parking.getEntryDate());
			int day = calendar.get(Calendar.DAY_OF_WEEK);

			if (parking.getPlaque().substring(0, 1).toUpperCase()
					.contains(admin.getRestrictPlaqueLetter().toUpperCase())) {
				return (day == Calendar.SUNDAY || day == Calendar.MONDAY);
			}
		}
		return true;
	}

	@Override
	public boolean validate(Parking parking) {
		boolean isValid = true;

		if (parking.getVehicleType() == null) {
			isValid = false;
		}

		if (StringUtils.trimToNull(parking.getPlaque()) == null) {
			isValid = false;
		}

		return isValid;
	}

	@Override
	public Parking complete(Parking parking) {
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(new Date());
		int hours = calendar.get(Calendar.HOUR);
		int minutes = calendar.get(Calendar.MINUTE);

		if (parking.isNew()) {
			if (parking.getEntryDate() == null) {
				parking.setEntryDate(new Date());
			}
			parking.setEntryHour(hours + ":" + minutes);
		} else {
			Date departureDate = parking.getDepartureDate();
			parking = this.parkingRepository.findOne(parking.getId());

			if (departureDate == null) {
				parking.setDepartureDate(new Date());
			}
			parking.setDepartureHour(hours + ":" + minutes);
		}
		return parking;
	}

	@Override
	public boolean validateIfPlaqueExist(Parking parking) {

		if (parking.isNew()) {
			int size = this.parkingRepository.findByPlaque(parking.getPlaque()).size();
			return size > 0;
		}
		return false;
	}

	@Override
	public boolean validateVehicleType(VehicleType vehicleType) {

		if (vehicleType != null) {
			return this.vehicleTypeRepository.findOne(vehicleType.getId()) != null;
		}
		return false;
	}

	@Override
	public List<Parking> findAll() {
		return this.parkingRepository.findAll();
	}

	@Override
	public List<Parking> findByPlaque(String plaque) {
		return this.parkingRepository.findByPlaque(plaque);
	}

}
