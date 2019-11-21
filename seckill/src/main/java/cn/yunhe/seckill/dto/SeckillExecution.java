package cn.yunhe.seckill.dto;

import cn.yunhe.seckill.entity.Successseckilled;
import cn.yunhe.seckill.enumpack.SeckillStateEnum;

/**
 * @author Zhangning
 *
 */
public class SeckillExecution {
	private long seckillId;
	private int state;
	private String stateInfo;
	private Successseckilled successKilled ;
	@Override
	public String toString() {
		return "SeckillExecution [seckillId=" + seckillId + ", state=" + state
				+ ", stateInfo=" + stateInfo + ", successKilled="
				+ successKilled + "]";
	}
	public SeckillExecution(long seckillId, SeckillStateEnum stateEnum,
			Successseckilled successKilled) {
		super();
		this.seckillId = seckillId;
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.successKilled = successKilled;
	}
	public SeckillExecution(long seckillId,SeckillStateEnum stateEnum) {
		this.seckillId = seckillId;
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		
	}
	public long getSeckillId() {
		return seckillId;
	}

	public void setSeckillId(long seckillId) {
		this.seckillId = seckillId;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}

	public Successseckilled getSuccessKilled() {
		return successKilled;
	}

	public void setSuccessKilled(Successseckilled successKilled) {
		this.successKilled = successKilled;
	}
	
	
}
