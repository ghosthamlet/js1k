(ns js1k.j2013.love.model.rose
  (:require [js1k.util :as util]))

(def z-buffer (atom {}))
(def size 500)
(def h -250)

(defn surface [a b c]
  (if (> c 60)
    {:x (- (* (util/usin (* a 7)) (+ 13 (/ 5 (+ 0.2 (util/upow (* b 4) 4))))) (* (util/usin b) 50))
     :y (+ 50 (* b size))
     :z (+ 625 (* (util/ucos (* a 7)) (+ 13 (/ 5 (+ 0.2 (util/upow (* b 4) 4))))) (* b 400))
     :r (- (* a 1) (/ b 2))
     :g a}
    (let [a0 (- (* a 2) 1)
          b0 (- (* b 2) 1)]
      (if (< (+ (* a0 a0) (* b0 b0)) 1)
        (condp < c
          37 (let [j (bit-and (int c) 1)
                   n (if (> j 0) 6 4)
                   o (- (+ (/ 0.5 (+ a 0.01)) (* 3 (util/ucos (* b 125)))) (* a 300))
                   w (* b h)]
               {:x (- (+ (* o (util/ucos n)) (* w (util/usin n)) (* j 610)) 390)
                :y (- (+ (- (* o (util/usin n)) (* w (util/ucos n))) 550) (* j 350))
                :z (- (+ 1180 (* 99 (util/ucos (+ b0 a0)))) (* j 300))
                :r (+ (- (+ (- 0.4 (* a 0.1)) (* (util/upow (- 1 (* b0 b0)) (* (- h) 6)) 0.15)) (* a b 0.4)) 
                      (/ (util/ucos (+ a b)) 5) 
                      (* (util/upow (util/ucos (/ (+ (* o (+ a 1)) (if (> b0 0) w (- w))) 25)) 30) 0.1 (- 1 (* b0 b0))))
                :g (- (+ (/ o 1e3) 0.7) (* o w 3e-6))})
          32 (let [c3 (- (* c 1.16) 0.15)
                   o (- (* a 45) 20)
                   w (* b b h)
                   z (+ (* o (util/usin c)) (* w (util/ucos c)) 620)]
               {:x (- (* o (util/ucos c)) (* w (util/usin c)))
                :y (+ 28 (- (* (util/ucos (* b0 0.5)) 99) (* b b b 60) (/ z 2) h))
                :z z
                :r (* b (+ (* b b 0.3) (* 0.15 (util/upow (- 1 (* a0 a0)) 7)) 0.3))
                :g (* b 0.7)})
          (let [o (* a0 (- 2 b) (- 80 (* c 2)))
                w (+ (- 99 (* (util/ucos a0) 120) (* (util/ucos b) (- (- h) (* c 4.9)))) (* 50 (util/ucos (util/upow (- 1 b) 7))) (* c 2))
                z (+ (* o (util/usin c)) (* w (util/ucos c)) 700)]
            {:x (- (* o (util/ucos c)) (* w (util/usin c)))
             :y (+ (- (* b0 99) (* (util/ucos (util/upow b 7)) 50) (/ c 3) (/ z 1.35)) 450)
             :z z
             :r (+ (* (- 1 (/ b 1.2)) 0.9) (* a 0.1))
             :g (+ (/ (util/upow (- 1 b) 20) 4) 0.05)}))))))
