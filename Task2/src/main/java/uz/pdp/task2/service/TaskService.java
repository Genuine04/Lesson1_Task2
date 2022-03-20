package uz.pdp.task2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.task2.entity.ProgrammingLang;
import uz.pdp.task2.entity.Task;
import uz.pdp.task2.entity.User;
import uz.pdp.task2.payload.ApiResponse;
import uz.pdp.task2.payload.ProgrammingLangDto;
import uz.pdp.task2.payload.TaskDto;
import uz.pdp.task2.payload.UserDto;
import uz.pdp.task2.repo.TaskRepo;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService {

    @Autowired
    TaskRepo taskRepo;


    //GET ALL
    public List<TaskDto> getAllTaskDtos(){
        return taskRepo.findAll().stream().map(this::taskDto).collect(Collectors.toList());
    }


    //GET BY ID
    public TaskDto getTaskDtoById(Integer id){
        Optional<Task> optionalTask = taskRepo.findById(id);
        return optionalTask.map(this::taskDto).orElse(null);
    }


    //POST
    public ApiResponse postTask(TaskDto taskDto){
        Task save = taskRepo.save(task(taskDto));
        return new ApiResponse("Saved successfully", true);
    }


    //PUT
    public ApiResponse putTask(TaskDto taskDto, Integer id){
        Optional<Task> optionalTask = taskRepo.findById(id);
        if (!optionalTask.isPresent())
            return new ApiResponse("This task is not found", false);
        Task task = optionalTask.get();
        task.setId(taskDto.getId());
        task.setName(taskDto.getName());
        task.setUser(taskDto.getUserDtos().stream().map(this::user).collect(Collectors.toSet()));
        task.setLang(programmingLang(taskDto.getLangDto()));
        Task save = taskRepo.save(task);
        return new ApiResponse("Updated successfully", true);
    }


    //DELETE
    public ApiResponse deleteTask(Integer id){
        try {
            taskRepo.deleteById(id);
            return new ApiResponse("Deleted successfully", true);
        }catch (Exception e){
            return new ApiResponse("This task is not found", false);
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

}
