package com.disankyo.database;

import java.io.Serializable;


/**
 * 玩家Buffer
 * @author will.xu
 * @version 2012-11-7 下午04:15:33
 */
public class UserBuffer implements Serializable {
	
	private static final long serialVersionUID = 5999077599242170574L;
	
	private String id;
	private String roleId;		//角色ID
	private int bufferId;		//BufferID
	private long triggerTime;	//触发时间(毫秒)
	private int time;			//持续时间(秒)
	private int bufferType;		//Buffer类型
	private int subType;        //Buffer辅类型
	private int adjustAttr1;	//调整属性1
	private int attrValue1;		//属性数值1(具体值)
	private int adjustAttr2;	//调整属性2
	private int attrValue2;		//属性数值2(具体值)
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public int getBufferId() {
		return bufferId;
	}
	public void setBufferId(int bufferId) {
		this.bufferId = bufferId;
	}
	public long getTriggerTime() {
		return triggerTime;
	}
	public void setTriggerTime(long triggerTime) {
		this.triggerTime = triggerTime;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public int getBufferType() {
		return bufferType;
	}
	public void setBufferType(int bufferType) {
		this.bufferType = bufferType;
	}
	public int getAdjustAttr1() {
		return adjustAttr1;
	}
	public void setAdjustAttr1(int adjustAttr1) {
		this.adjustAttr1 = adjustAttr1;
	}
	public int getAttrValue1() {
		return attrValue1;
	}
	public void setAttrValue1(int attrValue1) {
		this.attrValue1 = attrValue1;
	}
	public int getAdjustAttr2() {
		return adjustAttr2;
	}
	public void setAdjustAttr2(int adjustAttr2) {
		this.adjustAttr2 = adjustAttr2;
	}
	public int getAttrValue2() {
		return attrValue2;
	}
	public void setAttrValue2(int attrValue2) {
		this.attrValue2 = attrValue2;
	}

	public int getSubType() {
		return subType;
	}

	public void setSubType(int subType) {
		this.subType = subType;
	}
	
}
