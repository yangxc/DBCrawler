package com.peraglobal.mongo.dao;


import org.springframework.transaction.annotation.Transactional;

import com.peraglobal.mongo.model.Datum;


@Transactional
public interface DatumDao extends BaseDao<Datum> {
	

}
