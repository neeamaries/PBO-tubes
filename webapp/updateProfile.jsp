<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <!DOCTYPE html>
    <html lang="id">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/images/favicon.png">

        <title>Update Profile - FinTrack</title>

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
                <div class="d-flex align-items-center gap-3">
                    <a href="profile.jsp" class="text-white text-decoration-none">
                        <iconify-icon icon="solar:alt-arrow-left-bold" width="32"></iconify-icon>
                    </a>
                    <div>
                        <h2 class="fw-bold mb-0 fs-1 text-white">Update Profile</h2>
                    </div>
                </div>
            </div>
        </div>

        <div class="container overlap-container mb-5">
            <div class="card fintrack-card p-4 p-md-5">
                <div class="text-center mb-5">
                    <div class="profile-avatar-lg mb-4">
                        <img src="images/ProfilePicture.png" alt="">
                    </div>
                    <div class="">

                        <form action="${pageContext.request.contextPath}/updateProfile" method="POST">
                            <div class="row g-4 text-start">

                                <div class="col-md-6">
                                    <label class="form-label-custom">Username</label>
                                    <input type="text" name="username" class="form-control fintrack-input"
                                        value="@username" required>
                                </div>

                                <div class="col-md-6">
                                    <label class="form-label-custom">Phone Number</label>
                                    <input type="text" name="phone" class="form-control fintrack-input"
                                        value="Nomor Telepon Sebelumnya" required>
                                </div>

                                <div class="col-md-6">
                                    <label class="form-label-custom">Email Address</label>
                                    <input type="email" name="email" class="form-control fintrack-input"
                                        value="juliotanlain@mail.com" required>
                                </div>

                                <div class="col-md-6">
                                    <label class="form-label-custom">Address</label>
                                    <input type="text" name="address" class="form-control fintrack-input" value="None">
                                </div>

                                <div class="col-md-6">
                                    <label class="form-label-custom">New Password</label>
                                    <input type="password" name="password" class="form-control fintrack-input"
                                        placeholder="Leave blank to keep current password">
                                </div>

                                <div class="col-md-6">
                                    <label class="form-label-custom">Confirm Password</label>
                                    <input type="password" name="confirmPassword" class="form-control fintrack-input"
                                        placeholder="Confirm your new password">
                                </div>

                                <div class="col-12 mt-5 d-flex justify-content-end gap-3">
                                    <a href="profile.jsp"
                                        class="btn btn-light rounded-pill px-4 fw-bold text-muted d-flex align-items-center">
                                        Cancel
                                    </a>
                                    <button type="submit" class="btn btn-dark-teal rounded-pill px-5">
                                        Save Changes
                                    </button>
                                </div>

                            </div>
                        </form>
                    </div>

                </div>
            </div>

            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
            <script src="https://code.iconify.design/iconify-icon/1.0.7/iconify-icon.min.js"></script>
    </body>

    </html>