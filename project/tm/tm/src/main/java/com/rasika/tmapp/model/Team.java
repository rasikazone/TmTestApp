package com.rasika.tmapp.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name="team")
@EntityListeners(AuditingEntityListener.class)
public class Team implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="TEAM_ID")
	private String teamId;

	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}
	
	

}
