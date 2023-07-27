package id.co.nds.catalogue.services;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import id.co.nds.catalogue.entities.CategoryEntity;
import id.co.nds.catalogue.exceptions.ClientException;
import id.co.nds.catalogue.exceptions.NotFoundException;
import id.co.nds.catalogue.globals.GlobalConstant;
import id.co.nds.catalogue.models.CategoryModel;
import id.co.nds.catalogue.repos.CategoryRepo;
import id.co.nds.catalogue.validators.CategoryValidator;

@Service
public class CategoryService implements Serializable {
    @Autowired
    private CategoryRepo categoryRepo;

    CategoryValidator categoryValidator = new CategoryValidator();

    public CategoryEntity add(CategoryModel categoryModel) throws ClientException {
        categoryValidator.notNullCheckCategoryId(categoryModel.getId());
        categoryValidator.nullCheckName(categoryModel.getName());
        categoryValidator.validateName(categoryModel.getName());

        long count = categoryRepo.countByName(categoryModel.getName());
        if(count > 0) {
            throw new ClientException("Category name already exists");
        }

        CategoryEntity category = new CategoryEntity();
        category.setName(categoryModel.getName());
        category.setRecStatus(GlobalConstant.REC_STATUS_ACTIVE);
        category.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        category.setCreatorId(
            categoryModel.getActorId() == null ?  0 : categoryModel.getActorId()
        );
        return categoryRepo.save(category);
    }

    public List<CategoryEntity> findAll() {
        List<CategoryEntity> categories = new ArrayList<>();
        categoryRepo.findAll().forEach(categories::add);
        return categories;
    }

    public CategoryEntity findById(String id) throws ClientException, NotFoundException {
        categoryValidator.notNullCheckCategoryId(id);
        categoryValidator.validatedCategoryId(id);
        CategoryEntity category = categoryRepo.findById(id).orElse(null);
        categoryValidator.nullCheckObject(category);

        return category;
    }

    public CategoryEntity edit(CategoryModel categoryModel) throws ClientException, NotFoundException {
        categoryValidator.notNullCheckCategoryId(categoryModel.getId());
        categoryValidator.validatedCategoryId(categoryModel.getId());
        categoryValidator.nullCheckName(categoryModel.getName());
        categoryValidator.validateName(categoryModel.getName());

        if(!categoryRepo.existsById(categoryModel.getId())) {
            throw new NotFoundException("Catgeory id not found : " + categoryModel.getId());
        }

        CategoryEntity category = new CategoryEntity();
        category = findById(categoryModel.getId());

        if(categoryModel.getName() != null) {
            categoryValidator.validateName(categoryModel.getName());
            Long count = categoryRepo.countByName(categoryModel.getName());
            if(count > 0) {
                throw new ClientException("Category name already exists");
            }

            category.setName(categoryModel.getName());
        }

        category.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
        category.setUpdaterId(
            categoryModel.getActorId() == null ?  0 : categoryModel.getActorId()
        );

        return categoryRepo.save(category);
    }

    public CategoryEntity delete(CategoryModel categoryModel) throws ClientException, NotFoundException {
        categoryValidator.nullCheckCategoryId(categoryModel.getId());
        categoryValidator.validatedCategoryId(categoryModel.getId());

        if(!categoryRepo.existsById(categoryModel.getId())) {
            throw new NotFoundException("Category id not found : " + categoryModel.getId());
        }

        CategoryEntity category = new CategoryEntity();
        category = findById(categoryModel.getId());

        if(category.getRecStatus().equalsIgnoreCase(GlobalConstant.REC_STATUS_NON_ACTIVE)) {
            throw new ClientException("Category with id " + categoryModel.getId() + " is already deleted");
        }

        category.setRecStatus(GlobalConstant.REC_STATUS_NON_ACTIVE);
        category.setDeletedDate(new Timestamp(System.currentTimeMillis()));
        category.setDeleterId(
            categoryModel.getActorId() == null ?  0 : categoryModel.getActorId()
        );

        return categoryRepo.save(category);
    }
}
