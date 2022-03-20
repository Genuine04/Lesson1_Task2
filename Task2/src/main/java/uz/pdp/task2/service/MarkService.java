package uz.pdp.task2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.task2.entity.Mark;
import uz.pdp.task2.entity.ProgrammingLang;
import uz.pdp.task2.entity.Task;
import uz.pdp.task2.entity.User;
import uz.pdp.task2.payload.*;
import uz.pdp.task2.repo.MarkRepo;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MarkService {


    @Autowired
    MarkRepo markRepo;


    //GET ALL
    public List<MarkDto> getAllMarkDtos(){
        return markRepo.findAll().stream().map(this::markDto).collect(Collectors.toList());
    }


    //GET BY ID
    public MarkDto getMarkDtoById(Integer id){
        Optional<Mark> optionalMark = markRepo.findById(id);
        return optionalMark.map(this::markDto).orElse(null);
    }


    //POST
    public ApiResponse postMark(MarkDto markDto){
        Mark save = markRepo.save(mark(markDto));
        return new ApiResponse("Saved successfully", true);
    }


    //PUT
    public ApiResponse putMark(MarkDto markDto, Integer id){
        Optional<Mark> optionalMark = markRepo.findById(id);
        if (!optionalMark.isPresent())
            return new ApiResponse("Mark is not found", false);
        Mark mark = optionalMark.get();
        mark.setId(markDto.getId());
        mark.setMarksRate(markDto.getMarksRate());
        mark.setTasks(markDto.getTaskDtos().stream().map(this::task).collect(Collectors.toSet()));
        Mark save = markRepo.save(mark);
        return new ApiResponse("updated successfully", true);
    }


    //DELETE
    public ApiResponse deleteMark(Integer id){
        try {
            markRepo.deleteById(id);
            return new ApiResponse("Deleted successfully", true);
        }catch (Exception e){
            return new ApiResponse("Mark is not found", false);
        }
    }



    //USER DTO
    public UserDto userDto(User user){
        return new UserDto(user.getId(), user.getName(), user.getPhoneNumber());
    }


    //USER
    public User user(UserDto userDto){
        return new User(userDto.getId(), userDto.getName(), userDto.getPhoneNumber());
    }


    //PROGRAMMING LANGUAGE DTO
    public ProgrammingLangDto programmingLangDto(ProgrammingLang programmingLang){
        return new ProgrammingLangDto(programmingLang.getId(), programmingLang.getName());
    }


    //PROGRAMMING LANGUAGE
    public ProgrammingLang programmingLang(ProgrammingLangDto programmingLangDto){
        return new ProgrammingLang(programmingLangDto.getId(), programmingLangDto.getName());
    }


    //TASK DTO
    public TaskDto taskDto(Task task){
        return new TaskDto(task.getId(), task.getName(),
                task.getUser().stream().map(this::userDto).collect(Collectors.toSet()),
                programmingLangDto(task.getLang()));
    }


    //TASK
    public Task task(TaskDto taskDto){
        return new Task(taskDto.getId(), taskDto.getName(),
                taskDto.getUserDtos().stream().map(this::user).collect(Collectors.toSet()),
                programmingLang(taskDto.getLangDto()));
    }


    //MARK DTO
    public MarkDto markDto(Mark mark){
        return new MarkDto(mark.getId(), mark.getMarksRate(),
                mark.getTasks().stream().map(this::taskDto).collect(Collectors.toSet()));
    }


    //MARK
    public Mark mark(MarkDto markDto){
        return new Mark(markDto.getId(), markDto.getMarksRate(),
                markDto.getTaskDtos().stream().map(this::task).collect(Collectors.toSet()));
    }

}
