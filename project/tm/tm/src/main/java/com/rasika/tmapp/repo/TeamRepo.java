package com.rasika.tmapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rasika.tmapp.model.Team;
@Repository
public interface TeamRepo extends JpaRepository<Team,String>{

}
