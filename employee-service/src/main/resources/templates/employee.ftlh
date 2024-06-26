<!DOCTYPE html>
<html lang="ru">
<head>
  <Title>Сотрудники компании</Title>
  <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
<h1>Сотрудники компании</h1>

<h3>Сотрудник - ${employee.login}</h3>

<#if errorMessage??>
  <p class="error-message">Ошибка изменения сотрудника - ${errorMessage}</p>
</#if>

<form action="/ui/employee/update" method="post">
  <table>
    <tr>
      <td><label for="login">Логин:</label></td>
      <td><input type="text" name="login" required value=${employee.login} readonly></td>
    </tr>
    <tr>
      <td><label for="password">Пароль:</label></td>
      <td><input type="password" name="password" required value=${employee.password}></td>
    </tr>
    <tr>
      <td><label for="firstName">Имя:</label></td>
      <td><input type="text" name="firstName" required value=${employee.firstName}></td>
    </tr>
    <tr>
      <td><label for="lasttName">Фамилия:</label></td>
      <td><input type="text" name="lastName" required value=${employee.lastName}></td>
    </tr>
    <tr>
      <td><label for="birthDate">Дата рождения:</label></td>
      <td><input type="text" name="birthDate" required value=${employee.birthDate}></td>
    </tr>
    <tr>
      <td><label>Роли:</label></td>
      <td>
            <select multiple name="roles">
                <#list allRoles as role>
                    <#if employee.roles?seq_contains(role)>
                      <option value="${role}" selected>${role}</option>
                    <#else>
                      <option value="${role}">${role}</option>
                    </#if>
                </#list>
            </select>
      </td>
    </tr>
    <tr>
      <td colspan="2">
        <input type="submit" value="Обновить данные сотрудника" class="employee">
      </td>
    </tr>
  </table>

</form>

<form action="/ui/employee/delete/${employee.login}" method="post">
  <input type="submit" value="Уволить сотрудника" class="delete">
</form>

</body>
</html>