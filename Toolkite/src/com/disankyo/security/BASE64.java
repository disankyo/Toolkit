package com.disankyo.security;


import sun.misc.BASE64Decoder;     
import sun.misc.BASE64Encoder;     
    

public class BASE64 {

	/**    
     * BASE64解密   
   * @param key          
     * @return          
     * @throws Exception          
     */              
    public static byte[] decryptBASE64(String key) throws Exception {               
        return (new BASE64Decoder()).decodeBuffer(key);               
    }               
                  
    /**         
     * BASE64加密   
   * @param key          
     * @return          
     * @throws Exception          
     */              
    public static String encryptBASE64(byte[] key) throws Exception {     
        return (new BASE64Encoder()).encodeBuffer(key);               
    }
         
    public static void main(String[] args) throws Exception     
    {     
        String data = BASE64.encryptBASE64("http://aub.iteye.com/".getBytes());     
        System.out.println("加密前："+data);     
             
        byte[] byteArray = BASE64.decryptBASE64(data);     
        System.out.println("解密后："+new String(byteArray));    
        
        System.out.println(encryptBASE64("11111".getBytes()));
        System.out.println(new String(decryptBASE64("MTExMTE=")));
    }     

}
