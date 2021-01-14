package com.rorochen.study.huffmancoding;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class FileTest {


    public static byte[] unZip(byte[] rawZipData,HuffmanTree<Byte> tree){
        byte[] zlengthBytes=new byte[4];
        byte[] zData=new byte[rawZipData.length-4];
        Node<Byte> node=tree.root;
        System.arraycopy(rawZipData,0,zlengthBytes,0,zlengthBytes.length);
        System.arraycopy(rawZipData,4,zData,0,zData.length);
        StringBuilder stringBuilder=new StringBuilder();
        int zlength=Util.toInt(zlengthBytes);
        List<Byte> reuslt=new ArrayList<>();
        for(int i=0;(i<zData.length);i++){
            for(int j=7;j>=0&&zlength>0;j--){
                int temp=(zData[i]>>j)&0x01;
                stringBuilder.append(temp);
                if(temp==0){
                    node=node.left;
                }else {
                    node=node.right;
                }
                if(node.left==null&&node.right==null){
                    reuslt.add(node.data);
                    node= tree.root;
                }
                zlength--;
            }
        }
        System.out.println("压缩后的位数据："+stringBuilder);
        System.out.println("当前转换成原文件字节码：");
        for(Byte a: reuslt){
            System.out.print(a+"\t");
        }
        System.out.println();
        byte[] bytesResult=new byte[reuslt.size()];
        for(int i=0;i<reuslt.size();i++){
            bytesResult[i]=reuslt.get(i);
        }


        return bytesResult;
    }


    public static byte[] zip(byte[] doc,HuffmanTree tree){
        byte[] reuslt = null;
        Map<Byte ,String > codes =tree.getCodes();
        StringBuilder resultString=new StringBuilder();
        for(int i=0;i<doc.length;i++){
            String temp=codes.get(doc[i]);
            resultString.append(temp);
        }
        System.out.println("当前哈夫曼编码文档长度: "+resultString.length() +"(位)");
        System.out.println("压缩后的编码: "+resultString.toString());

//        是位的长度
        int ziplength=resultString.length();
        byte[] zLength=Util.toLh(ziplength);



        int complement=8-resultString.length()%8;
        for(int i=0;i<complement;i++){
            resultString.append("0");
        }
        reuslt=new byte[resultString.length()/8];
        for(int i=0,j=0;i<resultString.length();i+=8){
            String temp=resultString.substring(i,i+8);

            System.out.println("转换前：\t"+temp+"\t转换后：\t"+(byte)Integer.parseInt(temp,2));
            reuslt[j++]=(byte)Integer.parseInt(temp,2);

        }


        reuslt=Util.concatBytes(zLength,reuslt);

        System.out.println(Arrays.toString(reuslt));
        return reuslt;

    }

    public static void main(String[] args) throws IOException {

        List<Node> nodeList=new ArrayList<>();

        Map<Byte , Integer> count=new HashMap<>();

        File file=new File("/Users/chanroro/IdeaProjects/huffman/src/main/resources/testdoc.txt");
        System.out.println(file.getPath());
        FileInputStream in=new FileInputStream(file);
        System.out.println("文件大小：\t"+file.length() +"\t文件实际大小：\t"+in.available());
        byte[] buffer=new byte[1024];
        int total=0;
        int len=0;
        while ( (len=in.read(buffer))>0 ){
            total+=len;
            double perc=((double) total/file.length())*100;
            System.out.printf("读取文件进度 ：%.2f \n",perc);
            System.out.println("原始文档字节");
            for(int  i = 0 ; i<len ;i++){
                byte b=buffer[i];
                System.out.print(b+"\t");
                if(count.containsKey(b)){
                    count.replace(b,count.get(b)+1);
                }else {
                    count.put(b,1);
                }
            }
        }
        in.close();
        System.out.println();

        System.out.println("====文档进行统计====");
        for(Byte key :count.keySet()){
            System.out.println( "key:\t"+key+"\t( "+new String(new byte[]{key})+" )"+"\tmap:\t"+count.get(key));
        }

        System.out.println();

        System.out.println("====获取文档哈夫曼编码====");
        for(Byte key :count.keySet()){
            Node<Byte> node =new Node<>();
            node.weight=count.get(key);
            node.data=key;
            nodeList.add(node);
        }

        HuffmanTree tree=HuffmanTree.build(nodeList);
        Map<Byte ,String> map=tree.getCodes();
        for(Byte key :map.keySet()){
            System.out.println( "key:\t"+key+"\tmap:\t"+map.get(key));
        }

        FileInputStream in2=new FileInputStream(file);
        byte[] bytes=new byte[in2.available()];
        in2.read(bytes);
        in2.close();

        byte[] zipResult=zip(bytes,tree);

        Date now=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//        File zipFile=new File("/Users/chanroro/IdeaProjects/huffman/src/main/resources/zipfile"+sdf.format(now)+".cqhfm");
        File zipFile=new File("/Users/chanroro/IdeaProjects/huffman/src/main/resources/zipfile.cqhfm");
        if(zipFile.exists()){
            zipFile.delete();
        }
        FileOutputStream out=new FileOutputStream(zipFile);
        out.write(zipResult);
        out.flush();
        out.close();


        File zipFile1=new File("/Users/chanroro/IdeaProjects/huffman/src/main/resources/zipfile.cqhfm");
        FileInputStream zipIn=new FileInputStream(zipFile1);
        byte[] rawZipData=new byte[zipIn.available()];
        zipIn.read(rawZipData);
        zipIn.close();

        byte[] data=unZip(rawZipData,tree);


        File unZipFile=new File("/Users/chanroro/IdeaProjects/huffman/src/main/resources/testdoc-upzip.txt");

        if(unZipFile.exists()){
            unZipFile.delete();
        }

        FileOutputStream unZipOut=new FileOutputStream(unZipFile);
        unZipOut.write(data);
        unZipOut.flush();
        unZipOut.close();





    }
}
