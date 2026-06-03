<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <!DOCTYPE html>
    <html lang="id">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/images/favicon.png">

        <title>Profile - FinTrack</title>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
        <link href="https://fonts.googleapis.com/css2?family=Instrument+Sans:wght@400;600;700&display=swap"
            rel="stylesheet">

        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css?v=1">
    </head>

    <body>

        <jsp:include page="navbar.jsp" />
        <div class="dashboard-header">
            <div class="container">
                <p class="mb-1 text-light-teal">Your Detail</p>
                <h2 class="fw-bold mb-0 fs-1 text-white">Profile</h2>
            </div>
        </div>

        <div class="container overlap-container mb-5">

            <div class="card fintrack-card p-4 p-md-5">

                <div class="d-flex justify-content-between align-items-start mb-4">

                    <div class="profile-avatar-lg">
                        <img src="images/ProfilePicture.png" alt="">
                    </div>

                    <a href="updateProfile.jsp" class=" btn-dark-teal d-flex align-items-center gap-2 text-decoration-none rounded-pill">
                        <iconify-icon icon="solar:pen-bold" width="18"></iconify-icon>
                        Change my profile
                    </a>
                </div>

                <h1 class="fw-bold text-dark-teal mb-5">Julio Tanlain</h1>

                <div class="row g-4">
                    <div class="col-6 col-md-3">
                        <h6 class="fw-bold text-dark-teal mb-2">Username</h6>
                        <p class="mb-0 text-dark">
                            @juliokeceabiez
                        </p>
                    </div>

                    <div class="col-6 col-md-3">
                        <h6 class="fw-bold text-dark-teal mb-2">Phone Number</h6>
                        <p class="mb-0 text-dark">
                            0832748632746
                        </p>
                    </div>

                    <div class="col-6 col-md-3">
                        <h6 class="fw-bold text-dark-teal mb-2">Email Address</h6>
                        <p class="mb-0 text-dark">
                            juliotanlain@mail.com
                        </p>
                    </div>

                    <div class="col-6 col-md-3">
                        <h6 class="fw-bold text-dark-teal mb-2">Address</h6>
                        <p class="mb-0 text-dark">
                            none
                        </p>
                    </div>
                    <div class="col-12 mt-5">
                        <h6 class="fw-bold text-dark-teal mb-2">Password</h6>

                        <div class="d-flex flex-column flex-md-row align-items-start align-items-md-center gap-3">

                            <div class="d-flex align-items-center gap-2">
                                <input type="password" id="profilePassword"
                                    class="form-control-plaintext mb-0 text-dark p-0"
                                    style="width: 180px; outline: none; letter-spacing: 3px;" value="adalahpokoknya"
                                    readonly>

                                <button type="button"
                                    class="btn btn-sm text-muted p-0 border-0 d-flex align-items-center"
                                    onclick="togglePassword()">
                                    <iconify-icon id="eyeIcon" icon="solar:eye-bold" width="20"
                                        style="cursor: pointer; transition: color 0.2s;"></iconify-icon>
                                </button>
                            </div>
                            <a href="updateProfile.jsp"
                                class="btn btn-dark-teal d-flex align-items-center justify-content-center rounded-pill text-decoration-none"
                                style="width: 42px; height: 42px;" title="Change my password">
                                <iconify-icon icon="solar:pen-bold" width="20"></iconify-icon>
                            </a>

                        </div>
                    </div>
                </div>

            </div>

        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <script src="https://code.iconify.design/iconify-icon/1.0.7/iconify-icon.min.js"></script>

        <script>
            function togglePassword() {
                const pwdInput = document.getElementById("profilePassword");
                const eyeIcon = document.getElementById("eyeIcon");

                if (pwdInput.type === "password") {
                    // Tampilkan tulisan password & ganti icon menjadi mata tertutup
                    pwdInput.type = "text";
                    eyeIcon.setAttribute("icon", "solar:eye-closed-bold");
                } else {
                    // Sembunyikan kembali & ganti icon ke mata terbuka
                    pwdInput.type = "password";
                    eyeIcon.setAttribute("icon", "solar:eye-bold");
                }
            }
        </script>
    </body>

    </html>