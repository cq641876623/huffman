package com.rorochen.study.huffmancoding;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Node> nodeList=new ArrayList<>();

        for( int  i=1 ;i<6 ;i++){
            Node<String> node=new Node();
            node.data=i+"";
            node.weight=i;
            nodeList.add(node);
        }
        HuffmanTree tree=HuffmanTree.build(nodeList);
        tree.preOrder();
        tree.inOrder();



    }
}
