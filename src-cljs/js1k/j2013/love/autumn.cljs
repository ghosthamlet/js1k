(ns js1k.j2013.love.autumn
  (:require [js1k.util :as util]
            [js1k.common.data :as cdata]
            [js1k.j2013.love.model.autumn :as cl]))

(def LB cl/LB)

(def ^:export app-cfg (js-obj "url" ""))

(defn- leaves [f ctx L]
  (dotimes [s0 703]
    (let [s (- 702 s0)] 
      (if (f (util/usin (+ ((L s) 0) @cl/t)) 0) 
        (let [p0 ((L s) 1)
              q0 (+ 275 (* (util/ucos (+ @cl/t ((L s) 0))) ((L s) 2)))
              u0 (* 99 (- (* @cl/t 9) (rem s 300)))
              u (* u0 (if (< 0 u0) 1 0) (if (not= 0 s) 1 0))
              q (+ q0 u)
              p (+ p0 0.4 (* u (/ (util/usin (/ u 99)) 9)))]

          (set! (.-fillStyle ctx) 
                (str "rgba(" (cdata/rgb-color ((L s) 9)) "," 
                     (cdata/rgb-color ((L s) 10)) "," 
                     (cdata/rgb-color ((L s) 11)) "," 0.8 ")"))
          (.beginPath ctx)
          (.moveTo ctx (+ q ((L s) 3)) (+ p (* ((L s) 4) (util/ucos (/ u 14)))))
          (.lineTo ctx (+ q ((L s) 5)) (+ p (* ((L s) 6) (util/ucos (/ (/ u 14) 14)))))
          (.lineTo ctx (+ q ((L s) 7)) (+ p (* ((L s) 8) (util/ucos (/ (/ (/ u 14) 14) 14)))))
          (.closePath ctx)
          (.fill ctx))))))

(defn- trees [ctx B]
  (let [fx (fn [i] (+ 275 (* (util/ucos (+ ((B i) 0) @cl/t)) ((B i) 2))))] 
    (loop [s 241 
           x (fx s)
           y ((B s) 1)]
      (if (>= s 0)
        (let [w (get (get B s) 3)]
          (if (= 1 (rem s 2))
            (do
              (.beginPath ctx)
              (.moveTo ctx x y)
              (set! (.-lineWidth ctx) w))
            (do 
              (.lineTo ctx x y)
              (.stroke ctx)))
          (recur (dec s) (and (> s 0) (fx (dec s))) (and (> s 0) ((B (dec s)) 1))))))))

(defn- reflect [ctx]
  (loop [y 119]
    (if (> y -1)
      (do
        (.putImageData ctx 
                       (.getImageData 
                         ctx 0 
                         (int (+ (- cl/h 120 y) 
                                 (util/uabs (* (util/usin (+ (* @cl/t 9) (/ y 8))) (/ y 5))))) cl/w 1)
                       (util/uabs (int (* (util/usin (+ (* @cl/t 9) (/ y 4))) (/ y 9))))
                       (+ (- cl/h 120) y))
        (recur (dec y))))))

(defn- text [ctx]
  (set! (.-fillStyle ctx) "#e65")
  (set! (.-font ctx) "120px x")
  (.fillText ctx "\u2766" 600 (- cl/h 150)))

(defn- man [ctx]
  (let [w 1
        ax 353
        ay 460]
    (util/line ctx ax ay (+ ax 2) (+ ay 5) :w w)
    (util/line ctx (+ ax 2) (+ ay 5) (+ ax 2) (+ ay 5 10) :w w)
    (util/line ctx (+ ax 2) (+ ay 5) (- (+ ax 2) 5) (+ ay 5 11) :w w)
    (util/line ctx (+ ax 2) (+ ay 5) (+ ax 2 5) (+ ay 5 11) :w w)
    (util/line ctx (+ ax 2) (+ ay 5 10) (- (+ ax 2) 5) (+ ay 5 10 11) :w w)
    (util/line ctx (+ ax 2) (+ ay 5 10) (+ ax 2 5) (+ ay 5 10 11) :w w)
    
    (util/line ctx 330 462 327 467 :w w)
    (util/line ctx 327 467 329 475 :w w)
    (util/line ctx 327 467 322 472 :w w)
    (util/line ctx 330 465 333 476 :w w)
    (util/line ctx 329 475 322 470 :w w)
    (util/line ctx 329 475 333 470 :w w)
    (util/line ctx 322 470 325 478 :w w)
    (util/line ctx 333 470 333 478 :w w)))

(defn eloop [ctx]
  (swap! cl/t #(+ 0.03 %))
  (set! (.-fillStyle ctx) (.createRadialGradient ctx 720 (- (* @cl/t 40) 99) 1 460 460 900))
  (.addColorStop (.-fillStyle ctx) 0.06 "#fda")
  (.addColorStop (.-fillStyle ctx) 0.07 "#fc4")
  (.addColorStop (.-fillStyle ctx) 0.2 "#e65")
  (.addColorStop (.-fillStyle ctx) 1 "#326")
  (.fillRect ctx 0 0 cl/w cl/h)

  (text ctx)
  (leaves < ctx (first LB))
  (trees ctx (second LB))
  (leaves > ctx (first LB))
  (man ctx)
  (reflect ctx)

  (set! (.-fillStyle ctx) "rgba(0, 0, 99, 0.1)")
  (.fillRect ctx 0 (- cl/h 120) cl/w 120))

(defn ^:export init []
  (util/init eloop 50))

(defn reset []
  (reset! cl/t cl/orig-t)
  (def LB (cl/create-lb)))
