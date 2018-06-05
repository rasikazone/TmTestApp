package com.rasika.tmapp.model;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name="team_skill")
@EntityListeners(AuditingEntityListener.class)
public class TeamSkill implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	TeamSkillPK teamSkillPK;

	public TeamSkillPK getTeamSkillPK() {
		return teamSkillPK;
	}

	public void setTeamSkillPK(TeamSkillPK teamSkillPK) {
		this.teamSkillPK = teamSkillPK;
	}
}
