package id.co.nds.catalogue.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import id.co.nds.catalogue.entities.CategoryEntity;
import id.co.nds.catalogue.exceptions.ClientException;
import id.co.nds.catalogue.exceptions.NotFoundException;
import id.co.nds.catalogue.models.CategoryModel;
import id.co.nds.catalogue.models.ResponseModel;
import id.co.nds.catalogue.services.CategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping(value="/add")
    public ResponseEntity<ResponseModel> postCategoryController(@RequestBody CategoryModel categoryModel) {
        try {
            CategoryEntity category = categoryService.add(categoryModel);
            ResponseModel response = new ResponseModel();
            response.setMsg("Category added successfully");
            response.setData(category);
            return ResponseEntity.ok(response);
        } catch(Exception e) {
            ResponseModel response = new ResponseModel();
            response.setMsg(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping(value="/get")
    public ResponseEntity<ResponseModel> getAllCategoriesController() {
        try {
            List<CategoryEntity> category = categoryService.findAll();
            ResponseModel response = new ResponseModel();
            response.setMsg("Request successfully");
            response.setData(category);
            return ResponseEntity.ok(response);
        } catch(Exception e) {
            ResponseModel response = new ResponseModel();
            response.setMsg(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping(value="/get/{id}")
    public ResponseEntity<ResponseModel> getCategoryByIdController(@PathVariable String id) {
        try {
            CategoryEntity category = categoryService.findById(id);
            ResponseModel response = new ResponseModel();
            response.setMsg("Request successfully");
            response.setData(category);
            return ResponseEntity.ok(response);
        } catch(ClientException e) {
            ResponseModel response = new ResponseModel();
            response.setMsg(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch(NotFoundException e) {
            ResponseModel response = new ResponseModel();
            response.setMsg(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch(Exception e) {
            ResponseModel response = new ResponseModel();
            response.setMsg(e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PutMapping(value="/update")
    public ResponseEntity<ResponseModel> putCategoryController(@RequestBody CategoryModel categoryModel) {
        try {
            CategoryEntity category = categoryService.edit(categoryModel);
            ResponseModel response = new ResponseModel();
            response.setMsg("Category updated successfully");
            response.setData(category);
            return ResponseEntity.ok(response);
        } catch(ClientException e) {
            ResponseModel response = new ResponseModel();
            response.setMsg(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch(NotFoundException e) {
            ResponseModel response = new ResponseModel();
            response.setMsg(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch(Exception e) {
            ResponseModel response = new ResponseModel();
            response.setMsg(e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @DeleteMapping(value="/delete")
    public ResponseEntity<ResponseModel> deleteCategoryController(@RequestBody CategoryModel categoryModel) {
        try {
            CategoryEntity category = categoryService.delete(categoryModel);
            ResponseModel response = new ResponseModel();
            response.setMsg("Category deleted successfully");
            response.setData(category);
            return ResponseEntity.ok(response);
        }catch(ClientException e) {
            ResponseModel response = new ResponseModel();
            response.setMsg(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch(NotFoundException e) {
            ResponseModel response = new ResponseModel();
            response.setMsg(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch(Exception e) {
            ResponseModel response = new ResponseModel();
            response.setMsg(e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

}
