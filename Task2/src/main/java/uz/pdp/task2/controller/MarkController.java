package uz.pdp.task2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.task2.payload.ApiResponse;
import uz.pdp.task2.payload.MarkDto;
import uz.pdp.task2.service.MarkService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/mark")
public class MarkController {


    @Autowired
    MarkService markService;


    @GetMapping
    public HttpEntity<?> getAll(){
        List<MarkDto> allMarkDtos = markService.getAllMarkDtos();
        return ResponseEntity.ok(allMarkDtos);
    }


    @GetMapping("/{id}")
    public HttpEntity<?> getById(@PathVariable Integer id){
        MarkDto markDtoById = markService.getMarkDtoById(id);
        return ResponseEntity.ok(markDtoById);
    }


    @PostMapping
    public HttpEntity<?> post(@Valid @RequestBody MarkDto markDto){
        ApiResponse apiResponse = markService.postMark(markDto);
        return ResponseEntity.status(201).body(apiResponse);
    }


    @PutMapping("/{id}")
    public HttpEntity<?> put(@Valid @RequestBody MarkDto markDto, @PathVariable Integer id){
        ApiResponse apiResponse = markService.putMark(markDto, id);
        return ResponseEntity.status(apiResponse.isSuccess()? 202:404).body(apiResponse);
    }


    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id){
        ApiResponse apiResponse = markService.deleteMark(id);
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
