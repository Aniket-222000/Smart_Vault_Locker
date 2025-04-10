package com.ust.smartlocker.repository;


import com.ust.smartlocker.model.Locker;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface LockerRepository extends JpaRepository<Locker, Long> {
    Optional<Locker> findByLockerNumber(String lockerNumber);
}
