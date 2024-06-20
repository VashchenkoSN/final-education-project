<!DOCTYPE html>
<html lang = "ru">
<head>
  <Title>Сотрудники компании</Title>
  <link rel="stylesheet" href="../css/style.css">
</head>
<body>
<h1>Сотрудники компании</h1>
<h3>Добавление нового сотрудника</h3>

<#if errorMessage??>
  <p class="error-message">Ошибка добавления сотрудника - ${errorMessage}</p>
</#if>

<form action="/ui/employee/create" method="post">
  <p>Логин: <input type="text" name="login" required></p>
  <p>Пароль: <input type="password" name="password" required></p>
  <p>Имя: <input type="text" name="firstName" required></p>
  <p>Фамилия: <input type="text" name="lastName" required></p>
  <p>Дата рождения: <input type="text" name="birthDate" required></p>
  <label for="roles">Роли:</label>
  <select multiple name="roles">
    <option value="ROLE_EMPLOYEE">ROLE_EMPLOYEE</option>
    <option value="ROLE_ADMIN">ROLE_ADMIN</option>
  </select>
  <p><input type="submit" value="Добавить сотрудника"></p>
</form>

</body>
</html>