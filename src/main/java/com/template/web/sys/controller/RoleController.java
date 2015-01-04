//Powered By if, Since 2014 - 2020

package com.template.web.sys.controller;

import java.util.List;
import java.util.Map;

import com.template.common.base.BaseController;
import com.template.common.utils.JsonUtils;
import com.github.pagehelper.PageInfo;
import com.template.web.sys.model.SysOffice;
import com.template.web.sys.model.SysRole;
import com.template.web.sys.model.SysUser;
import com.template.web.sys.service.SysOfficeService;
import com.template.web.sys.service.SysRoleService;
import com.template.web.sys.service.SysUserService;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * @author 
 */

@Controller
@RequestMapping("${adminPath}/role")
public class RoleController extends BaseController {
	
	
	@Resource
	private SysRoleService sysRoleService;
	@Resource
	private SysUserService sysUserService;
	@Resource
	private SysOfficeService sysOfficeService;
	
	/**
	 * 跳转到模块页面
	* @param model
	* @return 模块html
	 */
	@RequestMapping
	public String toSysRole(Model model){
		return "sys/role/role";
	}
	
	/**
	 * 绑定用户界面
	* @return
	 */
	@RequestMapping("binduser")
	public String toBindUser(Long id,Model model){
		List<SysUser> users = sysUserService.findUserByRoleId(id);
		List<SysOffice> offices = sysOfficeService.select(null);
		model.addAttribute("users", users).addAttribute("roleId", id)
			.addAttribute("offices", JsonUtils.getInstance().toJson(offices));
		return "sys/role/role-user";
	}
	
	/**
	 * 部门的人员
	* @param officeId
	* @return
	 */
	@RequestMapping(value="officeuser",method=RequestMethod.POST)
	public @ResponseBody List<SysUser> officeUser(Long officeId){
		SysUser sysUser = new SysUser();
		sysUser.setOfficeId(officeId);
		return sysUserService.select(sysUser);
	}
	
	/**
	 * 保存角色绑定的用户
	* @return
	 */
	@RequestMapping(value="saveuser",method=RequestMethod.POST)
	public @ResponseBody Integer saveUser(Long roleId,@RequestParam(value="userIds[]") Long[] userIds){
		return null;
	}
	
	/**
	 * 分页显示
	* @param params
	* @return
	 */
	@RequestMapping(value="list",method=RequestMethod.POST)
	public String list(@RequestParam Map<String, Object> params,Model model){
		PageInfo<SysRole> page = sysRoleService.findPageInfo(params);
		model.addAttribute("page", page);
		return "sys/role/role-list";
	}
	
	/**
	 * 添加或更新
	* @param params
	* @return
	 */
	@RequestMapping(value="save",method=RequestMethod.POST)
	public @ResponseBody Integer save(@ModelAttribute SysRole sysRole){
		return sysRoleService.saveSysRole(sysRole);
	}
	
	/**
	 * 删除
	* @param 
	* @return
	 */
	@RequestMapping(value="del",method=RequestMethod.POST)
	public @ResponseBody Integer del(Long id){
		return sysRoleService.deleteSysRole(id);
	}
	
	/**
	 * 弹窗显示
	* @param params {"mode":"1.add 2.edit 3.detail}
	* @return
	 */
	@RequestMapping(value="{mode}/showlayer",method=RequestMethod.POST)
	public String layer(Long id,@PathVariable String mode, Model model){
		SysRole sysRole = null;
		if(StringUtils.equals("edit", mode)){
			sysRole = sysRoleService.selectByPrimaryKey(id);
			List<Long> resIds = sysRoleService.findResourceIdsByRoleId(id);
			if(sysRole.getDataScope().equals("9")){
				List<Long> officeIds = sysRoleService.findOfficeIdsByRoleId(id);
				model.addAttribute("officeIds", JsonUtils.getInstance().toJson(officeIds));
			}
			model.addAttribute("resIds", JsonUtils.getInstance().toJson(resIds));
		}
		model.addAttribute("sysrole", sysRole);
		return mode.equals("detail")?"sys/role/role-detail":"sys/role/role-save";
	}
	

}
