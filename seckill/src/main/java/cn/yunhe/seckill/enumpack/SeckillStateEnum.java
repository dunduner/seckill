package cn.yunhe.seckill.enumpack;

public enum  SeckillStateEnum{
	SUCCESS(1,"秒杀成功"),
	END(0,"秒杀结束"),
	REPEAT_KILL(-1,"重复秒杀"),
	INNER_ERROR(-2,"系统异常"),
	DATA_REWRITE(-3,"数据篡改");
	
	
	private int state;//状态码
	private String stateInfo;//状态码对应的值
	
	
	private SeckillStateEnum(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
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
	
	
	 public static SeckillStateEnum stateOf(int reslut) {
		 	//values 枚举自己的静态方法
		 	SeckillStateEnum[] values = values();
	        for (SeckillStateEnum state :values ) {
	            if (state.getState() == reslut) {
	                return state;
	            }
	        }
	        return null;
	 }
	 public static void main(String[] args) {
		 SeckillStateEnum stateOf = stateOf(-1);
		 System.out.println(stateOf);
	}
	
}
