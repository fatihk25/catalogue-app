package id.co.nds.catalogue.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import id.co.nds.catalogue.entities.UserEntity;
import id.co.nds.catalogue.exceptions.ClientException;
import id.co.nds.catalogue.exceptions.NotFoundException;
import id.co.nds.catalogue.models.ResponseModel;
import id.co.nds.catalogue.models.UserModel;
import id.co.nds.catalogue.services.UserService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping(value="/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping(value="/add")
    public ResponseEntity<ResponseModel> postUserController(@RequestBody UserModel userModel){
        try {
            UserEntity user = userService.add(userModel);

            ResponseModel response = new ResponseModel();
            response.setMsg("User added successfully");
            response.setData(user);
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
    public ResponseEntity<ResponseModel> getAllUserController() {
        try{
            List<UserEntity> users = userService.findAll();
            ResponseModel response = new ResponseModel();
            response.setMsg("Request successfully");
            response.setData(users);
            return ResponseEntity.ok(response);
        }catch(Exception e) {
            ResponseModel response = new ResponseModel();
            response.setMsg(e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping(value="/get/search")
    public ResponseEntity<ResponseModel> searchUserController(@RequestBody UserModel userModel) {
        try{
            List<UserEntity> users = userService.findAllByCriteria(userModel);
            ResponseModel response = new ResponseModel();
            response.setMsg("Request successfully");
            response.setData(users);
            return ResponseEntity.ok(response);
        }catch(Exception e) {
            ResponseModel response = new ResponseModel();
            response.setMsg(e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping(value="/get/role")
    public ResponseEntity<ResponseModel> getUserByRoleNameController(@RequestParam String roleName) {
        try{
            List<UserEntity> users = userService.findAllbyRoleName(roleName);
            ResponseModel response = new ResponseModel();
            response.setMsg("Request successfully");
            response.setData(users);
            return ResponseEntity.ok(response);
        }catch(Exception e) {
            ResponseModel response = new ResponseModel();
            response.setMsg(e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
    

    @GetMapping(value="/get/{id}")
    public ResponseEntity<ResponseModel> getUserByIdController(@PathVariable Integer id) {
        try{
            UserEntity user = userService.findById(id);
            ResponseModel response = new ResponseModel();
            response.setMsg("Request successfully");
            response.setData(user);
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
    public ResponseEntity<ResponseModel> putUserController(@RequestBody UserModel userModel) {
        try{
            UserEntity userEntity = userService.edit(userModel);
            ResponseModel response = new ResponseModel();
            response.setMsg("User updated successfully");
            response.setData(userEntity);
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

    @DeleteMapping(value="/delete")
    public ResponseEntity<ResponseModel> deleteUserController(@RequestBody UserModel userModel) {
        try{
            UserEntity userEntity = userService.delete(userModel);
            ResponseModel response = new ResponseModel();
            response.setMsg("User deleted successfully");
            response.setData(userEntity);
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
