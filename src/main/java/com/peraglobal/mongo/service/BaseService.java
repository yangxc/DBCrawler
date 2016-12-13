package com.peraglobal.mongo.service;

import java.io.InputStream;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;
import com.peraglobal.mongo.dao.BaseDao;

public abstract class BaseService<T> {
	
	@Autowired
	private BaseDao<T> baseDao;
	// 新增一条数据
	public void insert(T entity) {
		this.baseDao.insert(entity);
	}
	//保存或更新
	public void save(T entity) {
		this.baseDao.save(entity);
	}

	public void deleteById(String id) {
		this.baseDao.deleteById(id);	
	}

	public void update(Query query, Update update) {
		this.baseDao.update(query, update);
	}
	@Deprecated
	public void update(T entity) {
		this.baseDao.update(entity);
	}

	public List<T> findByCondition(Query query) {
		return this.baseDao.findByCondition(query);
	}

	public List<T> findList(int skip, int limit) {
		return this.baseDao.findList(skip, limit);
	}

	public T findOneById(String id) {
		return (T) this.baseDao.findOneById(id);
	}

	public long findByCount(Query query) {
		return this.baseDao.findByCount(query);
	}

	public List<T> findAll() {
		return this.baseDao.findAll();
	}
	
	// 条件分页查询
	public List<T> findByCondition(Criteria criteria,int skip, int limit){
		return this.baseDao.findByCondition(criteria, skip, limit);
	}
	
	public GridFSFile storeFile(InputStream input,String fileName){
		return this.baseDao.storeFile(input, fileName);
	}
	
	public GridFSDBFile findFileById(String id){
		return this.baseDao.findFileById(id);
	}
	
	public void deleteFiles(ObjectId objectId){
		this.baseDao.deleteFiles(objectId);
	}
}
