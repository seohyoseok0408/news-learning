package com.newslearning.domain.user.dto;

import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;

public class FileResponse {
    private final Resource resource;
    private final MediaType contentType;
    public FileResponse(Resource resource, MediaType contentType) {
        this.resource = resource; this.contentType = contentType;
    }
    public Resource resource() { return resource; }
    public MediaType contentType() { return contentType; }
}
