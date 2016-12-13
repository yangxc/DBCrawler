package com.peraglobal.mongo.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.peraglobal.mongo.dao.DatumDao;
import com.peraglobal.mongo.model.Datum;

//import com.peraglobal.pdp.core.condition.Pagination;
//import com.sun.mail.util.QEncoderStream;
@Service("datumService")
public class DatumService extends BaseService<Datum> {
	@Resource
	private DatumDao datumDao;
	
	//根据taskId统计采集数量
	public long findByTaskId (String taskId){
		Query query = new Query();
		query.addCriteria(new Criteria("taskId").is(taskId));
		return datumDao.findByCount(query);
	}
	
	//根据taskId统计完整数据的数量
	public long findFullByTaskId (String taskId){
		Query query = new Query();
		query.addCriteria(new Criteria("taskId").is(taskId).and("isFull").is("0"));
		return datumDao.findByCount(query);
	}
	//根据taskId统计不完整数据的数量
	public long findnotFullByTaskId (String taskId){
		Query query = new Query();
		query.addCriteria(new Criteria("taskId").is(taskId).and("isFull").is("1"));
		return datumDao.findByCount(query);
	}
	//根据taskId批量删除采集数据
	public void deleteByTaskId(String taskId){
		Query query = new Query();
		query.addCriteria(new Criteria("taskId").is(taskId));
		datumDao.deleteByConditions(query);
	}
	
	//根据taskId查询采集数据
	public List<Datum> findByTaskIdDatum (Query query,int skip,int limit){
		//query.with(new Sort(new Order(Direction.DESC, "birth")));  
        query.skip(skip).limit(limit);  
		return datumDao.findByCondition(query);
	}
}
