<#include "../base_template.ftl">
<#macro page_title>
    <title><@spring.messageText "page.title.media" "S.M.P Media"/></title>
</#macro>
<#macro page_body>
    <h1 class="text-center">VIDEOS</h1>
    <div class="row">
        <#assign index = 1>
        <#list videos as video>
            <div class="col-lg-4 col-md-12 mb-4">
                <div class="modal fade" id="modal-video-${index}" tabindex="-1" role="dialog" aria-labelledby="video-player" aria-hidden="true">
                    <div class="modal-dialog modal-lg" role="document">
                        <div class="modal-content">
                            <div class="modal-body mb-0 p-0">
                                <div class="embed-responsive embed-responsive-16by9 z-depth-1-half">
                                    <video id="video-${index}" controls preload="metadata" >
                                        <source class="embed-responsive-item" src="<@spring.url '/media/${video.mediaName}'/>" type="video/mp4">
                                        Your browser does not support the video tag.
                                    </video>
                                </div>
                            </div>
                            <div class="modal-footer justify-content-center">
                                <span class="mr-4">${video.mediaName}</span>
                                <button type="button" class="btn btn-outline-primary btn-rounded btn-md ml-4" data-dismiss="modal">Close</button>
                            </div>
                        </div>
                    </div>
                </div>
                <div data-toggle="tooltip" data-placement="top" title="${video.mediaName}">
                    <a><img class="img-fluid z-depth-1" src="<@spring.url '/img/${video.imgName}'/>" id="img-video-${index}" alt="video" data-toggle="modal" data-target="#modal-video-${index}"></a>
                    <p class="text-over-img">${video.mediaName}</p>
                </div>
                <#assign index++>
            </div>
        </#list>
    </div>
    <h1 class="text-center">Songs</h1>
    <div class="row">
        <#assign index = 1>
        <#list songs as song>
            <div class="col-lg-4 col-md-12 mb-4">
                <div class="modal fade" id="modal-music-${index}" tabindex="-1" role="dialog" aria-labelledby="audio-player" aria-hidden="true">
                    <div class="modal-dialog modal-lg" role="document">
                        <div class="modal-content">
                            <div class="modal-body mb-0 p-0">
                                <div class="embed-responsive z-depth-1-half">
                                    <audio id="music-${index}" class="w-100" controls preload="metadata">
                                        <source class="embed-responsive-item" src="<@spring.url '/media/${song.mediaName}'/>" type="video/mp4">
                                        Your browser does not support the video tag.
                                    </audio>
                                </div>
                            </div>
                            <div class="modal-footer justify-content-center">
                                <span class="mr-4">${song.mediaName}</span>
                                <button type="button" class="btn btn-outline-primary btn-rounded btn-md ml-4" data-dismiss="modal">Close</button>
                            </div>
                        </div>
                    </div>
                </div>
                <div data-toggle="tooltip" data-placement="top" title="${song.mediaName}">
                    <a><img class="img-fluid z-depth-1" src="<@spring.url '/img/${song.imgName}'/>" id="img-music-${index}" alt="video" data-toggle="modal" data-target="#modal-music-${index}"></a>
                    <p class="text-over-img">${song.mediaName}</p>
                </div>
                <#assign index++>
            </div>
        </#list>
    </div>
</#macro>

<@page_template/>