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