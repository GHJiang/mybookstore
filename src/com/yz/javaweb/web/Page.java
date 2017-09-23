package com.yz.javaweb.web;

import java.util.List;

public class Page<T> {

	// ��ǰ��ҳ��.
	private int pageNo;

	// ��ǰҳ��List

	private List<T> list;

	// ÿҳ��ʾ�������¼����

	private int pageSize = 3;

	// ���ж�������¼����

	private long totalItemNumber;

	// �Ե�ǰ��ҳ�����г�ʼ��
	public Page(int pageNo) {
		this.pageNo = pageNo;
	}

	// ��Ҫ����У��
	public int getPageNo() {
		if (pageNo < 0) {
			pageNo = 1;
		}
		if (pageNo > getTotalPageNumber()) {
			pageNo = getTotalPageNumber();
		}
		return pageNo;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public List<T> getList() {
		return list;
	}

	public int getPageSize() {
		return pageSize;
	}
	// ��ȡ�ܵ�ҳ����

	public int getTotalPageNumber() {

		int totalPageNumber = (int) totalItemNumber / pageSize;
		if (totalItemNumber % pageSize != 0) {
			totalPageNumber++;
		}

		return totalPageNumber;
	}

	// �����ܵļ�¼����
	public void setTotalItemNumber(long totalItemNumber) {
		this.totalItemNumber = totalItemNumber;
	}

	// �Ƿ�����һҳ���жϵ�ǰ��pageNo �Ƿ�С���ܵ�ҳ����
	public boolean isHasNext() {
		if (getPageNo() < getTotalPageNumber()) {
			return true;
		}
		return false;
	}

	// �ж��Ƿ�����һҳ���жϵ�ǰ��pageNo �Ƿ����һҳ�ļ�¼����
	public boolean isHasPrev() {

		if (getPageNo() > 1) {
			return true;
		}

		return false;
	}

	public int getPrevPage() {
		if (isHasPrev()) {
			return getPageNo() - 1;
		}
		return getPageNo();
	}

	public int getNextPage() {
		if (isHasNext()) {
			return getPageNo() + 1;
		}
		return getPageNo();
	}

}
