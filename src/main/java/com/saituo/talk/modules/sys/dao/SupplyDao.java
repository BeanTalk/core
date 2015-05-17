package com.saituo.talk.modules.sys.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.saituo.talk.common.persistence.BaseDao;
import com.saituo.talk.common.persistence.BaseEntity;
import com.saituo.talk.common.persistence.Parameter;
import com.saituo.talk.modules.sys.entity.Supply;

@Repository
public class SupplyDao extends BaseDao<Supply> {

	public List<String> findSupplyNameById(String id) {
		return find("select distinct supplyName from Supply where delFlag=:p1 and id = :p2", new Parameter(
				BaseEntity.DEL_FLAG_NORMAL, id));
	}

	public List<Supply> findSupplyMap() {
		return find("select distinct p from Supply p where delFlag=:p1", new Parameter(BaseEntity.DEL_FLAG_NORMAL));
	}

}
