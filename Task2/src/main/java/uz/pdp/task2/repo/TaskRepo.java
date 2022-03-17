package uz.pdp.task2.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.task2.entity.Task;

public interface TaskRepo extends JpaRepository<Task, Integer> {
}
