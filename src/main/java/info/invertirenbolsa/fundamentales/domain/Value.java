package info.invertirenbolsa.fundamentales.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="value")
public class Value extends IdentifiableEntity {

	private Integer id;
	private Balance balance;
	private ValueKey key;
	private Double value;
	
	public Value(){
		
	}
	
	public Value(Balance balance, ValueKey key, Double value) {
		super();
		this.balance = balance;
		this.key = key;
		this.value = value;
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
	@JoinColumn(name = "balance_id")
	@ForeignKey(name = "value_balance_fkey")
	public Balance getBalance() {
		return balance;
	}
	public void setBalance(Balance balance) {
		this.balance = balance;
	}
	
	@ManyToOne
	@JoinColumn(name = "key_value_id")
	@ForeignKey(name = "value_valuekey_fkey")
	public ValueKey getKey() {
		return key;
	}
	public void setKey(ValueKey key) {
		this.key = key;
	}
	
	@Column(name="value", nullable=false)
	@Min(1)
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
	
	
}
