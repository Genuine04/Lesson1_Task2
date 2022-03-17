package uz.pdp.task2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.task2.payload.ApiResponse;
import uz.pdp.task2.payload.UserDto;
import uz.pdp.task2.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {


    @Autowired
    UserService userService;


    @GetMapping
    public HttpEntity<?> getAll(){
        List<UserDto> allUserDtos = userService.getAllUserDtos();
        return ResponseEntity.ok(allUserDtos);
    }


    @GetMapping("/{id}")
    public HttpEntity<?> getById(@PathVariable Integer id){
        UserDto userDtoById = userService.getUserDtoById(id);
        return ResponseEntity.ok(userDtoById);
    }


    @PostMapping
    public HttpEntity<?> post(@Valid @RequestBody UserDto userDto){
        ApiResponse apiResponse = userService.postUser(userDto);
        return ResponseEntity.status(apiResponse.isSuccess()? 201:409).body(apiResponse);
    }


    @PutMapping("/{id}")
    public HttpEntity<?> put(@Valid @RequestBody UserDto userDto, @PathVariable Integer id){
        ApiResponse apiResponse = userService.putUser(userDto, id);
        return ResponseEntity.status(apiResponse.isSuccess()? 202:404).body(apiResponse);
    }


    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id) {
        ApiResponse apiResponse = userService.deleteUser(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 202 : 404).body(apiResponse);
    }
}
