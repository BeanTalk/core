package com.saituo.talk.modules.sys.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.saituo.talk.common.persistence.BaseDao;
import com.saituo.talk.common.persistence.Parameter;
import com.saituo.talk.modules.sys.entity.Dict;
import com.saituo.talk.modules.sys.entity.ProductBrand;

@Repository
public class ProductBrandDao extends BaseDao<ProductBrand> {

	public List<String> findBrandNameList(){
		return find("select brandName from ProductBrand where delFlag=:p1 group by brandName", new Parameter(Dict.DEL_FLAG_NORMAL));
	}
}
