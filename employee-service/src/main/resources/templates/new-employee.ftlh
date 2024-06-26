<!DOCTYPE html>
<html lang="ru">
<head>
  <Title>Сотрудники компании</Title>
  <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
<h1>Сотрудники компании</h1>
<h3>Добавление нового сотрудника</h3>

<#if errorMessage??>
  <p class="error-message">Ошибка добавления сотрудника - ${errorMessage}</p>
</#if>

<form action="/ui/employee/create" method="post">
  <table>
    <tr>
      <td><label for="login">Логин:</label></td>
      <td><input type="text" name="login" required></td>
    </tr>
    <tr>
      <td><label for="password">Пароль:</label></td>
      <td><input type="password" name="password" required></td>
    </tr>
    <tr>
      <td><label for="firstName">Имя:</label></td>
      <td><input type="text" name="firstName" required></td>
    </tr>
    <tr>
      <td><label for="lastName">Фамилия:</label></td>
      <td><input type="text" name="lastName" required></td>
    </tr>
    <tr>
      <td><label for="birthDate">Дата рождения:</label></td>
      <td><input type="text" name="birthDate" required></td>
    </tr>
    <tr>
      <td><label for="roles">Роли:</label></td>
      <td><select multiple name="roles">
          <option value="ROLE_EMPLOYEE">ROLE_EMPLOYEE</option>
          <option value="ROLE_ADMIN">ROLE_ADMIN</option>
        </select>
      </td>
    </tr>
    <tr>
      <td colspan="2">
        <input type="submit" value="Добавить сотрудника">
      </td>
    </tr>
  </table>

</form>

</body>
</html>