package id.co.nds.catalogue.validators;

import id.co.nds.catalogue.exceptions.ClientException;
import id.co.nds.catalogue.globals.GlobalConstant;

public class RoleValidator {
    public void nullCheckRoleId(String id) throws ClientException {
        if(id == null)  {
            throw new ClientException("Role ID cannot be null or zero");
        }
    }

    public void  notNullCheckRoleId(String id) throws ClientException {
        if(id != null) {
            throw new ClientException("Role ID is auto generated, do not input id");
        }
    }

    public void nullCheckName(String name) throws ClientException {
        if(name == null) {
            throw new ClientException("Role name is required");
        }
    }

    public void nullCheckObject(Object o) throws ClientException {
        if(o == null) {
            throw new ClientException("Product object is required");
        }
    }

    public void validateRoleId(String roleId) throws ClientException {
        if(roleId.length()!= 5 || !roleId.startsWith("R")) {
            throw new ClientException("User role id contains 5 digits and starts with 'R'");
        }
    }


    public void validateName(String name) throws ClientException {
        if(name.trim().equalsIgnoreCase("")) {
            throw new ClientException("Role name is required");
        }
    }

    public void validateRecStatus(String id, String recStatus) throws ClientException {
        if(recStatus.equalsIgnoreCase(GlobalConstant.REC_STATUS_NON_ACTIVE)) {
            throw new ClientException("Role with id " + id + " is already deleted");
        }
    }
}
