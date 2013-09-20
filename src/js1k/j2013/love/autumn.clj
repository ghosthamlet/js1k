(ns js1k.j2013.love.autumn
  (:use seesaw.core 
        seesaw.graphics
        seesaw.color
        js1k.util
        js1k.j2013.love.model.autumn
        clojure.algo.generic.math-functions)
  (:require [js1k.common.data :as cdata])
  (:import java.awt.Font))

(defn- leaves [f c g L]
  (dotimes [s0 703]
    (let [s (- 702 s0)] 
      (if (f (usin (+ ((L s) 0) @t)) 0) 
        (let [p0 ((L s) 1)
              q0 (+ 275 (* (ucos (+ @t ((L s) 0))) ((L s) 2)))
              u0 (* 99 (- (* @t 9) (rem s 300)))
              u (* u0 (if (< 0 u0) 1 0) (if (not= 0 s) 1 0))
              q (+ q0 u)
              p (+ p0 0.4 (* u (/ (usin (/ u 99)) 9)))]
          (draw g
                (path [] 
                      (move-to (+ q ((L s) 3)) (+ p (* ((L s) 4) (ucos (/ u 14)))))
                      (line-to (+ q ((L s) 5)) (+ p (* ((L s) 6) (ucos (/ (/ u 14) 14)))))
                      (line-to (+ q ((L s) 7)) (+ p (* ((L s) 8) (ucos (/ (/ (/ u 14) 14) 14))))))
                (style :background ;"#00FF00"
                       (color (cdata/rgb-color ((L s) 9)) (cdata/rgb-color ((L s) 10)) (cdata/rgb-color ((L s) 11)) 220)
                       ;(color (bit-not (bit-not (int ((L s) 9)))) (bit-not (bit-not (int ((L s) 10)))) (bit-not (bit-not (int ((L s) 11)))) 0.8)
                       )))))))

(defn- trees [c g B]
  (let [fx (fn [i] (+ 275 (* (ucos (+ ((B i) 0) @t)) ((B i) 2))))] 
    (loop [s 241 
           x (fx s) 
           y ((B s) 1)
           tx x
           ty y
           w (get (get B s) 3)
           tw w]
    (if (> s 0)
      (do
        (if (= 1 (rem s 2))
          (draw g
              (path []
                    (move-to x y) 
                    (line-to tx ty))
              (style :foreground "#000000" :background "#0000FF" :stroke tw :cap :round))
          (draw g
              (path []
                    (move-to x y) 
                    (line-to tx ty))
              (style :foreground "#000000" :background "#0000FF" :stroke w :cap :round)))
        (recur (dec s) tx ty (fx (dec s)) ((B (dec s)) 1) tw (get (get B (dec s)) 3)))))))

(def bi-orig (buffered-image w h))

(defn reflect [c g bi-orig]
  (let [bi-dest (buffered-image w h)
        in-x #(if (< 0 % 100) % 0)
        in-y #(if (< 0 % 100) % 0)]
    (paintall! c (.getGraphics bi-orig))
    (loop [y 120]
      (if (< y 0)
        bi-dest
        (let [arr (int-array w)] 
          (.getRGB bi-orig 0 
                            (in-y (abs (int (+ (- h 120 y) (* (usin (+ (* @t 9) (/ y 8))) (/ y 5)))))) 
                            w 1 arr 0 w)
          (.setRGB bi-dest 
                   (in-x (int (* (usin (+ (* @t 9) (/ y 4))) (/ y 9)))) (in-y (+ (- h 120) y)) 
                   w 1 arr 0 w)
          (recur (dec y)))))))
           

(defn paint [c g]
  (do
    (.setFont g (Font. "Default" Font/BOLD 120))
    (.drawString g "@" 500 (- h 100))
    (draw g
          ; sky
          (rect 0 0 w h) 
          (style :background 
                 (radial-gradient 
                   :center [720, (- (* @t 10) 99)] 
                   :focus [460 100] 
                   :radius 900 
                   :fractions [0.06 0.07 0.2 1] 
                   :colors ["#fda" "#fc4" "#e65" "#326"])))
    (push g
          (leaves < c g (-LB 0))
          (trees c g (-LB 1))
          (leaves > c g (-LB 0))
          ;(.drawImage g (reflect c g bi-orig) nil 0 0)
          (draw g 
                (rect 0 (- h 120) w 120)
                (style :background (color 0 0 99 90))))))

(def ui
  (frame 
    :width w
    :height h
    :title ""
    :content (border-panel
               :center (canvas :id :canvas :paint paint))))

(defn add-behaviors [root]
  (let [c (select root [:#canvas])
        tt (timer (fn [_]
                    (swap! t #(+ 0.03 %))
                    (repaint! c))
                  :delay 0
                  :start? true)]
    (config! c :paint #(paint %1 %2))
    ; Clean up timer on close (useful in repl)
    (listen root :window-closing (fn [_] (.stop tt)))
    root))
    
(defexample []
  (-> ui add-behaviors))

; (defn -main [& args]
;   (invoke-now
;     (-> (make-ui)
;       pack!
;       show!)))
; 
(-main)
; close in repl 
; (.dispose js1k.core/ui)
