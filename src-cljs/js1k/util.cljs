(ns js1k.util
  (:require [js1k.core :as core]
            [js1k.common.data :as cdata]))

(def pi (.-PI js/Math))
(def state (atom 1))

(defn usin [x] (.sin js/Math x))
(defn ucos [x] (.cos js/Math x))
(defn upow [x y] (.pow js/Math x y))

(defn log [& items]
  (.log js/console (apply str (interpose ", " items))))

(defn init [eloop t]
  (let [canvas (.getElementById js/document "canvas")
        ctx (.getContext canvas "2d")]
    ; (reset! cl/w (.-width canvas))
    ; (reset! cl/h (.-height canvas))
    ; (.addEventListener js/window "keydown" keyDown true)
    (swap! state (fn [_] (js/setInterval (fn [] (eloop ctx)) t)))))

(defn ^:export stop [app]
  (js/clearInterval @state)
  (app/reset))

(defn reg-app 
  [year theme name ^js-obj attr]
  (let [old-themes (aget core/apps year)
        new-themes (if (nil? old-themes) 
                     ; js-obj name attr, can't read name var?
                     (let [theme-obj (js-obj)
                           theme-obj2 (js-obj)]
                       (aset theme-obj name attr) 
                       (aset theme-obj2 theme theme-obj) theme-obj2)
                     (let [theme-obj (aget old-themes theme)
                           theme-obj2 (js-obj)] 
                       (aset theme-obj name attr) 
                       (aset theme-obj2 theme theme-obj) theme-obj2))]
    
    (aset core/apps year new-themes)))

; (defn reg-app2 [app]
;  "in javascript: apps.state is the raw value"
;  (swap! core/apps (cdata/deep-merge @core/apps app)))

