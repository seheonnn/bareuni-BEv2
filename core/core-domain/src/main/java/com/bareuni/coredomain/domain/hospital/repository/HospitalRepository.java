package com.bareuni.coredomain.domain.hospital.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bareuni.coredomain.domain.hospital.Hospital;

public interface HospitalRepository extends JpaRepository<Hospital, Long> {
}
