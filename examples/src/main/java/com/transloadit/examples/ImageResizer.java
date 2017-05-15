package com.transloadit.examples;

import com.transloadit.sdk.Assembly;
import com.transloadit.sdk.Transloadit;
import com.transloadit.sdk.exceptions.LocalOperationException;
import com.transloadit.sdk.exceptions.RequestException;
import com.transloadit.sdk.response.AssemblyResponse;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * This example resizes 2 uploaded files, both to 75x75.
 */
public class ImageResizer {
    public static void main (String [] args) {
        Transloadit transloadit = new Transloadit("TRANSLOADIT_KEY", "TRANSLOADIT_SECRET");

        Map<String, Object> stepOptions = new HashMap<>();
        stepOptions.put("width", 75);
        stepOptions.put("height", 75);
        stepOptions.put("resize_strategy", "pad");

        Assembly assembly = transloadit.newAssembly();
        assembly.addStep("resize", "/image/resize", stepOptions);

        assembly.addFile(new File(ImageResizer.class.getResource("/lol_cat.jpg").getFile()));
        assembly.addFile(new File(ImageResizer.class.getResource("/mona_lisa.jpg").getFile()));

        try {
            System.out.println("Uploading ...");
            AssemblyResponse response = assembly.save(true);

            // wait for assembly to finish executing.
            System.out.println("waiting for assembly to finish ...");
            while (!response.isFinished()) {
                response = transloadit.getAssemblyByUrl(response.getSslUrl());
            }

            JSONArray result = response.getStepResult("resize");

            System.out.println("Resize result:");
            for (int i = 0; i < result.length(); i++) {
                JSONObject item = result.getJSONObject(i);
                System.out.println(String.format("%s.%s: %s",
                        item.getString("basename"), item.getString("ext"), item.getString("url")));
            }
        } catch (RequestException | LocalOperationException e) {
            e.printStackTrace();
        }
    }
}