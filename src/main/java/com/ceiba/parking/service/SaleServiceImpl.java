package com.ceiba.parking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ceiba.parking.repository.SaleRepository;

@Service
public class SaleServiceImpl implements SaleService {

	@Autowired
	protected SaleRepository saleRepository;
}
