package com.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.web.dao.PdxxRepository;
/**
 * 盘点相关的定时任务
 * @author 夏夜梦星辰
 *
 */
@Component
public class PdTimerTask {
	private static Logger log = Logger.getLogger(PdTimerTask.class);
	
	@Autowired
	private PdxxRepository pdxxRep;
	
	private DateFormat formatter;
	/**
	 * init-method
	 */
	@PostConstruct
	public void init() {
		//TODO 动态配置定时任务参数
		log.info("====盘点状态初始化定时任务启动====");
		this.formatter = new SimpleDateFormat("yyyy-MM-dd");
	}
	
	/**
	 * 在6月和12月的最后一天执行该定时任务
	 */
	@Scheduled(cron = "0 0 0 30 6 *")
	public void pdReset1() {
		_pdReset();
	}
	/* //@Scheduled(cron = "0 0 0 L 6,12 *")
	 * SpringBoot自带的Scheduled，可以将它看成一个轻量级的Quartz
	 * 但是不支持在DayofMonth的位置使用L表示月的最后一天
	 * 无奈只好分为两个方法
	 * 如果整合Quartz可以使用上面的表达式
	 * */
	@Scheduled(cron = "0 0 0 31 12 *")
	public void pdReset2() {
		_pdReset();
	}
	
	private void _pdReset() {
		
		log.info("===重置盘点状态开始===当前日期:"+formatter.format(new Date()));
		int result = pdxxRep.PdztReset("未盘点");
		log.info("===重置盘点状态完成===影响的资产数据数量:"+result);
	}
}
