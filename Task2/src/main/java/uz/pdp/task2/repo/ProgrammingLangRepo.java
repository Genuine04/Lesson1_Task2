package uz.pdp.task2.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.task2.entity.ProgrammingLang;

public interface ProgrammingLangRepo extends JpaRepository<ProgrammingLang, Integer> {
}
