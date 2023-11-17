<%@page contentType="text/html" pageEncoding="UTF-8"%>

<div class="col-md-3" style="margin-bottom: 100px;">
    <div class="profile-sidebar">
        <!-- SIDEBAR USERPIC -->
        <div class="profile-userpic">
            <img src="http://keenthemes.com/preview/metronic/theme/assets/admin/pages/media/profile/profile_user.jpg" class="img-responsive" alt="">
        </div>
        <!-- END SIDEBAR USERPIC -->
        <!-- SIDEBAR USER TITLE -->
        <div class="usertitle">
            <div>
                <svg width="40" height="40" viewBox="0 0 13 13" xmlns="http://www.w3.org/2000/svg">
                    <path d="M13 6.5C13 10.0767 10.0767 13 6.5 13C2.92328 13 0 10.0767 0 6.5C0 2.92329 2.92328 0 6.5 0C10.0767 0 13 2.92329 13 6.5ZM10.3175 9.7672C9.83598 8.83863 8.87302 8.18519 7.73809 8.18519C7.66931 8.18519 7.60053 8.18519 7.56614 8.21958C7.22222 8.32276 6.87831 8.39154 6.5 8.39154C6.12169 8.39154 5.77778 8.32276 5.43386 8.21958C5.36508 8.18519 5.2963 8.18519 5.2619 8.18519C4.12698 8.18519 3.16402 8.83863 2.68254 9.7672C3.61111 10.8677 4.98677 11.5556 6.53439 11.5556C8.01323 11.5556 9.38889 10.8677 10.3175 9.7672ZM4.16138 4.84921C4.16138 6.12169 5.19312 7.15344 6.46561 7.15344C7.73809 7.15344 8.76984 6.12169 8.76984 4.84921C8.76984 3.57672 7.73809 2.54498 6.46561 2.54498C5.19312 2.54498 4.16138 3.57672 4.16138 4.84921Z" fill="red"></path>
                </svg>
            </div>
            <div class="info">
                <p> Xin chào </p>
                <div class="name">
                    ${client.getClientName()}
                </div>
            </div>

        </div>
        <div class="usermenu">
            <div class="title">
                THÔNG TIN XE
            </div>
            <a href="BaoTri.jsp"><i class="fa-solid fa-car"></i>Xe của tôi</a>

            <div class="title">
                ĐẶT HÀNG VÀ DỊCH VỤ
            </div>
            <a href="history"><i class="fa-solid fa-clock-rotate-left"></i>Lịch sử giao dịch</a>
            <a href="BaoTri.jsp"><i class="fa-solid fa-screwdriver-wrench"></i>Bảo dưỡng - Sửa chữa</a>
            <div class="title">
                TÀI KHOẢN
            </div>
            <a href="profile"><i class="fa-solid fa-user"></i>Thông tin cá nhân</a>
            <a href="BaoTri.jsp"><i class="fa-solid fa-triangle-exclamation"></i>Yêu cầu hỗ trợ</a>
            <a href="BaoTri.jsp"><i class="fa-solid fa-headset"></i>Liên hệ</a>
            
            <hr>
            <a href="clientlogout"> <i class="fa-solid fa-right-from-bracket"></i>Đăng xuất</a>

        </div>
        
        
        <!-- END MENU -->
    </div>
</div>