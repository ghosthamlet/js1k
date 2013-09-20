(ns js1k.j2013.love.autumn
  (:require [js1k.util :as util]
            [js1k.common.data :as cdata]
            [js1k.j2013.love.model.autumn :as cl]))

(util/reg-app "j2013" "love" "autumn" (js-obj "title" "Autumn love"))

(def LB cl/LB)

(defn- leaves [f ctx L]
  (dotimes [s0 703]
    (let [s (- 702 s0)] 
      (if (f (.sin js/Math (+ ((L s) 0) @cl/t)) 0) 
        (let [p0 ((L s) 1)
              q0 (+ 275 (* (.cos js/Math (+ @cl/t ((L s) 0))) ((L s) 2)))
              u0 (* 99 (- (* @cl/t 9) (rem s 300)))
              u (* u0 (if (< 0 u0) 1 0) (if (not= 0 s) 1 0))
              q (+ q0 u)
              p (+ p0 0.4 (* u (/ (.sin js/Math (/ u 99)) 9)))]

          (set! (.-fillStyle ctx) 
                (str "rgba(" (cdata/rgb-color ((L s) 9)) "," 
                     (cdata/rgb-color ((L s) 10)) "," 
                     (cdata/rgb-color ((L s) 11)) "," 0.8 ")"))
          (.beginPath ctx)
          (.moveTo ctx (+ q ((L s) 3)) (+ p (* ((L s) 4) (.cos js/Math (/ u 14)))))
          (.lineTo ctx (+ q ((L s) 5)) (+ p (* ((L s) 6) (.cos js/Math (/ (/ u 14) 14)))))
          (.lineTo ctx (+ q ((L s) 7)) (+ p (* ((L s) 8) (.cos js/Math (/ (/ (/ u 14) 14) 14)))))
          (.closePath ctx)
          (.fill ctx))))))

(defn- trees [ctx B]
  (let [fx (fn [i] (+ 275 (* (.cos js/Math (+ ((B i) 0) @cl/t)) ((B i) 2))))] 
    (loop [s 241 
           x (fx s)
           y ((B s) 1)]
      (if (> s 0)
        (let [w (get (get B s) 3)]
          (if (= 1 (rem s 2))
            (do
              (.beginPath ctx)
              (.moveTo ctx x y)
              (set! (.-lineWidth ctx) w))
            (do 
              ; (.strokeStyle ctx "#0F0")
              (.lineTo ctx x y)
              (.stroke ctx)))
          (recur (dec s) (fx (dec s)) ((B (dec s)) 1)))))))

(defn- reflect [ctx]
  (loop [y 119]
    (if (> y -1)
      (do
        (.putImageData ctx 
                       (.getImageData 
                         ctx 0 
                         (int (+ (- cl/h 120 y) 
                                 (.abs js/Math (* (.sin js/Math (+ (* @cl/t 9) (/ y 8))) (/ y 5))))) cl/w 1)
                       (.abs js/Math (int (* (.sin js/Math (+ (* @cl/t 9) (/ y 4))) (/ y 9))))
                       (+ (- cl/h 120) y))
        (recur (dec y))))))

(defn- text [ctx]
  (set! (.-fillStyle ctx) "#e65")
  (set! (.-font ctx) "120px x")
  (.fillText ctx "Autumn Tree" 300 (- cl/h 150)))

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
  (reflect ctx)

  (set! (.-fillStyle ctx) "rgba(0, 0, 99, 0.1)")
  (.fillRect ctx 0 (- cl/h 120) cl/w 120))

(defn ^:export init []
  (util/init eloop 50))

(defn reset []
  (swap! cl/t (fn [_] cl/orig-t))
  (def LB (cl/create-lb)))
