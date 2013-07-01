(defproject price "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [compojure "1.1.5"]
                 [ring/ring-core "1.1.8"]
                 [ring/ring-jetty-adapter "1.1.8"]
                 [com.taoensso/timbre "1.5.2"]
                 [clojurewerkz/money "1.3.0"]
                 [cheshire "5.2.0"]
                 [schejulure "0.1.4"]
                 [org.clojars.scsibug/feedparser-clj "0.4.0"]]
  :plugins [[lein-ring "0.8.5"]]
  :ring {:handler price.handler/app}
  :main price.handler
  :profiles {
   :production {
                :misc "configuration" ; app-specific stuff
                :offline true
                :mirrors {#"central|clojars"
                       "http://s3pository.herokuapp.com/clojure"}}
   :dev {:dependencies [[ring-mock "0.1.3"]
                   [lein-ring "0.8.3"]]}})
