package com.ceiba.parking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ceiba.parking.repository.AdminRepository;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	protected AdminRepository adminRepository;
}
