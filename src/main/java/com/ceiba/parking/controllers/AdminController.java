package com.ceiba.parking.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ceiba.parking.model.Admin;
import com.ceiba.parking.service.AdminService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class AdminController {

	@Autowired
	protected AdminService adminService;

	protected ObjectMapper mapper;

	@RequestMapping(value = "/admin/findAll")
	public List<Admin> findAll() {
		return this.adminService.findAll();
	}
}
