package com.saituo.talk.common.test.persistence;

import org.springframework.beans.factory.annotation.Autowired;

import com.saituo.talk.common.test.SpringTransactionalContextTests;
import com.saituo.talk.modules.sys.entity.Area;
import com.saituo.talk.modules.sys.entity.Gift;
import com.saituo.talk.modules.sys.service.AreaService;
import com.saituo.talk.modules.sys.service.GiftService;

public class GiftServiceTest extends SpringTransactionalContextTests {

	@Autowired
	private GiftService giftService;

	@Autowired
	private AreaService areaService;

	//@Test
	public void save() {
		Area area = areaService.get(20);
		Gift gift = new Gift();
		gift.setArea(area);
		gift.setGiftName("gift1");
		gift.setGiftNum(10);
		gift.setGiftStatus(1);
		gift.setNeedPea(100);
		gift.setNeedScore(1000);
		giftService.save(gift);
	}

	//@Test
	public void find() {
		Gift gift = giftService.get(1);
		System.out.println(gift.getGiftName());
	}

	// @Test
	// public void findPage() {
	// List<Gift> giftList = giftService.getGiftListOrderByPea();
	// System.out.println(giftList.size());
	// }

}
