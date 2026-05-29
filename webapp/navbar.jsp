<nav class="navbar navbar-expand-lg navbar-custom py-3 text-white">
    <div class="container">

        <a class="navbar-brand" href="dashboard.jsp">
            <img src="${pageContext.request.contextPath}/images/FLogo.png" class="navbar-logo" alt="FinTrack Logo">
        </a>

        <button class="navbar-toggler text-white border-0" type="button" data-bs-toggle="collapse"
            data-bs-target="#navbarNav">
            <i class="bi bi-list fs-1"></i>
        </button>

        <div class="collapse navbar-collapse justify-content-center" id="navbarNav">
            <ul class="navbar-nav gap-5 align-items-center">
                <li class="nav-item">
                    <a class="nav-link text-white px-3 d-flex align-items-center" href="wallet.jsp">
                        <i class="bi bi-wallet2 me-2"></i>Wallet
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link text-white px-3 d-flex align-items-center" href="transaction.jsp">
                        <i class="bi bi-currency-dollar me-1"></i>Transaction
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link text-white px-3 d-flex align-items-center" href="budget.jsp">
                        <i class="bi bi-piggy-bank me-2"></i>Budget
                    </a>
                </li>
            </ul>
        </div>

        <div class="d-flex align-items-center gap-3">

            <a href="#" class="text-decoration-none">
                <div class="icon-circle">
                    <i class="bi bi-bell-fill fs-5"></i>
                </div>
            </a>

            <div class="dropdown">
                <a class="text-white text-decoration-none dropdown-toggle d-flex align-items-center gap-2" href="#"
                    data-bs-toggle="dropdown">
                    <div class="profile-circle">
                        <i class="bi bi-person-fill fs-4"></i>
                    </div>
                    <span class="fw-semibold text-white">
                        <%= (session.getAttribute("username") !=null) ? session.getAttribute("username")
                            : "Julio Tanlain" %>
                    </span>
                </a>

                <ul class="dropdown-menu dropdown-menu-end shadow border-0 mt-2">
                    <li><a class="dropdown-item" href="profile.jsp"><i class="bi bi-person me-2"></i>Profile</a></li>
                    <li>
                        <hr class="dropdown-divider">
                    </li>
                    <li>
                        <form action="${pageContext.request.contextPath}/logout" method="POST" class="m-0">
                            <button type="submit" class="dropdown-item text-danger"><i
                                    class="bi bi-box-arrow-right me-2"></i>Logout</button>
                        </form>
                    </li>
                </ul>
            </div>

        </div>

    </div>
</nav>

<style>
    .hide-dropdown-arrow::after {
        display: none !important;
    }
</style>
