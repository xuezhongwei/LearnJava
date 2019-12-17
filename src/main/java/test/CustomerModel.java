package test;

public class CustomerModel extends PersonModel {
	/**
	 * 主键
	 */
	private long id;
	
	/**
	 * 联系人
	 */
	private String contract;
	/**
	 * 电话
	 */
	private String telephone;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 备注
	 */
	private String remark;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getContract() {
		return contract;
	}
	public void setContract(String contract) {
		this.contract = contract;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Override
	public String toString() {
		return super.toString() + "CustomerModel [id=" + id + ", contract=" + contract + ", telephone=" + telephone + ", email=" + email
				+ ", remark=" + remark + "]";
	}
	
}
