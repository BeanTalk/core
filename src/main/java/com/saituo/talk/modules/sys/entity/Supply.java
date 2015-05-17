package com.saituo.talk.modules.sys.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.saituo.talk.common.persistence.IdEntity;

@Entity
@Table(name = "st_supplier")
@DynamicInsert
@DynamicUpdate
public class Supply extends IdEntity<Supply> {

	private static final long serialVersionUID = -5556270627672809931L;

	private String supplyName;

	public Supply() {
	}

	public Supply(Integer id) {
		this.id = id;
	}

	@Column(name = "supplier_name")
	public String getSupplyName() {
		return supplyName;
	}

	public void setSupplyName(String supplyName) {
		this.supplyName = supplyName;
	}
}
