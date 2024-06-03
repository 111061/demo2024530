package com.example.demo.Controller;

import okhttp3.*;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/upload")
public class FileUploadController {

    private static final String API_KEY = "sk-proj-EkVFkga8yLU0XbCC8xeFT3BlbkFJvb4lJyKenJ6vFykC9YU2";
    private static final String API_URL = "https://api.openai.com/v1/files";
    private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);

    @PostMapping
    public String handleFileUpload(@RequestParam("file") MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String fileType = file.getContentType();

        try {
            OkHttpClient client = new OkHttpClient();

            okhttp3.RequestBody fileBody = okhttp3.RequestBody.create(file.getBytes(), MediaType.parse(fileType));
            MultipartBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("file", fileName, fileBody)
                    .addFormDataPart("purpose", "fine-tune")  // 更改为有效的 purpose 值
                    .build();

            Request request = new Request.Builder()
                    .url(API_URL)
                    .post(requestBody)
                    .addHeader("Authorization", "Bearer " + API_KEY)
                    .build();

            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                String errorResponse = response.body() != null ? response.body().string() : "No response body";
                logger.error("File upload failed: " + response + " - " + errorResponse);
                return "File upload failed: " + response.message() + " - " + errorResponse;
            }

            String responseBody = response.body().string();
            JSONObject jsonResponse = new JSONObject(responseBody);
            String fileId = jsonResponse.getString("id");

            return callOpenAI(fileId, "Please summarize the content of this file.");
        } catch (IOException e) {
            logger.error("File upload encountered an error", e);
            return "File upload encountered an error: " + e.getMessage();
        }
    }

    private String callOpenAI(String fileId, String prompt) throws IOException {
        OkHttpClient client = new OkHttpClient();

        JSONObject json = new JSONObject();
        json.put("model", "gpt-3.5-turbo");
        json.put("prompt", prompt);
        json.put("max_tokens", 100);

        okhttp3.RequestBody body = okhttp3.RequestBody.create(
                json.toString(),
                MediaType.parse("application/json")
        );

        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/completions")
                .post(body)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            String errorResponse = response.body() != null ? response.body().string() : "No response body";
            throw new IOException("OpenAI API request failed: " + response + " - " + errorResponse);
        }

        return response.body().string();
    }
}

