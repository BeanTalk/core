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
import com.saituo.talk.modules.sys.entity.Dict;
import com.saituo.talk.modules.sys.entity.Menu;

/**
 * 菜单DAO接口
 * 
 * @author ThinkGem
 * @version 2013-8-23
 */
@Repository
public class MenuDao extends BaseDao<Menu> {

	public List<Menu> findAllActivitiList() {
		return find("from Menu where module=:p1 and delFlag=:p2 order by sort", new Parameter(Dict.SYS_MODULE,
				Dict.DEL_FLAG_NORMAL));
	}

	public List<Menu> findByParentIdsLike(String parentIds) {
		return find("from Menu where module=:p1 and parentIds like :p2", new Parameter(Dict.SYS_MODULE, parentIds));
	}

	public List<Menu> findAllList() {
		return find("from Menu where delFlag=:p1 order by sort", new Parameter(Dict.DEL_FLAG_NORMAL));
	}

	public List<Menu> findSysModuleAllList() {
		return find("from Menu where module=:p1 and delFlag=:p2 order by sort", new Parameter(Dict.SYS_MODULE,
				Dict.DEL_FLAG_NORMAL));
	}

	public List<Menu> findByUserId(String userId) {
		return find(
				"select distinct m from Menu m, Role r, User u where m in elements (r.menuList) and r in elements (u.roleList)"
						+ " and m.module=:p1 and m.delFlag=:p2 and r.delFlag=:p2 and u.delFlag=:p2 and u.id=:p3"
						+ " order by m.sort", new Parameter(Dict.SYS_MODULE, Menu.DEL_FLAG_NORMAL, userId));
	}

	public List<Menu> findAllActivitiList(String userId) {
		return find(
				"select distinct m from Menu m, Role r, User u where m in elements (r.menuList) and r in elements (u.roleList)"
						+ " and m.module=:p1 and m.delFlag=:p2 and r.delFlag=:p2 and u.delFlag=:p2 and u.id=:p3 order by m.sort",
				new Parameter(Dict.SYS_MODULE, Menu.DEL_FLAG_NORMAL, userId));
	}

}
