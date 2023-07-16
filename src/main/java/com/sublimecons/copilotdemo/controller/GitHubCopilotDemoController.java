package com.sublimecons.copilotdemo.controller;

import jp.co.future.uroborosql.SqlAgent;
import jp.co.future.uroborosql.config.SqlConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

// Framework: Spring Boot
// Class: RestController
// Path: /api
// Description: GitHub Copilot Demo Controller
@RestController
@RequestMapping("/api")
public class GitHubCopilotDemoController {

    @Autowired
    private SqlConfig sqlConfig;

    // 商品情報テーブル(m_item)から商品ID(item_id)をキーに商品名(item_name)を取得するAPI
    // Path: /item/{id}
    // Method: GET
    // Description: 商品情報取得API
    public String getItem(@PathVariable("id") String id){

        try(SqlAgent sqlAgent = sqlConfig.agent()){
            Map<String, Object> result = sqlAgent.queryWith("select item_name from m_item")
                    .first();
            String itemName = (String) result.get("itemName");
            return itemName;
        }
    }

}



