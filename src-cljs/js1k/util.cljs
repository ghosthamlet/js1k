(ns js1k.util
  (:require [js1k.core :as core]
            [js1k.common.data :as cdata]))

(def pi (.-PI js/Math))
(def state (atom 1))

(defn usin [x] (.sin js/Math x))
(defn ucos [x] (.cos js/Math x))
(defn upow [x y] (.pow js/Math x y))
(defn uabs [x] (.abs js/Math x))

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

(defn line [ctx x y tx ty & {:keys [w style]}]
  (.beginPath ctx)
  (.moveTo ctx x y)
  (.lineTo ctx tx ty)
  (and w (set! (.-lineWidth ctx) w))
  (and style (.strokeStyle ctx style))
  (.stroke ctx))

; deprecated
; use js->clj, then map and deep-merge, then clj->js
(defn reg-app 
  "(util/reg-app \"j2013\" \"love\" \"autumn\" (js-obj \"title\" \"Autumn love\"))"
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

; if x is nil, don't exec expr
; (defmacro exec?! [expr x])
