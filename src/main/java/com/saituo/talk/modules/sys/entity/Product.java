package com.saituo.talk.modules.sys.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.NotEmpty;

import com.saituo.talk.common.persistence.IdEntity;
import com.saituo.talk.common.utils.excel.annotation.ExcelField;

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
public class Product extends IdEntity<Product> {

	private static final long serialVersionUID = 1L;

	private String productName;// 产品名称
	private String productNum;// 货号
	private ProductBrand brand;// 产品品牌编码
	private String specValue;// 规格
	private String unitValue;// 单位
	private Double catalogFee;// 目录价
	private Double deliveryFee;// 备货价

	@NotEmpty
	@Column(name = "product_name")
	@ExcelField(title = "产品名称", align = 2, sort = 10)
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	@NotEmpty
	@Column(name = "product_num")
	@ExcelField(title = "货号", align = 2, sort = 15)
	public String getProductNum() {
		return productNum;
	}

	public void setProductNum(String productNum) {
		this.productNum = productNum;
	}

	@ManyToOne
	// @JoinColumn(name = "brand_id", insertable = false, updatable = false,
	// nullable = false)
	@JoinColumn(name = "brand_id")
	@ExcelField(title = "品牌", align = 2, sort = 20)
	public ProductBrand getBrand() {
		return brand;
	}

	public void setBrand(ProductBrand brand) {
		this.brand = brand;
	}

	@Column(name = "spec_value")
	@ExcelField(title = "规格", align = 2, sort = 25)
	public String getSpecValue() {
		return specValue;
	}

	public void setSpecValue(String specValue) {
		this.specValue = specValue;
	}

	@Column(name = "unit_value")
	@ExcelField(title = "单位", align = 2, sort = 30)
	public String getUnitValue() {
		return unitValue;
	}
	public void setUnitValue(String unitValue) {
		this.unitValue = unitValue;
	}

	@Column(name = "catalog_fee")
	@ExcelField(title = "目录价", align = 2, sort = 30)
	public Double getCatalogFee() {
		return catalogFee;
	}

	public void setCatalogFee(Double catalogFee) {
		this.catalogFee = catalogFee;
	}

	@Column(name = "delivery_fee")
	@ExcelField(title = "备货价", align = 2, sort = 30)
	public Double getDeliveryFee() {
		return deliveryFee;
	}
	public void setDeliveryFee(Double deliveryFee) {
		this.deliveryFee = deliveryFee;
	}
}
