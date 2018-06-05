package com.rasika.tmapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rasika.tmapp.model.Task;
@Repository
public interface TaskRepo extends JpaRepository<Task,String> {

}
