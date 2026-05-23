package com.huige.learning.openapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/open-api")
public class OpenApiController {

    @Autowired
    private OpenApiMapper mapper;

    @GetMapping
    public Map<String, Object> search(@RequestParam(defaultValue = "") String name,
                                      @RequestParam(defaultValue = "") String category) {
        Map<String, Object> result = new HashMap<>();
        result.put("list", mapper.search(name, category));
        result.put("categories", mapper.categories());
        return result;
    }

    @GetMapping("/categories")
    public List<String> categories() {
        return mapper.categories();
    }

    @GetMapping("/{id}")
    public OpenApi detail(@PathVariable Long id) {
        return mapper.findById(id);
    }
}
