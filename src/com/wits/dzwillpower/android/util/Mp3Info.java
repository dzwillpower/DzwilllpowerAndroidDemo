package com.wits.dzwillpower.android.util;

public class Mp3Info {
	private long id; // 歌曲ID
	private String title; // 歌曲名称
	private String album; // 专辑
	private String artist; // 歌手名称
	private long duration; // 歌曲时长
	private long size; // 歌曲大小
	private String url; // 歌曲路径
	private String lrcTitle; // 歌词名称
	private String lrcSize; // 歌词大小
	private int albumId; // 音乐专辑ID

	public Mp3Info() {
		super();
	}

	/**
	 * 
	 * @param id
	 * @param title
	 * @param album
	 * @param artist
	 * @param duration
	 * @param size
	 * @param lrcTitle
	 * @param lrcSize
	 * @param url
	 */
	public Mp3Info(int id, String title, String album, String artist, int duration, int size, String lrcTitle,
			String lrcSize, String url, int albumId) {
		super();
		this.id = id;
		this.title = title;
		this.album = album;
		this.artist = artist;
		this.duration = duration;
		this.size = size;
		this.lrcTitle = lrcTitle;
		this.lrcSize = lrcSize;
		this.url = url;
		this.albumId = albumId;
	}

	@Override
	public String toString() {
		return "Mp3Info [id=" + id + ", title=" + title + ", album=" + album + ", artist=" + artist + ", duration="
				+ duration + ", size=" + size + ", url=" + url + ", lrcTitle=" + lrcTitle + ", lrcSize=" + lrcSize
				+ ", albumId=" + albumId + "]";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getLrcTitle() {
		return lrcTitle;
	}

	public void setLrcTitle(String lrcTitle) {
		this.lrcTitle = lrcTitle;
	}

	public String getLrcSize() {
		return lrcSize;
	}

	public void setLrcSize(String lrcSize) {
		this.lrcSize = lrcSize;
	}

	public int getAlbumId() {
		return albumId;
	}

	public void setAlbumId(int albumId) {
		this.albumId = albumId;
	}

}