package com.v1.manfaa.Repository;

import com.v1.manfaa.Model.CompanyProfile;
import com.v1.manfaa.Model.ServiceRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, Integer> {
    ServiceRequest findServiceRequestById(Integer id);

    List<ServiceRequest> findServiceRequestsByCompanyProfile(CompanyProfile companyProfile);

    List<ServiceRequest> findServiceRequestsByCompanyProfileAndStatus(CompanyProfile companyProfile, String status);

    @Query("SELECT sr FROM ServiceRequest sr WHERE LOWER(sr.title) LIKE LOWER(CONCAT('%', ?1, '%')) OR LOWER(sr.description) LIKE LOWER(CONCAT('%', ?1, '%'))")
    List<ServiceRequest> searchServiceRequestsByKeyword(String keyword);



}
