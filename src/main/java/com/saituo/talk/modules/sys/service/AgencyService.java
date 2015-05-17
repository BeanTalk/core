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
import com.saituo.talk.modules.sys.dao.AgencyDao;
import com.saituo.talk.modules.sys.entity.Agency;

@Service
@Transactional(readOnly = true)
public class AgencyService {

	@Autowired
	private AgencyDao agencyDao;

	public Page<Agency> findAllAgency(Page<Agency> page, Agency agency) {
		DetachedCriteria dc = agencyDao.createDetachedCriteria();

		if (StringUtils.isNotEmpty(agency.getAgentName())) {
			dc.add(Restrictions.like("agentName", "%" + agency.getAgentName() + "%"));
		}
		dc.add(Restrictions.eq(BaseEntity.FIELD_DEL_FLAG, BaseEntity.DEL_FLAG_NORMAL));
		if (!StringUtils.isNotEmpty(page.getOrderBy())) {
			dc.addOrder(Order.asc("agentName"));
		}
		return agencyDao.find(page, dc);
	}

	public long count(Agency agency) {

		DetachedCriteria dc = agencyDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(agency.getAgentName())) {
			dc.add(Restrictions.eq("agentName", agency.getAgentName()));
		}
		dc.add(Restrictions.eq(BaseEntity.FIELD_DEL_FLAG, BaseEntity.DEL_FLAG_NORMAL));
		return agencyDao.count(dc);

	}

	public Map<Integer, String> getAgencyMap() {
		Map<Integer, String> mapData = Maps.newHashMap();
		List<Agency> agencyList = agencyDao.findBrandIdAndNameMap();
		for (Agency agency : agencyList) {
			mapData.put(agency.getId(), agency.getAgentName());
		}
		return mapData;
	}

	public List<Agency> findAll() {
		return agencyDao.findAll();
	}

	public Agency get(Integer id) {
		return agencyDao.get(id);
	}

	public Agency findProductBrand(Agency agency) {

		DetachedCriteria dc = agencyDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(agency.getAgentName())) {
			dc.add(Restrictions.eq("agentName", agency.getAgentName()));
		}
		return agencyDao.find(dc).get(0);
	}

	@Transactional(readOnly = false)
	public void save(Agency agency) {
		agencyDao.save(agency);
	}

	@Transactional(readOnly = false)
	public void delete(Integer id) {
		agencyDao.clear();
		agencyDao.deleteById(id);
	}
}
