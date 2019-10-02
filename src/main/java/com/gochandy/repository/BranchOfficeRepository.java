package com.gochandy.repository;
import com.gochandy.domain.BranchOffice;
import com.gochandy.domain.BranchOfficeId;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the BranchOffice entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BranchOfficeRepository extends JpaRepository<BranchOffice, BranchOfficeId>, JpaSpecificationExecutor<BranchOffice> {

}
