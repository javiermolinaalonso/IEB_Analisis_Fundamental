package info.invertirenbolsa.fundamentales.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="company")
public class Company extends IdentifiableEntity {

	private Integer id;
	private String name;
	private String cif;
	private String ticker;
	
	public Company(){
		super();
	}
	
	public Company(String name, String cif, String ticker){
		this();
		this.name = name;
		this.cif = cif;
		this.ticker = ticker;
	}
	
	public Company(Integer id, String name, String cif, String ticker){
		this(name, cif, ticker);
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
	@Column(name="company_name", length=255, nullable=false)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name="cif", unique=true, length=10, nullable=false)
	public String getCif() {
		return cif;
	}
	public void setCif(String cif) {
		this.cif = cif;
	}
	
	@Column(name="ticker", unique=true, length=6, nullable=false)
	public String getTicker() {
		return ticker;
	}
	public void setTicker(String ticker) {
		this.ticker = ticker;
	}
	
}
