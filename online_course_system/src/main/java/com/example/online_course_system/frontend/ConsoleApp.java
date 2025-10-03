package com.example.online_course_system.frontend;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Scanner;

public class ConsoleApp {
    private static final String BASE = "http://localhost:8080";
    private static String token = null;

    public static void main(String[] args) throws Exception {
        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .build();

        Scanner sc = new Scanner(System.in);
        boolean running = true;

        System.out.println("=== Online Course System CLI ===");

        while (running) {
            System.out.println("\n1) Register");
            System.out.println("2) Login");
            System.out.println("3) Browse Courses");
            System.out.println("4) Enroll in Course");
            System.out.println("5) Update Progress");
            System.out.println("6) My Enrollments");
            System.out.println("7) Exit");
            System.out.print("Choose: ");

            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1 -> register(client, sc);
                case 2 -> login(client, sc);
                case 3 -> browseCourses(client);
                case 4 -> enroll(client, sc);
                case 5 -> updateProgress(client, sc);
                case 6 -> viewEnrollments(client);
                case 7 -> running = false;
                default -> System.out.println("Invalid choice");
            }
        }
        sc.close();
    }

    // ---------- Helper HTTP Methods ----------
    private static String post(HttpClient client, String path, String body, boolean auth) throws Exception {
        HttpRequest.Builder builder = HttpRequest.newBuilder(URI.create(BASE + path))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body, StandardCharsets.UTF_8));
        if (auth && token != null) builder.header("Authorization", "Bearer " + token);
        HttpResponse<String> resp = client.send(builder.build(), HttpResponse.BodyHandlers.ofString());
        System.out.println(resp.statusCode() + " -> " + resp.body());
        return resp.body();
    }

    private static void get(HttpClient client, String path) throws Exception {
        HttpRequest.Builder builder = HttpRequest.newBuilder(URI.create(BASE + path)).GET();
        if (token != null) builder.header("Authorization", "Bearer " + token);
        HttpResponse<String> resp = client.send(builder.build(), HttpResponse.BodyHandlers.ofString());
        System.out.println(resp.statusCode() + " -> " + resp.body());
    }

    private static void put(HttpClient client, String path, String body, boolean auth) throws Exception {
        HttpRequest.Builder builder = HttpRequest.newBuilder(URI.create(BASE + path))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(body, StandardCharsets.UTF_8));
        if (auth && token != null) builder.header("Authorization", "Bearer " + token);
        HttpResponse<String> resp = client.send(builder.build(), HttpResponse.BodyHandlers.ofString());
        System.out.println(resp.statusCode() + " -> " + resp.body());
    }

    // ---------- Menu Actions ----------
    private static void register(HttpClient client, Scanner sc) throws Exception {
        System.out.print("Username: "); String u = sc.nextLine();
        System.out.print("Password: "); String p = sc.nextLine();
        System.out.print("Role (ADMIN/INSTRUCTOR/STUDENT): "); String r = sc.nextLine();
        String json = String.format("{\"username\":\"%s\",\"password\":\"%s\",\"role\":\"%s\"}", u, p, r);
        post(client, "/auth/register", json, false);
    }

    private static void login(HttpClient client, Scanner sc) throws Exception {
        System.out.print("Username: "); String u = sc.nextLine();
        System.out.print("Password: "); String p = sc.nextLine();
        String json = String.format("{\"username\":\"%s\",\"password\":\"%s\",\"role\":\"STUDENT\"}", u, p);
        String res = post(client, "/auth/login", json, false);
        // crude regex extraction of token
        token = res.replaceAll(".*\"token\"\\s*:\\s*\"([^\"]+)\".*", "$1");
        System.out.println("Logged in. Token stored.");
    }

    private static void browseCourses(HttpClient client) throws Exception {
        get(client, "/student/courses");
    }

    private static void enroll(HttpClient client, Scanner sc) throws Exception {
        System.out.print("Course ID: "); String id = sc.nextLine();
        post(client, "/student/enroll/" + id, "{}", true);
    }

    private static void updateProgress(HttpClient client, Scanner sc) throws Exception {
        System.out.print("Enrollment ID: "); String id = sc.nextLine();
        System.out.print("Progress (0-100): "); String prog = sc.nextLine();
        String json = String.format("{\"progress\": %s}", prog);
        put(client, "/student/enrollment/" + id + "/progress", json, true);
    }

    private static void viewEnrollments(HttpClient client) throws Exception {
        get(client, "/student/enrollments/me");
    }
}
