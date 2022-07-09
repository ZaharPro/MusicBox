$(document).on('change', '.file-input', function () {
    let msg = $(this).prev();
    msg.text($(this).val().split('\\').pop());
});