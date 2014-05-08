package com.disankyo.test;

/**
 * 
 * @author xujianxin
 * @time Aug 9, 2013 1:17:29 PM
 */
public class ShiftOperate {

	/**
	 * overview：
	 * 
	 * @Title: main
	 * @param args
	 *            void
	 * @author linfenliang
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// /**
		// * byte a = 27;// 转换成int为 00000000000000000000000000011011 byte b =
		// * -1;转换成int为 11111111111111111111111111111111
		// *
		// * int g = a >> 1;// 有符号右移1位，左侧缺的位以符号位补齐，正数就是0，
		// * "00000000000000000000000000001101" = 13 int f = b>> 1; //
		// * 有符号右移1位，左侧缺的位以符号位补齐，负数就是1， “11111111111111111111111111111111”= -1
		// *
		// * 故此时打印出来，g=13，gf=-1。
		// *
		// *
		// *
		// * g = a >>> 1;// 无符号右移1位，左侧缺的位以0补齐，
		// "00000000000000000000000000001101"
		// * = 13 f = b>>> 1; // 无符号右移1位，左侧缺的位以0补齐，
		// * “01111111111111111111111111111111”= 2147483647
		// *
		// * 故此时打印出来，g=13，gf=2147483647。
		// *
		// * f = b<< 1; // 无符号左移1位，右侧缺的位以0补齐，
		// “10000000000000000000000000000010“=
		// * -2 故此时打印出来，f= -2
		// */
		 int a = 27,b=-1;
		// System.out.println(Integer.toBinaryString(a));
		// System.out.println(Integer.toBinaryString(b));
		// System.out.println("------------");
		// System.out.println(a);
		 System.out.println(a + ">>1(带符号右移一位)：" + (a>>1));
		 System.out.println(a + ">>>1(无符号右移一位)：" + (a>>>1));
		// System.out.println(a+"<<1(左移一位)："+(a<<1));
		// System.out.println("------------");
		// System.out.println(b);
		 System.out.println(b+">>1(带符号右移一位)："+(b>>1));
		 System.out.println(b+">>>1(无符号右移一位)："+(b>>>1));
		 System.out.println(b+"<<1(左移一位)："+(b<<1));
		int x = 42;
		String strX = Integer.toBinaryString(x);
		System.out.println("对" + strX + "按位非NOT~==>" + Integer.toBinaryString(~x));
		int y = 15;
		String strY = Integer.toBinaryString(y);
		System.out.println("对" + strX + "和" + strY + "按位与AND&==>" + Integer.toBinaryString(x & y));
		System.out.println("对" + strX + "和" + strY + "按位或OR|==>" + Integer.toBinaryString(x | y));
		System.out.println("对" + strX + "和" + strY + "按位异或XOR^==>" + Integer.toBinaryString(x ^ y));
//		byte b1 = 64;
		System.out.println(b << 2);
		System.out.println((byte) (b << 2));
		char hex[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a',
				'b', 'c', 'd', 'e', 'f' };
		byte b2 = (byte) 0xf1;
		System.out.println(
				Integer.toBinaryString(0x0f) + ":" 
				+ Integer.toBinaryString(0x0f1) + ":"
				+ (Integer.toBinaryString(0xf1 & 0x0f)));
		
		System.out.println("b = 0x" + hex[(b2 >> 4) & 0xf] + hex[b2 & 0x0f]);
		
		System.out.println(Integer.toBinaryString(-2) + "---"
				+ Integer.toBinaryString(-2 >>> 28));
		
		System.out.println(0xFF);
	}

}
