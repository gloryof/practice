module.exports = function (grunt) {

    var pkg = grunt.file.readJSON("package.json");

    grunt.initConfig({
        watch : {
            css : {
                files: ["src/scss/*.scss"],
                task: ["packege"]
            }
        },
        clean: {
            before_build: ["build/concat/*", "build/minify/*", "build/bless/*", "build/ready/*"],
            result: ["result/css/*"]
        },
        sass: {
            compile: {
                options: {
                    style: "ntested"
                },
                files: {
                    "build/concat/concat-a.css" : "src/scss/concat-a.scss",
                    "build/concat/concat-b.css" : "src/scss/concat-b.scss"
                }
            }
        },
        concat: {
            execute_concat: {
                src: ["build/concat/concat-a.css", "build/concat/concat-b.css"],
                dest: "build/bless/concat.css"
            }
        },
        bless: {
            target: {
                files: {
                    "build/minify/concat.css" : "build/bless/concat.css"
                }
            }
        },
        copy: {
            target: {
                files: [{
                    expand: true,
                    src: ["build/ready/**"],
                    dest: "result/css/",
                    flatten: true,
                    filter: "isFile"
                }]
            }
        }
    });

    grunt.loadNpmTasks("grunt-contrib-compass");
    grunt.loadNpmTasks("grunt-contrib-copy");
    grunt.loadNpmTasks("grunt-contrib-clean");
    grunt.loadNpmTasks("grunt-contrib-watch");
    grunt.loadNpmTasks("grunt-contrib-concat");
    grunt.loadNpmTasks("grunt-contrib-sass");
    grunt.loadNpmTasks("grunt-contrib-cssmin");
    grunt.loadNpmTasks('grunt-bless');

    grunt.registerTask("package", ["build-css", "publish"]);
    grunt.registerTask("build-css", ["clean:before_build", "sass", "concat", "bless"]);
    grunt.registerTask("publish", ["clean:result", "copy"]);
}
