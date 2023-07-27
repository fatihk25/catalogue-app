package id.co.nds.catalogue.validators;

import id.co.nds.catalogue.exceptions.ClientException;
import id.co.nds.catalogue.globals.GlobalConstant;

public class CategoryValidator {
    public void nullCheckCategoryId(String id) throws ClientException {
        if(id == null)  {
            throw new ClientException("Category ID cannot be null or zero");
        }
    }

    public void  notNullCheckCategoryId(String id) throws ClientException {
        if(id != null) {
            throw new ClientException("Category ID is auto generated, do not input id");
        }
    }

    public void nullCheckName(String name) throws ClientException {
        if(name == null) {
            throw new ClientException("Category name is required");
        }
    }

    public void nullCheckObject(Object o) throws ClientException {
        if(o == null) {
            throw new ClientException("Product object is required");
        }
    }

    public void validatedCategoryId(String id) throws ClientException {
        if(id.length() == 0 || !id.startsWith("PC")) {
            throw new ClientException("Category ID must contains six digits and starts with 'PC'");
        }
    }

    public void validateName(String name) throws ClientException {
        if(name.trim().equalsIgnoreCase("")) {
            throw new ClientException("Category name is required");
        }
    }

    public void validateRecStatus(String id, String recStatus) throws ClientException {
        if(recStatus.equalsIgnoreCase(GlobalConstant.REC_STATUS_NON_ACTIVE)) {
            throw new ClientException("Category with id " + id + " is already deleted");
        }
    }
}
