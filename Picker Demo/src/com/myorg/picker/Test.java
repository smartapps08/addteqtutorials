package com.myorg.picker;


public class Test {
	
	public static void main(String args[])
	{
		Test test=new Test();
		test.interface1.interface1();
		
	}
	
	
	
	Interface1 interface1=new Interface1() {
		public void interface1() {
			System.out.println("Test");
			
		}
	};
}
