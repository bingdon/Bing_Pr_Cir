package com.example.projectcircle.db.interfaces;

import java.util.List;

import com.example.projectcircle.bean.NewConstactBean;

public interface NewContactsInterface {

	public long delete(NewConstactBean newConstactBean);

	public List<NewConstactBean> queryData();

	public List<NewConstactBean> queryData(int pagersize);

	public List<NewConstactBean> queryDataId(NewConstactBean newConstactBean);
	
	public long insert(NewConstactBean newConstactBean);
	
	public long update(NewConstactBean newConstactBean);

	public long deleteAll();

	public void close();

}
