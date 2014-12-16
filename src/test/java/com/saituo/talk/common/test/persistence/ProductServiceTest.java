package com.saituo.talk.common.test.persistence;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.saituo.talk.common.persistence.Page;
import com.saituo.talk.common.test.SpringTransactionalContextTests;
import com.saituo.talk.modules.sys.entity.Product;
import com.saituo.talk.modules.sys.entity.ProductBrand;
import com.saituo.talk.modules.sys.service.ProductService;

public class ProductServiceTest extends SpringTransactionalContextTests {

	@Autowired
	private ProductService productService;

	//@Test
	public void find() {
		Product product = productService.get(3);
		System.out.println(product.getBrand().getBrandName());
	}

	//@Test
	public void findPage() {
		Page<Product> page = new Page<Product>();
		Product product = new Product();
		product.setProductName("Phospho-Akt Substrate (RXRXXS*/T*) (23C8D2) Rabbit mAb, 100ul");
		Page<Product> productPage = productService.findAllProduct(page, product);
		System.out.println(productPage.getCount());
	}
	
	
	@Test
	public void save() {
		Product product = productService.get(102);
		ProductBrand productBrand = new ProductBrand();
		productBrand.setId(1);
		product.setBrand(productBrand);
		productService.save(product);
	}
}
