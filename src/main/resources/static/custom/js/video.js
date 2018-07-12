$(document).ready(function() {
    function drawPoster(video) {
        let canvas = document.createElement("canvas");
        canvas.height = video.videoHeight;
        canvas.width = video.videoWidth;
        let ctx = canvas.getContext("2d");
        ctx.drawImage(video, 0, 0, canvas.width, canvas.height);
        let videoId = $(video).attr("id");
        $( "#img-" + videoId).attr("src",canvas.toDataURL());
    }
    $("video").on("pause", function() {
        drawPoster(this);
    });
});
