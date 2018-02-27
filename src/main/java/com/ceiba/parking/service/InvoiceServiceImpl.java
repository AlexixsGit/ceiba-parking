package com.ceiba.parking.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ceiba.parking.model.Admin;
import com.ceiba.parking.model.Invoice;
import com.ceiba.parking.model.Parking;
import com.ceiba.parking.model.Price;
import com.ceiba.parking.repository.AdminRepository;
import com.ceiba.parking.repository.InvoiceRepository;
import com.ceiba.parking.repository.ParkingRepository;
import com.ceiba.parking.repository.PriceRepository;
import com.ceiba.parking.repository.VehicleTypeRepository;
import com.ceiba.parking.util.Constants;

@Service
public class InvoiceServiceImpl implements InvoiceService {

	@Autowired
	protected InvoiceRepository invoiceRepository;

	@Autowired
	protected AdminRepository adminRepository;

	@Autowired
	protected VehicleTypeRepository vehicleTypeRepository;

	@Autowired
	protected PriceRepository priceRepository;

	@Autowired
	protected ParkingRepository parkingRepository;

	@Override
	public Invoice save(Invoice invoice) {
		return this.invoiceRepository.save(invoice);
	}

	@Override
	public boolean validate(Parking parking) {
		boolean isValid = true;

		if (parking.getPlaque().isEmpty()) {
			isValid = false;
		}

		if (parking.getVehicleType() == null || parking.getVehicleType().getId() == null) {
			isValid = false;
		}
		return isValid;
	}

	@Override
	public Invoice complete(Parking parking, Invoice invoice) {
		Invoice newInvoice = invoice;
		newInvoice.setIva(0d);
		newInvoice.setCreationDate(new Date());
		newInvoice.setPlaque(parking.getPlaque());
		newInvoice.setEngineCapacity(parking.getEngineCapacity());
		newInvoice.setDescVehicleType(
				this.vehicleTypeRepository.findOne(parking.getVehicleType().getId()).getDescription());
		newInvoice.setEmployee(Constants.EMPLOYEE);
		newInvoice.setEntryDate(parking.getEntryDate());
		newInvoice.setDepartureDate(parking.getDepartureDate());

		Double additionalCost = this.calculateAdditionalCost(parking);
		newInvoice.setAdditionalCost(additionalCost);
		Double subtotal = this.calculateSubtotal(parking);
		newInvoice.setSubtotal(subtotal);
		newInvoice.setTotal(subtotal + additionalCost + newInvoice.getIva());

		return newInvoice;
	}

	@Override
	public Double calculateAdditionalCost(Parking parking) {

		Double price = 0d;
		Admin admin = this.adminRepository.findByVehicleType(parking.getVehicleType().getId());

		if (parking.getEngineCapacity() > admin.getEngineCapacity()) {
			Price priceEntity = this.priceRepository.findByVehicleType(parking.getVehicleType());
			if (priceEntity != null) {
				price = priceEntity.getHighEnginePrice();
			}
		}
		return price;
	}

	@Override
	public List<Long> getTimes(Parking parking) {
		Admin admin = this.adminRepository.findByVehicleType(parking.getVehicleType().getId());

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(parking.getEntryDate());
		Date entryDate = calendar.getTime();

		calendar = Calendar.getInstance();
		calendar.setTime(parking.getDepartureDate());
		Date departureDate = calendar.getTime();

		// Calcula el total de minutos que tienen las fechas
		long totalMinutes = (((departureDate.getTime() - entryDate.getTime()) / 1000) / 60);

		// Calcula el total de horas que tienen las fechas
		long totalHours = totalMinutes > 0 ? totalMinutes / 60 : 0;

		// Calcula el total de dias que tienen las fechas
		long totalDays = totalHours > 0 ? totalHours / 24 : 0;

		// Calcula las horas que le restan a las fechas
		long remainHours = Math.abs(((totalDays * 24) * 60) - (totalHours * 60)) / 60;

		// Calcula los minutos que le restan a las fechas
		long remainMinutes = Math.abs(totalMinutes - (totalHours * 60));

		// Calcula los dias que le restan a las fechas
		long remainDays = admin.getHoursForADay() > 0 && admin.getHoursForADay() <= 24
				? remainHours / admin.getHoursForADay()
				: 0;

		// Calcula las horas que faltan
		remainHours = remainHours - (remainDays * admin.getHoursForADay());

		List<Long> times = new ArrayList<>();
		times.add(totalDays);
		times.add(remainDays);
		times.add(remainHours);
		times.add(remainMinutes);

		return times;
	}

	@Override
	public Double calculateSubtotal(Parking parking) {
		Price price = this.priceRepository.findByVehicleType(parking.getVehicleType());
		Double subtotal = 0d;

		if (price != null) {

			List<Long> times = this.getTimes(parking);

			long totalDays = times.get(0);
			long remainDays = times.get(1);
			long remainHours = times.get(2);
			long remainMinutes = times.get(3);

			subtotal = ((totalDays + remainDays) * price.getDayPrice()) + (remainHours * price.getHourPrice())
					+ (remainMinutes > 0 ? price.getHourPrice() : 0d);
		}
		return subtotal;

	}

	@Override
	public boolean validateIfPlaqueExists(String plaque) {
		return !this.parkingRepository.findByPlaqueWithDepartureDate(plaque).isEmpty();
	}
}
