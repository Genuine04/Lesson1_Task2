package uz.pdp.task2.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.task2.entity.ProgrammingLang;
import uz.pdp.task2.entity.User;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {

    private Integer id;

    @NotNull(message = "Name cannot be empty")
    private String name;

    @NotNull(message = "User's list cannot be empty")
    private Set<UserDto> userDtos;

    @NotNull(message = "Programming language cannot be empty")
    private ProgrammingLangDto langDto;
}
