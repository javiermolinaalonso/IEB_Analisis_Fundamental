package info.invertirenbolsa.fundamentales.domain;

import info.invertirenbolsa.fundamentales.domain.enums.BalanceType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="balance")
public class Balance extends IdentifiableEntity {

	private Integer id;
	private Company company;
	private String period;
	private BalanceType balanceType;
	
	public Balance(){
		super();
	}
	
	public Balance(Company company, String period, BalanceType balanceType) {
		this();
		this.company = company;
		this.period = period;
		this.balanceType = balanceType;
	}

	public Balance(Integer id, Company company, String period, BalanceType balanceType) {
		this(company, period, balanceType);
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
	
	@ManyToOne
	@JoinColumn(name = "company_id")
	@ForeignKey(name = "balance_company_fkey")
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	
	@Column(name="period", length=8, nullable=false)
	@Pattern(regexp="[0-9]{4}(Q1|Q2|Q3|Q4|3M|6M|9M|Y)")
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	
	@Column(name="type", length=2, nullable=false)
	public BalanceType getBalanceType() {
		return balanceType;
	}
	public void setBalanceType(BalanceType balanceType) {
		this.balanceType = balanceType;
	}
	
	
}
