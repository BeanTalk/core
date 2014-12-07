package com.saituo.talk.common.test.persistence;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.saituo.talk.common.test.SpringTransactionalContextTests;
import com.saituo.talk.modules.sys.dao.ProductDao;
import com.saituo.talk.modules.sys.entity.Product;

public class ProductDaoTest extends SpringTransactionalContextTests {

	@Autowired
	private ProductDao productDao;

	@Test
	public void find() {
		Product product = productDao.get(1);
		System.out.println(product.getBrand().getBrandName());
	}
}
