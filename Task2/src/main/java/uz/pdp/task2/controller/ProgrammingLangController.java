package uz.pdp.task2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.task2.payload.ApiResponse;
import uz.pdp.task2.payload.ProgrammingLangDto;
import uz.pdp.task2.service.ProgrammingLangService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/programmingLang")
public class ProgrammingLangController {


    @Autowired
    ProgrammingLangService programmingLangService;


    @GetMapping
    public HttpEntity<?> getAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        List<ProgrammingLangDto> allProgrammingLanguage = programmingLangService
                .getAllProgrammingLanguage(page, size);
        return ResponseEntity.ok(allProgrammingLanguage);
    }


    @GetMapping("/{id}")
    public HttpEntity<?> getById(@PathVariable Integer id){
        ProgrammingLangDto programmingLanguageById = programmingLangService.getProgrammingLanguageById(id);
        return ResponseEntity.ok(programmingLanguageById);
    }


    @PostMapping
    public HttpEntity<?> post(@Valid @RequestBody ProgrammingLangDto programmingLangDto){
        ApiResponse apiResponse = programmingLangService.postProgrammingLanguage(programmingLangDto);
        return ResponseEntity.status(201).body(apiResponse);
    }


    @PutMapping("/{id}")
    public HttpEntity<?> put(@Valid @RequestBody ProgrammingLangDto programmingLangDto, @PathVariable Integer id){
        ApiResponse apiResponse = programmingLangService.putProgrammingLanguage(programmingLangDto, id);
        return ResponseEntity.status(apiResponse.isSuccess()? 202:404).body(apiResponse);
    }


    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id){
        ApiResponse apiResponse = programmingLangService.deleteProgrammingLanguage(id);
        return ResponseEntity.status(apiResponse.isSuccess()?204:404).body(apiResponse);
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
