$(document).ready(function () {
  if ($('.main-container').width() < 1024) {
    $('.products').removeClass('gallery').addClass('list');
  }
  else {
    $('.products').removeClass('list').addClass('gallery');
  }

  $(window).on("resize", function () {
    if ($('.main-container').width() < 1024) {
      $('.products').removeClass('gallery').addClass('list');
    }
    else {
      $('.products').removeClass('list').addClass('gallery');
    }
  });
});
