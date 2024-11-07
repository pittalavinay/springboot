package com.example.rest.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.rest.entity.Student;

public interface StudentRepo extends JpaRepository<Student,Long> {
	 boolean existsByValue(String value);
	    void deleteByValue(String value);

}
