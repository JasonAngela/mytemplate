//Powered By if, Since 2014 - 2020

package com.template.web.sys.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.template.common.base.ServiceMybatis;
import com.template.web.sys.mapper.SysOfficeMapper;
import com.template.web.sys.model.SysOffice;
import com.template.web.sys.model.SysRole;
import com.template.web.sys.model.SysUser;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author 
 */

@Service("sysOfficeService")
@CacheConfig(cacheNames="sysOffice")
public class SysOfficeService extends ServiceMybatis<SysOffice> {

	@Resource
	private SysOfficeMapper sysOfficeMapper;
	
	/**
	 *新增或更新SysOffice
	 */
	@CacheEvict(allEntries=true)
	public int saveSysOffice(SysOffice sysOffice){
		int count = 0;
		//新的parentIds
		sysOffice.setParentIds(sysOffice.getParentIds()+sysOffice.getParentId()+","); 
		int grade = sysOffice.getParentIds().split(",").length;
		sysOffice.setGrade(String.valueOf(grade));
		if(null == sysOffice.getId()){
			count = this.insertSelective(sysOffice);
		}else{
			//getParentIds() 当前选择的父节点parentIds , getParentId()父节点的id
			//先更新parentId，此节点的parentIds以更新
			count = this.updateByPrimaryKeySelective(sysOffice); 
			//不移动节点不更新子节点的pids
			if(!StringUtils.equals(sysOffice.getOldParentIds(), sysOffice.getParentIds())){
				sysOfficeMapper.updateParentIds(sysOffice); //批量更新子节点的parentIds
			}
		}
		return count;
	}
	
	@CacheEvict(allEntries=true)
	public int deleteOfficeByRootId(Long id){
		int roleCount = this.beforeDeleteTreeStructure(id, "officeId", SysRole.class,SysOffice.class);
		if(roleCount<0) return -1;
		int userOfficeCount = this.beforeDeleteTreeStructure(id, "officeId", SysUser.class,SysOffice.class);
		int userCompanyCount = this.beforeDeleteTreeStructure(id, "companyId",  SysUser.class,SysOffice.class);
		if(userOfficeCount+userCompanyCount<0) return -1;
		return sysOfficeMapper.deleteOfficeByRootId(id);
	}
	
	/**
	 * 全部机构
	* @return
	 */
	@Cacheable(key="'office'")
	public List<SysOffice> findAllOffice(){
		return this.select(new SysOffice());
	}
	
	
	/**
	 * 根据条件分页查询SysOffice列表
	 * @param {"pageNum":"页码","pageSize":"条数","isCount":"是否生成count sql",......}
	 */
	public PageInfo<SysOffice> findPageInfo(Map<String,Object> params) {
        PageHelper.startPage(params);
        List<SysOffice> list=sysOfficeMapper.findPageInfo(params); 
        return new PageInfo<SysOffice>(list);
	}

}
