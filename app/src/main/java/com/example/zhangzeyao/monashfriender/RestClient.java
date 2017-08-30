package com.example.zhangzeyao.monashfriender;

import android.util.Log;

import com.example.zhangzeyao.monashfriender.models.Friendship;
import com.example.zhangzeyao.monashfriender.models.Student;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.Date;

/**
 * Created by zhangzeyao on 21/4/17.
 */

public class RestClient {

    private static final String BASE_URL = "http://118.139.56.64:8080/MonashFriendFinderDB/webresources";
//private static final String BASE_URL = "http://127.0.0.1:8080/MonashFriendFinderDB/webresources";

    public static String findByMonEmail(String email) {
        final String methodPath = "/monashfriendshiprestclient.student/findByMonemail";

        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        try {
            url = new URL(BASE_URL + methodPath + "/" + email + "?");

            conn = (HttpURLConnection) url.openConnection();

            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);

            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");

            Scanner inStream = new Scanner(conn.getInputStream());
            Log.i("Myapp", "Success3");

            while (inStream.hasNextLine()) {

                textResult += inStream.nextLine();
            }
            System.out.print(textResult);
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("Error", e.toString());
        } finally {
            conn.disconnect();
        }

        return textResult;
    }

    public static String findByStudID(String ID) {
        final String methodPath = "/monashfriendshiprestclient.student/findByStudentid";

        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        try {
            url = new URL(BASE_URL + methodPath + "/" + ID + "?");

            conn = (HttpURLConnection) url.openConnection();

            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);

            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");

            Scanner inStream = new Scanner(conn.getInputStream());
            Log.i("Myapp", "Success3");

            while (inStream.hasNextLine()) {

                textResult += inStream.nextLine();
            }
            System.out.print(textResult);
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("Error", e.toString());
        } finally {
            conn.disconnect();
        }

        return textResult;
    }

    public static void createStudent(Student student) {
        URL url = null;
        HttpURLConnection conn = null;
        final String methodPath = "/monashfriendshiprestclient.student/";
        try {
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
            String stringStudentJson = gson.toJson(student);
            Log.i("MyAppNotice", stringStudentJson);
            url = new URL(BASE_URL + methodPath);

            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");

            conn.setDoOutput(true);
            conn.setFixedLengthStreamingMode(stringStudentJson.getBytes().length);

            conn.setRequestProperty("Content-Type", "application/json");

            PrintWriter out = new PrintWriter(conn.getOutputStream());
            out.print(stringStudentJson);
            out.close();
            Log.i("error", new Integer(conn.getResponseCode()).toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
    }

    public static void updateStudent(Student student) {
        String studentId = String.valueOf(student.getStudentid());
        URL url = null;
        HttpURLConnection conn = null;
        final String methodPath = "/monashfriendshiprestclient.student/" + studentId + "?";
        try {
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
            String stringStudentJson = gson.toJson(student);
            url = new URL(BASE_URL + methodPath);

            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("PUT");

            conn.setDoOutput(true);
            conn.setFixedLengthStreamingMode(stringStudentJson.getBytes().length);

            conn.setRequestProperty("Content-Type", "application/json");

            PrintWriter out = new PrintWriter(conn.getOutputStream());
            out.print(stringStudentJson);
            out.close();
            Log.i("error", new Integer(conn.getResponseCode()).toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
    }

    public static String weatherForcast(Student student) {

        //Need latitude and longtitude of current place;
        final String path = "https://api.darksky.net/forecast/0673983b111016b137342522f1621b3a/-37.8840,145.0266";
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        try {
            url = new URL(path);

            conn = (HttpURLConnection) url.openConnection();

            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);

            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            Log.i("Myapp", "Success2");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            Log.i("Myapp", "Success3");
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            bufferedReader.close();
            textResult = stringBuilder.toString();

        } catch (Exception e) {
            e.printStackTrace();
            Log.i("Error", e.toString());
        } finally {
            conn.disconnect();
        }

        return textResult;
    }

    public static String findAllUnit() {
        final String methodPath = "/monashfriendshiprestclient.student/findAllUnit";

        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        try {
            url = new URL(BASE_URL + methodPath + "?");

            conn = (HttpURLConnection) url.openConnection();

            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);

            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");

            Scanner inStream = new Scanner(conn.getInputStream());
            Log.i("Myapp", "Success4");

            while (inStream.hasNextLine()) {

                textResult += inStream.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("Error", e.toString());
        } finally {
            conn.disconnect();
        }

        return textResult;
    }

    public static String findByStudentidAndTime(Integer studentid, Date startDate, Date endDate) {
        final String methodPath = "/monashfriendshiprestclient.studentlocation/findByStudentidAndTimeReturnLocation";

        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            url = new URL(BASE_URL + methodPath + "/" + String.valueOf(studentid) + "/" + df.format(startDate) + "/" + df.format(endDate) + "?");

            conn = (HttpURLConnection) url.openConnection();

            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);

            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");

            Scanner inStream = new Scanner(conn.getInputStream());
            Log.i("Myapp", "Success3");

            while (inStream.hasNextLine()) {

                textResult += inStream.nextLine();
            }
            System.out.print(textResult);
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("Error", e.toString());
        } finally {
            conn.disconnect();
        }

        return textResult;
    }

    public static String findByStudentid(Student student) {
        final String methodPath = "/monashfriendshiprestclient.friendship/findByStudentid";

        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        try {
            url = new URL(BASE_URL + methodPath + "/" + String.valueOf(student.getStudentid()) + "?");

            conn = (HttpURLConnection) url.openConnection();

            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);

            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");

            Scanner inStream = new Scanner(conn.getInputStream());
            Log.i("Myapp", "Success3");

            while (inStream.hasNextLine()) {

                textResult += inStream.nextLine();
            }
            System.out.print(textResult);
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("Error", e.toString());
        } finally {
            conn.disconnect();
        }

        return textResult;
    }

    public static String findByFriendid(Student student) {
        final String methodPath = "/monashfriendshiprestclient.friendship/findByFriendid";

        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        try {
            url = new URL(BASE_URL + methodPath + "/" + String.valueOf(student.getStudentid()) + "?");

            conn = (HttpURLConnection) url.openConnection();

            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);

            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");

            Scanner inStream = new Scanner(conn.getInputStream());
            Log.i("Myapp", "Success3");

            while (inStream.hasNextLine()) {

                textResult += inStream.nextLine();
            }
            System.out.print(textResult);
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("Error", e.toString());
        } finally {
            conn.disconnect();
        }

        return textResult;
    }

    public static void updateFriendship(Friendship friendship) {
        URL url = null;
        HttpURLConnection conn = null;
        final String methodPath = "/monashfriendshiprestclient.friendship/" + friendship.getRelationid() + "?";
        try {
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
            String stringStudentJson = gson.toJson(friendship);
            url = new URL(BASE_URL + methodPath);

            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("PUT");

            conn.setDoOutput(true);
            conn.setFixedLengthStreamingMode(stringStudentJson.getBytes().length);

            conn.setRequestProperty("Content-Type", "application/json");

            PrintWriter out = new PrintWriter(conn.getOutputStream());
            out.print(stringStudentJson);
            out.close();
            Log.i("error", new Integer(conn.getResponseCode()).toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
    }

    public static void createFriendship(Friendship friendship) {
        URL url = null;
        HttpURLConnection conn = null;
        final String methodPath = "/monashfriendshiprestclient.friendship/";
        try {
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
            String stringStudentJson = gson.toJson(friendship);
            Log.i("MyAppNotice", stringStudentJson);
            url = new URL(BASE_URL + methodPath);

            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");

            conn.setDoOutput(true);
            conn.setFixedLengthStreamingMode(stringStudentJson.getBytes().length);

            conn.setRequestProperty("Content-Type", "application/json");

            PrintWriter out = new PrintWriter(conn.getOutputStream());
            out.print(stringStudentJson);
            out.close();
            Log.i("error", new Integer(conn.getResponseCode()).toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
    }

    public static String searchGoogleAPI(String keyword) {
        final String methodPath = "/monashfriendshiprestclient.student/searchByKeywords";

        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        try {
            url = new URL(BASE_URL + methodPath + "/" + keyword + "?");

            conn = (HttpURLConnection) url.openConnection();

            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);

            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");

            Scanner inStream = new Scanner(conn.getInputStream());
            Log.i("Myapp", "Success3");

            while (inStream.hasNextLine()) {

                textResult += inStream.nextLine();
            }
            System.out.print(textResult);
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("Error", e.toString());
        } finally {
            conn.disconnect();
        }

        return textResult;
    }

    public static String findByKeywords(String keywords, Student student){
        final String methodPath = "/monashfriendshiprestclient.student/ReportWSd";

        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        try {
            url = new URL(BASE_URL + methodPath + "/" + Integer.valueOf(student.getStudentid()) + "/"+keywords + "?");

            conn = (HttpURLConnection) url.openConnection();

            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);

            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");

            Scanner inStream = new Scanner(conn.getInputStream());
            Log.i("Myapp", "Success3");

            while (inStream.hasNextLine()) {

                textResult += inStream.nextLine();
            }
            System.out.print(textResult);
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("Error", e.toString());
        } finally {
            conn.disconnect();
        }

        return textResult;

    }

    public static String findByRelationid(int relationid) {
        final String methodPath = "/monashfriendshiprestclient.friendship/findByRelationid";

        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        try {
            url = new URL(BASE_URL + methodPath + "/" + String.valueOf(relationid) + "?");
            conn = (HttpURLConnection) url.openConnection();

            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);

            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");

            Scanner inStream = new Scanner(conn.getInputStream());
            Log.i("Myapp", "Success3");

            while (inStream.hasNextLine()) {

                textResult += inStream.nextLine();
            }
            System.out.print(textResult);
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("Error", e.toString());
        } finally {
            conn.disconnect();
        }

        return textResult;
    }

    public static String findLocationByStudentid(Student student) {
        final String methodPath = "/monashfriendshiprestclient.studentlocation/findByStudentid";

        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        try {
            url = new URL(BASE_URL + methodPath + "/" + String.valueOf(student.getStudentid())+ "?");
            conn = (HttpURLConnection) url.openConnection();

            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);

            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");

            Scanner inStream = new Scanner(conn.getInputStream());
            Log.i("Myapp", "Success3");

            while (inStream.hasNextLine()) {

                textResult += inStream.nextLine();
            }
            System.out.print(textResult);
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("Error", e.toString());
        } finally {
            conn.disconnect();
        }

        return textResult;
    }
}
