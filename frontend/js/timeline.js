var _this = this;
var link = $('#navbar a.dot');
function scrNav() {
    var sTop = $('.right-main').scrollTop();
    $('.slider-inside').each(function () {
        var id = $(this).attr('id'),
            offset = $(this).offset().top - 1,
            height = $(this).height();
        console.log($(this).index());
        if (sTop >= offset && sTop < (offset + height)) {
            link.removeClass('active');
            $('#navbar').find('[data-scroll="' + id+ '"]').toggleClass('active');
        }
    });

}
$(document).ready(function () {
    $('.icon-container i').click(function(){
        $(this).parents('.left-container').find('.left-inside').toggleClass("hide");
        $(this).parents('.left-container').find('.hided-inside').toggleClass("show");
        $(this).parents('.left-container').toggleClass("shrink");
        $(this).parents('.main-container').find('.right-container').toggleClass("expand");
        $(this).toggleClass("rotate");
    });
    $('header .fa-search').click(function(){
        $(this).parent('header').find('.search-input').toggleClass("show");
         $(this).parent('header').find('.logo').hide();
    });
    $('.custom-notification').click(function(){
        $(this).find('.notification-dropdown').toggleClass("show");
    });
   


    

    link.on('click', function (e) {
        var target = $($(this).attr('href'));
        $('.right-main').animate({
            scrollTop: target.offset().top
        }, 732);
        $(this).addClass('active');
        e.preventDefault();
    });

});




