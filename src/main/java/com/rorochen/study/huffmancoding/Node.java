package com.rorochen.study.huffmancoding;

import java.util.Map;

public class Node<T> {

    public Node left;
    public Node right;
    public int weight;
    public T data;


    /**
     * 前序遍历
     */
    public void preOrder(){
        System.out.print(this.data+"\t");
        if( null != left )this.left.preOrder();
        if( null != right )this.right.preOrder();

    }

    /**
     * 中序遍历
     */
    public void inOrder() {
        if( null != left )this.left.inOrder();
        System.out.print(this.data+"\t");
        if( null != right )this.right.inOrder();
    }

    /**
     * 后序遍历
     */
    public void postOrder() {
        if( null != left )this.left.postOrder();
        if( null != right )this.right.postOrder();
        System.out.print(this.data+"\t");
    }


    /**
     * 插入节点
     * @param node 待插入节点
     */
    public void insert(Node node){
        if(node.weight < this.weight){
            if( null == this.left ) this.left=node;
            else this.left.insert(node);
        }else {
            if( null == this.right ) this.right=node;
            else this.right.insert(node);
        }
    }

    public void getCodes(Map map,String append,StringBuilder stringBuilder){
        StringBuilder s=new StringBuilder(stringBuilder.toString());
        s.append(append);
        if(this.data!=null){
           map.put(data,s.toString());
        }
        if(this.left!=null)this.left.getCodes(map,"0",s);
        if(this.right!=null)this.right.getCodes(map,"1",s);
    }


}
