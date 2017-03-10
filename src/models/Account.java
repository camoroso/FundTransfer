package models;

public class Account {
	 	
	 	private String cardNumber;
	 	private String expirationMonth;
	 	private String expirationYear;
	 	private String CVV;
	 	private float remainBalance;
	 	private Consumer relatedConsumer;
	 	
	 	public Account(String cardNumber, String cVV, String expirationMonth, String expirationYear, float remainBalance,
	 			Consumer relatedConsumer) {
	 		this.cardNumber = cardNumber;
	 		this.CVV = cVV;
	 		this.expirationMonth = expirationMonth;
	 		this.expirationYear = expirationYear;
	 		this.remainBalance = remainBalance;
	 		this.relatedConsumer = relatedConsumer;
	 	}
	 	
	 	public Account() {
	 	}
	 	
	 	public String getCardNumber() {
	 		return cardNumber;
	 	}
	 	
	 	public void setCardNumber(String cardNumber) {
	 		this.cardNumber = cardNumber;
	 	}
	 	
	 	public String getCVV() {
	 		return CVV;
	 	}
	 	
	 	public void setCVV(String cVV) {
	 		CVV = cVV;
	 	}
	 	
	 	public String getExpirationMonth() {
	 		return expirationMonth;
	 	}
	 	
	 	public void setExpirationMonth(String expirationMonth) {
	 		this.expirationMonth = expirationMonth;
	 	}
	 	
	 	public String getExpirationYear() {
	 		return expirationYear;
	 	}
	 	
	 	public void setExpirationYear(String expirationYear) {
	 		this.expirationYear = expirationYear;
	 	}
	 	
	 	public float getRemainBalance() {
	 		return remainBalance;
	 	}
	 	
	 	public void setRemainBalance(float remainBalance) {
	 		this.remainBalance = remainBalance;
	 	}
	 	
	 	public Consumer getRelatedConsumer() {
	 		return relatedConsumer;
	 	}
	 	
	 	public void setRelatedConsumer(Consumer relatedConsumer) {
	 		this.relatedConsumer = relatedConsumer;
	 	}
	 
	 }