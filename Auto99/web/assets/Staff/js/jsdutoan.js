$(document).ready(function() {

    $(document).on("click", ".kieudang", function(e) {
        e.preventDefault();
        var gd = $(this);
        var text = $(this).attr('text');
        var xe = $(this).attr('val');
        $("#kieudangtxt").html(xe);
        $("#kieudangtxt").attr('style', 'color:#222;');
        $("#kieudang").val(text);

        $("#dongxetxt").html('Chọn');
        $("#dongxetxt").attr('style', '');
        $("#dongxe").val(0);

        $("#phienbantxt").html('Chọn');
        $("#phienbantxt").attr('style', '');
        $("#phienban").val(0);

        $("#mauxetxt").html('Chọn');
        $("#mauxetxt").attr('style', '');
        $("#mauxe").val(0);
        $("#giaxe").val(0);

        $("#mucdichtxt").html('Chọn');
        $("#mucdichtxt").attr('style', '');
        $("#mucdich").val(0);
        $("#baohiembatbuoc").val(0);

        $.post("/global/load/dutoandongxe.asp", {
            lang: $('#lang').val(),
            parent_id: $('#kieudang').val(),
        }, function(response) {
            setTimeout("finishAjax('txtdongxe', '" + escape(response) + "')", 50);
        });

        $.post("/global/load/dutoanmucdich.asp", {
            lang: $('#lang').val(),
            parent_id: $('#dongxe').val(),
        }, function(response) {
            setTimeout("finishAjax('txtmucdich', '" + escape(response) + "')", 50);
        });
        finishPhi();
        return false;
    });
    $('.boxkieudang').click(function(e) {
        $('.boxkieudang ul').slideToggle("fast");
    });

    $(document).on("click", ".dongxe", function(e) {
        e.preventDefault();
        var gd = $(this);
        var text = $(this).attr('text');
        var xe = $(this).attr('val');

        $("#dongxetxt").html(xe);
        $("#dongxetxt").attr('style', 'color:#222;');
        $("#dongxe").val(text);

        $("#phienbantxt").html('Chọn');
        $("#phienbantxt").attr('style', '');
        $("#phienban").val(0);

        $("#mauxetxt").html('Chọn');
        $("#mauxetxt").attr('style', '');
        $("#mauxe").val(0);
        $("#giaxe").val(0);

        $("#mucdichtxt").html('Chọn');
        $("#mucdichtxt").attr('style', '');
        $("#mucdich").val(0);
        $("#baohiembatbuoc").val(0);

        $.post("/global/load/dutoanphienban.asp", {
            lang: $('#lang').val(),
            parent_id1: $('#kieudang').val(),
            parent_id2: $('#dongxe').val(),
        }, function(response) {
            setTimeout("finishAjax('txtphienban', '" + escape(response) + "')", 50);
        });

        $.post("/global/load/dutoanmucdich.asp", {
            lang: $('#lang').val(),
            parent_id: $('#dongxe').val(),
        }, function(response) {
            setTimeout("finishAjax('txtmucdich', '" + escape(response) + "')", 50);
        });
        finishPhi();
        return false;
    });
    $('.boxdongxe').click(function(e) {
        $('.boxdongxe ul').slideToggle("fast");
    });

    $(document).on("click", ".phienban", function(e) {
        e.preventDefault();
        var gd = $(this);
        var text = $(this).attr('text');
        var xe = $(this).attr('val');

        $("#phienbantxt").html(xe);
        $("#phienbantxt").attr('style', 'color:#222;');
        $("#phienban").val(text);

        $("#mauxetxt").html('Chọn');
        $("#mauxetxt").attr('style', '');
        $("#mauxe").val(0);
        $("#giaxe").val(0);

        $.post("/global/load/dutoanmauxe.asp", {
            lang: $('#lang').val(),
            parent_id: $('#phienban').val(),
        }, function(response) {
            setTimeout("finishAjax('txtmauxe', '" + escape(response) + "')", 50);
        });
        finishPhi();
        return false;
    });
    $('.boxphienban').click(function(e) {
        $('.boxphienban ul').slideToggle("fast");
    });

    $(document).on("click", ".mauxe", function(e) {
        e.preventDefault();
        var gd = $(this);
        var text = $(this).attr('text');
        var xe = $(this).attr('val');
        var gia = $(this).attr('pri');
        $("#mauxetxt").html(xe);
        $("#mauxetxt").attr('style', 'color:#222;');
        $("#mauxe").val(text);
        $("#giaxe").val(gia);
        finishPhi();
        return false;
    });
    $('.boxmauxe').click(function(e) {
        $('.boxmauxe ul').slideToggle("fast");
    });

    $(document).on("click", ".tinh", function(e) {
        e.preventDefault();
        var gd = $(this);
        var text = $(this).attr('text');
        var xe = $(this).attr('val');
        var thuetruocba1 = $(this).attr('thuetruocba1');
        var thuetruocba2 = $(this).attr('thuetruocba2');
        var phidangky = $(this).attr('phidangky');
        $("#tinhtxt").html(xe);
        $("#tinhtxt").attr('style', 'color:#222;');
        $("#tinh").val(text);
        $("#thuetruocba1").val(thuetruocba1);
        $("#thuetruocba2").val(thuetruocba2);
        $("#phidangky").val(phidangky);
        finishPhi();
        return false;
    });
    $('.boxtinh').click(function(e) {
        $('.boxtinh ul').slideToggle("fast");
    });

    $(document).on("click", ".mucdich", function(e) {
        e.preventDefault();
        var gd = $(this);
        var text = Number($(this).attr('text'));
        var xe = $(this).attr('val');
        var baohiembatbuoc = text + (text * 10 / 100);
        $("#mucdichtxt").html(xe);
        $("#mucdichtxt").attr('style', 'color:#222;');
        $("#mucdich").val(xe);
        $("#mucdich").trigger('change');
        $("#baohiembatbuoc").val(baohiembatbuoc);
        finishPhi();
        return false;
    });
    $('.boxmucdich').click(function(e) {
        $('.boxmucdich ul').slideToggle("fast");
    });

    $(document).on("click", ".khachhang", function(e) {
        e.preventDefault();
        var gd = $(this);
        var text = $(this).attr('text');
        var xe = $(this).attr('val');
        $("#khachhangtxt").html(xe);
        $("#khachhangtxt").attr('style', 'color:#222;');
        $("#khachhang").val(xe);
        return false;
    });
    $('.boxkhachhang').click(function(e) {
        $('.boxkhachhang ul').slideToggle("fast");
    });

    $(document).on("click", ".phiduongbo", function(e) {
        e.preventDefault();
        var gd = $(this);
        var text = $(this).attr('text');
        var xe = $(this).attr('val');
        $("#phiduongbotxt").html(xe);
        $("#phiduongbotxt").attr('style', 'color:#222;');
        $("#phiduongbo").val(text);
        $("#philuuhanhduongbo").html(addCommas(text));
        finishPhi();
        return false;
    });
    $('.boxphiduongbo').click(function(e) {
        $('.boxphiduongbo ul').slideToggle("fast");
    });

});

