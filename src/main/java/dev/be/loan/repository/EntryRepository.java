package dev.be.loan.repository;

import dev.be.loan.domain.Entry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EntryRepository extends JpaRepository<Entry, Long> {
    Optional<Entry> findByApplicationId(Long applicationId);
}
