package com.bareuni.coredomain.domain.hospital.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bareuni.coredomain.domain.hospital.HospitalImage;

public interface HospitalImageRepository extends JpaRepository<HospitalImage, Long> {

	List<HospitalImage> findAllByHospitalIsNull();
}
