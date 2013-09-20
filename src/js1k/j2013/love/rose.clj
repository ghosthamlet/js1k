(ns js1k.j2013.love.rose
  (:use [seesaw.core] 
        [seesaw.graphics]
        [seesaw.color]
        [js1k.util]
        [js1k.j2013.love.model.rose]
        [clojure.algo.generic.math-functions])
  (:require [js1k.common.data :as cdata])
  (:import java.awt.Font))

(defn flower [c g]
  (loop [i 0] 
    ; animation can use 90000
    (if (< i 600000) 
      (let [point (surface (rand) (rand) (/ (rem i 46) 0.74))] 
        (if-not (nil? point) 
          (let [z (:z point)
                x (int (- (/ (* (:x point) size) z) h))
                y (int (- (/ (* (:y point) size) z) h))
                z-buffer-index (+ (* y size) x)
                z-buffer-val (@z-buffer z-buffer-index)] 
            ; when (or (nil? z-buffer-val) (> z-buffer-val z)) 
            ;  (swap! z-buffer assoc z-buffer-index z)
              (draw g
                    (rect x y 1 1)
                    (style :background 
                           (color (cdata/rgb-color (- (int (* (:r point) h)))) 
                                  (cdata/rgb-color (- (int (* (:g point) h)))) 
                                  (cdata/rgb-color (- (int (* (:r point) (:r point) (- 80))))))))))
        (recur (inc i))))))

(defn paint [c g]
  (push g
        (flower c g)))

(def ui
  (frame 
    :width size
    :height size
    :title "Rose"
    :content (border-panel
               :center (canvas :id :canvas :paint paint))))

(defn add-behaviors [root]
  (let [c (select root [:#canvas])
        tt (timer (fn [_] (repaint! c))
                  :delay 1000000
                  :start? true)]
    (config! c :paint #(paint %1 %2))
    ; Clean up timer on close (useful in repl)
    (listen root :window-closing (fn [_] (.stop tt)))
    root))
    
(defexample []
  (-> ui add-behaviors))

(-main)

(comment 
(use 'seesaw.core)
(use 'seesaw.graphics)
(use 'seesaw.color)
(def fr (frame :width 500 :height 500 :content (border-panel :center (canvas :id :canvas))))
(show! fr)
(def ccc (select fr [:#canvas]))
(defn paint [c g] (loop [s 3] (if (> s 0) (draw g (path [] (move-to (* s 10) 10) (line-to 100 100) (line-to 200 300)) (style :stroke 3 :background "#00F")) (recur (dec s)))))
(config! ccc :paint #(paint %1 %2))
)
