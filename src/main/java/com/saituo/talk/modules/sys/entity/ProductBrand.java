package com.saituo.talk.modules.sys.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;

import com.saituo.talk.common.persistence.BaseEntity;

@Entity
@Table(name = "st_product_brand")
@DynamicInsert
@DynamicUpdate
public class ProductBrand extends BaseEntity<ProductBrand> {

	private static final long serialVersionUID = -2781004730198300131L;

	private Integer id;

	private String brandName;

	private String acceptPerson;

	private Date acceptDate;

	private Double buyDiscount;

	private Double weightDiscount;

	private Double limitDiscount;

	private List<Product> products;

	public ProductBrand(Integer id) {
		super();
		this.id = id;
	}

	public ProductBrand() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "brand_id")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "brand_name")
	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
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

	@Column(name = "buy_discount")
	public Double getBuyDiscount() {
		return buyDiscount;
	}

	public void setBuyDiscount(Double buyDiscount) {
		this.buyDiscount = buyDiscount;
	}

	@Column(name = "weight_discount")
	public Double getWeightDiscount() {
		return weightDiscount;
	}

	public void setWeightDiscount(Double weightDiscount) {
		this.weightDiscount = weightDiscount;
	}

	@Column(name = "limit_discount")
	public Double getLimitDiscount() {
		return limitDiscount;
	}

	public void setLimitDiscount(Double limitDiscount) {
		this.limitDiscount = limitDiscount;
	}

	@OneToMany(mappedBy = "brand", fetch = FetchType.LAZY)
	@Where(clause = "del_flag='" + DEL_FLAG_NORMAL + "'")
	@OrderBy(value = "id")
	@NotFound(action = NotFoundAction.IGNORE)
	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

}
