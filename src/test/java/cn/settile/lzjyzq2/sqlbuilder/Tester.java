package cn.settile.lzjyzq2.sqlbuilder;

import cn.settile.lzjyzq2.sqlbuilder.core.SqlBuilder;
import cn.settile.lzjyzq2.sqlbuilder.engine.SqlFreemarkerEngine;
import cn.settile.lzjyzq2.sqlbuilder.kit.ConfigKit;
import cn.settile.lzjyzq2.sqlbuilder.model.SqlBuilderPara;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class Tester {

    @Test
    public void Test(){
        ConfigKit.me().getFolders().add("E:\\Project\\SqlBuilder\\src\\test\\resources\\sql");

        // 设置 全局模板引擎
        SqlBuilder.setEngine(new SqlFreemarkerEngine());
        // Freemarker
        System.out.println("render:sql1");
        String sql1 = SqlBuilder.render("user.fromUser", new SqlBuilderPara("name", "颖"));
        System.out.println(sql1);

        System.out.println("render:sql2");
        String sql2 = SqlBuilder.sql("user.fromUserWhereId")
                .para("id",1231564)
                .para("name","张三")
                .render();
        System.out.println(sql2.trim());

        System.out.println("render:sql3");
        String sql3 = SqlBuilder.sql("user.fromUserWhereAge")
                .para("age",18)
                .render();
        System.out.println(sql3.trim());

        System.out.println("render:sql4");
        String sql4 = SqlBuilder.sql("user.testNumberFormat")
                .para("age",12346)
                .render();
        System.out.println(sql4.trim());

        System.out.println("render:sql5");
        String sql5 = SqlBuilder.sql("user.testNonParams").render();
        System.out.println(sql5.trim());

        System.out.println("render:sql6");
        Map<String,Object> params = new HashMap<>();
        params.put("userid",1234668996);
        String sql6 = SqlBuilder.sql("user.testMapParams")
                .para(params)
                .render();
        System.out.println(sql6.trim());
    }
}
