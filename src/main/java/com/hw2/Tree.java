package com.hw2;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;
public class Tree {
    private TreeNode root;

    public Tree(TreeNode root) {
        this.root = root;
    }

    public TreeNode getRoot() {
        return root;
    }

    // Получить список всех узлов
    public List<TreeNode> getAllNodes() {
        List<TreeNode> nodes = new ArrayList<>();
        addAllChildren(nodes, root);
        return nodes;
    }

    // Рекурсивно добавляем все дочерние узлы в список
    private void addAllChildren(List<TreeNode> list, TreeNode node) {
        list.add(node);
        for (TreeNode child : node.getChildren()) {
            addAllChildren(list, child);
        }
    }

    // Получить список всех листьев
    public List<TreeNode> getLeaves() {
        return getAllNodes().stream().filter(TreeNode::isLeaf).collect(Collectors.toList());
    }
}
