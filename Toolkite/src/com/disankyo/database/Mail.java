package com.disankyo.database;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Index;

@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Table(name = "MAIL")
public class Mail{
	@Id //主键
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	@Column
	@Index(name="playerId")
	private Integer playerId;//玩家(收件人)id
	@Column
	private Integer senderId;//发送人id 系统为0 
	@Column
	private String title;//邮件标题 （发件人名称）
	@Column
	private String content;//邮件内容
	@Column
	private Integer time;//发送时间
	@Column
	private Integer type;//邮件类型： 1、系统邮件 2、事件邮件 3、玩家邮件
	
	/***
	 * 系统邮件子类型：
	 *
	 * 
	 * 事件邮件子类型：
	 *  1、竞技场被动防守成功。邮件样式：对方在竞技场中挑战主公，可惜自不量力，被主公您狠狠的修理了一番。
		2、竞技场被动防守失败。邮件样式：对方在竞技场中挑战主公，您奋力反抗，可惜技不如人，败下阵来，排名滑落至xxx。
		3、残章争夺被动防守成功。邮件样式：对方妄图抢夺主公的[残章名称]，可惜自不量力，被主公您狠狠的修理了一番。
		4、残章争夺被动防守失败。邮件样式：对方妄图抢夺主公的[残章名称]，您奋力反抗，奈何力有不逮，残章被对方抢走。
		5、被动结交好友审批。邮件样式见【好友定义】。
		6、主动结交好友成功回复。邮件样式见【好友定义】。
		7、主动结交好友失败回复。邮件样式见【好友定义】。
		8、被动断绝好友关系提示。邮件样式见【好友定义】。
	 * 
	 * 
	 * 玩家邮件子类型：
	 * 
	 */
	@Column
	private Integer subType;//邮件子类型
	@Column
	private String itemNos;//附带道具编号,多个用“,”隔开
	@Column
	private String itemNums;//附带道具数量,多个用“,”隔开
	@Column
	private Integer money;//附带银两  
	@Column
	private Integer gold;//附带元宝
	@Column
	private Integer status;//邮件状态：0、未读取 1、已读取 2、已领取 3、已通过 4、已拒绝
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getPlayerId() {
		return playerId;
	}
	public void setPlayerId(Integer playerId) {
		this.playerId = playerId;
	}
	public Integer getSenderId() {
		return senderId;
	}
	public void setSenderId(Integer senderId) {
		this.senderId = senderId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getTime() {
		return time;
	}
	public void setTime(Integer time) {
		this.time = time;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getSubType() {
		return subType;
	}
	public void setSubType(Integer subType) {
		this.subType = subType;
	}
	
	public String getItemNos() {
		if(itemNos==null)return "";
		return itemNos;
	}
	public void setItemNos(String itemNos) {
		this.itemNos = itemNos;
	}
	public String getItemNums() {
		if(itemNums==null)return "";
		return itemNums;
	}
	public void setItemNums(String itemNums) {
		this.itemNums = itemNums;
	}
	public Integer getMoney() {
		if(money==null)return 0;
		return money;
	}
	public void setMoney(Integer money) {
		this.money = money;
	}
	public Integer getGold() {
		if(gold==null)return 0;
		return gold;
	}
	public void setGold(Integer gold) {
		this.gold = gold;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**是否有附件*/
	public boolean haveAttachment(){
		if(this.getMoney()>0 || this.getGold()>0 || this.getItemNos().length()>0){
			return true;
		}else{
			return false;
		}
	}
	
	
	
}
