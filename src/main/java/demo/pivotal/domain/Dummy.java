package demo.pivotal.domain;

import java.io.Serializable;

public class Dummy implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String field1;
	private int field2;
	private String field3;
	
	public Dummy(){
		
	}

	public Dummy(String field1, int field2) {
		super();
		this.field1 = field1;
		this.field2 = field2;
	}

	public String getField1() {
		return field1;
	}

	public void setField1(String field1) {
		this.field1 = field1;
	}

	public int getField2() {
		return field2;
	}

	public void setField2(int field2) {
		this.field2 = field2;
	}

	public String getField3() {
		return field3;
	}

	public void setField3(String field3) {
		this.field3 = field3;
	}

	@Override
	public String toString() {
		return "Dummy [field1=" + field1 + ", field2=" + field2 + ", field3="
				+ field3 + "]";
	}

}
