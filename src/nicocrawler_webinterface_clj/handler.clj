(ns nicocrawler-webinterface-clj.handler
  (:use compojure.core
        nicocrawler-webinterface-clj.views)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]))

(defroutes app-routes
  (GET "/"  {params :params} (index-page params))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))
