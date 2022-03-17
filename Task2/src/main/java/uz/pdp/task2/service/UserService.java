package uz.pdp.task2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.task2.entity.User;
import uz.pdp.task2.payload.ApiResponse;
import uz.pdp.task2.payload.UserDto;
import uz.pdp.task2.repo.UserRepo;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class UserService {


    @Autowired
    UserRepo userRepo;


    //GET ALL
    public List<UserDto> getAllUserDtos(){
        return userRepo.findAll().stream().map(this::userDto).collect(Collectors.toList());
    }


    //GET BY ID
    public UserDto getUserDtoById(Integer id){
        Optional<User> optionalUser = userRepo.findById(id);
        return optionalUser.map(this::userDto).orElse(null);
    }


    //POST
    public ApiResponse postUser(UserDto userDto){
        if (userRepo.existsByPhoneNumber(userDto.getPhoneNumber()))
            return new ApiResponse("This phone number already existed", false);
        User save = userRepo.save(user(userDto));
        return new ApiResponse("Saved successfully", true);
    }


    //PUT
    public ApiResponse putUser(UserDto userDto, Integer id){
        Optional<User> optionalUser = userRepo.findById(id);
        if (!optionalUser.isPresent())
            return new ApiResponse("This user is not found", false);
        User user = optionalUser.get();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setPhoneNumber(userDto.getPhoneNumber());
        User save = userRepo.save(user);
        return new ApiResponse("Updated successfully", true);
    }


    //DELETE
    public ApiResponse deleteUser(Integer id){
        try {
            userRepo.deleteById(id);
            return new ApiResponse("Deleted successfully", true);
        }catch (Exception e){
            return new ApiResponse("This user is not found", false);
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

}
