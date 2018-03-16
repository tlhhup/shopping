package org.tlh.shopping.util;

import java.util.List;

public class PageInfo<T> {

	private int number=0;
	private int size=10;
	private List<T> content;
	private int totalPage;
	private int total;

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		if(number<0){
			number=0;
		}
		this.number = number;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public List<T> getContent() {
		return content;
	}

	public void setContent(List<T> content) {
		this.content = content;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
		this.totalPage=total/this.size;
		if(total%size>0){
			this.totalPage++;
		}
	}

	public boolean getIsFirst() {
		return number<=0;
	}

	public boolean getIsLast() {
		return this.number>=totalPage;
	}
	
	public int getFrom(){
		return (this.number-1)*this.size;
	}

}
