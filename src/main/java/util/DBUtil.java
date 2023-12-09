package util;

import db.DatabaseConnection;
import com.hw2.Tree;
import com.hw2.TreeNode;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBUtil {

    public static List<Tree> buildTreesFromDB(DatabaseConnection dbConnection) {
        Map<Integer, TreeNode> nodeMap = new HashMap<>();
        try (Connection connection = dbConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT id, parent_id FROM TREES")) {

            while (resultSet.next()) {
                int nodeId = resultSet.getInt("id");
                int parentId = resultSet.getInt("parent_id");

                TreeNode node = nodeMap.computeIfAbsent(nodeId, TreeNode::new);
                if (nodeId != parentId) {
                    TreeNode parent = nodeMap.computeIfAbsent(parentId, TreeNode::new);
                    parent.addChild(node);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при чтении из БД", e);
        }

        return nodeMap.values().stream()
                .filter(TreeNode::isRoot)
                .map(Tree::new)
                .collect(Collectors.toList());
    }

    public static int countTotalLeaves(List<Tree> trees) {
        return trees.stream().mapToInt(tree -> tree.getLeaves().size()).sum();
    }
}
