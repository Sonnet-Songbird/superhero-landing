<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>


<html>
<head>
    <title>Title</title>
    <style>
        * {
            box-sizing: border-box;
            margin: 0;
            padding: 0;
        }
        :root {
            --padding: 60px;
        }
        .box {
            position: relative;
            margin: 50px auto;
            width: 400px;
            display: flex;
            flex-direction: column;
            justify-content: center;
            padding: var(--padding);
            background-color: #c4dfff;
            border-radius: 7px;
        }

        .box input,
        .box button {
            padding: 15px;
            font-size: 1.2em;
            border: none;
        }
        .box input {
            margin-bottom: 25px;
        }
        .box button {
            background-color: #ffebe0;
            color: #547fb2;
            border-radius: 4px;
        }
        div {
            background-color: #fdf9f7;
            position: absolute;
            font-size: 1.2em;
            display: none;
        }
        .div_id {
            top: 5%;
        }
    </style>
</head>
<body>
<c:if test="${joinResult == 0}">
    <script>
        alert("아이디 중복")
    </script>
</c:if>
<form class="box" action="join.do" method="post" onsubmit="return validateForm(this);">
    <div class="div_id" id="div_id">4~10글자 사이, 영어와 숫자로 입력해주세요.</div>
    <input type="text" placeholder="ID" name="id" id="id">
    <input type="email" placeholder="Email" name="email" id=email />
    <input type="password" placeholder="Password" name="pw" id="pw" />
    <div class="div_pw" id="div_pw">4~10글자 사이, 영문, 숫자, 특수문자로 입력해주세요.</div>
    <button name="submit">Sign Up</button>
</form>

<script type="text/javascript">
    function validateForm(form) {
        if(form.id.value == ""){
            alert("아이디를 입력하세요.");
            form.id.focus();
            return false;
        }
        if(form.email.value == ""){
            alert("이메일을 입력하세요.");
            form.email.focus();
            return false;
        }
        if(form.pw.value == ""){
            alert("비밀번호를 입력하세요.");
            form.pw.focus();
            return false;
        }
        if (joinId(elInputId.value) === false) {
            alert("아이디를 다시 입력해주세요");
            form.id.focus();
            return false;
        }
        if (joinPw(elInputPw.value) === false) {
            alert("비밀번호를 다시 입력해주세요");
            form.pw.focus();
            return false;
        }
    }
    let elInputId = document.querySelector('#id')
    let elInputPw= document.querySelector('#pw');

    function joinPw (str) {
        return /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{4,}$/.test(str);
    }
    function joinId(str) {
        return /^[A-Za-z0-9][A-Za-z0-9]*$/.test(str);
    }
    elInputId.onkeyup = function () {
        if (elInputId.value.length !== 0) {
            if (joinId(elInputId.value) === false) {
                document.getElementById('div_id').style.display = 'block'
            } else if(joinId(elInputId.value) === true) {
                document.getElementById('div_id').style.display = 'none'
            }
        }
    }
    elInputPw.onkeyup = function () {
        if (elInputPw.value.length !== 0) {
            if (joinPw(elInputPw.value) === false) {
                document.getElementById('div_pw').style.display = 'block'
                elInputPw.focus()
                return false;
            } else if(joinPw(elInputPw.value) === true) {
                document.getElementById('div_pw').style.display = 'none'
            }
        }
    }
    const crypto = require('crypto');
    const salt = crypto.randomBytes(128).toString('base64');
    app.post('join.do', async function (req, res, next) {
        const body = req.body;
        const elInputPw = body.pw;
        const salt = crypto.randomBytes(128).toString('base64');
        const hashPassword = crypto
            .createHash('sha512')
            .update(elInputPw + salt)
            .digest('hex');}
    );
</script>
</body>
</html>
