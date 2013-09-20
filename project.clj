(defproject js1k "0.1.0-SNAPSHOT"
            :description "FIXME: write description"
            :url "http://example.com/FIXME"
            :license {:name "Eclipse Public License"
                      :url "http://www.eclipse.org/legal/epl-v10.html"}
            :dependencies [[org.clojure/clojure "1.5.1"]
                           [seesaw "1.4.3"]
                           [org.clojure/algo.generic "0.1.1"]]

            :plugins [[lein-cljsbuild "0.3.2"]]

            ;; lein uberjar
            :source-paths ["src"]
            :main js1k.core
            ;; cljsbuild does not like cljs files being in the root source folder
            ;; lein cljsbuild once
            ;; file name has '-' like autumn-model can't copy to target folder when build
            :cljsbuild {:crossovers [js1k.common
                                     js1k.j2013.love.model 
                                     js1k.j2012.spring.model]
                        :builds
                        [{:source-path "src-cljs"
                          :compiler
                          {:output-to "js/js1k.js"
                           ;; :optimizations :advanced
                           :pretty-print true}}]})
