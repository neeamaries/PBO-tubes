<!DOCTYPE html>
<html lang="id">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/images/favicon.png">
    
    <title>Log In - FinTrack</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
    <link href="https://fonts.googleapis.com/css2?family=Instrument+Sans:wght@400;600;700&display=swap"
        rel="stylesheet">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>

<body>
    <div class="container-fluid p-0">
        <div class="row g-0 min-vh-100">

            <div class="col-lg-6 login-side">
                <div class="login-container text-center">
                    <h1 class="login-title">LOG IN</h1>

                    <form>

                        <div class="input-group mb-3">
                            <span class="input-group-text"><i class="bi bi-person-fill"></i></span>
                            <input type="text" name="loginUsername" class="form-control" placeholder="Username"
                                required>
                        </div>

                        <div class="input-group mb-4">
                            <span class="input-group-text"><i class="bi bi-key-fill"></i></span>
                            <input type="password" name="loginPassword" class="form-control" placeholder="Password"
                                required>
                        </div>

                        <button type="submit" class="btn btn-lime w-100 mb-4">LOG IN</button>

                        <div class="referToRegister">
                            <a href="register.jsp" class="register-link">Create new account? <b>Click here</b></a>
                        </div>
                    </form>
                </div>
            </div>

            <div class="col-lg-6 image-side">
                <img src="${pageContext.request.contextPath}/images/FLoginLogo.png" class="main-visual"
                    alt="FinTrack Visual">

                <div
                    style="position: absolute; width: 300px; height: 300px; background: rgba(152, 206, 0, 0.1); filter: blur(100px); top: 20%; left: 30%;">
                </div>
            </div>

        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>

</html>