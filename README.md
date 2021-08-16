# SqlBuilder
一个SQL渲染库
> 此库基于[sql-builder](https://gitee.com/unknow0409/sql-builder) 修改  
> 移除了日志模块  
> 修改了使用的SQL markdown文件模板格式  

## 工具优势
读取外部sql.md文件并支持缓存
开放SqlRenderEngine接口让您可以自己渲染原生语句
内部已集成只要引入jar包即可直接使用的模板引擎（Beetl、Freemarker）

## 工具配置方式(两种)
1. 使用配置文件
    1. 将 sqlbuilder-config-default.properties 拷入您的项目src文件夹下
    2. 并将其更名为 sqlbuilder-config.properties
    3. sqlFolders 填入sql文件存放目录，多目录用逗号（,）分隔
    4. sqlMode 选择运行模式，run模式为产品模式读取缓存速度快，没有实时性；debug为开发模式，实时监测sql文件变化改动sql无需重启
    5. 引入 sql-builder.jar 包
2. 使用代码配置
   ```java
   ConfigKit configKit = ConfigKit.me();
   List<String> folders = new ArrayList<>();
   folders.add("your sql file folder path");
   // simple
   // folders.add("xxxxxxxxxxx/sql/");
   configKit.setFolders(folders);
   configKit.setSqlMode("run");
   // or configKit.setSqlMode("debug");
   SqlBuilder.setEngine(new SqlFreemarkerEngine());
   // or SqlBuilder.setEngine(new SqlBeetlEngine());
   ```
## 测试代码
```java
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
```
## 测试markdown(user.md)
<pre>
- fromUser  
这是注释  
```sql
select * from t_user where enable = 1 
```

- fromUserWhereId  
这也是注释  
```sql
select * from t_user where id = ${id?c?if_exists}
<#if name ??>
    and u_name = ${name}
</#if>
```
- fromUserWhereAge
这也是注释  
```sql
select * from t_user where age > ${age?c?if_exists}
```
- testNumberFormat
```sql
select * from t_user where age > ${age?if_exists}
```
- testNonParams
```sql
select 'no sql';
```
- testMapParams
```sql
select '${userid?if_exists}'
```
</pre>
## 测试结果
```
render:sql1
select * from t_user where enable = 1 
render:sql2
select * from t_user where id = 1231564
    and u_name = 张三
render:sql3
select * from t_user where age > 18
render:sql4
select * from t_user where age > 12346
render:sql5
select 'no sql';
render:sql6
select '1234668996'
```
