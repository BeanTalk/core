/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.saituo.talk.modules.sys.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.saituo.talk.common.persistence.BaseDao;
import com.saituo.talk.common.persistence.Parameter;
import com.saituo.talk.modules.sys.entity.Office;

/**
 * 机构DAO接口
 * @author ThinkGem
 * @version 2013-8-23
 */
@Repository
public class OfficeDao extends BaseDao<Office> {
	
	public List<Office> findByParentIdsLike(String parentIds){
		return find("from Office where parentIds like :p1", new Parameter(parentIds));
	}
	
}
