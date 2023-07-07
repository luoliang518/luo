package com.luo.utils.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class GitBranchCreation {
    private static String gitDirectory = "E:\\IdeaProject\\esign_6.0\\"; // Git 存储库的绝对路径
    private static List<Project> projects;
    private static String tagName = "-6.0.6.0-release"; // 要基于的标签名称
    private static String newBranchName = "feature/6.0.6.0_gjyl"; // 新分支的名称
    public static void main(String[] args) {
        try {
            for (Project project : projects) {
                // 执行 Git 命令行命令
                String[] command = {"git", "-C", gitDirectory+project.getProjectName(),  "checkout", "-b",newBranchName, project.getTagName()};
                Process process = Runtime.getRuntime().exec(command);
                // 读取命令行输出
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
                // 等待命令执行完成
                int exitCode = process.waitFor();
                System.out.println("Command executed with exit code: " + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static class Project {
        private String projectName;
        private String tagName;

        public Project(String projectName, String tagName) {
            this.projectName = projectName;
            this.tagName = tagName;
        }

        public String getProjectName() {
            return projectName;
        }

        public String getTagName() {
            return tagName;
        }
    }

    static {
        // 添加项目信息到列表
        projects.add(new Project("esign-docs", "docs"+tagName));
        projects.add(new Project("esign-signs", "signs"+tagName));
        projects.add(new Project("esign-manage", "manage"+tagName));
        projects.add(new Project("esign-seals", "seals"+tagName));
        projects.add(new Project("esign-unified-login", "login"+tagName));
        projects.add(new Project("esign-unified-portal", "portal"+tagName));
        projects.add(new Project("esign-evidence", "evidence"+tagName));
        projects.add(new Project("esign-file-system", "system"+tagName));
        projects.add(new Project("esign-manage-base", "base"+tagName));
        projects.add(new Project("esign-manage-tools", tagName));
        projects.add(new Project("esign-manage-wf", "wf"+tagName));
        projects.add(new Project("esign-open-gateway", "gateway"+tagName));
    }
}
