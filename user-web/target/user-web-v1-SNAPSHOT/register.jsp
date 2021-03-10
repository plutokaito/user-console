<head>
<jsp:directive.include file="/WEB-INF/jsp/prelude/include-head-meta.jspf" />
	<title>My Home Page</title>
    <style>
      .bd-placeholder-img {
        font-size: 1.125rem;
        text-anchor: middle;
        -webkit-user-select: none;
        -moz-user-select: none;
        -ms-user-select: none;
        user-select: none;
      }

      @media (min-width: 768px) {
        .bd-placeholder-img-lg {
          font-size: 3.5rem;
        }
      }
    </style>
</head>
<body>
	<div class="container">
		<form class="form-signin">
			<h1 class="h3 mb-3 font-weight-normal">Sign Up</h1>
			<label for="inputPhone" class="sr-only">请输出电子邮件</label>
			<input type="phone" id="inputPhone" class="form-control" placeholder="请输入手机号码" required autofocus>
			<label for="inputPassword" class="sr-only">Password</label>
			<input type="password" id="inputPassword" class="form-control" placeholder="请输入密码" required>
			<button class="btn btn-lg btn-primary btn-block" type="submit">Sign up</button>
			<p class="mt-5 mb-3 text-muted">&copy; 2021</p>
		</form>
	</div>
</body>