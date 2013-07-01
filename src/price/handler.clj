(ns price.handler
  (:use compojure.core)
  (:use [clojure.string :only (split triml)])
  (:require [compojure.handler :as handler]
            [taoensso.timbre :as timbre :refer (trace debug info warn error report)]
            [cheshire.core :refer :all]
            [compojure.route :as route]
            [ring.adapter.jetty :as jetty]
            [feedparser-clj.core :as feed]
            [clojurewerkz.money.amounts :as ma]
            [clojurewerkz.money.currencies :as mc]))

(declare all-currencies)
(def all-exchange-rates {})


(defn parse-value "doc-string" [desc-string]
  ; "1 British Pound Sterling = 15.40322 South African Rand"
  (let [v (split desc-string #"\s+")
        eq-pos (.indexOf v "=")
        val-pos (+ eq-pos 1)]
    (def valu (clojure.string/replace (v val-pos) #"," ""))

    (format "%.6f"  (read-string valu))))


(defn get-forex-feed "doc-string" [currency]
  (def url (str "http://themoneyconverter.com/rss-feed/" (name currency) "/rss.xml"))
  (debug "requesting " url)
  (def exchange-rates {})
  (let [rss (feed/parse-feed url)]    
    (doseq [row (get rss :entries)]
      (def rate {})
      (doseq [item row]
        (def x (into {} row))
        (def rate {
                   (keyword (subs (get x :title) 0 3))
                   (parse-value (get (get x :description) :value))}))
      (def exchange-rates (conj exchange-rates rate))))
  exchange-rates)


(defn load-all-currency-exchange-rates "doc-string" []
  (info "loading all exchange rates")
  (doseq [currency (keys all-currencies)]
    (def all-currencies (merge-with #(or %1 %2) 
                                    {currency  (get-forex-feed currency)} 
                                    all-currencies))))


(defn get-exchange-rate "doc-string" [c-from c-to]
  (def f (get all-currencies c-from))
  (def t (get f c-to))
  (read-string t))



(defn convert "doc-string" [from-c amount into-c]
  (let [
        from (mc/for-code from-c)
        to (mc/for-code into-c)]
    ; (debug "FROM: " from)
    ; (debug "TO: " to)
    (str (ma/convert-to (ma/amount-of from (read-string amount)) to (get-exchange-rate (keyword from-c) (keyword into-c)) :down))))



(defroutes app-routes
  (GET "/" [] "PONG")
  (GET "/c" 
       {params :params}  (convert (get params :from) (get params :amount) (get params :into) ))
  (route/resources "/")
  (route/not-found "Not Found"))



(def app
  (handler/site app-routes))



;for deployment purposes
(defn -main
  [& [port]]
  (debug "starting")
  (load-all-currency-exchange-rates)
  (let [port (Integer. (or port
                           (System/getenv "PORT")
                           4444))]
    (jetty/run-jetty #'app {:port  port
                            :join? false})))





;(def all-currencies {:GBP nil :EUR nil })

(def all-currencies {:SYP nil :IDR nil :MUR nil :NOK nil :BGN nil :XOF nil :BOB nil :ARS nil :QAR nil :MDL nil :KHR nil :ILS nil :AUD nil :LTL nil :PYG nil :BSD nil :SAR nil :PEN nil :RUB nil :NZD nil :GHS nil :JMD nil :VND nil :ZAR nil :ISK nil :TND nil :PKR nil :CZK nil :OMR nil :NGN nil :UYU nil :MKD nil :RON nil :USD nil :UAH nil :MGA nil :MAD nil :LKR nil :HUF nil :BMD nil :COP nil :BHD nil :PHP nil :KWD nil :CHF nil :HRK nil :FJD nil :BAM nil :EGP nil :MXN nil :LVL nil :GBP nil :SCR nil :AWG nil :UGX nil :NPR nil :KRW nil :CLP nil :GMD nil :BBD nil :KES nil :GTQ nil :SEK nil :JPY nil :XCD nil :PLN nil :JOD nil :BDT nil :HKD nil :TWD nil :LAK nil :INR nil :AED nil :VEF nil :CNY nil :RSD nil :MVR nil :THB nil :XPF nil :EUR nil :MYR nil :PAB nil :CAD nil :TRY nil :BRL nil :NAD nil :XAF nil :LBP nil :SGD nil :DKK nil})
