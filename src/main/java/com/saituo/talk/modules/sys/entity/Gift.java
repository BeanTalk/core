package com.saituo.talk.modules.sys.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.validator.constraints.NotEmpty;

import com.saituo.talk.common.persistence.IdEntity;
import com.saituo.talk.common.utils.excel.annotation.ExcelField;

@Entity
@Table(name = "st_gift")
@DynamicInsert
@DynamicUpdate
public class Gift extends IdEntity<Gift> {

	private static final long serialVersionUID = 1L;

	private Area area; // 地区
	private String giftName;// 礼品名称
	private Integer giftStatus; // 礼品状态
	private Integer giftNum;// 礼品可使用数量
	private Integer needPea; // 所需豆豆
	private Integer needScore; // 所需积分

	@ManyToOne
	@JoinColumn(name = "area_id")
	@NotFound(action = NotFoundAction.IGNORE)
	@ExcelField(title = "归属区域", align = 2, sort = 40)
	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	@NotEmpty
	@Column(name = "gift_name")
	@ExcelField(title = "礼品名称", align = 2, sort = 10)
	public String getGiftName() {
		return giftName;
	}

	public void setGiftName(String giftName) {
		this.giftName = giftName;
	}

	@Column(name = "gift_status")
	@ExcelField(title = "礼品状态", align = 2, sort = 15)
	public Integer getGiftStatus() {
		return giftStatus;
	}

	public void setGiftStatus(Integer giftStatus) {
		this.giftStatus = giftStatus;
	}

	@Column(name = "gift_num")
	@ExcelField(title = "礼品所剩数量", sort = 25)
	public Integer getGiftNum() {
		return giftNum;
	}
	public void setGiftNum(Integer giftNum) {
		this.giftNum = giftNum;
	}

	@Column(name = "need_pea")
	@ExcelField(title = "所需豆豆", sort = 30)
	public Integer getNeedPea() {
		return needPea;
	}
	public void setNeedPea(Integer needPea) {
		this.needPea = needPea;
	}

	@Column(name = "need_score")
	@ExcelField(title = "所需积分", sort = 35)
	public Integer getNeedScore() {
		return needScore;
	}
	public void setNeedScore(Integer needScore) {
		this.needScore = needScore;
	}

}
