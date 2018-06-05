package com.rasika.tmapp.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name="task")
@EntityListeners(AuditingEntityListener.class)
public class Task implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="TASK_ID")
	private String taskId;
	
	@Column(name="SKILL")
	private String skill;

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getSkill() {
		return skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}
	
	
	
}
