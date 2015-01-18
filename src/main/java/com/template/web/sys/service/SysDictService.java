//Powered By if, Since 2014 - 2020

package com.template.web.sys.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.template.common.base.ServiceMybatis;
import com.template.web.sys.mapper.SysAreaMapper;
import com.template.web.sys.mapper.SysDictMapper;
import com.template.web.sys.model.SysArea;
import com.template.web.sys.model.SysDict;
import com.template.web.sys.model.SysOffice;
import com.template.web.sys.model.SysRole;

/**
 * 
 * @author
 */

@Service("sysDictService")
@CacheConfig(cacheNames = "sysDict")
public class SysDictService extends ServiceMybatis<SysDict> {

	@Resource
	private SysDictMapper sysDictMapper;

	@Resource
	private SysAreaMapper sysAreaMapper;

	/**
	 * 保存或更新
	 * 
	 * @param sysDict
	 * @return
	 */
	@CacheEvict(allEntries = true)
	public int saveSysdict(SysDict sysDict) {
		return this.save(sysDict);
	}

	/**
	 * 删除
	* @param sysDict
	* @return
	 */
	@CacheEvict(allEntries = true)
	public int deleteSysDict(SysDict sysDict) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("type", sysDict.getValue());
		if(sysDict.getType().equals("sys_area_type")){
			int areaCount = this.beforeDelete(SysArea.class,params);
			if(areaCount<0) return -1;
		}
		if(sysDict.getType().equals("sys_office_type")){
			int officeCount = this.beforeDelete(SysOffice.class,params);
			if(officeCount<0) return -1;
		}
		if(sysDict.getType().equals("sys_data_scope")){
			int roleCount = this.beforeDelete(SysRole.class, params);
			if(roleCount<0) return -1;
		}
		return this.updateDelFlagToDelStatusById(SysDict.class, sysDict.getId());
	}

	/**
	 * 根据字典类型查询,做一下缓存
	 */
	@Cacheable(key = "'dict'+#sysDict['type']")
	public List<SysDict> findSysDictListByParams(SysDict sysDict) {
		List<SysDict> dicts = this.select(sysDict);
		return dicts;
	}

}
