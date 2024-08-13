package com.bareuni.coredomain.domain.hospital.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bareuni.coredomain.domain.hospital.HospitalImage;

public interface HospitalImageRepository extends JpaRepository<HospitalImage, Long> {
}
