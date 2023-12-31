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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import id.co.nds.catalogue.entities.ProductEntity;
import id.co.nds.catalogue.entities.ProductInfoEntity;
import id.co.nds.catalogue.exceptions.ClientException;
import id.co.nds.catalogue.exceptions.NotFoundException;
import id.co.nds.catalogue.models.ProductModel;
import id.co.nds.catalogue.models.ResponseModel;
import id.co.nds.catalogue.services.ProductService;

@RestController
@RequestMapping(value="/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping(value="/add")
    public ResponseEntity<ResponseModel> postProductController(@RequestBody ProductModel productModel){
        try {
            ProductEntity product = productService.add(productModel);

            ResponseModel response = new ResponseModel();
            response.setMsg("Product added successfully");
            response.setData(product);
            return ResponseEntity.ok(response);
        } catch(ClientException e) {
            ResponseModel response = new ResponseModel();
            response.setMsg(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch(Exception e) {
            ResponseModel response = new ResponseModel();
            response.setMsg(e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }   

    @GetMapping(value="/get")
    public ResponseEntity<ResponseModel> getAllProductController(){
        try {
            List<ProductEntity> products = productService.findAll();
            ResponseModel response = new ResponseModel();
            response.setMsg("Request successfully");
            response.setData(products);
            return ResponseEntity.ok(response);
        } catch(Exception e) {
            ResponseModel response = new ResponseModel();
            response.setMsg(e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping(value="/get/search")
    public ResponseEntity<ResponseModel> searchProductController(@RequestBody ProductModel productModel){
        try {
            List<ProductEntity> products = productService.findAllByCriteria(productModel);
            ResponseModel response = new ResponseModel();
            response.setMsg("Request successfully");
            response.setData(products);
            return ResponseEntity.ok(response);
        } catch(Exception e) {
            ResponseModel response = new ResponseModel();
            response.setMsg(e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping(value="/get/info")
    public ResponseEntity<ResponseModel> getAllByCategoryController(@RequestParam String categoryId) {
        try { 
            List<ProductInfoEntity> products = productService.findAllByCategory(categoryId);
            ResponseModel response = new ResponseModel();
            response.setMsg("Request successfully");
            response.setData(products);
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

    @GetMapping(value="/get/category")
    public ResponseEntity<ResponseModel> getProductByCategoryIdController(@RequestParam String categoryId) {
        try { 
            List<ProductEntity> products = productService.findProductByCategoryId(categoryId);
            ResponseModel response = new ResponseModel();
            response.setMsg("Request successfully");
            response.setData(products);
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

    @GetMapping(value="/get/{id}")
    public ResponseEntity<ResponseModel> getProductByIdController(@PathVariable Integer id){
        try {
            ProductEntity product = productService.findById(id);
            ResponseModel response = new ResponseModel();
            response.setMsg("Request successfully");
            response.setData(product);
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
    public ResponseEntity<ResponseModel> putProductController(@RequestBody ProductModel productModel) {
        try {
            ProductEntity product = productService.edit(productModel);

            ResponseModel response = new ResponseModel();
            response.setMsg("Product updated successfully");
            response.setData(product);
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


    @DeleteMapping("delete")
    public ResponseEntity<ResponseModel> deleteProductController(@RequestBody ProductModel productModel) {
        try {
            ProductEntity product = productService.delete(productModel);

            ResponseModel response = new ResponseModel();
            response.setMsg("Product deleted successfully");
            response.setData(product);
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
}
