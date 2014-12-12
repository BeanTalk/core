package com.saituo.talk.modules.sys.service;

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

		if (product != null && product.getBrand() != null) {
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

	public Product get(Integer id) {
		return productDao.get(id);
	}

	@Transactional(readOnly = false)
	public void save(Product product) {
		if (product.getId() != null) {
			productDao.update(product);
		} else {
			productDao.save(product);
		}
	}

	@Transactional(readOnly = false)
	public void delete(Integer id) {
		productDao.delete(id);
	}

}
