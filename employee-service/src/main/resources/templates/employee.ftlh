<!DOCTYPE html>
<html>
<head>
  <Title>Сотрудники компании</Title>
  <link rel="stylesheet" href="../css/style.css">
</head>
<body>
<h1>Сотрудники компании</h1>

<h3>Сотрудник - ${employee.login}</h3>

<#if errorMessage??>
  <p class="error-message">Ошибка изменения сотрудника - ${errorMessage}</p>
</#if>

<form action="/ui/employee/update" method="post">
  <p>Логин: <input type="text" name="login" required value=${employee.login} readonly></p>
  <p>Пароль: <input type="password" name="password" required value=${employee.password}></p>
  <p>Имя: <input type="text" name="firstName" required value=${employee.firstName}></p>
  <p>Фамилия: <input type="text" name="lastName" required value=${employee.lastName}></p>
  <p>Дата рождения: <input type="text" name="birthDate" required value=${employee.birthDate}></p>
  <label for="roles">Роли:</label>
  <select multiple name="roles">
    <option value="ROLE_EMPLOYEE">ROLE_EMPLOYEE</option>
    <option value="ROLE_ADMIN">ROLE_ADMIN</option>
  </select>
  <p><input type="submit" value="Обновить данные сотрудника"></p>
</form>

<form action="/ui/employee/delete/${employee.login}" method="post">
  <p><input type="submit" value="Уволить сотрудника"></p>
</form>

</body>
</html>