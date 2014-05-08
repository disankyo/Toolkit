package com.disankyo.test;

public class Outer {
	class Middler {
	   class Inner {
	    int va = 100;
	    public void fun() {
	     System.out.println("This is Inner");
	    }
	   }
	}

	public static void main(String[] args) {
	   Outer out=new Outer();
	   Middler mid = out.new Middler();
	   Middler.Inner in1 =mid.new Inner();
	   System.out.println(in1.va);
	   in1.fun();
	   Middler.Inner in2 = new Outer().new Middler().new Inner();
	   in2.fun();
	}
	}