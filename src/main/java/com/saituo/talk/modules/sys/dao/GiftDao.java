package com.saituo.talk.modules.sys.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.saituo.talk.common.enums.GiftStatus;
import com.saituo.talk.common.persistence.BaseDao;
import com.saituo.talk.common.persistence.Parameter;
import com.saituo.talk.modules.sys.entity.Gift;

@Repository
public class GiftDao extends BaseDao<Gift> {

	public List<Gift> findAllList(String orderby) {
		return find("from Gift where delFlag=:p1 and giftStatus=:p2", new Parameter(Gift.DEL_FLAG_NORMAL,
				GiftStatus.START.getValue()));
	}

	public void delete(Integer giftId) {
		update("update Gift set delFlag = :p2 where id = :p1", new Parameter(giftId, "1"));
	}
}
