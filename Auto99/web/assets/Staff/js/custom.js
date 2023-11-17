jQuery(document).ready(function($) {


    "use strict";

    // Page loading animation

    $("#preloader").animate({
        'opacity': '0'
    }, 600, function() {
        setTimeout(function() {
            $("#preloader").css("visibility", "hidden").fadeOut();
        }, 300);
    });


    $(window).scroll(function() {
        var scroll = $(window).scrollTop();
        var box = $('.header-text').height();
        var header = $('header').height();

        if (scroll >= box - header) {
            $("header").addClass("background-header");
        } else {
            $("header").removeClass("background-header");
        }
    });

    if ($('.owl-clients').length) {
        $('.owl-clients').owlCarousel({
            mouseDrag: false,
            loop: true,
            nav: true,
            dots: false,
            items: 1,
            margin: 30,
            autoplay: false,
            smartSpeed: 700,
            autoplayTimeout: 6000,
            responsive: {
                0: {
                    items: 1,
                    margin: 0
                },
                460: {
                    items: 1,
                    margin: 0
                },
                576: {
                    items: 2,
                    margin: 20
                },
                992: {
                    items: 3,
                    margin: 30
                }
            }
        });
    }
    if ($('.owl-theme').length) {
        $('.owl-theme').owlCarousel({
            dots: false,
            nav: true,
            margin: 30,
            mouseDrag: false,
            responsive: {
                0: {
                    items: 1,
                    margin: 0
                },
                500: {
                    items: 2,
                    margin: 0
                },
                720: {
                    items: 3,
                    margin: 0
                },
                992: {
                    items: 4,
                    margin: 20
                },
            }
        });
    }
    if ($('.owl-banner').length) {
        $('.owl-banner').owlCarousel({
            loop: true,
            nav: false,
            dots: true,
            items: 1,
            margin: 0,
            autoplay: true,
            smartSpeed: 700,
            autoplayTimeout: 4000,
            responsive: {
                0: {
                    items: 1,
                    margin: 0
                },
                460: {
                    items: 1,
                    margin: 0
                },
                576: {
                    items: 1,
                    margin: 20
                },
                992: {
                    items: 1,
                    margin: 30
                }
            }
        });
    }
});