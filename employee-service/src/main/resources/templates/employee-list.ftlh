<!DOCTYPE html>
<html lang="ru">
<head>
  <Title>Сотрудники компании</Title>
  <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
<h1>Сотрудники компании</h1>

<table width="30%">
  <tr>
    <td>Вы зашли как: <i>${user.login}</i></td>
    <td>
      <form action="/logout" method="post">
        <input type="submit" value="Выйти" class="loginButton"/>
      </form>
    </td>
  </tr>
</table>

<h3>Список сотрудников компании</h3>

<#if employees??>
  <table class="employee-table">
    <tr>
      <td style="font-weight: bold">Логин сотрудника</td>
      <td style="font-weight: bold">Фамилия</td>
      <td style="font-weight: bold">Имя</td>
      <td style="font-weight: bold">Дата рождения</td>
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