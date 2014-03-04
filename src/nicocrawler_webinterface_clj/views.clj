(ns nicocrawler-webinterface-clj.views
  (:use [hiccup core page]))

(def conffile-path "/usr/local/bin/nicocrawler/conf/nicocrawler.conf")

(defn watching-mylist []
  (map #(let [mylist %1]
          [:tr
           [:td             
            [:a {:href (str "http://www.nicovideo.jp/mylist/" (:mylist-id mylist))}
             (:mylist-id mylist)]]
           [:td (:artist mylist)]
           [:td (:album mylist)]])
       (:mylists (read-string (slurp conffile-path)))))

(defn index-page-template []
  (html5
   [:head
    [:title "nicocrawler webinterface"]]
   [:body
    [:h1 "nicocrawler webinterface"]
    [:h2 "register mylist"]
    [:form {:method "get" :action "/"}
     [:h3 "mylist ID"]
     [:p [:input {:type "text" :name "mylist-id"}]] 
     [:h3 "Artist"]
     [:p [:input {:type "text" :name "artist"}]] 
     [:h3 "Album"]
     [:p [:input {:type "text" :name "album"}]] 
     [:p [:input {:type "submit" :name "submit" :value "register"}]]]
    [:h2 "watching mylist"]
    [:table {:border 1}
     [:tr [:th "mylist ID"] [:th "Artist"] [:th "Album"]]
     (watching-mylist)
     ]]))

(defn merge-conf [submit]
  "input-from-webは{:mylist-id 0000 :artist"
  (let [conf (read-string (slurp conffile-path))
        mylists (:mylists conf)]
    (assoc conf :mylists 
           (merge mylists (dissoc submit :submit)))))

(defn write-conf [conf]
  (spit conffile-path conf))

(defn index-page [params]
  (if (= params {})
    (index-page-template) 
  (do
    (write-conf (merge-conf params))
    (index-page-template))))
