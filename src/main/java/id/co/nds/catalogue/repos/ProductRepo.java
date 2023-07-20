package id.co.nds.catalogue.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import id.co.nds.catalogue.entities.ProductEntity;
import id.co.nds.catalogue.globals.GlobalConstant;

@Repository
public interface ProductRepo extends JpaRepository<ProductEntity, Integer>, JpaSpecificationExecutor<ProductEntity> {
    @Query(value = "SELECT COUNT(*) FROM ms_product WHERE rec_status = '" +
        GlobalConstant.REC_STATUS_ACTIVE 
        + "' AND lower(name) = lower(:name)", nativeQuery = true)
        long countByName(@Param("name") String name);
}
