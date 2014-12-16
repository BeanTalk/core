package com.saituo.talk.common.enums;

/**
 * 审批状态枚举
 */
public enum GiftStatus implements ValueEnum<Integer> {

	READY(0, "未开始兑换"), START(1, "开始兑换"), END(2, "结束兑换");

	// 值
	private Integer value;
	// 名称
	private String name;

	private GiftStatus(Integer value, String name) {
		this.value = value;
		this.name = name;
	}

	/**
	 * 获取值
	 * 
	 * @return Integer
	 */
	public Integer getValue() {
		return value;
	}

	/**
	 * 获取名称
	 * 
	 * @return String
	 */
	public String getName() {
		return name;
	}

}
