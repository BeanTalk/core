package com.saituo.talk.modules.sys.service;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saituo.talk.common.persistence.BaseEntity;
import com.saituo.talk.common.persistence.Page;
import com.saituo.talk.common.utils.StringUtils;
import com.saituo.talk.modules.sys.dao.GiftDao;
import com.saituo.talk.modules.sys.entity.Gift;
import com.saituo.talk.modules.sys.entity.Product;

/**
 * 礼品Service
 * 
 * @author ThinkGem
 * @version 2013-5-29
 */
@Service
@Transactional(readOnly = true)
public class GiftService {

	@Autowired
	private GiftDao giftDao;

	public Page<Gift> find(Page<Gift> page, Gift gift) {

		DetachedCriteria dc = giftDao.createDetachedCriteria();

		if (gift.getGiftStatus() != null) {
			dc.add(Restrictions.eq("giftStatus", gift.getGiftStatus()));
		}

		if (StringUtils.isNotEmpty(gift.getGiftName())) {
			dc.add(Restrictions.eq("giftName", gift.getGiftName()));
		}

		if (gift.getArea() != null && gift.getArea().getId() != null) {
			dc.add(Restrictions.eq("area.id", gift.getArea().getId()));
		}

		if (gift.getGiftNum() != null) {
			dc.add(Restrictions.lt("giftNum", gift.getGiftNum()));
			dc.addOrder(Order.asc("giftNum"));
		}

		if (gift.getNeedScore() != null) {
			dc.add(Restrictions.lt("needScore", gift.getNeedScore()));
			dc.addOrder(Order.asc("needScore"));
		}

		dc.add(Restrictions.eq(BaseEntity.FIELD_DEL_FLAG, BaseEntity.DEL_FLAG_NORMAL));
		return giftDao.find(page, dc);
	}

	public List<Gift> getGiftList() {
		return giftDao.findAllList("needPea");
	}

	public Gift get(Integer giftId) {
		return giftDao.get(giftId);
	}

	public List<Gift> findAll() {
		DetachedCriteria detachedCriteria = giftDao.createDetachedCriteria();
		detachedCriteria.add(Restrictions.eq(BaseEntity.FIELD_DEL_FLAG, BaseEntity.DEL_FLAG_NORMAL));
		return giftDao.find(detachedCriteria);
	}

	public long count(Gift gift) {

		DetachedCriteria dc = giftDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(gift.getGiftName())) {
			dc.add(Restrictions.eq("giftName", gift.getGiftName()));
		}
		dc.add(Restrictions.eq(BaseEntity.FIELD_DEL_FLAG, BaseEntity.DEL_FLAG_NORMAL));
		return giftDao.count(dc);

	}

	@Transactional(readOnly = false)
	public void save(Gift gift) {
		giftDao.clear();
		giftDao.save(gift);
	}

	@Transactional(readOnly = false)
	public void update(Gift gift) {
		giftDao.update(gift);
	}

	@Transactional(readOnly = false)
	public void delete(Integer giftId) {
		giftDao.delete(giftId);
	}

}
