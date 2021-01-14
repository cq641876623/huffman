package com.rorochen.study.huffmancoding;

public class Util {

    public static byte[] toLh(int n){
        byte[] b=new byte[4];
        b[0]=(byte)(n & 0xff);
        b[1]= (byte) (n >> 8& 0xff);
        b[2]= (byte) (n >> 16& 0xff);
        b[3]= (byte) (n >> 24& 0xff);
        return b;
    }

    public static int toInt(byte[] b){
        int res=0;
        for(int i=0;i<b.length;i++){
           res+=(b[i]& 0xff)<<(i*8);
        }
        return res;
    }


    public static byte[] concatBytes( byte[] a ,byte[] b ){
        byte[] bytes=new byte[a.length+b.length];
        for(int i=0;i<a.length;i++){
            bytes[i]=a[i];
        }
        for(int i=0;i<b.length;i++){
            bytes[i+a.length]=b[i];
        }
        return bytes;

    }


}
