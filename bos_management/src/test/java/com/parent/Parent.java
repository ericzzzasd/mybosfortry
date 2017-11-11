package com.parent;


public class Parent {

	public Parent() {
		Class<? extends Parent> class1 = this.getClass();
		try {
			System.out.println(class1.getField("a").toGenericString() );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
	
	

}


