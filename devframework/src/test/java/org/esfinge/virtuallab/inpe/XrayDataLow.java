package org.esfinge.virtuallab.inpe;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the xray_data_low database table.
 * 
 */
@Entity
@Table(name="xray_data_low", schema = "goes")
public class XrayDataLow{

	@Id
	private Long id;

	@Column(name="event_datetime")
	private Calendar eventDateTime;

	@Column(name="long_xray")
	private double longXray;

	private double  ratio;

	@Column(name="short_xray")
	private double  shortXray;

	@Column(name="header_fk")
	private Long header;

	public XrayDataLow() {
	}
	
	

	public XrayDataLow(Long id, Calendar eventDatetime, double longXray, double ratio, double shortXray, long header) {
		super();
		this.id = id;
		this.eventDateTime = eventDatetime;
		this.longXray = longXray;
		this.ratio = ratio;
		this.shortXray = shortXray;
		this.header = header;
	}



	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Calendar getEventDateTime() {
		return this.eventDateTime;
	}

	public void setEventDateTime(Calendar eventDatetime) {
		this.eventDateTime = eventDatetime;
	}

	public double getLongXray() {
		return this.longXray;
	}

	public void setLongXray(float longXray) {
		this.longXray = longXray;
	}

	public double getRatio() {
		return this.ratio;
	}

	public void setRatio(float ratio) {
		this.ratio = ratio;
	}

	public double getShortXray() {
		return this.shortXray;
	}

	public void setShortXray(float shortXray) {
		this.shortXray = shortXray;
	}

	public Long getHeader() {
		return this.header;
	}

	public void setHeader(Long header) {
		this.header = header;
	}



	@Override
	public String toString() {
		return "XrayDataLow [id=" + id + ", eventDateTime=" + eventDateTime + ", longXray=" + longXray + ", ratio="
				+ ratio + ", shortXray=" + shortXray + ", header=" + header + "]";
	}
	
	

}