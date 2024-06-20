<!DOCTYPE html>
<html lang = "ru">
<head>
  <Title>Сотрудники компании</Title>
  <link rel="stylesheet" href="../css/style.css">
</head>
<body>
<h1>Сотрудники компании</h1>
<span>Вы зашли как: <i>${user.login}</i></span>
<form action="/logout" method="post">
  <input type="submit" value="Выйти"/>
</form>
<h3>Список сотрудников компании</h3>

<#if employees??>
  <table id="employee-table">
    <tr>
      <td>Логин сотрудника</td>
      <td>Фамилия</td>
      <td>Имя</td>
      <td>Дата рождения</td>
    </tr>
      <#list employees as employee>
        <tr>
          <td><a href="ui/employee/${employee.login}"><b>${employee.login}</b></a>
          <td>${employee.lastName}</td>
          <td>${employee.firstName}</td>
          <td>${employee.birthDate}</td>
        </tr>
      </#list>
  </table>
<#else>
  <h4>Нет сотрудников</h4>
</#if>

<p><a href="ui/employee/new">Добавить сотрудника</a></p>

</body>
</html>