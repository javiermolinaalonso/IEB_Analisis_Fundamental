package info.invertirenbolsa.fundamentales.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

public class Value {

	private Integer id;
	private Balance balance;
	private ValueKey key;
	private Double value;
	
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
	public Balance getBalance() {
		return balance;
	}
	public void setBalance(Balance balance) {
		this.balance = balance;
	}
	
	@ManyToOne
	@JoinColumn(name = "key_value_id")
	public ValueKey getKey() {
		return key;
	}
	public void setKey(ValueKey key) {
		this.key = key;
	}
	
	@Column(name="value")
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
	
	
}
