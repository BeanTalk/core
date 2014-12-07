package com.saituo.talk.modules.sys.dao;

import org.springframework.stereotype.Repository;

import com.saituo.talk.common.persistence.BaseDao;
import com.saituo.talk.modules.sys.entity.Product;

@Repository
public class ProductDao extends BaseDao<Product> {

	public Product get(String parentIds) {
		return get(1);
	}
}
