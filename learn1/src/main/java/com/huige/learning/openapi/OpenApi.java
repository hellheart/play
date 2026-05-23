package com.huige.learning.openapi;

public class OpenApi {

    private Long id;
    private String name;
    private String description;
    private String url;
    private String category;
    private String method;
    private Integer needAuth;
    private String status;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getMethod() { return method; }
    public void setMethod(String method) { this.method = method; }
    public Integer getNeedAuth() { return needAuth; }
    public void setNeedAuth(Integer needAuth) { this.needAuth = needAuth; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
