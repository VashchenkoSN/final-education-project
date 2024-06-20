<!DOCTYPE html>
<html>
<head>
    <Title>Сотрудники компании</Title>
    <link rel="stylesheet" href="../css/style.css">
</head>
<body>
<h1>Сотрудники компании</h1>
<h3>Вход в админ панель</h3>

<#if error??>
  <p>Некорректный логин/пароль</p>
</#if>
<form action="/ui/login" method="post" >
    <p>Логин: <input type="text" name="username"></p>
    <p>Пароль: <input type="password" name="password"></p>
    <input type="submit" value="Вход">
</form>

</body>
</html>