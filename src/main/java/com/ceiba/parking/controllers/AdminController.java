package com.ceiba.parking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.ceiba.parking.service.AdminService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class AdminController {

	@Autowired
	protected AdminService adminService;

	protected ObjectMapper mapper;
}
