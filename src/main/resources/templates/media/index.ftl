<#include "../base_template.ftl">
<#macro page_title>
    <title><@spring.messageText "page.title.media" "S.M.P Media"/></title>
</#macro>
<#macro page_body>
    <h1 class="text-center">List of uploaded videos</h1>
    <!-- Grid row -->
    <div class="row">
        <#assign index = 1>
        <#list medias as media>
            <!-- Grid column -->
            <div class="col-lg-4 col-md-12 mb-4">
                <!--Modal: Name-->
                <div class="modal fade" id="modal-${index}" tabindex="-1" role="dialog" aria-labelledby="video-player" aria-hidden="true">
                    <div class="modal-dialog modal-lg" role="document">
                        <!--Content-->
                        <div class="modal-content">
                            <!--Body-->
                            <div class="modal-body mb-0 p-0">
                                <div class="embed-responsive embed-responsive-16by9 z-depth-1-half">
                                    <video id="video-${index}" controls preload="metadata" >
                                        <source class="embed-responsive-item" src="/video/${media.mediaName}" type="video/mp4">
                                        Your browser does not support the video tag.
                                    </video>
                                </div>
                            </div>
                            <!--Footer-->
                            <div class="modal-footer justify-content-center">
                                <span class="mr-4">Spread the word!</span>
                                <a type="button" class="btn-floating btn-sm btn-fb"><i class="fa fa-facebook"></i></a>
                                <!--Twitter-->
                                <a type="button" class="btn-floating btn-sm btn-tw"><i class="fa fa-twitter"></i></a>
                                <!--Google +-->
                                <a type="button" class="btn-floating btn-sm btn-gplus"><i class="fa fa-google-plus"></i></a>
                                <!--Linkedin-->
                                <a type="button" class="btn-floating btn-sm btn-ins"><i class="fa fa-linkedin"></i></a>
                                <button type="button" class="btn btn-outline-primary btn-rounded btn-md ml-4" data-dismiss="modal">Close</button>
                            </div>
                        </div>
                        <!--/.Content-->
                    </div>
                </div>
                <!--Modal: Name-->
                <a><img class="img-fluid z-depth-1" src="/img/${media.imgName}" id="img-video-${index}" alt="video" data-toggle="modal" data-target="#modal-${index}"></a>
                <#assign index++>
            </div>
        </#list>
    </div>
</#macro>

<@page_template/>