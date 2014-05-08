package com.disankyo.buffer;

import java.io.UnsupportedEncodingException;
import java.nio.Buffer;
import java.nio.ByteBuffer;

/**
 * 动态扩容的bytebuffer类实现
 * @author xujianxin
 * @time Oct 22, 2013 9:33:40 AM
 */
public class DynamicByteBuffer1{

	static final int MIN_CAPATICY = 16;
	
	static final int MAX_CAPACITY = 30 << 1;
	
	static final int LOOT_TIMES = 20;
	
	private ByteBuffer byteBuffer;
	
	public DynamicByteBuffer1(){
	}
	
	/**
	 * 重新分配一个容量为capacity的直接字节缓冲区
	 * @param capacity
	 * @return
	 */
	public void allocateDirect(int capacity){
		if(capacity < MIN_CAPATICY){
			capacity = MIN_CAPATICY;
		}
		byteBuffer = ByteBuffer.allocateDirect(capacity);
	}
	
	/**
	 * 重新分配一个容量为capacity的bytebuffer
	 * @param capacity 容量
	 * @return
	 */
	public void allocate(int capacity){
		byteBuffer = ByteBuffer.allocate(capacity);
	}
	
	/**
	 * 把一个字节数组包装到缓冲区
	 * @param array 字节数组
	 * @param offset 偏移量
	 * @param length 长度
	 * @return
	 */
	public void wrap(byte[] array, int offset, int length){
		byteBuffer = ByteBuffer.wrap(array, offset, length);
	}
	
	public byte get(){
		return byteBuffer.get();
	}
	
	public int getInt(){
		return byteBuffer.getInt();
	}
	
	public char getChar(){
		return byteBuffer.getChar();
	}
	
	public short getShort(){
		return byteBuffer.getShort();
	}
	
	public double getDouble(){
		return byteBuffer.getDouble();
	}
	
	public float getFloat(){
		return byteBuffer.getFloat();
	}
	
	public long getLong(){
		return byteBuffer.getLong();
	}
	
	public boolean getBoolean(){
		byte b = get();
		boolean flag = b == 1 ? true : false;
		
		return flag; 
	}
	
	public ByteBuffer put(byte b){
		int remain = remaining();
		
		if(remain < 1){
			expendCapacity();
		}
		
		byteBuffer.put(b);
		
		return byteBuffer;
	}
	
	public ByteBuffer putChar(char value){
		int remain = remaining();
		
		if(remain < 1){
			expendCapacity();
		}
		
		byteBuffer.putChar(value);
		
		return byteBuffer;
	}
	
	public ByteBuffer putShort(short value){
		int remain = remaining();
		if(remain < 2){
			expendCapacity();
		}
		
		byteBuffer.putShort(value);
		
		return byteBuffer;
	}
	
	public ByteBuffer putInt(int value){
		int remain = remaining();
		if(remain < 4){
			expendCapacity();
		}
		
		byteBuffer.putInt(value);
		
		return byteBuffer;
	}
	
	public ByteBuffer putFloat(float value){
		int remain = remaining();
		if(remain < 4){
			expendCapacity();
		}
		
		byteBuffer.putFloat(value);
		
		return byteBuffer;
	}
	
	public ByteBuffer putLong(long value){
		int remain = remaining();
		if(remain < 8){
			expendCapacity();
		}
		
		byteBuffer.putLong(value);
		
		return byteBuffer;
	}
	
	public ByteBuffer putDouble(double value){
		int remain = remaining();
		if(remain < 8){
			expendCapacity();
		}
		
		byteBuffer.putDouble(value);
		
		return byteBuffer;
	}
	
	public final Buffer flip(){
		return byteBuffer.flip();
	}
	
	public final int position(){
		return byteBuffer.position();
	}
	
	public final int limit(){
		return byteBuffer.limit();
	}
	
	public final int capacity(){
		return byteBuffer.capacity();
	}
	
	public final int remaining(){
		return byteBuffer.remaining();
	}
	
	public ByteBuffer put(byte[] src){
		int remain = remaining();
		int srcLength = src.length;
		
		if(remain < srcLength){
			
			int capacity = capacity();
			int position = position();
			
			for(int i = 0; i < LOOT_TIMES; i++){
				if(capacity - position < srcLength){
					capacity = capacity << 1;
				} else {
					break;
				}
			}
			
			flip();
			
			byte[] data = new byte[limit()];
			byteBuffer.get(data);
			byteBuffer = ByteBuffer.allocate(capacity);
			byteBuffer.put(data);
		}
		
		byteBuffer.put(src);
		
		return byteBuffer;
	}
	
	public ByteBuffer get(byte[] dst){
		return byteBuffer.get(dst);
	}
	
	public void putString(String src){
		byte[] bb = null;
		
		try {
			bb = src.getBytes("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		short len = (short)bb.length;
		putShort(len);
		put(bb);
	}
	
	public String getString(ByteBuffer byteBuffer){
		
		short len = byteBuffer.getShort();
		byte[] bb = new byte[len];
		byteBuffer.get(bb);
		
		String src = null;
		try {
			src = new String(bb, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return src;
	}
	
	public byte[] getBytes(){
		
		flip();
		byte[] bb = new byte[limit()];
		get(bb);
		
		return bb;
	}
	
	private void expendCapacity(){
		flip();
		
		byte[] data = new byte[limit()];
		byteBuffer.get(data);
		byteBuffer = ByteBuffer.allocate(capacity() << 1);
		byteBuffer.put(data);
	}
	
}
	