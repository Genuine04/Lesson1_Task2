package uz.pdp.task2.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.task2.entity.Task;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MarkDto {

    private Integer id;

    @NotNull(message = "Marks rate cannot be empty")
    private Integer marksRate;

    @NotNull(message = "Task's list cannot be empty")
    private Set<TaskDto> taskDtos;
}
