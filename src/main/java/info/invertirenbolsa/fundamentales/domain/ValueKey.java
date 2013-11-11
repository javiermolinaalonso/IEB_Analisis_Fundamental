package info.invertirenbolsa.fundamentales.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="value_key")
public class ValueKey extends IdentifiableEntity {

	private Integer id;
	private Integer xbrlid;
	private String keyName;
	
	public ValueKey(){
		super();
	}
	
	public ValueKey(Integer xbrlid, String keyName) {
		this();
		this.xbrlid = xbrlid;
		this.keyName = keyName;
	}

	public ValueKey(Integer id, Integer xbrlid, String keyName) {
		this(xbrlid, keyName);
		this.setId(id);
	}
	
	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Column(name="xbrlid", unique=true)
	public Integer getXbrlid() {
		return xbrlid;
	}
	public void setXbrlid(Integer xbrlid) {
		this.xbrlid = xbrlid;
	}
	
	@Column(name="key_name", nullable=false, unique=true)
	public String getKeyName() {
		return keyName;
	}
	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}
	
	
}
