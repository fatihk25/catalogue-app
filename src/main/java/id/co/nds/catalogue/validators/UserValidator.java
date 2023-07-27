package id.co.nds.catalogue.validators;

import id.co.nds.catalogue.exceptions.ClientException;
import id.co.nds.catalogue.globals.GlobalConstant;

public class UserValidator {
    public void nullCheckUserId(Integer id) throws ClientException {
        if(id == null)  {
            throw new ClientException("User ID cannot be null or zero");
        }
    }

    public void  notNullCheckUserId(Integer Id) throws ClientException {
        if(Id != null) {
            throw new ClientException("User ID is auto generated, do not input id");
        }
    }

    public void nullCheckFullName(String fullName) throws ClientException {
        if(fullName == null) {
            throw new ClientException("User FullName is required");
        }
    }

    public void nullCheckRoleId(String roleId) throws ClientException {
        if(roleId == null) {
            throw new ClientException("Role id is required");
        }
    }

    public void nullCheckCallNumber(String callNumber) throws ClientException {
        if(callNumber == null) {
            throw new ClientException("User call number is required");
        }
    }

    public void nullCheckObject(Object o) throws ClientException {
        if(o == null) {
            throw new ClientException("User object is required");
        }
    }

    public void validatedUserId(Integer id) throws ClientException {
        if(id <= 0) {
            throw new ClientException("User ID input is invalid");
        }
    }

    public void validateFullName(String fullName) throws ClientException {
        if(fullName.trim().equalsIgnoreCase("")) {
            throw new ClientException("User full name is required");
        }
    }

    public void validateRoleId(String roleId) throws ClientException {
        if(roleId.length()!= 5 || !roleId.startsWith("R")) {
            throw new ClientException("User role id contains 5 digits and starts with 'R'");
        }
    }

    public void validateCallNumber(String callNumber) throws ClientException {
        if(!(callNumber.length() >= 10 && callNumber.length() <= 13) ||
                !(callNumber.startsWith("0") || callNumber.startsWith("+62")) ||
                !callNumber.substring(1).matches("\\d{9,12}")) {
            throw new ClientException("User call number contains 9-12 digits and starts with '0' or '+62'");
        }
    }

    public void validateRecStatus(String id, String recStatus) throws ClientException {
        if(recStatus.equalsIgnoreCase(GlobalConstant.REC_STATUS_NON_ACTIVE)) {
            throw new ClientException("User with id " + id + " is already deleted");
        }
    }
}
