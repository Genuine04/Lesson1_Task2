package uz.pdp.task2.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.task2.entity.User;

public interface UserRepo extends JpaRepository<User, Integer> {

    boolean existsByPhoneNumber(String phoneNumber);

}
