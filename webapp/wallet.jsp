<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <!DOCTYPE html>
    <html lang="id">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/images/favicon.png">

        <title>Wallet - FinTrack</title>

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
                <p class="mb-1 text-light-teal">Manage</p>
                <h2 class="fw-bold mb-0 fs-1 text-white">My Wallet</h2>
            </div>
        </div>
        <div class="container overlap-container mb-5">
            <div class="row g-4">

                <div class="col-12 col-md-6 col-lg-4">
                    <div class="card fintrack-card theme-dark p-4 h-100">
                        <div class="d-flex justify-content-between align-items-start mb-3">
                            <div class="d-flex align-items-center gap-2">
                                <iconify-icon icon="solar:wallet-bold-duotone" width="28"
                                    class="text-dark-teal"></iconify-icon>
                                <span class="badge bg-light text-dark border">Physical</span>
                            </div>
                            <div class="dropdown">
                                <button class="btn btn-sm border-0 text-white" type="button" data-bs-toggle="dropdown">
                                    <iconify-icon icon="solar:menu-dots-bold" width="20"></iconify-icon>
                                </button>
                                <ul class="dropdown-menu dropdown-menu-end shadow border-0">
                                    <li><a class="dropdown-item" href="#"><iconify-icon icon="solar:pen-bold"
                                                class="me-2"></iconify-icon>Manage</a></li>
                                    <li><a class="dropdown-item text-danger" href="#"><iconify-icon
                                                icon="solar:trash-bin-trash-bold" class="me-2"></iconify-icon>Delete</a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                        <h5 class="fw-bold text-dark-teal mb-1">Dompet Utama</h5>
                        <h4 class="fw-bold text-dark mb-0">Rp 2.500.000</h4>
                    </div>
                </div>

                <div class="col-12 col-md-6 col-lg-4">
                    <div class="card fintrack-card theme-lime p-4 h-100">
                        <div class="d-flex justify-content-between align-items-start mb-3">
                            <div class="d-flex align-items-center gap-2">
                                <iconify-icon icon="solar:smartphone-bold-duotone" width="28"
                                    class="text-dark-teal"></iconify-icon>
                                <span class="badge bg-light text-dark border">E-Wallet (GoPay)</span>
                            </div>
                            <div class="dropdown">
                                <button class="btn btn-sm border-0" type="button" data-bs-toggle="dropdown">
                                    <iconify-icon icon="solar:menu-dots-bold" width="20"></iconify-icon>
                                </button>
                                <ul class="dropdown-menu dropdown-menu-end shadow border-0">
                                    <li><a class="dropdown-item" href="#"><iconify-icon icon="solar:pen-bold"
                                                class="me-2"></iconify-icon>Manage</a></li>
                                    <li><a class="dropdown-item text-danger" href="#"><iconify-icon
                                                icon="solar:trash-bin-trash-bold" class="me-2"></iconify-icon>Delete</a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                        <h5 class="fw-bold text-dark-teal mb-1">Tabungan Jajan</h5>
                        <h4 class="fw-bold text-dark mb-0">Rp 10.000.000</h4>
                    </div>
                </div>
                <div class="col-12 col-md-6 col-lg-4">
                    <div class="card fintrack-card theme-lime p-4 h-100">
                        <div class="d-flex justify-content-between align-items-start mb-3">
                            <div class="d-flex align-items-center gap-2">
                                <iconify-icon icon="solar:smartphone-bold-duotone" width="28"
                                    class="text-dark-teal"></iconify-icon>
                                <span class="badge bg-light text-dark border">E-Wallet (GoPay)</span>
                            </div>
                            <div class="dropdown">
                                <button class="btn btn-sm border-0" type="button" data-bs-toggle="dropdown">
                                    <iconify-icon icon="solar:menu-dots-bold" width="20"></iconify-icon>
                                </button>
                                <ul class="dropdown-menu dropdown-menu-end shadow border-0">
                                    <li><a class="dropdown-item" href="#"><iconify-icon icon="solar:pen-bold"
                                                class="me-2"></iconify-icon>Manage</a></li>
                                    <li><a class="dropdown-item text-danger" href="#"><iconify-icon
                                                icon="solar:trash-bin-trash-bold" class="me-2"></iconify-icon>Delete</a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                        <h5 class="fw-bold text-dark-teal mb-1">Tabungan Jajan</h5>
                        <h4 class="fw-bold text-dark mb-0">Rp 10.000.000</h4>
                    </div>
                </div>
                <div class="col-12 col-md-6 col-lg-4">
                    <div class="card fintrack-card theme-lime p-4 h-100">
                        <div class="d-flex justify-content-between align-items-start mb-3">
                            <div class="d-flex align-items-center gap-2">
                                <iconify-icon icon="solar:smartphone-bold-duotone" width="28"
                                    class="text-dark-teal"></iconify-icon>
                                <span class="badge bg-light text-dark border">E-Wallet (GoPay)</span>
                            </div>
                            <div class="dropdown">
                                <button class="btn btn-sm border-0" type="button" data-bs-toggle="dropdown">
                                    <iconify-icon icon="solar:menu-dots-bold" width="20"></iconify-icon>
                                </button>
                                <ul class="dropdown-menu dropdown-menu-end shadow border-0">
                                    <li><a class="dropdown-item" href="#"><iconify-icon icon="solar:pen-bold"
                                                class="me-2"></iconify-icon>Manage</a></li>
                                    <li><a class="dropdown-item text-danger" href="#"><iconify-icon
                                                icon="solar:trash-bin-trash-bold" class="me-2"></iconify-icon>Delete</a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                        <h5 class="fw-bold text-dark-teal mb-1">Tabungan Jajan</h5>
                        <h4 class="fw-bold text-dark mb-0">Rp 10.000.000</h4>
                    </div>
                </div>

                <div class="col-12 col-md-6 col-lg-4">
                    <button
                        class="card fintrack-card p-4 h-100 w-100 d-flex flex-column align-items-center justify-content-center text-muted"
                        style="border: 2px dashed #a0b2af; background-color: transparent; cursor: pointer;">
                        <div class="rounded-circle d-flex align-items-center justify-content-center mb-2"
                            style="width: 50px; height: 50px; background-color: #e0e4e3;">
                            <iconify-icon icon="solar:add-folder-bold" width="24" class="text-dark-teal"></iconify-icon>
                        </div>
                        <span class="fw-bold text-dark-teal">Add New Wallet</span>
                    </button>
                </div>

            </div>
        </div>

    </body>
    <script src="https://code.iconify.design/iconify-icon/1.0.7/iconify-icon.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

    </html>