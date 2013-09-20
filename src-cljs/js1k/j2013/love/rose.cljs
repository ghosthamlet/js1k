(ns js1k.j2013.love.rose
  (:require [js1k.util :as util]
            [js1k.common.data :as cdata]
            [js1k.j2013.love.model.rose :as cl]))

(util/reg-app "j2013" "love" "rose" (js-obj "title" "Rose"))

(defn rose [ctx]
  ; animation can use 90000
  ; if i too large, render will much slow, 
  ; but give a small i, and a small setTimeout time, render speed up
  (dotimes [i 600] 
    (let [point (cl/surface (rand) (rand) (/ (rem i 46) 0.74))] 
      (if-not (nil? point) 
        (let [z (:z point)
              x (int (- (/ (* (:x point) cl/size) z) cl/h))
              y (int (- (/ (* (:y point) cl/size) z) cl/h))
              ; z-buffer-index (+ (* y size) x)
              ; z-buffer-val (@z-buffer z-buffer-index)
              ] 
          ; when (or (nil? z-buffer-val) (> z-buffer-val z)) 
          ;  (swap! z-buffer assoc z-buffer-index z)
          (set! (.-fillStyle ctx)
                (str "rgba(" 
                     (cdata/rgb-color (- (int (* (:r point) cl/h)))) "," 
                     (cdata/rgb-color (- (int (* (:g point) cl/h)))) ","
                     (cdata/rgb-color (- (int (* (:r point) (:r point) (- 80))))) ", 255)"))
          (.fillRect ctx x y 1 1))))))

(defn eloop [ctx]
  (rose ctx))

(defn ^:export init []
  (util/init eloop 10))

(defn reset [] 0)
