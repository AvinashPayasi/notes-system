package com.notes.system.api.controller;

import com.notes.system.api.ApiResponse;
import com.notes.system.api.ApiStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@Tag(name = "System", description = "General system and API information endpoints")
public class RootController {

    @Operation(
        summary = "Get API root",
        description = "Returns basic information about the Notes API.",
        responses = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "200",
                        description = "Welcome to Notes API",
                        content = @Content
                ),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "500",
                        description = "Something went wrong",
                        content = @Content
                )
        }
    )
    @GetMapping("/")
    public ResponseEntity<ApiResponse<Map<String, String>>> rootMapping(){
        Map<String, String> data= Map.of(
            "version","v1",
            "api","/api",
            "documentation",""
                );
        ApiResponse<Map<String, String>> apiResponse= new ApiResponse<>(ApiStatus.SUCCESS, "Welcome to Notes API",data);
        //Status Code: 200
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @Operation(
        summary = "Get available API versions",
        description = "Returns the list of available API versions.",
        responses = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "200",
                        description = "Available API versions",
                        content = @Content
                ),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "500",
                        description = "Something went wrong",
                        content = @Content
                )
        }
    )
    @GetMapping("/api")
    public ResponseEntity<ApiResponse<Map<String, List<String>>>> apiMapping(){
        ArrayList<String> versions=new ArrayList<>();
        versions.add("v1");
        Map<String, List<String>> data=Map.of(
                "versions",versions
        );
        ApiResponse<Map<String, List<String>>> apiResponse= new ApiResponse<>(ApiStatus.SUCCESS, "Available API versions",data);
        //Status Code:200
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @Operation(
        summary = "Get API v1 endpoints",
        description = "Returns the main endpoints available in API version 1.",
        responses = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "200",
                        description = "Notes API v1",
                        content = @Content
                ),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "500",
                        description = "Something went wrong",
                        content = @Content
                )
        }
    )
    @GetMapping("/api/v1")
    public ResponseEntity<ApiResponse<Map<String, String>>> versionMapping(){
        Map<String , String> data= Map.of(
            "login","POST /api/v1/notes/login",
            "register","POST /api/v1/notes/register",
            "resources","GET /api/v1/notes",
            "documentation",""
        );
        ApiResponse<Map<String , String>> apiResponse=new ApiResponse<>(ApiStatus.SUCCESS, "Notes API v1", data);
        //Status Code:200
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);

    }

}
