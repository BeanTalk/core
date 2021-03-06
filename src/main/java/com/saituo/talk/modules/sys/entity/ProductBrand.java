package com.saituo.talk.modules.sys.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;

import com.saituo.talk.common.persistence.IdEntity;
import com.saituo.talk.common.utils.excel.annotation.ExcelField;

@Entity
@Table(name = "st_product_brand")
@DynamicInsert
@DynamicUpdate
public class ProductBrand extends IdEntity<ProductBrand> {

	private static final long serialVersionUID = -4521867732802841977L;

	private String brandName;

	private String uniqueBrandName;

	private Double buyDiscount;

	private Double weightDiscount;

	private Double limitDiscount;

	private List<Product> products;

	public ProductBrand() {
	}

	public ProductBrand(Integer id) {
		this.id = id;
	}

	@Column(name = "brand_name")
	@ExcelField(title = "品牌名称", align = 2, sort = 10)
	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	@Column(name = "buy_discount")
	@ExcelField(title = "采购折扣", align = 2, sort = 15)
	public Double getBuyDiscount() {
		return buyDiscount;
	}

	public void setBuyDiscount(Double buyDiscount) {
		this.buyDiscount = buyDiscount;
	}

	@Column(name = "weight_discount")
	@ExcelField(title = "加权折扣", align = 2, sort = 20)
	public Double getWeightDiscount() {
		return weightDiscount;
	}

	public void setWeightDiscount(Double weightDiscount) {
		this.weightDiscount = weightDiscount;
	}

	@Column(name = "limit_discount")
	@ExcelField(title = "最高限价折扣", align = 2, sort = 20)
	public Double getLimitDiscount() {
		return limitDiscount;
	}

	public void setLimitDiscount(Double limitDiscount) {
		this.limitDiscount = limitDiscount;
	}

	@Column(name = "brand_name_unique")
	@ExcelField(title = "品牌唯一标识", align = 2, sort = 25)
	public String getUniqueBrandName() {
		return uniqueBrandName;
	}

	public void setUniqueBrandName(String uniqueBrandName) {
		this.uniqueBrandName = uniqueBrandName;
	}

	@OneToMany(mappedBy = "brand", fetch = FetchType.LAZY)
	@Where(clause = "del_flag='" + DEL_FLAG_NORMAL + "'")
	@NotFound(action = NotFoundAction.IGNORE)
	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

}
