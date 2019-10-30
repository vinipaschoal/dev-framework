package org.esfinge.virtuallab.inpe;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the station database table.
 * 
 */
@Entity
@Table(name="station", schema = "imager")
public class StationM {

	@Id
	private Long id;

	private float altitude;

	private String code;

	@Column(name="last_generation_date_acq")
	private Timestamp lastGenerationDateAcq;

	private float latitude;

	private float longitude;

	@Column(name="name_station")
	private String nameStation;

	public StationM() {
	}
	
	

	public StationM(Long id, float altitude, String code, Timestamp lastGenerationDateAcq, float latitude,
			float longitude, String nameStation) {
		super();
		this.id = id;
		this.altitude = altitude;
		this.code = code;
		this.lastGenerationDateAcq = lastGenerationDateAcq;
		this.latitude = latitude;
		this.longitude = longitude;
		this.nameStation = nameStation;
	}



	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public float getAltitude() {
		return this.altitude;
	}

	public void setAltitude(float altitude) {
		this.altitude = altitude;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Timestamp getLastGenerationDateAcq() {
		return this.lastGenerationDateAcq;
	}

	public void setLastGenerationDateAcq(Timestamp lastGenerationDateAcq) {
		this.lastGenerationDateAcq = lastGenerationDateAcq;
	}

	public float getLatitude() {
		return this.latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public float getLongitude() {
		return this.longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public String getNameStation() {
		return this.nameStation;
	}

	public void setNameStation(String nameStation) {
		this.nameStation = nameStation;
	}

}