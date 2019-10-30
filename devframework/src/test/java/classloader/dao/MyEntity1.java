package classloader.dao;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/*
@Entity
@Table(name="temperatura")
public class MyEntity
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String latitude;
	private String longitude;
	private String local;
	private double maxima;
	private double minima;
	private String mes;
	
	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getLatitude()
	{
		return latitude;
	}

	public void setLatitude(String latitude)
	{
		this.latitude = latitude;
	}

	public String getLongitude()
	{
		return longitude;
	}

	public void setLongitude(String longitude)
	{
		this.longitude = longitude;
	}
	
	public String getLocal()
	{
		return local;
	}
	
	public void setLocal(String local)
	{
		this.local = local;
	}

	public double getMaxima()
	{
		return maxima;
	}

	public void setMaxima(double maxima)
	{
		this.maxima = maxima;
	}

	public double getMinima()
	{
		return minima;
	}

	public void setMinima(double minima)
	{
		this.minima = minima;
	}

	public String getMes()
	{
		return mes;
	}

	public void setMes(String mes)
	{
		this.mes = mes;
	}
}
*/

@Entity
@Table(name="station")
public class MyEntity1 {

	@Id
	private Long id;

	private float altitude;

	private String code;

	private float latitude;

	private float longitude;

	@Column(name="name_station")
	private String nameStation;

	public MyEntity1() {
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