$(document).mouseup(function(e) {

    var boxkieudang = $('.boxkieudang');
    if (!boxkieudang.is(e.target) && boxkieudang.has(e.target).length === 0) {
        if ($('.boxkieudang ul:visible').length == 1) {
            $('.boxkieudang ul').slideToggle("fast");
        }
    }

    var boxdongxe = $('.boxdongxe');
    if (!boxdongxe.is(e.target) && boxdongxe.has(e.target).length === 0) {
        if ($('.boxdongxe ul:visible').length == 1) {
            $('.boxdongxe ul').slideToggle("fast");
        }
    }

    var boxphienban = $('.boxphienban');
    if (!boxphienban.is(e.target) && boxphienban.has(e.target).length === 0) {
        if ($('.boxphienban ul:visible').length == 1) {
            $('.boxphienban ul').slideToggle("fast");
        }
    }

    var boxmauxe = $('.boxmauxe');
    if (!boxmauxe.is(e.target) && boxmauxe.has(e.target).length === 0) {
        if ($('.boxmauxe ul:visible').length == 1) {
            $('.boxmauxe ul').slideToggle("fast");
        }
    }

    var boxtinh = $('.boxtinh');
    if (!boxtinh.is(e.target) && boxtinh.has(e.target).length === 0) {
        if ($('.boxtinh ul:visible').length == 1) {
            $('.boxtinh ul').slideToggle("fast");
        }
    }

    var boxmucdich = $('.boxmucdich');
    if (!boxmucdich.is(e.target) && boxmucdich.has(e.target).length === 0) {
        if ($('.boxmucdich ul:visible').length == 1) {
            $('.boxmucdich ul').slideToggle("fast");
        }
    }

    var boxkhachhang = $('.boxkhachhang');
    if (!boxkhachhang.is(e.target) && boxkhachhang.has(e.target).length === 0) {
        if ($('.boxkhachhang ul:visible').length == 1) {
            $('.boxkhachhang ul').slideToggle("fast");
        }
    }

    var boxphiduongbo = $('.boxphiduongbo');
    if (!boxphiduongbo.is(e.target) && boxphiduongbo.has(e.target).length === 0) {
        if ($('.boxphiduongbo ul:visible').length == 1) {
            $('.boxphiduongbo ul').slideToggle("fast");
        }
    }

});

