package cn.yunhe.seckill.config;


import cn.yunhe.seckill.bean.Config;

/**
 *
 *  业务接口
 *
 */
public interface ConfigService {

    /**
     * 根据primaryKey获取对象
     */
    public Config  getConfigByPrimaryKey(String key);

}