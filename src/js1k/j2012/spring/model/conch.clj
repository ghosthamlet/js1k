(ns js1k.j2012.spring.model.conch
  (:require [js1k.util :as util]))

(def w 600)
(def h 600)
(def ox (atom 120))
(def oy (atom 120))
(def cr (atom 0))
(def cu (atom 0))
(def cf (atom 0))
(def s-u (atom 0))
(def s-f (atom 0))
(def m (atom 0))
(def c-u (atom 1))
(def c-f (atom 1))
(def n (atom 1))
(def dy (atom 1))
(def dx (atom 2))
(def -m 684)
; grid dot size
(def ds 1)

(declare p calc)

(def pi util/pi)
(def sin util/usin)
(def cos util/ucos)

(defn init [i]
  (let [-f (* (/ i 683) pi)
        -u (* pi 2 (/ (rem i 36) 35))
        r (+ 30 (* (rem i 36) 2))
        e (sin -f)]
    (calc {:x (* r e (cos -u))
           :y (* r e (sin -u))
           :z (* r (cos -f))})))

(defn calc [o]
  (let [-x0 (+ (* (- (:x o)) @s-u) (* (:y o) @c-u))
        -y0 (+ (- (* (- (:x o)) @c-u @c-f) (* (:y o) @s-u @c-f)) (* (:z o) @s-f))
        -z (+ (- (* (- (:x o)) @s-f @c-u) (* (:y o) @s-u @s-f) (* (:z o) @c-f)) 240)
        -y (+ (* -x0 (sin @cr)) (* -y0 (cos @cr)))
        -x (- (* -x0 (cos @cr)) (* -y0 (sin @cr)))]
    (merge o 
           (if (= -z 0)
             {:s-x (+ @ox -x)
              :s-y (+ @oy -y)}
             {:s-x (+ @ox (/ (* 180 -x) -z))
              :s-y (- @oy (/ (* 180 -y) -z))}))))

(defn re-calc []
  (swap! cu #(+ % 0.017))
  (swap! cf #(- % 0.017))
  (swap! cr #(+ % 0.017))
  (swap! s-u (fn [_] (sin @cu)))
  (swap! c-u (fn [_] (cos @cu)))
  (swap! s-f (fn [_] (sin @cf)))
  (swap! c-f (fn [_] (cos @cf)))
  (swap! ox #(+ % @dx))
  (swap! oy #(+ % @dy))
  (if (or (> @ox (- w 90)) (< @ox 90)) (swap! dx #(- %)))
  (if (or (> @oy (- h 90)) (< @oy 90)) (swap! dy #(- %)))
  (dotimes [i -m]
    (swap! p assoc i (#(calc (get % i)) @p))))

(def p 
  (atom (loop [i 0 p0 []]
          (if (= i -m)
            p0
            (recur (inc i) (conj p0 (init i)))))))

(defn valid-coor [i]
  (if (nil? i) 0 (int i)))

