package com.template.web.monitor.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.github.abel533.sql.SqlMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.template.common.constant.Constant;

@Controller
@RequestMapping("${adminPath}/monitor/db")
public class SQLExecutorController {
	
	@Resource
	private SqlMapper sqlMapper;
	
	@RequestMapping("druid")
	public String showDruid() {
		return "sys/monitor/db/druid";
	}

	@RequestMapping(value = "sql", method = RequestMethod.GET)
	public String showSQLForm() {
		return "sys/monitor/db/sqlForm";
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "sql", method = RequestMethod.POST)
	public String executeSQL(String sql, Integer pageSize, Integer pageNum,
			final Model model) {
		try {
			if (sql != null) {
				String lowerCaseSQL = sql.trim().toLowerCase();
				final boolean isDML = lowerCaseSQL.startsWith("insert")
						|| lowerCaseSQL.startsWith("update")
						|| lowerCaseSQL.startsWith("delete");
				final boolean isDQL = lowerCaseSQL.startsWith("select");
				if (!isDML && !isDQL) {
					model.addAttribute(Constant.MSG,
							"您执行的SQL不允许，只允许insert、update、delete、select");
				} else if (isDML) {
					String mode = lowerCaseSQL.substring(0, 6);
					int count = 0;
					if ("update".equals(mode)) {
						count = sqlMapper.update(sql);
					} else if ("insert".equals(mode)) {
						count = sqlMapper.insert(sql);
					} else if ("delete".equals(mode)) {
						count = sqlMapper.delete(sql);
					}
					model.addAttribute(Constant.MSG, mode + "影响行数:" + count);
				} else if (isDQL) {
					PageHelper.startPage(pageNum,pageSize);
					List<Map<String, Object>> list = sqlMapper.selectList(sql);
					String[] columns = test(list);
					PageInfo page = new PageInfo(list);
					model.addAttribute("columns", columns)
						.addAttribute("page", page);
				}
			}
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			model.addAttribute(Constant.MSG, sw.toString());
		}
		return "sys/monitor/db/result";
	}

	public String[] test(List<Map<String, Object>> list) {
		List<List<Object>> result = null;
		String[] columns = null;
		if (list != null && list.size() > 0) {
			Map<String, Object> keyMap = null;
			int length = 0;
			for (Map<String, Object> objectMap : list) {
				if (objectMap.size() > length) {
					length = objectMap.size();
					keyMap = objectMap;
				}
			}
			if (keyMap != null && length > 0) {
				columns = new String[length];
				Set<Map.Entry<String, Object>> entry = keyMap.entrySet();
				int i = 0;
				for (Map.Entry<String, Object> objectEntry : entry) {
					columns[i] = objectEntry.getKey();
					i++;
				}
			}
		}
		if (columns != null && columns.length > 0) {
			result = new ArrayList<List<Object>>();
			List<Object> dataList;
			for (Map<String, Object> objectMap : list) {
				dataList = new ArrayList<Object>(columns.length);
				for (String column : columns) {
					dataList.add(objectMap.get(column));
				}
				result.add(dataList);
			}
		}
		return columns;
	}

}
