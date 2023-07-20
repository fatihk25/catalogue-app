package id.co.nds.catalogue.validators;

import id.co.nds.catalogue.exceptions.ClientException;
import id.co.nds.catalogue.globals.GlobalConstant;

public class ProductValidator {
    public void nullCheckProductId(Integer id) throws ClientException {
        if(id == null)  {
            throw new ClientException("Product ID cannot be null or zero");
        }
    }

    public void  notNullCheckProductId(Integer Id) throws ClientException {
        if(Id != null) {
            throw new ClientException("Product ID is auto generated, do not input id");
        }
    }

    public void nullCheckName(String name) throws ClientException {
        if(name == null) {
            throw new ClientException("Product name is required");
        }
    }

    public void nullCheckQuantity(Integer quantity) throws ClientException {
        if(quantity == null) {
            throw new ClientException("Product quantity is required");
        }
    }

    public void nullCheckCategoryId(String categoryId) throws ClientException {
        if(categoryId == null) {
            throw new ClientException("Product category is required");
        }
    }

    public void nullCheckObject(Object o) throws ClientException {
        if(o == null) {
            throw new ClientException("Product object is required");
        }
    }

    public void validatedProductId(Integer id) throws ClientException {
        if(id <= 0) {
            throw new ClientException("Product ID input is invalid");
        }
    }

    public void validateName(String name) throws ClientException {
        if(name.trim().equalsIgnoreCase("")) {
            throw new ClientException("Product name is required");
        }
    }

    public void validateQuantity(Integer quantity) throws ClientException {
        if(quantity < 0) {
            throw new ClientException("Product quantity must be positive integer");
        }
    }

    public void validateCategoryId(String categoryId) throws ClientException {
        if(categoryId.length() != 6 || !categoryId.startsWith("PC")) {
            throw new ClientException("Product category id contains six sigits and starts with 'pc' ");
        }
    }

    public void validateRecStatus(String id, String recStatus) throws ClientException {
        if(recStatus.equalsIgnoreCase(GlobalConstant.REC_STATUS_NON_ACTIVE)) {
            throw new ClientException("Product with id " + id + " is already deleted");
        }
    }
}
