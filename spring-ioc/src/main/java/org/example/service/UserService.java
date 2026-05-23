package org.example.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

public interface UserService {
    List<Map<String, Object>> run();

    List<Map<String, Object>> hello();

    void retrySucc();

}
