package com.rasika.tmapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rasika.tmapp.model.TeamSkill;
import com.rasika.tmapp.model.TeamSkillPK;

@Repository
public interface TeamSkillRepo extends JpaRepository<TeamSkill,TeamSkillPK>{

}
