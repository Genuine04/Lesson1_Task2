package uz.pdp.task2.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.task2.entity.Mark;

public interface MarkRepo extends JpaRepository<Mark, Integer> {
}
