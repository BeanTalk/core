package com.saituo.talk.modules.sys.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.saituo.talk.common.persistence.IdEntity;

@Entity
@Table(name = "st_agency")
@DynamicInsert
@DynamicUpdate
public class Agency extends IdEntity<Agency> {

	private static final long serialVersionUID = -5556270627672809931L;

	private String agentName;

	@Column(name = "agent_name")
	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public Agency() {
	}

	public Agency(Integer id) {
		this.id = id;
	}

}
