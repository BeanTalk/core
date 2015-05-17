package com.saituo.talk.modules.sys.service;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.saituo.talk.common.persistence.BaseEntity;
import com.saituo.talk.common.persistence.Page;
import com.saituo.talk.common.utils.StringUtils;
import com.saituo.talk.modules.sys.dao.SupplyDao;
import com.saituo.talk.modules.sys.entity.Supply;

@Service
@Transactional(readOnly = true)
public class SupplyService {

	@Autowired
	private SupplyDao supplyDao;

	public Page<Supply> findAllSupply(Page<Supply> page, Supply supply) {
		DetachedCriteria dc = supplyDao.createDetachedCriteria();

		if (StringUtils.isNotEmpty(supply.getSupplyName())) {
			dc.add(Restrictions.like("supplyName", "%" + supply.getSupplyName() + "%"));
		}
		dc.add(Restrictions.eq(BaseEntity.FIELD_DEL_FLAG, BaseEntity.DEL_FLAG_NORMAL));
		if (!StringUtils.isNotEmpty(page.getOrderBy())) {
			dc.addOrder(Order.asc("supplyName"));
		}
		return supplyDao.find(page, dc);
	}

	public long count(Supply supply) {

		DetachedCriteria dc = supplyDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(supply.getSupplyName())) {
			dc.add(Restrictions.eq("supplyName", supply.getSupplyName()));
		}
		dc.add(Restrictions.eq(BaseEntity.FIELD_DEL_FLAG, BaseEntity.DEL_FLAG_NORMAL));
		return supplyDao.count(dc);

	}

	public Map<Integer, String> getSupplyMap() {
		Map<Integer, String> mapData = Maps.newHashMap();
		List<Supply> supplyList = supplyDao.findSupplyMap();
		for (Supply supply : supplyList) {
			mapData.put(supply.getId(), supply.getSupplyName());
		}
		return mapData;
	}

	public List<Supply> findAll() {
		return supplyDao.findAll();
	}

	public Supply get(Integer id) {
		return supplyDao.get(id);
	}

	public Supply findProductBrand(Supply supply) {

		DetachedCriteria dc = supplyDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(supply.getSupplyName())) {
			dc.add(Restrictions.eq("supplyName", supply.getSupplyName()));
		}
		return supplyDao.find(dc).get(0);
	}

	@Transactional(readOnly = false)
	public void save(Supply supply) {
		supplyDao.save(supply);
	}

	@Transactional(readOnly = false)
	public void delete(Integer id) {
		supplyDao.clear();
		supplyDao.deleteById(id);
	}
}
