package com.saituo.talk.modules.sys.service;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saituo.talk.common.persistence.BaseEntity;
import com.saituo.talk.common.persistence.Page;
import com.saituo.talk.common.utils.StringUtils;
import com.saituo.talk.modules.sys.dao.ProductDao;
import com.saituo.talk.modules.sys.entity.Product;

@Service
@Transactional(readOnly = true)
public class ProductService {

	@Autowired
	private ProductDao productDao;

	public Page<Product> findAllProduct(Page<Product> page, Product product) {

		DetachedCriteria dc = productDao.createDetachedCriteria();

		if (StringUtils.isNotEmpty(product.getProductName())) {
			dc.add(Restrictions.like("productName", "%" + product.getProductName() + "%"));
		}

		if (product != null && product.getBrand() != null && product.getBrand().getId() != null) {
			dc.add(Restrictions.eq("brand.id", product.getBrand().getId()));
		}

		if (StringUtils.isNotEmpty(product.getProductNum())) {
			dc.add(Restrictions.eq("productNum", product.getProductNum()));
		}

		dc.add(Restrictions.eq(BaseEntity.FIELD_DEL_FLAG, BaseEntity.DEL_FLAG_NORMAL));
		if (!StringUtils.isNotEmpty(page.getOrderBy())) {
			dc.addOrder(Order.asc("productName")).addOrder(Order.desc("productNum"));
		}
		return productDao.find(page, dc);
	}

	public long count(Product product) {

		DetachedCriteria dc = productDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(product.getProductName())) {
			dc.add(Restrictions.eq("productName", product.getProductName()));
		}
		if (product != null && product.getBrand() != null && product.getBrand().getId() != null) {
			dc.add(Restrictions.eq("brand.id", product.getBrand().getId()));
		}
		dc.add(Restrictions.eq(BaseEntity.FIELD_DEL_FLAG, BaseEntity.DEL_FLAG_NORMAL));
		return productDao.count(dc);

	}

	public List<Product> findAll() {
		DetachedCriteria detachedCriteria = productDao.createDetachedCriteria();
		detachedCriteria.add(Restrictions.eq(BaseEntity.FIELD_DEL_FLAG, BaseEntity.DEL_FLAG_NORMAL));
		return productDao.find(detachedCriteria);
	}

	public Product get(Integer id) {
		return productDao.get(id);
	}

	@Transactional(readOnly = false)
	public void save(Product product) {
		productDao.clear();
		productDao.save(product);
	}

	@Transactional(readOnly = false)
	public void delete(Integer id) {
		productDao.delete(id);
	}

}
