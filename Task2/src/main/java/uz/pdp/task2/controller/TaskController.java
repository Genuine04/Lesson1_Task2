package uz.pdp.task2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.task2.payload.ApiResponse;
import uz.pdp.task2.payload.TaskDto;
import uz.pdp.task2.service.TaskService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/task")
public class TaskController {


    @Autowired
    TaskService taskService;


    @GetMapping
    public HttpEntity<?> getAll(){
        List<TaskDto> allTaskDtos = taskService.getAllTaskDtos();
        return ResponseEntity.ok(allTaskDtos);
    }


    @GetMapping("/{id}")
    public HttpEntity<?> getById(@PathVariable Integer id){
        TaskDto taskDtoById = taskService.getTaskDtoById(id);
        return ResponseEntity.ok(taskDtoById);
    }


    @PostMapping
    public HttpEntity<?> post(@Valid @RequestBody TaskDto taskDto){
        ApiResponse apiResponse = taskService.postTask(taskDto);
        return ResponseEntity.status(201).body(apiResponse);
    }


    @PutMapping("/{id}")
    public HttpEntity<?> put(@Valid @RequestBody TaskDto taskDto, @PathVariable Integer id){
        ApiResponse apiResponse = taskService.putTask(taskDto, id);
        return ResponseEntity.status(apiResponse.isSuccess()? 202:404).body(apiResponse);
    }


    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id){
        ApiResponse apiResponse = taskService.deleteTask(id);
        return ResponseEntity.status(apiResponse.isSuccess()? 204:404).body(apiResponse);
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> xatolikniUshlabOlibOzimizniMessageniQaytarish(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}
