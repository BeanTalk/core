package com.saituo.talk.modules.sys.service;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.saituo.talk.common.persistence.BaseEntity;
import com.saituo.talk.common.persistence.Page;
import com.saituo.talk.common.utils.StringUtils;
import com.saituo.talk.modules.sys.dao.ProductBrandDao;
import com.saituo.talk.modules.sys.entity.ProductBrand;

@Service
@Transactional(readOnly = true)
public class ProductBrandService {

	@Autowired
	private ProductBrandDao productBrandDao;

	public Page<ProductBrand> findAllProductBrand(Page<ProductBrand> page, ProductBrand productBrand) {

		DetachedCriteria dc = productBrandDao.createDetachedCriteria();

		if (StringUtils.isNotEmpty(productBrand.getBrandName())) {
			dc.add(Restrictions.like("productBrand.brandName", "%" + productBrand.getBrandName() + "%"));
		}

		dc.add(Restrictions.eq(BaseEntity.FIELD_DEL_FLAG, BaseEntity.DEL_FLAG_NORMAL));
		if (!StringUtils.isNotEmpty(page.getOrderBy())) {
			dc.addOrder(Order.asc("productBrand.brandName"));
		}
		return productBrandDao.find(page, dc);
	}

	public Map<Integer, String> getProductBrandMap() {
		Map<Integer, String> mapData = Maps.newHashMap();
		List<ProductBrand> productBrandList = productBrandDao.findAll();
		for (ProductBrand productBrand : productBrandList) {
			mapData.put(productBrand.getId(), productBrand.getBrandName());
		}
		return mapData;
	}

	public List<ProductBrand> findAll() {
		return productBrandDao.findAll();
	}

	public List<String> getBrandNameList() {
		return productBrandDao.findBrandNameList();
	}

	public ProductBrand get(Integer id) {
		return productBrandDao.get(id);
	}

	public ProductBrand findProductBrand(ProductBrand productBrand) {

		DetachedCriteria dc = productBrandDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(productBrand.getBrandName())) {
			dc.add(Restrictions.eq("productBrand.brandName", productBrand.getBrandName()));
		}
		return productBrandDao.find(dc).get(0);
	}

	@Transactional(readOnly = false)
	public void save(ProductBrand productBrand) {
		productBrandDao.save(productBrand);
	}

	@Transactional(readOnly = false)
	public void delete(Integer id) {
		productBrandDao.deleteById(id);
	}
}
