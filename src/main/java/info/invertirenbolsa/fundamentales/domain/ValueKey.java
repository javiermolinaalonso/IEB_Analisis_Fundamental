package info.invertirenbolsa.fundamentales.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

public class ValueKey {

	private Integer id;
	private Integer xbrlid;
	private String keyName;
	
	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name="xbrlid")
	public Integer getXbrlid() {
		return xbrlid;
	}
	public void setXbrlid(Integer xbrlid) {
		this.xbrlid = xbrlid;
	}
	
	@Column(name="key_name")
	public String getKeyName() {
		return keyName;
	}
	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}
	
	
}
