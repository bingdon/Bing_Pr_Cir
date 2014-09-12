package com.example.projectcircle.db.interfaces;

public interface DataInterface {

	public long delete(long id);

	public long delete(String id);

	public Object queryData();

	public Object queryData(int pagersize);

	public Object queryDataId(long id);

	public Object queryDataId(int id);

	public Object deleteAll();

	public void close();

}
