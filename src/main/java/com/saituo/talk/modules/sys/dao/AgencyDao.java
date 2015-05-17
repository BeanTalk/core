package com.saituo.talk.modules.sys.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.saituo.talk.common.persistence.BaseDao;
import com.saituo.talk.common.persistence.BaseEntity;
import com.saituo.talk.common.persistence.Parameter;
import com.saituo.talk.modules.sys.entity.Agency;

@Repository
public class AgencyDao extends BaseDao<Agency> {

	public List<String> findAgencyNameById(String id) {
		return find("select distinct agentName from Agency where delFlag=:p1 and id = :p2", new Parameter(
				BaseEntity.DEL_FLAG_NORMAL, id));
	}

	public List<Agency> findBrandIdAndNameMap() {
		return find("select distinct p from Agency p where delFlag=:p1", new Parameter(BaseEntity.DEL_FLAG_NORMAL));
	}

}
