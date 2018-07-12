let gulp = require("gulp");
let concat = require("gulp-concat");
let rename = require("gulp-rename");
let uglify = require("gulp-uglify");
let jsValidate = require("gulp-jsvalidate");

let jsFiles = "src/main/resources/static/custom/js/*.js",
    jsDest = "src/main/resources/static/custom";

gulp.task("scripts", function() {
    return gulp.src(jsFiles)
        .pipe(concat("app.js"))
        .pipe(gulp.dest(jsDest));
});

gulp.task("minify", function() {
    return gulp.src(jsFiles)
        .pipe(concat("app.js"))
        .pipe(gulp.dest(jsDest))
        .pipe(rename("app.min.js"))
        .pipe(uglify())
        .pipe(gulp.dest(jsDest));
});

gulp.task("validate", function () {
    console.log("Validate JavaScript");
    return gulp.src(jsFiles)
        .pipe(jsValidate());
});

gulp.task("watch", [], function() {
    return gulp.watch([jsFiles], ["scripts"]);
});
