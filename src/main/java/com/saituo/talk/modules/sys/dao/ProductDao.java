package com.saituo.talk.modules.sys.dao;

import org.springframework.stereotype.Repository;

import com.saituo.talk.common.persistence.BaseDao;
import com.saituo.talk.common.persistence.Parameter;
import com.saituo.talk.modules.sys.entity.Product;

@Repository
public class ProductDao extends BaseDao<Product> {

	public void delete(Integer productId) {
		update("update product set delFlag = :p2 where id = :p1", new Parameter(productId, 0));
	}

}
