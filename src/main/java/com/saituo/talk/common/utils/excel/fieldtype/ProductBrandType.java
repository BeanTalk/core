package com.saituo.talk.common.utils.excel.fieldtype;

import com.saituo.talk.common.utils.SpringContextHolder;
import com.saituo.talk.modules.sys.entity.ProductBrand;
import com.saituo.talk.modules.sys.service.ProductBrandService;

public class ProductBrandType {

	private static ProductBrandService productBrandService = SpringContextHolder.getBean(ProductBrandService.class);

	/**
	 * 获取对象值（导入）
	 */
	public static Object getValue(String val) {
		for (ProductBrand e : productBrandService.findAll()) {
			if (val.equals(e.getBrandName())) {
				return e;
			}
		}
		return null;
	}

	/**
	 * 设置对象值（导出）
	 */
	public static String setValue(Object val) {
		if (val != null && ((ProductBrand) val).getBrandName() != null) {
			return ((ProductBrand) val).getBrandName();
		}
		return "";
	}
}
