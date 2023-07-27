package id.co.nds.catalogue.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import id.co.nds.catalogue.entities.RoleEntity;
import id.co.nds.catalogue.globals.GlobalConstant;

public interface RoleRepo extends JpaRepository<RoleEntity, String>, JpaSpecificationExecutor<RoleEntity>  {
    @Query(value="SELECT COUNT(*) from ms_category WHERE rec_status ='" 
    + GlobalConstant.REC_STATUS_ACTIVE+ "' AND LOWER(name) = LOWER(:name)", nativeQuery = true)
    long countByName(@Param("name") String name);

    @Query(value="SELECT u.*, r.name AS role_name FROM ms_user AS u " +
            "JOIN ms_role AS r on u.role_id = r.id " +
            "WHERE u.rec_status = '" + GlobalConstant.REC_STATUS_NON_ACTIVE + "' AND LOWER(r.name) = LOWER(:name)", nativeQuery = true)
    List<RoleEntity> findUserByRoleNameWhereNoActive(@Param("name") String name);
}
