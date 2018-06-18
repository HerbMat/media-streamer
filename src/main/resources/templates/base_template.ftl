<#import "spring.ftl" as spring />

<#macro page_title>
            <title><@spring.messageText "page.title" "S.M.P"/></title>
</#macro>

<#macro header_links>
            <link rel="stylesheet" href="/bootstrap/dist/css/bootstrap.css">
</#macro>

<#macro navbar>
    <a class="navbar-brand" href="#"><@spring.messageText "page.title" "S.M.P"/></a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
                <a class="nav-link" href="#"><@spring.messageText "home.link" "Home"/><span class="sr-only">(current)</span></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="#">Link</a>
            </li>
        </ul>
    </div>
</#macro>

<#macro page_body>
    <h1><@spring.messageText "home.title" "Welcome!"/></h1>
    <button class="btn btn-primary" type="button" href="#">TEST</button>
</#macro>

<#macro page_template>
    <html>
        <head>
            <@page_title/>
            <@header_links/>
        </head>
        <body>
            <nav class="navbar navbar-expand-lg navbar-light bg-light">
                <@navbar/>
            </nav>
            <main role="main" class="container">
                <@page_body/>
            </main>
            <footer class="page-footer font-small blue pt-4 mt-4">
                <div class="footer-copyright text-center py-3">© 2018 Copyright:
                    <a href="https://mdbootstrap.com/bootstrap-tutorial/"> MDBootstrap.com</a>
                </div>
            </footer>
        </body>
    </html>
</#macro>
