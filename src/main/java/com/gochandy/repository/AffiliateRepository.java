package com.gochandy.repository;
import com.gochandy.domain.Affiliate;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Affiliate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AffiliateRepository extends JpaRepository<Affiliate, String>, JpaSpecificationExecutor<Affiliate> {

}
