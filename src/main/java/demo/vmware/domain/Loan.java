package demo.vmware.domain;

import java.io.Serializable;

public class Loan implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String casefileId;
	private String lenderLoanNumber;
	private String borrowerLastName;
	private String executionType;
	private String remittanceType;
	private String servicing;
	private String rateLockExpirationDate;
	private String noteRate;
	private String loanAmount;
	private String submittedCommitmentExpirationDate;
	private String passThrough;
	private String passThroughPrice;
	private String netSRP;
	private String passThroughPriceNetSRP;
	private String product;
	private String userId;
	
	public String getCasefileId() {
		return casefileId;
	}
	public void setCasefileId(String casefileId) {
		this.casefileId = casefileId;
	}
	public String getLenderLoanNumber() {
		return lenderLoanNumber;
	}
	public void setLenderLoanNumber(String lenderLoanNumber) {
		this.lenderLoanNumber = lenderLoanNumber;
	}
	public String getBorrowerLastName() {
		return borrowerLastName;
	}
	public void setBorrowerLastName(String borrowerLastName) {
		this.borrowerLastName = borrowerLastName;
	}
	public String getExecutionType() {
		return executionType;
	}
	public void setExecutionType(String executionType) {
		this.executionType = executionType;
	}
	public String getRemittanceType() {
		return remittanceType;
	}
	public void setRemittanceType(String remittanceType) {
		this.remittanceType = remittanceType;
	}
	public String getServicing() {
		return servicing;
	}
	public void setServicing(String servicing) {
		this.servicing = servicing;
	}
	public String getRateLockExpirationDate() {
		return rateLockExpirationDate;
	}
	public void setRateLockExpirationDate(String rateLockExpirationDate) {
		this.rateLockExpirationDate = rateLockExpirationDate;
	}
	public String getNoteRate() {
		return noteRate;
	}
	public void setNoteRate(String noteRate) {
		this.noteRate = noteRate;
	}
	public String getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(String loanAmount) {
		this.loanAmount = loanAmount;
	}
	public String getSubmittedCommitmentExpirationDate() {
		return submittedCommitmentExpirationDate;
	}
	public void setSubmittedCommitmentExpirationDate(
			String submittedCommitmentExpirationDate) {
		this.submittedCommitmentExpirationDate = submittedCommitmentExpirationDate;
	}
	public String getPassThrough() {
		return passThrough;
	}
	public void setPassThrough(String passThrough) {
		this.passThrough = passThrough;
	}
	public String getPassThroughPrice() {
		return passThroughPrice;
	}
	public void setPassThroughPrice(String passThroughPrice) {
		this.passThroughPrice = passThroughPrice;
	}
	public String getNetSRP() {
		return netSRP;
	}
	public void setNetSRP(String netSRP) {
		this.netSRP = netSRP;
	}
	public String getPassThroughPriceNetSRP() {
		return passThroughPriceNetSRP;
	}
	public void setPassThroughPriceNetSRP(String passThroughPriceNetSRP) {
		this.passThroughPriceNetSRP = passThroughPriceNetSRP;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	@Override
	public String toString() {
		return String
				.format("Loan [casefileId=%s, lenderLoanNumber=%s, borrowerLastName=%s, executionType=%s, remittanceType=%s, servicing=%s, rateLockExpirationDate=%s, noteRate=%s, loanAmount=%s, submittedCommitmentExpirationDate=%s, passThrough=%s, passThroughPrice=%s, netSRP=%s, passThroughPriceNetSRP=%s, product=%s, userId=%s]",
						casefileId, lenderLoanNumber, borrowerLastName,
						executionType, remittanceType, servicing,
						rateLockExpirationDate, noteRate, loanAmount,
						submittedCommitmentExpirationDate, passThrough,
						passThroughPrice, netSRP, passThroughPriceNetSRP,
						product, userId);
	}
}
