(ns js1k.j2013.love.model.autumn)

(def w 1300)
(def h 600)

(def t (atom -0.4))
(def orig-t @t)

(def -b [])
(def -l
  [[1 (- h 115) 1
    -150 0
    0 -15
    150 0
    60 9 0]])

(defn- -LB 
  [p y r l s -l b]
  (let [l2 (loop [l0 -l i (- (* 2 (if (> l 3) 1 0)) 1)]
             (if (< i 0)
               l0
               (recur 
                 (conj (vec l0) 
                       [(+ p (/ (rand) 9))
                        (+ y (- (* (rand) 30) 15))
                        (+ r (- (* (rand) 30) 15))
                        (- (* (rand) 40) 20) (- (* (rand) 40) 20)
                        (- (* (rand) 40) 20) (- (* (rand) 40) 20)
                        (- (* (rand) 40) 20) (- (* (rand) 40) 20)
                        (+ (* (rand) 40 5) 40) (* (rand) 40) 20]) 
                 (dec i))))]
    (if (= l 6)
      [l2 b]
      (vec 
        (let [-p (+ p (- (* (rand) 2) 1) (* s (if (= l 2) 1 0)))
              -y (- y (- (+ (* (rand) 60) 20) (/ r 5.0)))
              -r (+ r (- (* (rand) 25 s) 5)) 
              f (fn [i lb] (vec (lazy-seq (-LB -p -y -r (+ l 1) i (first lb) (second lb)))))]
          (loop [i 2 -lb (f (* i 2) [l2 (conj (conj (vec b) [p y r]) [-p -y -r (- 12 (* l 2))])])]
            (if (< i 1)
              -lb
              (recur (dec i) (f (* (dec i) 2) -lb)))))))))

(defn create-lb []
  (-LB 9 (- h 80) 0 1 0 -l -b))

(def LB (create-lb))
