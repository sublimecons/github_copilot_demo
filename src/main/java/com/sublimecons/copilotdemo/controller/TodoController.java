package com.sublimecons.copilotdemo.controller;

import jp.co.future.uroborosql.SqlAgent;
import jp.co.future.uroborosql.config.SqlConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class TodoController {

    @Autowired
    private SqlConfig sqlConfig;

    @GetMapping("/todo/{id}")
    public String getTodo(@PathVariable("id") String id){
        try(SqlAgent sqlAgent = sqlConfig.agent()){
            Map<String, Object> result = sqlAgent.queryWith("select todo_name from m_todo")
                    .first();
            String todoName = (String) result.get("todoName");
            return todoName;
        }
    }
}
