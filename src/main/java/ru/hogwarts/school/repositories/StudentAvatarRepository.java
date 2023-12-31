package ru.hogwarts.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.model.Avatar;

import java.util.Optional;

public interface StudentAvatarRepository extends JpaRepository<Avatar, Long> {

    Optional<Avatar> findAvatarByStudentId(Long studentId);
}
