<#import "/spring.ftl" as spring />
<!DOCTYPE html>
<html lang="ru">
<head>
  <meta charset="UTF-8">
  <title>Сотрудники компании</title>
  <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
<h1>Сотрудники компании</h1>
<h3>Вход в админ панель</h3>

<#if error??>
  <p class="error-message">Некорректный логин/пароль</p>
</#if>

<div class="container">
  <form action="/ui/login" method="post">
    <table>
      <tr>
        <td><label for="username">Логин:</label></td>
        <td><input type="text" name="username"></td>
      </tr>
      <tr>
        <td><label for="password">Пароль:</label> </td>
        <td><input type="password" name="password"></td>
      </tr>
      <tr>
        <td colspan="2">
          <input type="submit" value="Вход" class="loginButton">
        </td>
      </tr>
    </table>
  </form>
</div>

</body>
</html>