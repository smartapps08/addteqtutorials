package com.myorg.dbdemo;

public class Note {
	private int id;
	private String title;
	private String body;
	public Note(String title, String body) {
		super();
		this.title = title;
		this.body = body;
	}
	
	
	
	public Note(int id, String title, String body) {
		super();
		this.id = id;
		this.title = title;
		this.body = body;
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getTitle() {
		return title;
	}
	
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	
}
