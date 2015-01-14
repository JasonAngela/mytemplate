package com.template.common.base;

import java.util.Date;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.alibaba.fastjson.JSONObject;

@SuppressWarnings({ "unused"})
@Entity
public class BaseEntity extends JSONObject {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator="JDBC")
    private Long id; //id <主键id>
	
	private String updateBy; //update_by <更新者>
	private Date updateDate; //update_date <更新时间>
	private String createBy; //create_by <创建者>
	private Date createDate; //create_date <创建时间>
	private String delFlag; //del_flag <删除标记(0.正常  1.删除)>
	
	public BaseEntity() {
		super();
	}
	
	public Long getId() {
		return this.getLong("id");
    }
   
    public void setId(Long id) {
		this.set("id", id);
    }
	
	public String getCreateBy() {
		return this.getString("createBy");
    }
   
    public void setCreateBy(String createBy) {
		this.set("createBy", createBy);
    }

	public Date getCreateDate() {
		return this.getDate("createDate");
    }
   
    public void setCreateDate(Date createDate) {
		this.set("createDate", createDate);
    }
    public String getUpdateBy() {
		return this.getString("updateBy");
    }
   
    public void setUpdateBy(String updateBy) {
		this.set("updateBy", updateBy);
    }

	public Date getUpdateDate() {
		return this.getDate("updateDate");
    }
   
    public void setUpdateDate(Date updateDate) {
		this.set("updateDate", updateDate);
    }

	public String getDelFlag() {
		return this.getString("delFlag");
    }
   
    public void setDelFlag(String delFlag) {
		this.set("delFlag", delFlag);
    }

	public BaseEntity(Map<String, Object> map) {
		super(map);
	}

	public BaseEntity getEntity(String key) {
		Object value = this.get(key);
		if (value instanceof BaseEntity) {
			return (BaseEntity) value;
		}

		JSONObject jobj = null;

		if (value instanceof JSONObject) {
			jobj = (JSONObject) value;
		} else {
			jobj = (JSONObject) toJSON(value);
		}

		return jobj == null ? null : new BaseEntity(jobj);
	}

	public BaseEntity set(String key, Object value, boolean force) {
		if (force || value != null) {
			super.put(key, value);
		}
		return this;
	}

	public BaseEntity set(String key, Object value) {
		return this.set(key, value, true);
	}

	public BaseEntity setAll(Map<? extends String, ? extends Object> m) {
		super.putAll(m);
		return this;
	}

	public static BaseEntity err(int errCode) {
		return new BaseEntity().set("errCode", errCode);
	}

	public static BaseEntity err(int errCode, String errMsg) {
		return new BaseEntity().set("errCode", errCode).set("errMsg", errMsg);
	}
}
