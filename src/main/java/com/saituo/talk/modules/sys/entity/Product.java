package com.saituo.talk.modules.sys.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.NotEmpty;

import com.saituo.talk.common.persistence.BaseEntity;

/**
 * 产品Entity
 * 
 * @author Lr
 * @version 2014-12-07
 */
@Entity
@Table(name = "st_product")
@DynamicInsert
@DynamicUpdate
public class Product extends BaseEntity<Product> {

	private static final long serialVersionUID = 1L;

	private Integer id;// 品编码产
	private String productName;// 产品名称
	private String productNum;// 货号
	private ProductBrand brand;// 产品品牌编码
	private String specValue;// 规格
	private String unitValue;// 单位
	private Double catalogFee;// 目录价
	private Double deliveryFee;// 备货价
	private String acceptPerson;// 创建者
	private Date acceptDate; // 创建时间
	private String delFlag = DEL_FLAG_NORMAL; // 删除标记

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "product_id")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	@NotEmpty
	@Column(name = "product_name")
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	@NotEmpty
	@Column(name = "product_num")
	public String getProductNum() {
		return productNum;
	}

	public void setProductNum(String productNum) {
		this.productNum = productNum;
	}

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "brand_id")
	public ProductBrand getBrand() {
		return brand;
	}

	public void setBrand(ProductBrand brand) {
		this.brand = brand;
	}

	@Column(name = "spec_value")
	public String getSpecValue() {
		return specValue;
	}

	public void setSpecValue(String specValue) {
		this.specValue = specValue;
	}

	@Column(name = "unit_value")
	public String getUnitValue() {
		return unitValue;
	}
	public void setUnitValue(String unitValue) {
		this.unitValue = unitValue;
	}

	@Column(name = "catalog_fee")
	public Double getCatalogFee() {
		return catalogFee;
	}

	public void setCatalogFee(Double catalogFee) {
		this.catalogFee = catalogFee;
	}

	@Column(name = "delivery_fee")
	public Double getDeliveryFee() {
		return deliveryFee;
	}
	public void setDeliveryFee(Double deliveryFee) {
		this.deliveryFee = deliveryFee;
	}

	@Column(name = "accept_person")
	public String getAcceptPerson() {
		return acceptPerson;
	}
	public void setAcceptPerson(String acceptPerson) {
		this.acceptPerson = acceptPerson;
	}

	@Column(name = "accept_date")
	public Date getAcceptDate() {
		return acceptDate;
	}

	public void setAcceptDate(Date acceptDate) {
		this.acceptDate = acceptDate;
	}

	@Column(name = "del_flag")
	public String getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
}
