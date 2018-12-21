package com.lmdsoft.modules.gen.entity;

import java.io.Serializable;
import java.util.Date;


import java.math.BigDecimal;



/**
 * 
 * 
 * @author lmdsoft
 * @email lmdsoft@163.com
 * @date 2018-12-04 10:47:45
 */
public class CeshiGenEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	//$column.comments
	private Long id;
	//$column.comments
	private String value;
	//$column.comments
	private String name;
	//$column.comments
	private String type;
	//$column.comments
	private String title;
	//$column.comments
	private Date createtime;

	/**
	 * 设置：ID
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：ID
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：值
	 */
	public void setValue(String value) {
		this.value = value;
	}
	/**
	 * 获取：值
	 */
	public String getValue() {
		return value;
	}
	/**
	 * 设置：名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：类型
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * 获取：类型
	 */
	public String getType() {
		return type;
	}
	/**
	 * 设置：标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 获取：标题
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getCreatetime() {
		return createtime;
	}
}
