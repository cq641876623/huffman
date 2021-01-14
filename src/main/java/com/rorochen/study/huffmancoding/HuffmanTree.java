package com.rorochen.study.huffmancoding;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HuffmanTree<T> {

    public Node<T> root;

    /**
     * 二叉树插入节点（哈夫曼树不支持插入节点）
     * @param node 节点
     */
    private void insert(Node node){
        if( null == root){
            root=node;
        }else {
            if(node.weight < root.weight){
                if( null == root.left ) root.left=node;
                else root.left.insert(node);
            }else {
                if( null == root.right ) root.right=node;
                else root.right.insert(node);
            }
        }

    }

    /**
     * 前序遍历
     */
    public void preOrder(){
        System.out.print("前序遍历：\t");
        if( null != root ){
            root.preOrder();
        }else {
            System.out.println("该树为空");
        }
        System.out.println();

    }

    /**
     * 中序遍历
     */
    public void inOrder(){
        System.out.print("中序遍历：\t");
        if( null != root ){
            root.inOrder();
        }else {
            System.out.println("该树为空");
        }
        System.out.println();
    }

    /**
     * 后序遍历
     */
    public void postOrder(){
        System.out.print("后序遍历：\t");
        if( null != root ){
            root.postOrder();
        }else {
            System.out.println("该树为空");
        }
        System.out.println();
    }

    /**
     * 构造哈夫曼树
     * @param nodeList 节点集合
     * @return 构造完成的哈夫曼树
     */
    public static HuffmanTree build(List<Node> nodeList){
        HuffmanTree tree = null;
      if( null == nodeList || 0 == nodeList.size() ){
          System.out.println("传入值为空");
      }else {
          Node min1,min2; //每次选取最小节点和次小节点进行合并
          while (nodeList.size()!=1){
              min1=null;
              min2=null;
              for (Node node : nodeList){
//                  如果最小节点为null，则当前节点为最小节点
                  if( null == min1 ){
                      min1=node;
                      continue;
                  }
//                  当次小节点为null时
                  if( null == min2 ){
//                      判断当前节点是否是最小节点
                      if( node.weight < min1.weight ){
//                          是最小节点则最小节点与次小节点换位置，并将该节点设为最小节点
                          min2=min1;
                          min1=node;
                          continue;
                      }else {
//                          如果不是最小节点则当前节点为次小节点
                          min2 = node;
                      }
                  }
                  if( node.weight < min1.weight ){
                      min2=min1;
                      min1=node;
                  }
//                  当前节点小于次小节点并且该节点不是最小节点
                  if( node .weight < min2.weight && min1!=node){
                        min2=node;
                  }

              }
              Node node=new Node();
              node.weight=min1.weight+ min2.weight;
              node.left=min1;
              node.right=min2;
              nodeList.add(node);
              nodeList.remove(min1);
              nodeList.remove(min2);
          }
          tree=new HuffmanTree();
          tree.root=nodeList.get(0);

      }
      return tree;
    }

    public Map<T,String>  getCodes(){
        Map<T,String> map=new HashMap<>();
        if( null !=root){
            root.getCodes(map,"",new StringBuilder());
        }
        return map;



    }

}
