package id.co.nds.catalogue.services;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import id.co.nds.catalogue.entities.RoleEntity;
import id.co.nds.catalogue.entities.UserEntity;
import id.co.nds.catalogue.exceptions.ClientException;
import id.co.nds.catalogue.exceptions.NotFoundException;
import id.co.nds.catalogue.globals.GlobalConstant;
import id.co.nds.catalogue.models.UserModel;
import id.co.nds.catalogue.repos.RoleRepo;
import id.co.nds.catalogue.repos.UserRepo;
import id.co.nds.catalogue.repos.specs.UserSpec;
import id.co.nds.catalogue.validators.RoleValidator;
import id.co.nds.catalogue.validators.UserValidator;

@Service
public class UserService implements Serializable {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    UserValidator userValidator = new UserValidator();
    RoleValidator roleValidator = new RoleValidator();

    public UserEntity add(UserModel userModel) throws ClientException {
        userValidator.notNullCheckUserId(userModel.getId());
        userValidator.nullCheckFullName(userModel.getFullName());
        userValidator.validateFullName(userModel.getFullName());
        userValidator.nullCheckRoleId(userModel.getRoleId());
        userValidator.validateRoleId(userModel.getRoleId());
        userValidator.validateCallNumber(userModel.getCallNumber());

        long count = userRepo.countByName(userModel.getFullName());
        if(count > 0) {
            throw new ClientException("User full name already exists");
        }

        UserEntity user = new UserEntity();
        user.setFullName(userModel.getFullName());
        user.setRoleId(userModel.getRoleId());
        user.setCallNumber(userModel.getCallNumber());
        user.setRecStatus(GlobalConstant.REC_STATUS_ACTIVE);
        user.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        user.setCreatorId(
            userModel.getActorId() == null ?  0 : userModel.getActorId()
        );
        return userRepo.save(user);
    }

    public List<UserEntity> findAll() {
        List<UserEntity> users = new ArrayList<>();
        userRepo.findAll().forEach(users::add);
        return users;
    }

    public List<UserEntity> findAllByCriteria(UserModel userModel) {
        List<UserEntity> users = new ArrayList<>();
        UserSpec userSpec = new UserSpec(userModel);
        userRepo.findAll(userSpec).forEach(users::add);
        return users;
    }

    public List<UserEntity> findAllbyRoleName(String roleName) throws ClientException, NotFoundException {
        roleValidator.nullCheckName(roleName);
        roleValidator.validateName(roleName);
        List<UserEntity> users = userRepo.findUserByRoleName(roleName);
        userValidator.nullCheckObject(users);
        return users;
    }

    public UserEntity findById(Integer id) throws ClientException, NotFoundException {
        userValidator.nullCheckUserId(id);
        userValidator.validatedUserId(id);
        UserEntity user = userRepo.findById(id).orElse(null);
        userValidator.nullCheckObject(user);

        return user;
    }

    public List<RoleEntity> findAllByRole(String roleId) throws ClientException, NotFoundException {
        roleValidator.nullCheckRoleId(roleId);
        roleValidator.validatedRoleId(roleId);
        List<RoleEntity> roles = roleRepo.findUserByRoleNameWhereNoActive(roleId);
        roleRepo.findAll().forEach(roles::add);
        return roles;
    }


    public UserEntity edit(UserModel userModel) throws ClientException, NotFoundException {
        userValidator.nullCheckUserId(userModel.getId());
        userValidator.validatedUserId(userModel.getId());

        if(!userRepo.existsById(userModel.getId())) {
            throw new NotFoundException("User ID not found");
        }

        UserEntity user = new UserEntity();
        user = findById(userModel.getId());

        if(userModel.getFullName() != null) {
            userValidator.validateFullName(userModel.getFullName());
            Long count = userRepo.countByName(userModel.getFullName());
            if(count > 0) {
                throw new ClientException("User full name already exists");
            }

            user.setFullName(userModel.getFullName());
        }

        if(userModel.getRoleId() != null) {
            userValidator.validateRoleId(userModel.getRoleId());
            user.setRoleId(userModel.getRoleId());
        }

        if(userModel.getCallNumber() != null) {
            userValidator.validateCallNumber(userModel.getCallNumber());
            user.setCallNumber(userModel.getCallNumber());
        }

        user.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
        user.setUpdaterId(
            userModel.getActorId() == null ?  0 : userModel.getActorId()
        );

        return userRepo.save(user);
    }

    public UserEntity delete(UserModel userModel) throws ClientException, NotFoundException {
        userValidator.nullCheckUserId(userModel.getId());
        userValidator.validatedUserId(userModel.getId());

        if(!userRepo.existsById(userModel.getId())) {
            throw new NotFoundException("User ID not found");
        }

        UserEntity user = new UserEntity();
        user = findById(userModel.getId());

        if(user.getRecStatus().trim().equalsIgnoreCase(GlobalConstant.REC_STATUS_NON_ACTIVE)) {
            throw new ClientException("User already deleted");
        }

        user.setRecStatus(GlobalConstant.REC_STATUS_NON_ACTIVE);
        user.setDeletedDate(new Timestamp(System.currentTimeMillis()));
        user.setDeleterId(
            user.getId() == null ?  0 : user.getId()
        );

        return userRepo.save(user);
    }
}
