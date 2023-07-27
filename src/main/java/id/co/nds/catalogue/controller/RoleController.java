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

import id.co.nds.catalogue.entities.RoleEntity;
import id.co.nds.catalogue.exceptions.ClientException;
import id.co.nds.catalogue.exceptions.NotFoundException;
import id.co.nds.catalogue.models.RoleModel;
import id.co.nds.catalogue.models.ResponseModel;
import id.co.nds.catalogue.services.RoleService;

@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @PostMapping(value="/add")
    public ResponseEntity<ResponseModel> postRoleController(@RequestBody RoleModel roleModel) {
        try {
            RoleEntity role = roleService.add(roleModel);
            ResponseModel response = new ResponseModel();
            response.setMsg("Category added successfully");
            response.setData(role);
            return ResponseEntity.ok(response);
        } catch(Exception e) {
            ResponseModel response = new ResponseModel();
            response.setMsg(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping(value="/get")
    public ResponseEntity<ResponseModel> getAllRolesController() {
        try {
            List<RoleEntity> role = roleService.findAll();
            ResponseModel response = new ResponseModel();
            response.setMsg("Request successfully");
            response.setData(role);
            return ResponseEntity.ok(response);
        } catch(Exception e) {
            ResponseModel response = new ResponseModel();
            response.setMsg(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping(value="/get/{id}")
    public ResponseEntity<ResponseModel> getRoleByIdController(@PathVariable String id) {
        try {
            RoleEntity role = roleService.findById(id);
            ResponseModel response = new ResponseModel();
            response.setMsg("Request successfully");
            response.setData(role);
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
    public ResponseEntity<ResponseModel> putRoleController(@RequestBody RoleModel roleModel) {
        try {
            RoleEntity role = roleService.edit(roleModel);
            ResponseModel response = new ResponseModel();
            response.setMsg("Category updated successfully");
            response.setData(role);
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
    public ResponseEntity<ResponseModel> deleteRoleController(@RequestBody RoleModel roleModel) {
        try {
            RoleEntity role = roleService.delete(roleModel);
            ResponseModel response = new ResponseModel();
            response.setMsg("Category deleted successfully");
            response.setData(role);
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
