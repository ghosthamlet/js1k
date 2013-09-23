(ns js1k.core
  (:use seesaw.core)
  (:require [clojure.java.io :as io] [clojure.string :as cstr] 
            [js1k.common.data :as cdata])
  (:gen-class :main true))

(def apps (atom {}))

(defn -apps []
  "has side effects, must dorun to avoid lazy"
  (if (.isDirectory (io/file "src")) 
    (dorun (for [fs (file-seq (io/file "src"))
                 :let [path (.getPath fs)]
                 :when (and (re-find #"\.clj" path) 
                            (not (re-find #"common|model|core\.clj|util\.clj" path)))]
             (let [coll (rest (cstr/split path #"\\"))
                   pk (cstr/replace (cstr/join "." coll) ".clj" "")]
               (swap! apps 
                      #(cdata/deep-merge %
                                         {(nth coll 1) 
                                          {(nth coll 2) 
                                           {(cstr/replace (nth coll 3) ".clj" "") 
                                            {:title (nth coll 3) :pk pk}}}}))
               (spit "resource/apps.txt" @apps)
               (spit "resource/apps.json" (cdata/map->json @apps)))))
    (reset! apps (-> "apps.txt" io/resource slurp read-string eval))))

(defn menus [apps]
  (menubar :items 
           (for [[year themes] apps]
             (menu :text year
                   :items (for [[theme app] themes]
                            (menu :text theme
                                  :items (for [a app]
                                           (menu-item :text (first a)
                                                      :class :app
                                                      :id (:pk (second a))))))))))


(defn init []
  (-apps)
  (let [m (menus @apps)
        ui (frame 
             :width 1300
             :height 600
             :title "js1k"
             :content (border-panel
                        :north m
                        :center (canvas :id :canvas)))]
    ; this will get menuitem at once, so (menus) can't lazy, the same (-apps)
    (listen (select ui [:.app]) 
            :action #(let [pk (-> % id-of str (subs 1) symbol)]
                       (require pk)
                       ((eval (symbol (str pk "/add-behaviors"))) ui)))
    ui))

(defn -main [& args]
  (invoke-now
    (-> (init) show!)))

; (defn load-model []
;   (simple-tree-model
;     (complement string?)
;     (comp seq vals)
;     (maps)))
; 
; (config! f :content  menubar :items  (menu :text "text" :items (for [item ["file1" "fiel2"]] (radio-menu-item :text item))) (menu :text "abc" :items (for [item ["abc"]] (menu-item :text item))))
