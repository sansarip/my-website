(defproject my-website "0.1.0-SNAPSHOT"
  :plugins [[lein-cljsbuild "1.1.7"]
            [lein-ring "0.12.5"]]
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [org.clojure/clojurescript "1.10.520"]
                 [com.bhauman/figwheel-main "0.2.3"]
                 [com.bhauman/rebel-readline-cljs "0.1.4"]
                 [reagent "0.8.1"]
                 [re-frame "0.10.9"]
                 [clj-commons/secretary "1.2.4"]
                 [compojure "1.6.1"]
                 [yogthos/config "1.1.5"]
                 [metosin/reitit "0.3.10"]
                 [ring "1.7.1"]
                 [devcards "0.2.6"]
                 [binaryage/devtools "0.9.10"]
                 [net.dhleong/spade "1.0.3"]
                 [org.clojure/test.check "0.9.0"]
                 [re-catch "0.1.4"]]
  :min-lein-version "2.5.3"
  :source-paths ["src/clj" "src/cljs" "src/cljc"]
  :test-paths ["test/cljs"]
  :clean-targets ^{:protect false} ["resources/public/cljs-out" "resources/public/js/compiled" "target"]
  :aliases {"prod"      ["ring" "uberjar"]
            "fig:prod"  ["run" "-m" "figwheel.main" "-bo" "prod"]
            "fig:stage" ["trampoline" "run" "-m"]
            "fig:dev"   ["trampoline" "run" "-m" "figwheel.main" "-b" "dev" "-r"]}

  :profiles
  {:dev     {:dependencies [[binaryage/devtools "0.9.10"]
                            [day8.re-frame/re-frame-10x "0.4.2"]
                            [day8.re-frame/tracing "0.5.3"]
                            [com.bhauman/figwheel-main "0.2.3"]
                            [com.bhauman/rebel-readline-cljs "0.1.4"]
                            [devcards "0.2.5"]
                            [javax.servlet/servlet-api "2.5"]
                            [ring/ring-mock "0.3.2"]
                            [org.clojure/test.check "0.10.0"]]}
   :prod    {:dependencies [[day8.re-frame/tracing-stubs "0.5.3"]]}
   :uberjar {:source-paths       ["env/prod/clj"]
             :dependencies       [[day8.re-frame/tracing-stubs "0.5.3"]]
             :omit-source        true
             :main               my-website.server
             :aot                [my-website.server]
             :uberjar-name       "my-website-1.jar"
             :prep-tasks         ["compile" ["fig:prod"]]
             :uberjar-exclusions [#"devcards\.html" #"auto\-testing\.html"]}}

  :ring {:handler my-website.handler/app}

  :cljsbuild
  {:builds
   {:min
    {:source-paths ["src/cljs"]
     :compiler     {:main            my_website.core
                    :output-to       "resources/public/js/compiled/app.js"
                    :optimizations   :simple
                    :closure-defines {goog.DEBUG false}
                    :pretty-print    false}}}})

