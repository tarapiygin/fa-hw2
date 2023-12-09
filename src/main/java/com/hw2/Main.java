package com.hw2;

import db.H2DatabaseConnection;
import util.DBUtil;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.io.InputStream;

public class Main {
    private static String h2Password;

    public static void main(String[] args) {
        Properties props = new Properties();
        try (InputStream input = Main.class.getClassLoader().getResourceAsStream("application.properties")) {
            props.load(input);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        String h2Url = props.getProperty("db.h2.url");
        String h2User = props.getProperty("db.h2.user");
        String h2Password = props.getProperty("db.h2.password");

//        String postgresUrl = props.getProperty("db.postgres.url");
//        String postgresUser = props.getProperty("db.postgres.user");
//        String postgresPassword = props.getProperty("db.postgres.password");

        // Создание объектов подключения к БД с использованием считанных параметров
        List<Tree> trees = DBUtil.buildTreesFromDB(new H2DatabaseConnection(h2Url, h2User, h2Password));
        int totalLeaves = DBUtil.countTotalLeaves(trees);
        writeOutputToFile(totalLeaves);
    }

    private static void writeOutputToFile(int totalLeaves) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("output.csv"))) {
            writer.write(String.valueOf(totalLeaves));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
