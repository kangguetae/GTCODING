package com.parucnc.test_3.domain;

public class GenreVO {
	private int gno;
	private String genreEng;
	private String genreKor;
	private int selected;
	
	public int getSelected() {
		return selected;
	}
	public void setSelected(int selected) {
		this.selected = selected;
	}
	public int getGno() {
		return gno;
	}
	public void setGno(int gno) {
		this.gno = gno;
	}
	public String getGenreEng() {
		return genreEng;
	}
	public void setGenreEng(String genreEng) {
		this.genreEng = genreEng;
	}
	public String getGenreKor() {
		return genreKor;
	}
	public void setGenreKor(String genreKor) {
		this.genreKor = genreKor;
	}
	

}
