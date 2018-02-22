package com.ceiba.parking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ceiba.parking.repository.PriceRepository;

@Service
public class PriceServiceImpl implements PriceService {

	@Autowired
	protected PriceRepository priceRepositor;
}
