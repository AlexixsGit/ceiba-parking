package com.ceiba.parking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ceiba.parking.model.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

}
