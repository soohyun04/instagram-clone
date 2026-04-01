<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>유저 목록</title>
</head>
<body>
<h1>유저 목록</h1>
<ul id="userList"></ul>

<script>
    const 유저목록불러오기 = async () => {
        try {
            const response = await fetch("/api/users");
            const users    = await response.json();
            유저목록그리기(users);
        } catch (error) {
            console.log("에러 발생", error);
        }
    };

    const 유저목록그리기 = (users) => {
        const list = document.getElementById("userList");

        users.forEach(user => {
            const li = document.createElement("li");
            li.textContent = user.name + " / " + user.email;
            list.appendChild(li);
        });
    };

    유저목록불러오기();
</script>
</body>
</html>