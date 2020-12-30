package com.me.test;

// TODO: Auto-generated Javadoc
/**
 * The Class Test.
 *
 * @author nieming
 * @version  v1.0
 * @date 2020-7-4
 */
public class Test {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		Animal a=new Animal();
		Cat c =new Cat();
		a=c;
		Dog d=new Dog();
	//	a=d;
		if(a instanceof Dog) {
			System.out.println(123);
		}
	}

}
