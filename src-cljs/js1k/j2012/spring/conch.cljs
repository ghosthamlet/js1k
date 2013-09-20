(ns js1k.j2012.spring.conch
  (:require [js1k.util :as util]
            [js1k.j2012.spring.model.conch :as cl]))

(util/reg-app "j2012" "spring" "conch" (js-obj "title" "3D conch"))

(defn conch [ctx]
  (swap! cl/m #(+ @cl/n %))
  (if (or (> @cl/m 254) (< @cl/m 1)) (swap! cl/n #(- %)))
  (dotimes [i cl/-m]
    (set! (.-fillStyle ctx) (str "rgba(" @cl/m "," (int (/ i 4)) "," 128 ", 255)"))
    (.fillRect ctx (cl/valid-coor (:s-x (get @cl/p i))) (cl/valid-coor (:s-y (get @cl/p i))) cl/ds cl/ds)))

(defn eloop [ctx]
  (set! (.-fillStyle ctx) "rgba(0, 0, 0, 255)")
  (.fillRect ctx 0 0 cl/w cl/h)
  (conch ctx)
  (cl/re-calc))

(defn ^:export init []
  (util/init eloop 10))

(defn reset [] 0)
