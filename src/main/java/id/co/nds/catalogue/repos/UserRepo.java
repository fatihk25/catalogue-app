package id.co.nds.catalogue.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import id.co.nds.catalogue.entities.RoleEntity;
import id.co.nds.catalogue.entities.UserEntity;
import id.co.nds.catalogue.globals.GlobalConstant;

public interface UserRepo extends JpaRepository<UserEntity, Integer>, JpaSpecificationExecutor<UserEntity> {
    @Query(value = "SELECT COUNT(*) FROM ms_user WHERE rec_status = '" +
        GlobalConstant.REC_STATUS_ACTIVE 
        + "' AND lower(fullname) = lower(:fullname)", nativeQuery = true)
        long countByName(@Param("fullname") String fullName);


     @Query(value="SELECT u.*, r.name AS role_name FROM ms_user AS u " +
            "JOIN ms_role AS r on u.role_id = r.id " +
            "WHERE r.name = ?1", nativeQuery = true)
    List<UserEntity> findUserByRoleName(@Param("roleName") String roleName);
}
