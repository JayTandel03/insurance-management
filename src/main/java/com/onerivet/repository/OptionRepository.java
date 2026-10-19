package com.onerivet.repository;

import com.onerivet.model.entity.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OptionRepository extends JpaRepository<Option, Integer> {
	Optional<Option> findByOptionLabelIgnoreCase(String optionLabel);

}