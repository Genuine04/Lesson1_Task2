package uz.pdp.task2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.task2.entity.ProgrammingLang;
import uz.pdp.task2.payload.ApiResponse;
import uz.pdp.task2.payload.ProgrammingLangDto;
import uz.pdp.task2.repo.ProgrammingLangRepo;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProgrammingLangService {

    @Autowired
    ProgrammingLangRepo programmingLangRepo;


    //GET ALL
    public List<ProgrammingLangDto> getAllProgrammingLanguage(){
        return programmingLangRepo.findAll().stream().map(this::programmingLangDto).collect(Collectors.toList());
    }


    //GET BY ID
    public ProgrammingLangDto getProgrammingLanguageById(Integer id){
        Optional<ProgrammingLang> optionalProgrammingLang = programmingLangRepo.findById(id);
        return optionalProgrammingLang.map(this::programmingLangDto).orElse(null);
    }


    //POST
    public ApiResponse postProgrammingLanguage(ProgrammingLangDto programmingLangDto){
        ProgrammingLang save = programmingLangRepo.save(programmingLang(programmingLangDto));
        return new ApiResponse("Saved successfully", true);
    }


    //PUT
    public ApiResponse putProgrammingLanguage(ProgrammingLangDto programmingLangDto, Integer id){
        Optional<ProgrammingLang> optionalProgrammingLang = programmingLangRepo.findById(id);
        if (!optionalProgrammingLang.isPresent())
            return new ApiResponse("This programming language is not found", false);
        ProgrammingLang programmingLang = optionalProgrammingLang.get();
        programmingLang.setId(programmingLangDto.getId());
        programmingLang.setName(programmingLangDto.getName());
        ProgrammingLang save = programmingLangRepo.save(programmingLang);
        return new ApiResponse("Updated successfully", true);
    }


    //DELETE
    public ApiResponse deleteProgrammingLanguage(Integer id){
      try {
          programmingLangRepo.deleteById(id);
          return new ApiResponse("Deleted successfully", true);
      }catch (Exception e){
          return new ApiResponse("This programming language is not found", false);
      }
    }




    //PROGRAMMING LANGUAGE DTO
    public ProgrammingLangDto programmingLangDto(ProgrammingLang programmingLang){
        return new ProgrammingLangDto(programmingLang.getId(), programmingLang.getName());
    }


    //PROGRAMMING LANGUAGE
    public ProgrammingLang programmingLang(ProgrammingLangDto programmingLangDto){
        return new ProgrammingLang(programmingLangDto.getId(), programmingLangDto.getName());
    }
}