function finishAjax(id, response) {
    $('#' + id).html(unescape(response));
}

function finishPhi() {
    var giaxe = Number($('#giaxe').val());
    var thuetruocba1 = Number($('#thuetruocba1').val());
    var thuetruocba2 = Number($('#thuetruocba2').val());
    var phidangky = Number($('#phidangky').val());
    var phidangkiem = Number($('#phidangkiem').val());
    var baohiembatbuoc = Number($('#baohiembatbuoc').val());
    var phiduongbo = Number($('#phiduongbo').val());

    var truocba = (giaxe * thuetruocba1) / 100;
    var tongphi = giaxe + truocba + phidangky + phidangkiem + baohiembatbuoc + phiduongbo;

    if (giaxe > 0) {
        $("#gianiemyettxt").html(addCommas(giaxe));
        $("#gianiemyettxt").attr('style', 'color:#222;');
    } else {
        $("#gianiemyettxt").html(0);
        $("#gianiemyettxt").attr('style', '');
    };

    if (giaxe > 0 && thuetruocba1 > 0 && phidangky > 0 && baohiembatbuoc > 0) {
        $("#giatinhphitruocba").html(addCommas(giaxe));
        $("#lephitruocba").html(addCommas(truocba));
        $("#lephidangky").html(addCommas(phidangky));
        $("#lephidangkiem").html(addCommas(phidangkiem));
        $("#philuuhanhduongbo").html(addCommas(phiduongbo));
        $("#baohiemdansubatbuoc").html(addCommas(baohiembatbuoc));
        $("#tongphitxt").html(addCommas(tongphi));
        $("#tongphitxt").attr('style', 'color:#222;');
        $(".kq").attr('style', 'display:block;');
    } else {
        $("#giatinhphitruocba").html(0);
        $("#lephitruocba").html(0);
        $("#lephidangky").html(0);
        $("#lephidangkiem").html(0);
        $("#philuuhanhduongbo").html(0);
        $("#baohiemdansubatbuoc").html(0);
        $("#tongphitxt").html(0);
        $("#tongphitxt").attr('style', '');
        $(".kq").attr('style', '');
    }

}