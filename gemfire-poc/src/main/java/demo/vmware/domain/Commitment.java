package demo.vmware.domain;

import java.io.Serializable;

public class Commitment implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -219083193245880069L;
	private String commitmentId;
	private String casefileId;
	private String status;
	private String lenderLoanNumber;
	private String borrowerLastName;
	private String servicing;
	private String executionType;
	private String remittanceType;
	private String commitmentDate;
	private String expirationDate;
	private String loanAmount;
	private String passThrough;
	private String passthroughPrice;
	private String extensionFees;
	private String pairOffFee;
	private String userId;
	private String product;
	private String coupon;
	private String outstandingBalance;
	
	public String getCommitmentId() {
		return commitmentId;
	}
	public void setCommitmentId(String commitmentId) {
		this.commitmentId = commitmentId;
	}
	public String getCasefileId() {
		return casefileId;
	}
	public void setCasefileId(String casefileId) {
		this.casefileId = casefileId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public String getCommitmentDate() {
		return commitmentDate;
	}
	public void setCommitmentDate(String commitmentDate) {
		this.commitmentDate = commitmentDate;
	}
	public String getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}
	public String getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(String loanAmount) {
		this.loanAmount = loanAmount;
	}
	
	public String getPassthroughPrice() {
		return passthroughPrice;
	}
	public void setPassthroughPrice(String passthroughPrice) {
		this.passthroughPrice = passthroughPrice;
	}
	public String getExtensionFees() {
		return extensionFees;
	}
	public void setExtensionFees(String extensionFees) {
		this.extensionFees = extensionFees;
	}
	public String getPairOffFee() {
		return pairOffFee;
	}
	public void setPairOffFee(String pairOffFee) {
		this.pairOffFee = pairOffFee;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getLenderLoanNumber() {
		return lenderLoanNumber;
	}
	public void setLenderLoanNumber(String lenderLoanNumber) {
		this.lenderLoanNumber = lenderLoanNumber;
	}
	public String getServicing() {
		return servicing;
	}
	public void setServicing(String servicing) {
		this.servicing = servicing;
	}
	
	public String getPassThrough() {
		return passThrough;
	}
	public void setPassThrough(String passThrough) {
		this.passThrough = passThrough;
	}
	public String getCoupon() {
		return coupon;
	}
	public void setCoupon(String coupon) {
		this.coupon = coupon;
	}
	public String getOutstandingBalance() {
		return outstandingBalance;
	}
	public void setOutstandingBalance(String outstandingBalance) {
		this.outstandingBalance = outstandingBalance;
	}
	
	@Override
	public String toString() {
		return String
				.format("Commitment [commitmentId=%s, casefileId=%s, status=%s, lenderLoanNumber=%s, borrowerLastName=%s, servicing=%s, executionType=%s, remittanceType=%s, commitmentDate=%s, expirationDate=%s, loanAmount=%s, passThrough=%s, passthroughPrice=%s, extensionFees=%s, pairOffFee=%s, userId=%s, product=%s, coupon=%s, outstandingBalance=%s]",
						commitmentId, casefileId, status, lenderLoanNumber,
						borrowerLastName, servicing, executionType,
						remittanceType, commitmentDate, expirationDate,
						loanAmount, passThrough, passthroughPrice,
						extensionFees, pairOffFee, userId, product, coupon,
						outstandingBalance);
	}
	
}
