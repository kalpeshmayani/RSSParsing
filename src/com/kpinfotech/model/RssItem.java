package com.kpinfotech.model;

import java.io.Serializable;

import com.kpinfotech.global.AppMethod;

@SuppressWarnings("serial")
public class RssItem implements Serializable {

	private String title, thumbnail, summary, audio, duration;

	public RssItem(String title, String thumbnail, String summary, String audio, String duration) {
		this.title = title;
		this.summary = summary;
		this.duration = duration;
		
		if(AppMethod.isStringValid(thumbnail))
			this.thumbnail = thumbnail.replace(" ", "");
		
		if(AppMethod.isStringValid(audio))
			this.audio = audio.replace(" ", "");
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	public String getAudio() {
		return audio;
	}
	public void setAudio(String audio) {
		this.audio = audio;
	}

	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	
}